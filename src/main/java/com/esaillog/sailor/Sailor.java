package com.esaillog.sailor;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Sailor {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;

}
