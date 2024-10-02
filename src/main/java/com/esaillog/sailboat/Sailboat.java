package com.esaillog.sailboat;

import com.esaillog.port.Port;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Sailboat {
    private UUID id;
    private String name;
    private String registerNumber;
    private String type;
    private Port homePort;
    private double length;
    private double engineKW;
}
