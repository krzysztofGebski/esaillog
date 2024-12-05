package com.esaillog.cruise;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CruiseRepository extends JpaRepository<Cruise, UUID> {
}
