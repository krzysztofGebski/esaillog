package com.esaillog;

import com.esaillog.cruise.Cruise;
import com.esaillog.cruise.CruiseRepository;
import com.esaillog.port.Port;
import com.esaillog.sailboat.Sailboat;
import com.esaillog.sailboat.SailboatType;
import com.esaillog.sailor.Sailor;
import com.esaillog.sailor.SailorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final SailorRepository sailorRepository;
    private final CruiseRepository cruiseRepository;


    @Override
    public void run(String... args) {
        Sailor firstSailor = new Sailor("Krzysztof", "Gębski", "krzysztof.gebski@mail.com");
        Sailor secondSailor = new Sailor("Agata", "Betleja", "agata.betleja@mail.com");
        Sailor thirdSailor = new Sailor("Jakub", "Wiewióra", "jakub.wiewiora@mail.com");

        sailorRepository.save(firstSailor);
        sailorRepository.save(secondSailor);
        sailorRepository.save(thirdSailor);

        Sailboat sailboat = new Sailboat("Critter", "POL123", SailboatType.SLOOP, 35, 45);

        Cruise firstCruise = new Cruise("Baltic cruise 2024", sailboat);
        Port startPort = new Port("Gdynia", "Marina Gdynia");
        Port endPort = new Port("Hel", "Marina Hel");


        firstCruise.setSkipper(firstSailor);
        firstCruise.addParticipant(firstSailor);
        firstCruise.addParticipant(secondSailor);
        firstCruise.addParticipant(thirdSailor);
        firstCruise.setStartAndEndPorts(startPort, endPort);
        firstCruise.setSailboat(sailboat);


        cruiseRepository.save(firstCruise);
    }
}
