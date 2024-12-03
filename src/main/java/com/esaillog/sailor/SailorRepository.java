package com.esaillog.sailor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SailorRepository extends JpaRepository<Sailor, UUID> {
}
