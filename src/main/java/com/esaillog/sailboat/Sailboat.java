package com.esaillog.sailboat;

import com.esaillog.cruise.Cruise;
import com.esaillog.port.Port;

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
public class Sailboat {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String registerNumber;
    @Enumerated(EnumType.STRING)
    private SailboatType type;
    @ManyToOne
    @JoinColumn(name = "port_id")
    private Port homePort;
    private double length;
    private double engineKW;
    @OneToMany(mappedBy = "sailboat")
    private Set<Cruise> cruises = new HashSet<>();
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;
    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    @Override
    public String toString() {
        String homePortName = (homePort != null && Hibernate.isInitialized(homePort))
                ? homePort.getName()
                : "null or uninitialized";

        String cruiseNames = (cruises != null && Hibernate.isInitialized(cruises))
                ? cruises.stream()
                .map(Cruise::getName)
                .collect(Collectors.joining(", "))
                : "[lazy or uninitialized]";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

        String formattedCreatedAt = (createdAt != null) ? formatter.format(createdAt) : "null";
        String formattedUpdatedAt = (updatedAt != null) ? formatter.format(updatedAt) : "null";

        return "Sailboat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", registerNumber='" + registerNumber + '\'' +
                ", type='" + type + '\'' +
                ", homePort=" + homePortName +
                ", length=" + length +
                ", engineKW=" + engineKW +
                ", cruises=[" + cruiseNames + "]" +
                ", createdAt=" + formattedCreatedAt +
                ", updatedAt=" + formattedUpdatedAt +
                '}';
    }
}
