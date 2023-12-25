package com.esaillog.training;

import java.util.List;

public record TrainingDTO(String id, String name, TrainingType type, boolean hasCertificate, String startDate,
        String endDate, List<String> sailors) {

}
