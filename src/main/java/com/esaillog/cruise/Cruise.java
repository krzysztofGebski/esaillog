package com.esaillog.cruise;

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


    public Cruise(String name, Sailboat sailboat) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.sailboat = sailboat;
    }

    public void updateName(String newName) {
        if (!StringUtils.hasText(newName)) {
            throw new IllegalArgumentException("Name cannot be null or blank.");
        }
        this.name = newName;
    }

    public void addParticipant(Sailor sailor) {
        if (sailor == null || this.participants.contains(sailor)) {
            return; // Guard clause to prevent recursion and nulls
        }
        this.participants.add(sailor);
        sailor.addCruise(this);
    }

    public void removeParticipant(Sailor sailor) {
        if (sailor == null || !this.participants.contains(sailor)) {
            return; // Guard clause
        }
        this.participants.remove(sailor);
        sailor.removeCruise(this);
    }

    public void addParticipants(Set<Sailor> participants) {
        if (participants != null) {
            participants.forEach(this::addParticipant);
        }
    }

    public void setStartAndEndPorts(Port startPort, Port endPort) {
        this.startPort = startPort;
        this.endPort = endPort;
    }

    public void addVisitedPort(Port port) {
        if (port == null || this.visitedPorts.contains(port)) {
            return; // Assuming Port has a similar bidirectional setup
        }
        this.visitedPorts.add(port);
        port.addCruise(this);
    }

    public void removeVisitedPort(Port port) {
        if (port == null || !this.visitedPorts.contains(port)) {
            return; // Assuming Port has a similar bidirectional setup
        }
        this.visitedPorts.remove(port);
        port.removeCruise(this);
    }

    public void addVisitedPorts(Set<Port> ports) {
        if (ports != null) {
            ports.forEach(this::addVisitedPort);
        }
    }

    public void setSailboat(Sailboat newSailboat) {
        if (this.sailboat != null) {
            this.sailboat.removeCruise(this);
        }
        this.sailboat = newSailboat;
        if (newSailboat != null) {
            newSailboat.addCruise(this);
        }
    }

    public void setSkipper(Sailor newSkipper) {
        if (this.skipper != null) {
            this.skipper.removeSkipperedCruise(this);
        }
        this.skipper = newSkipper;
        if (newSkipper != null) {
            newSkipper.addSkipperedCruise(this);
        }
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

        String sailboatName = (sailboat != null && Hibernate.isInitialized(sailboat)) ? sailboat.getName() : "null or uninitialized";
        String skipperName = (skipper != null && Hibernate.isInitialized(skipper)) ?
                skipper.getFirstName() + " " + skipper.getLastName() : "null or uninitialized";

        return "Cruise{" + "id=" + id + ", name='" + name + '\'' + ", participants=" + formatParticipantsNames(participants)
                + ", visitedPorts=" + formatVisitedPortNames(visitedPorts) + ", sailboat=" + sailboatName + ", skipper=" + skipperName
                + ", createdAt=" + formattedCreatedAt + ", updatedAt=" + formattedUpdatedAt + '}';
    }

    private String formatParticipantsNames(Set<Sailor> sailors) {
        if (sailors == null) {
            return "null";
        }
        if (sailors.isEmpty()) {
            return "[]";
        }
        return sailors.stream()
                      .map(sailor -> sailor.getFirstName() + " " + sailor.getLastName())
                      .collect(Collectors.joining(", ", "[", "]"));
    }

    private String formatVisitedPortNames(Set<Port> ports) {
        if (ports == null) {
            return "null";
        }
        if (ports.isEmpty()) {
            return "[]";
        }
        return ports.stream()
                    .map(Port::getName)
                    .collect(Collectors.joining(", ", "[", "]"));
    }
}
