package com.esaillog.cruise;

import com.esaillog.common.ReferenceMapper;
import com.esaillog.cruise.dtos.UpdateCruiseRequest;
import com.esaillog.port.Port;
import com.esaillog.sailboat.Sailboat;
import com.esaillog.sailor.Sailor;
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
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Represents a cruise, which is a central entity in the domain model.
 * <p>
 * A cruise holds information about its participants, visited ports, the sailboat used,
 * and the skipper. It is the owning side for all its relationships.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class Cruise implements Persistable<UUID> {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                                                                                 .withZone(ZoneId.systemDefault());
    @Id
    @EqualsAndHashCode.Include
    private UUID id;
    private String name;
    @ManyToMany
    @JoinTable(name = "cruise_sailor", joinColumns = @JoinColumn(name = "cruise_id"), inverseJoinColumns = @JoinColumn(name = "sailor_id"))
    private Set<Sailor> participants = new HashSet<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "start_port_id")
    private Port startPort;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "end_port_id")
    private Port endPort;
    @ManyToMany
    @JoinTable(name = "cruise_port", joinColumns = @JoinColumn(name = "cruise_id"), inverseJoinColumns = @JoinColumn(name = "port_id"))
    private Set<Port> visitedPorts = new HashSet<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sailboat_id")
    private Sailboat sailboat;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skipper_id")
    private Sailor skipper;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;
    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;
    @Version
    private Long version;


    /**
     * Constructs a new Cruise with a given name and sailboat.
     *
     * @param name     The name of the cruise.
     * @param sailboat The sailboat assigned to the cruise.
     */
    public Cruise(String name, Sailboat sailboat) {
        this.id = UUID.randomUUID();
        updateName(name);
        setSailboat(sailboat);
    }

    /**
     * Updates the cruise's details from a data transfer object.
     * This method selectively updates fields that are provided (not null).
     * It uses a ReferenceMapper to resolve IDs from the DTO into entity references.
     *
     * @param request The DTO containing new data for the cruise.
     * @param mapper  The mapper to resolve entity references from IDs.
     */
    public void update(UpdateCruiseRequest request, ReferenceMapper mapper) {
        if (StringUtils.hasText(request.name())) {
            this.updateName(request.name());
        }
        if (request.skipperId() != null) {
            this.setSkipper(mapper.toSailor(request.skipperId()));
        }
        if (request.sailboatId() != null) {
            this.setSailboat(mapper.toSailboat(request.sailboatId()));
        }
        if (request.startPortId() != null || request.endPortId() != null) {
            Port newStartPort = (request.startPortId() != null) ? mapper.toPort(request.startPortId()) : this.startPort;
            Port newEndPort = (request.endPortId() != null) ? mapper.toPort(request.endPortId()) : this.endPort;
            this.setStartAndEndPorts(newStartPort, newEndPort);
        }
        // Note: Updating collections like participants and visitedPorts is often handled via dedicated endpoints (e.g., POST
        // /cruises/{id}/participants)
    }

    /**
     * Updates the name of the cruise.
     *
     * @param newName The new name for the cruise.
     * @throws IllegalArgumentException if the new name is null or blank.
     */
    public void updateName(String newName) {
        if (!StringUtils.hasText(newName)) {
            throw new IllegalArgumentException("Name cannot be null or blank.");
        }
        this.name = newName;
    }

    /**
     * Adds a sailor to the cruise participants.
     * This method ensures the bidirectional relationship is consistent by adding the cruise
     * to the sailor's set of cruises.
     *
     * @param sailor The sailor to add as a participant.
     */
    public void addParticipant(Sailor sailor) {
        if (sailor == null || this.participants.contains(sailor)) {
            return;
        }
        this.participants.add(sailor);
        sailor.addCruise(this);
    }

    /**
     * Removes a sailor from the cruise participants.
     * This method ensures the bidirectional relationship is consistent by removing the cruise
     * from the sailor's set of cruises.
     *
     * @param sailor The sailor to remove from participants.
     */
    public void removeParticipant(Sailor sailor) {
        if (sailor == null || !this.participants.contains(sailor)) {
            return;
        }
        this.participants.remove(sailor);
        sailor.removeCruise(this);
    }

    /**
     * Adds a collection of sailors to the cruise participants.
     *
     * @param participants A set of sailors to add.
     */
    public void addParticipants(Set<Sailor> participants) {
        if (participants != null) {
            participants.forEach(this::addParticipant);
        }
    }

    /**
     * Sets the start and end ports for the cruise.
     *
     * @param startPort The port where the cruise begins.
     * @param endPort   The port where the cruise ends.
     */
    public void setStartAndEndPorts(Port startPort, Port endPort) {
        this.startPort = startPort;
        this.endPort = endPort;
    }

    /**
     * Adds a port to the list of visited ports during the cruise.
     * This method ensures the bidirectional relationship is consistent by adding this cruise
     * to the port's set of cruises.
     *
     * @param port The port that was visited.
     */
    public void addVisitedPort(Port port) {
        if (port == null || this.visitedPorts.contains(port)) {
            return;
        }
        this.visitedPorts.add(port);
        port.addCruise(this);
    }

    /**
     * Removes a port from the list of visited ports.
     * This method ensures the bidirectional relationship is consistent by removing this cruise
     * from the port's set of cruises.
     *
     * @param port The port to remove from the visited list.
     */
    public void removeVisitedPort(Port port) {
        if (port == null || !this.visitedPorts.contains(port)) {
            return;
        }
        this.visitedPorts.remove(port);
        port.removeCruise(this);
    }

    /**
     * Adds a collection of ports to the list of visited ports.
     *
     * @param ports A set of ports to add.
     */
    public void addVisitedPorts(Set<Port> ports) {
        if (ports != null) {
            ports.forEach(this::addVisitedPort);
        }
    }

    /**
     * Sets or updates the sailboat for the cruise.
     * This method correctly manages the bidirectional relationship by removing the cruise
     * from the old sailboat (if any) and adding it to the new one.
     *
     * @param newSailboat The new sailboat for the cruise.
     */
    public void setSailboat(Sailboat newSailboat) {
        if (this.sailboat != null) {
            this.sailboat.removeCruise(this);
        }
        this.sailboat = newSailboat;
        if (newSailboat != null) {
            newSailboat.addCruise(this);
        }
    }

    /**
     * Sets or updates the skipper for the cruise.
     * This method correctly manages the bidirectional relationship by removing this cruise
     * from the old skipper's set of skippered cruises (if any) and adding it to the new one.
     *
     * @param newSkipper The new skipper for the cruise.
     */
    public void setSkipper(Sailor newSkipper) {
        if (this.skipper != null) {
            this.skipper.removeSkipperedCruise(this);
        }
        this.skipper = newSkipper;
        if (newSkipper != null) {
            newSkipper.addSkipperedCruise(this);
        }
    }

    /**
     * Checks if the entity is new or has been persisted before.
     * This is used by Spring Data JPA to determine whether to call persist or merge.
     *
     * @return {@code true} if the entity is new (version is null), {@code false} otherwise.
     */
    @Transient
    @Override
    public boolean isNew() {
        return this.version == null;
    }

    /**
     * Returns a string representation of the cruise, including its name, participants, visited ports,
     * sailboat, skipper, and audit dates.
     *
     * @return A string summary of the cruise.
     */
    @Override
    public String toString() {

        String formattedCreatedAt = (createdAt != null) ? DATE_TIME_FORMATTER.format(createdAt) : "null";
        String formattedUpdatedAt = (updatedAt != null) ? DATE_TIME_FORMATTER.format(updatedAt) : "null";

        String sailboatName = (sailboat != null && Hibernate.isInitialized(sailboat)) ? sailboat.getName() : "null or uninitialized";
        String skipperName = (skipper != null && Hibernate.isInitialized(skipper)) ?
                skipper.getFirstName() + " " + skipper.getLastName() : "null or uninitialized";

        return "Cruise{" + "id=" + id + ", name='" + name + '\'' + ", participants=" + formatParticipantsNames(participants)
                + ", visitedPorts=" + formatVisitedPortNames(visitedPorts) + ", sailboat=" + sailboatName + ", skipper=" + skipperName
                + ", createdAt=" + formattedCreatedAt + ", updatedAt=" + formattedUpdatedAt + '}';
    }

    private String formatParticipantsNames(Set<Sailor> sailors) {
        if (!Hibernate.isInitialized(sailors)) {
            return "[uninitialized]";
        }
        if (sailors.isEmpty()) {
            return "[]";
        }
        return sailors.stream()
                      .map(sailor -> sailor.getFirstName() + " " + sailor.getLastName())
                      .collect(Collectors.joining(", ", "[", "]"));
    }

    private String formatVisitedPortNames(Set<Port> ports) {
        if (!Hibernate.isInitialized(ports)) {
            return "[uninitialized]";
        }
        if (ports.isEmpty()) {
            return "[]";
        }
        return ports.stream()
                    .map(Port::getName)
                    .collect(Collectors.joining(", ", "[", "]"));
    }
}
