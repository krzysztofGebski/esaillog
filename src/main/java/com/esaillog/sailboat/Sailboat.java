package com.esaillog.sailboat;

import com.esaillog.port.Port;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Sailboat {
    @Id
    private UUID id;
    private String name;
    private String registerNumber;
    private String type;
    @OneToOne
    private Port homePort;
    private double length;
    private double engineKW;
}
