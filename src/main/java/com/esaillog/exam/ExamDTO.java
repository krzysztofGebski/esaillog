package com.esaillog.exam;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ExamDTO(String id,
                      @NotBlank String name, String number,
                      @NotNull(message = "Date is mandatory") String date,
                      @NotNull(message = "Exam type is mandatory") ExamType type,
                      @JsonSetter(nulls = Nulls.AS_EMPTY) List<String> participants) {
}
