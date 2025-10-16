package com.esaillog.sailor;

import com.esaillog.cruise.Cruise;
import com.esaillog.sailor.dtos.UpdateSailorRequest;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
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

/**
 * Represents a sailor who can participate in or skipper a cruise.
 * <p>
 * This entity holds personal information about the sailor and maintains relationships
 * to the cruises they are associated with. It is the non-owning side of the relationships.
 */
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
     * Updates the sailor's details from a data transfer object.
     * It selectively updates fields that are provided (not null or blank).
     * This method centralizes the update logic and ensures validation rules are applied.
     *
     * @param request The DTO containing new data for the sailor.
     */
    public void update(UpdateSailorRequest request) {
        if (StringUtils.hasText(request.firstName())) {
            this.firstName = request.firstName();
        }
        if (StringUtils.hasText(request.lastName())) {
            this.lastName = request.lastName();
        }
        if (StringUtils.hasText(request.email())) {
            this.changeEmail(request.email());
        }
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
     * <p>
     * <strong>WARNING:</strong> This is a helper method for maintaining the bidirectional relationship.
     * It only synchronizes the state in memory and <strong>does not</strong> persist the relationship to the database.
     * The relationship is owned by the {@link Cruise} entity.
     * <p>
     * To correctly add a participant to a cruise, always call {@link Cruise#addParticipant(Sailor)}.
     *
     * @param cruise the cruise to add.
     */
    public void addCruise(Cruise cruise) {
        this.cruises.add(cruise);
    }

    /**
     * Removes a cruise from the set of cruises this sailor has participated in.
     * <p>
     * <strong>WARNING:</strong> This is a helper method. To correctly remove a participant,
     * always call {@link Cruise#removeParticipant(Sailor)}.
     *
     * @param cruise the cruise to remove.
     */
    public void removeCruise(Cruise cruise) {
        this.cruises.remove(cruise);
    }

    /**
     * Adds a cruise to the set of cruises this sailor has skippered.
     * <p>
     * <strong>WARNING:</strong> This is a helper method. To correctly set a skipper,
     * always call {@link Cruise#setSkipper(Sailor)}.
     *
     * @param cruise the cruise to add.
     */
    public void addSkipperedCruise(Cruise cruise) {
        this.skipperedCruises.add(cruise);
    }

    /**
     * Removes a cruise from the set of cruises this sailor has skippered.
     * <p>
     * <strong>WARNING:</strong> This is a helper method. To correctly change or remove a skipper,
     * always call {@link Cruise#setSkipper(Sailor)}.
     *
     * @param cruise the cruise to remove.
     */
    public void removeSkipperedCruise(Cruise cruise) {
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
        if (!Hibernate.isInitialized(cruiseSet)) {
            return "[uninitialized]";
        }
        if (cruiseSet.isEmpty()) {
            return "[]";
        }
        return cruiseSet.stream()
                        .map(Cruise::getName)
                        .collect(Collectors.joining(", ", "[", "]"));
    }
}
