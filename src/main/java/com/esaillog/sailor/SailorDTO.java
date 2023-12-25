package com.esaillog.sailor;

import java.util.List;

public record SailorDTO(String id, String firstName, String lastName, String email, List<String> trainings) {

}
