package com.esaillog.port;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Port {
    private UUID id;
    private String name;
    private String description;
}
