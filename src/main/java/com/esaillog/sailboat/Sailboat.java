package com.esaillog.sailboat;

import com.esaillog.cruise.Cruise;
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
 * Represents a sailboat entity.
 * <p>
 * This class holds information about a specific sailboat, including its identification,
 * technical specifications, and relationships with its home port and cruises.
 * It also manages the lifecycle of its associated {@link Cruise} entities.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class Sailboat implements Persistable<UUID> {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                                                                                 .withZone(ZoneId.systemDefault());
    @Id
    @EqualsAndHashCode.Include
    private UUID id;
    private String name;
    private String registerNumber;
    @Enumerated(EnumType.STRING)
    private SailboatType type;
    private double lengthInFeet;
    private double engineKW;
    @OneToMany(mappedBy = "sailboat", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Cruise> cruises = new HashSet<>();
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;
    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;
    @Version
    private Long version;


    /**
     * Constructs a new Sailboat with essential parameters.
     *
     * @param name           The name of the sailboat.
     * @param registerNumber The official registration number of the sailboat.
     * @param type           The type of the sailboat (e.g., Sloop, Ketch).
     * @param lengthInFeet   The length of the sailboat in feet.
     * @param engineKW       The power of the sailboat's engine in kilowatts.
     */
    public Sailboat(String name, String registerNumber, SailboatType type, double lengthInFeet, double engineKW) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.registerNumber = registerNumber;
        this.type = type;
        this.lengthInFeet = lengthInFeet;
        this.engineKW = engineKW;
    }

    /**
     * Returns an unmodifiable view of the set of cruises associated with this sailboat.
     * This prevents direct modification of the underlying collection from outside the entity,
     * enforcing the use of {@link #addCruise(Cruise)} and {@link #removeCruise(Cruise)} methods.
     *
     * @return An unmodifiable {@link Set} of {@link Cruise} objects.
     */
    public Set<Cruise> getCruises() {
        return Collections.unmodifiableSet(cruises);
    }

    /**
     * Updates multiple parameters of the sailboat in a single operation.
     *
     * @param name           The new name for the sailboat. Cannot be null or blank.
     * @param registerNumber The new registration number. Cannot be null or blank.
     * @param lengthInFeet   The new length in feet. Cannot be negative.
     * @param engineKW       The new engine power in kilowatts. Cannot be negative.
     * @throws IllegalArgumentException if any of the parameters are invalid.
     */
    public void changeSailboatParams(String name, String registerNumber, double lengthInFeet, double engineKW) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Name cannot be null or blank.");
        }
        if (!StringUtils.hasText(registerNumber)) {
            throw new IllegalArgumentException("Register number cannot be null or blank.");
        }
        if (lengthInFeet < 0) {
            throw new IllegalArgumentException("Length cannot be negative.");
        }
        if (engineKW < 0) {
            throw new IllegalArgumentException("Engine KW cannot be negative.");
        }
        this.name = name;
        this.registerNumber = registerNumber;
        this.lengthInFeet = lengthInFeet;
        this.engineKW = engineKW;
    }

    /**
     * Adds a cruise to this sailboat and establishes a bidirectional relationship.
     * <p>
     * <strong>WARNING:</strong> This is a helper method for maintaining the bidirectional relationship.
     * It only synchronizes the state in memory and <strong>does not</strong> persist the relationship to the database.
     * The relationship is owned by the {@link Cruise} entity.
     * <p>
     * To correctly associate a cruise with a sailboat, always call {@link Cruise#setSailboat(Sailboat)}.
     *
     * @param cruise The {@link Cruise} to add. Must not be null.
     */
    public void addCruise(Cruise cruise) {
        this.cruises.add(cruise);
    }

    /**
     * Removes a cruise from this sailboat and breaks the bidirectional relationship.
     * <p>
     * <strong>WARNING:</strong> This is a helper method. To correctly change the sailboat for a cruise,
     * always call {@link Cruise#setSailboat(Sailboat)}.
     *
     * @param cruise The {@link Cruise} to remove.
     */
    public void removeCruise(Cruise cruise) {
        cruises.remove(cruise);
    }

    /**
     * Checks if the entity is new. This implementation is used by Spring Data JPA
     * to determine whether to call {@code persist()} or {@code merge()}.
     * An entity is considered new if its version is {@code null}.
     *
     * @return {@code true} if the entity has not been persisted yet, {@code false} otherwise.
     */
    @Transient
    @Override
    public boolean isNew() {
        return this.version == null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        String formattedCreatedAt = (createdAt != null) ? DATE_TIME_FORMATTER.format(createdAt) : "null";
        String formattedUpdatedAt = (updatedAt != null) ? DATE_TIME_FORMATTER.format(updatedAt) : "null";

        return "Sailboat{" + "id=" + id + ", name='" + name + '\'' + ", registerNumber='" + registerNumber + '\'' + ", type='" + type + '\''
                + ", length=" + lengthInFeet + ", engineKW=" + engineKW + ", cruises=" + formatCruiseNames(cruises) + ", createdAt="
                + formattedCreatedAt + ", updatedAt=" + formattedUpdatedAt + '}';
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
