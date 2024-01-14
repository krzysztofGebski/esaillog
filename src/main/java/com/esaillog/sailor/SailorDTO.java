package com.esaillog.sailor;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record SailorDTO(String id, @NotBlank(message = "First name is mandatory") String firstName,
                        @NotBlank(message = "Last name is mandatory") String lastName,
                        @Email(message = "Email is mandatory") String email,
                        @JsonSetter(nulls = Nulls.AS_EMPTY)
                        List<String> trainings) {
}
