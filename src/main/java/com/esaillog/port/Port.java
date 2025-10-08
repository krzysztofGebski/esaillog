package com.esaillog.port;

import com.esaillog.cruise.Cruise;
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

    public void addCruise(Cruise cruise) {
        if (cruise == null || this.cruises.contains(cruise)) {
            return;
        }
        this.cruises.add(cruise);
        cruise.addVisitedPort(this);
    }

    public void removeCruise(Cruise cruise) {
        if (cruise == null || !this.cruises.contains(cruise)) {
            return;
        }
        this.cruises.remove(cruise);
        cruise.removeVisitedPort(this);
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
