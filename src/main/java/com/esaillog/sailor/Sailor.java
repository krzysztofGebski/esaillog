package com.esaillog.sailor;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Sailor {
    @Id
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
}
