package com.esaillog.sailboat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SailboatRepository extends JpaRepository<Sailboat, UUID> {
}
