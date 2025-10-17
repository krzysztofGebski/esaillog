package com.esaillog.sailor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data JPA repository for {@link Sailor} entities.
 * Provides standard database operations for the Sailor entity.
 */
@Repository
public interface SailorRepository extends JpaRepository<Sailor, UUID> {
}
