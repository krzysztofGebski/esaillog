package com.esaillog.sailor;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Sailor {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;

}
