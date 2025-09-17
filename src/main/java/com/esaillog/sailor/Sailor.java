package com.esaillog.sailor;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.esaillog.cruise.Cruise;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Sailor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    @ManyToMany(mappedBy = "participants")
    private Set<Cruise> cruises = new HashSet<>();
    @OneToMany(mappedBy = "skipper")
    private Set<Cruise> skipperedCruises = new HashSet<>();
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;
    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;


    @Override
    public String toString() {
        String cruiseNames = (cruises != null && Hibernate.isInitialized(cruises))
                ? cruises.stream()
                .map(Cruise::getName)
                .collect(Collectors.joining(", "))
                : "[uninitialized]";

        String skipperedCruiseNames = (skipperedCruises != null && Hibernate.isInitialized(skipperedCruises))
                ? skipperedCruises.stream()
                .map(Cruise::getName)
                .collect(Collectors.joining(", "))
                : "[uninitialized]";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

        String formattedCreatedAt = (createdAt != null) ? formatter.format(createdAt) : "null";
        String formattedUpdatedAt = (updatedAt != null) ? formatter.format(updatedAt) : "null";

        return "Sailor{" + "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", cruises=" + cruiseNames +
                ", skipperedCruises=" + skipperedCruiseNames +
                ", createdAt=" + formattedCreatedAt +
                ", updatedAt=" + formattedUpdatedAt +
                '}';
    }
}
