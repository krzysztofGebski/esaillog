package com.esaillog.sailor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class Sailor {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
}
