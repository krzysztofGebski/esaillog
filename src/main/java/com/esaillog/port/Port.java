package com.esaillog.port;

import com.esaillog.cruise.Cruise;
import com.esaillog.sailboat.Sailboat;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Port {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;
    @OneToMany(mappedBy = "homePort")
    private Set<Sailboat> sailboats = new HashSet<>();
    @ManyToMany(mappedBy = "visitedPorts")
    private Set<Cruise> cruises = new HashSet<>();
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;
    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    @Override
    public String toString() {
        String sailboatNames = (sailboats != null && Hibernate.isInitialized(sailboats))
                ? sailboats.stream()
                .map(Sailboat::getName)
                .collect(Collectors.joining(", "))
                : "[lazy or uninitialized]";

        String cruiseNames = (cruises != null && Hibernate.isInitialized(cruises))
                ? cruises.stream()
                .map(Cruise::getName)
                .collect(Collectors.joining(", "))
                : "[lazy or uninitialized]";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

        String formattedCreatedAt = (createdAt != null) ? formatter.format(createdAt) : "null";
        String formattedUpdatedAt = (updatedAt != null) ? formatter.format(updatedAt) : "null";

        return "Port{" + "id=" + id + ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", sailboats=[" + sailboatNames + "]" +
                ", cruises=[" + cruiseNames + "]" +
                ", createdAt=" + formattedCreatedAt +
                ", updatedAt=" + formattedUpdatedAt +
                '}';
    }
}
