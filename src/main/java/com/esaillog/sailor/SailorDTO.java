package com.esaillog.sailor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record SailorDTO(String id, @NotBlank(message = "First name is mandatory") String firstName,
                        @NotBlank(message = "Last name is mandatory") String lastName,
                        @Email(message = "Email is mandatory") String email, List<String> trainings) {

}
