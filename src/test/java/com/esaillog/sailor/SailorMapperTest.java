package com.esaillog.sailor;

import com.esaillog.cruise.Cruise;
import com.esaillog.cruise.CruiseRepository;
import com.esaillog.error.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SailorMapperTest {

    @Mock
    private CruiseRepository cruiseRepository;

    @InjectMocks
    private SailorMapper sailorMapper;

    private Sailor sailor;
    private SailorDto sailorDto;
    private Cruise cruiseOne;
    private Cruise cruiseTwo;
    private Cruise skipperedCruise;

    @BeforeEach
    void setUp() {
        UUID sailorId = UUID.randomUUID();
        UUID cruiseOneId = UUID.randomUUID();
        UUID cruiseTwoId = UUID.randomUUID();
        UUID skipperedCruiseId = UUID.randomUUID();

        cruiseOne = new Cruise();
        cruiseOne.setId(cruiseOneId);

        cruiseTwo = new Cruise();
        cruiseTwo.setId(cruiseTwoId);

        skipperedCruise = new Cruise();
        skipperedCruise.setId(skipperedCruiseId);

        sailor = new Sailor();
        sailor.setId(sailorId);
        sailor.setFirstName("Jack");
        sailor.setLastName("Sparrow");
        sailor.setEmail("captain@blackpearl.com");
        sailor.setCruises(Set.of(cruiseOne, cruiseTwo));
        sailor.setSkipperedCruises(Set.of(skipperedCruise));

        sailorDto = new SailorDto(
                sailorId.toString(),
                "Jack",
                "Sparrow",
                "captain@blackpearl.com",
                Set.of(cruiseOneId.toString(), cruiseTwoId.toString()),
                Set.of(skipperedCruiseId.toString())
        );
    }

    @Test
    void toSailorDto_shouldMapSailorToSailorDto() {
        SailorDto resultDto = sailorMapper.toSailorDto(sailor);

        assertNotNull(resultDto);
        assertEquals(sailor.getId().toString(), resultDto.id());
        assertEquals(sailor.getFirstName(), resultDto.firstName());
        assertEquals(sailor.getLastName(), resultDto.lastName());
        assertEquals(sailor.getEmail(), resultDto.email());
        assertEquals(2, resultDto.cruisesIDs().size());
        assertTrue(resultDto.cruisesIDs().contains(cruiseOne.getId().toString()));
        assertEquals(1, resultDto.skipperedCruisesIDs().size());
        assertTrue(resultDto.skipperedCruisesIDs().contains(skipperedCruise.getId().toString()));
    }

    @Test
    void toSailor_shouldMapSailorDtoToSailor() {
        Set<UUID> cruiseUuids = sailorDto.cruisesIDs().stream().map(UUID::fromString).collect(Collectors.toSet());
        Set<UUID> skipperedCruiseUuids = sailorDto.skipperedCruisesIDs().stream().map(UUID::fromString).collect(Collectors.toSet());

        when(cruiseRepository.findAllById(cruiseUuids)).thenReturn(List.of(cruiseOne, cruiseTwo));
        when(cruiseRepository.findAllById(skipperedCruiseUuids)).thenReturn(List.of(skipperedCruise));

        Sailor resultSailor = sailorMapper.toSailor(sailorDto);

        assertNotNull(resultSailor);
        assertEquals(sailorDto.id(), resultSailor.getId().toString());
        assertEquals(sailorDto.firstName(), resultSailor.getFirstName());
        assertEquals(2, resultSailor.getCruises().size());
        assertEquals(1, resultSailor.getSkipperedCruises().size());
    }

    @Test
    void toSailor_whenCruiseIdNotFound_shouldThrowEntityNotFoundException() {
        SailorDto dtoWithInvalidId = new SailorDto(
                sailorDto.id(), sailorDto.firstName(), sailorDto.lastName(), sailorDto.email(),
                Set.of(cruiseOne.getId().toString(), UUID.randomUUID().toString()),
                Collections.emptySet()
        );

        Set<UUID> requestedIds = dtoWithInvalidId.cruisesIDs().stream().map(UUID::fromString).collect(Collectors.toSet());
        when(cruiseRepository.findAllById(requestedIds)).thenReturn(List.of(cruiseOne));

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> sailorMapper.toSailor(dtoWithInvalidId)
        );

        assertTrue(exception.getMessage().startsWith("Cruises not found with ids:"));
    }
}
