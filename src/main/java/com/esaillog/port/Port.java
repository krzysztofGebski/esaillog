package com.esaillog.port;

import com.esaillog.cruise.Cruise;
import com.esaillog.port.dtos.UpdatePortRequest;
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
 * Represents a port that can be a start/end point for a cruise or a visited location.
 * <p>
 * This entity holds information about a specific port and is the non-owning side
 * of the relationship with the {@link Cruise} entity.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class Port implements Persistable<UUID> {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                                                                                 .withZone(ZoneId.systemDefault());
    @Id
    @EqualsAndHashCode.Include
    private UUID id;
    private String name;
    private String description;
    @ManyToMany(mappedBy = "visitedPorts", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Cruise> cruises = new HashSet<>();
    @CreatedDate
    @Column(nullable = false, updatable = false)
    @Getter
    private Instant createdAt;
    @LastModifiedDate
    @Column(nullable = false)
    @Getter
    private Instant updatedAt;
    @Version
    @Getter
    private Long version;

    public Port(String name, String description) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
    }

    public Set<Cruise> getCruises() {
        return Collections.unmodifiableSet(cruises);
    }

    /**
     * Updates the port's details from a data transfer object.
     * It selectively updates fields that are provided (not null or blank).
     *
     * @param request The DTO containing new data for the port.
     */
    public void update(UpdatePortRequest request) {
        if (StringUtils.hasText(request.name())) {
            this.name = request.name();
        }
        if (StringUtils.hasText(request.description())) {
            this.description = request.description();
        }
    }

    public void updateNameAndDescription(String name, String description) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Name cannot be null or blank.");
        }
        if (!StringUtils.hasText(description)) {
            throw new IllegalArgumentException("Description cannot be null or blank.");
        }
        this.name = name;
        this.description = description;
    }

    /**
     * <p>
     * <strong>WARNING:</strong> This is a helper method for maintaining the bidirectional relationship.
     * It only synchronizes the state in memory and <strong>does not</strong> persist the relationship to the database.
     * The relationship is owned by the {@link Cruise} entity.
     * <p>
     * To correctly add a visited port to a cruise, always call {@link Cruise#addVisitedPort(Port)}.
     *
     * @param cruise the cruise to add.
     */
    public void addCruise(Cruise cruise) {
        this.cruises.add(cruise);
    }

    /**
     * <p>
     * <strong>WARNING:</strong> This is a helper method. To correctly remove a visited port,
     * always call {@link Cruise#removeVisitedPort(Port)}.
     *
     * @param cruise the cruise to remove.
     */
    public void removeCruise(Cruise cruise) {
        this.cruises.remove(cruise);
    }

    @Transient
    @Override
    public boolean isNew() {
        return this.version == null;
    }

    @Override
    public String toString() {

        String formattedCreatedAt = (createdAt != null) ? DATE_TIME_FORMATTER.format(createdAt) : "null";
        String formattedUpdatedAt = (updatedAt != null) ? DATE_TIME_FORMATTER.format(updatedAt) : "null";

        return "Port{" + "id=" + id + ", name='" + name + '\'' + ", description='" + description + '\'' + ", cruises="
                + formatCruiseNames(cruises) + ", createdAt=" + formattedCreatedAt + ", updatedAt=" + formattedUpdatedAt + '}';
    }

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
