package com.esaillog.sailor;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Sailor {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
}
