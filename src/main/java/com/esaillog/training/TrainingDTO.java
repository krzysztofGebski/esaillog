package com.esaillog.training;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record TrainingDTO(String id,
                          @NotBlank(message = "Training name is mandatory") String name, String number,
                          @NotNull(message = "Training type is mandatory") TrainingType type,
                          @NotNull(message = "Certificate should be true of false") boolean hasCertificate,
                          @NotNull(message = "Start date is mandatory") String startDate,
                          @NotNull(message = "End date is mandatory") String endDate,
                          @JsonSetter(nulls = Nulls.AS_EMPTY) List<String> sailors) {

}
