package com.esaillog.sailor;

import com.esaillog.cruise.Cruise;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class Sailor implements Persistable<UUID> {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                                                                                 .withZone(ZoneId.systemDefault());
    @Id
    @EqualsAndHashCode.Include
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    @ManyToMany(mappedBy = "participants", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Cruise> cruises = new HashSet<>();
    @OneToMany(mappedBy = "skipper", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Cruise> skipperedCruises = new HashSet<>();
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;
    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;
    @Version
    private Long version;

    /**
     * Constructs a new Sailor with the given first name, last name, and email.
     * A unique ID is automatically generated.
     *
     * @param firstName the first name of the sailor.
     * @param lastName  the last name of the sailor.
     * @param email     the email address of the sailor.
     */
    public Sailor(String firstName, String lastName, String email) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    /**
     * Gets the set of cruises in which this sailor has participated.
     * The returned set is unmodifiable.
     *
     * @return an unmodifiable set of {@link Cruise} objects.
     */
    public Set<Cruise> getCruises() {
        return Collections.unmodifiableSet(cruises);
    }

    /**
     * Gets the set of cruises that this sailor has skippered.
     * The returned set is unmodifiable.
     *
     * @return an unmodifiable set of {@link Cruise} objects.
     */
    public Set<Cruise> getSkipperedCruises() {
        return Collections.unmodifiableSet(skipperedCruises);
    }

    /**
     * Updates the sailor's first and last name.
     *
     * @param firstName the new first name. Must not be null or blank.
     * @param lastName  the new last name. Must not be null or blank.
     * @throws IllegalArgumentException if the first name or last name is null or blank.
     */
    public void updateName(String firstName, String lastName) {
        if (!StringUtils.hasText(firstName)) {
            throw new IllegalArgumentException("First name cannot be null or blank.");
        }
        if (!StringUtils.hasText(lastName)) {
            throw new IllegalArgumentException("Last name cannot be null or blank.");
        }
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Changes the sailor's email address after validating its format.
     *
     * @param newEmail the new email address. Must not be null or blank and must be a valid format.
     * @throws IllegalArgumentException if the email is null, blank, or has an invalid format.
     */
    public void changeEmail(String newEmail) {
        if (!StringUtils.hasText(newEmail)) {
            throw new IllegalArgumentException("Email cannot be null or blank.");
        }
        try {
            new InternetAddress(newEmail).validate();
        } catch (AddressException ex) {
            throw new IllegalArgumentException("'" + newEmail + "' is not a valid email address.", ex);
        }
        this.email = newEmail;
    }

    /**
     * Adds a cruise to the set of cruises this sailor has participated in.
     * This method maintains the bidirectional relationship between Sailor and Cruise.
     *
     * @param cruise the cruise to add.
     */
    public void addCruise(Cruise cruise) {
        if (cruise == null || this.cruises.contains(cruise)) {
            return; // Guard clause to prevent recursion and nulls
        }
        this.cruises.add(cruise);
        cruise.addParticipant(this);
    }

    /**
     * Removes a cruise from the set of cruises this sailor has participated in.
     * This method maintains the bidirectional relationship between Sailor and Cruise.
     *
     * @param cruise the cruise to remove.
     */
    public void removeCruise(Cruise cruise) {
        if (cruise == null || !this.cruises.contains(cruise)) {
            return; // Guard clause
        }
        this.cruises.remove(cruise);
        cruise.removeParticipant(this);
    }

    /**
     * Adds a cruise to the set of cruises this sailor has skippered.
     * This method maintains the bidirectional relationship by setting this sailor as the skipper on the cruise.
     *
     * @param cruise the cruise to add.
     */
    public void addSkipperedCruise(Cruise cruise) {
        // This method should only manage this side of the relationship.
        // The Cruise entity is responsible for setting the skipper.
        this.skipperedCruises.add(cruise);
    }

    /**
     * Removes a cruise from the set of cruises this sailor has skippered.
     * This method maintains the bidirectional relationship by removing the skipper from the cruise.
     *
     * @param cruise the cruise to remove.
     */
    public void removeSkipperedCruise(Cruise cruise) {
        // This method should only manage this side of the relationship.
        // The Cruise entity is responsible for un-setting the skipper.
        this.skipperedCruises.remove(cruise);
    }

    /**
     * Checks if the entity is new.
     * This is determined by checking if the version field is null.
     *
     * @return {@code true} if the entity is new, {@code false} otherwise.
     */
    @Transient
    @Override
    public boolean isNew() {
        return this.version == null;
    }

    /**
     * Returns a string representation of the Sailor object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {

        String formattedCreatedAt = (createdAt != null) ? DATE_TIME_FORMATTER.format(createdAt) : "null";
        String formattedUpdatedAt = (updatedAt != null) ? DATE_TIME_FORMATTER.format(updatedAt) : "null";

        return "Sailor{" + "id=" + id + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", email='" + email + '\''
                + ", cruises=" + formatCruiseNames(cruises) + ", skipperedCruises=" + formatCruiseNames(skipperedCruises) + ", createdAt="
                + formattedCreatedAt + ", updatedAt=" + formattedUpdatedAt + ", version=" + version + '}';
    }

    /**
     * Formats a set of cruises into a comma-separated string of their names.
     * Helper method for {@link #toString()}.
     *
     * @param cruiseSet the set of cruises to format.
     * @return a string representation of cruise names, e.g., "[Cruise A, Cruise B]".
     */
    private String formatCruiseNames(Set<Cruise> cruiseSet) {
        if (cruiseSet == null) {
            return "null";
        }
        if (cruiseSet.isEmpty()) {
            return "[]";
        }
        return cruiseSet.stream()
                        .map(Cruise::getName)
                        .collect(Collectors.joining(", ", "[", "]"));
    }
}
