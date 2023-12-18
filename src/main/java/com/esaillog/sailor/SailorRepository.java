package com.esaillog.sailor;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SailorRepository extends JpaRepository<Sailor, UUID> {

}
