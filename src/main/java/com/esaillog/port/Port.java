package com.esaillog.port;

import com.esaillog.cruise.Cruise;
import com.esaillog.sailboat.Sailboat;
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
public class Port implements Persistable<UUID> {
    @Id
    @EqualsAndHashCode.Include
    @Getter
    private UUID id;
    @Getter
    private String name;
    @Getter
    private String description;
    @OneToMany(mappedBy = "homePort", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Sailboat> sailboats = new HashSet<>();
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

    public Set<Sailboat> getSailboats() {
        return Collections.unmodifiableSet(sailboats);
    }

    public Set<Cruise> getCruises() {
        return Collections.unmodifiableSet(cruises);
    }

    public void updateNameAndDescription(String name, String description) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Name cannot be null or blank.");
        }
        if (!StringUtils.hasText(description)) {
            throw new IllegalArgumentException("Description cannot be null or blank.");
        }
    }

    public void addSailboat(Sailboat sailboat) {
        sailboats.add(sailboat);
        sailboat.setHomePort(this);
    }

    public void removeSailboat(Sailboat sailboat) {
        sailboats.remove(sailboat);
        sailboat.setHomePort(null);
    }

    public void addCruise(Cruise cruise) {
        cruises.add(cruise);
        cruise.getVisitedPorts()
              .add(this);
    }

    public void removeCruise(Cruise cruise) {
        cruises.remove(cruise);
        cruise.getVisitedPorts()
              .remove(this);
    }

    @Transient
    @Override
    public boolean isNew() {
        return this.version == null;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                                                       .withZone(ZoneId.systemDefault());

        String formattedCreatedAt = (createdAt != null) ? formatter.format(createdAt) : "null";
        String formattedUpdatedAt = (updatedAt != null) ? formatter.format(updatedAt) : "null";

        return "Port{" + "id=" + id + ", name='" + name + '\'' + ", description='" + description + '\'' + ", sailboats=" + formatSailboatNames(sailboats) +
                ", cruises=" + formatCruiseNames(cruises) + ", createdAt=" + formattedCreatedAt + ", updatedAt=" + formattedUpdatedAt + '}';
    }

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

    private String formatSailboatNames(Set<Sailboat> sailboatSet) {
        if (sailboatSet == null) {
            return "null";
        }
        if (sailboatSet.isEmpty()) {
            return "[]";
        }
        return sailboatSet.stream()
                          .map(Sailboat::getName)
                          .collect(Collectors.joining(", ", "[", "]"));
    }
}
