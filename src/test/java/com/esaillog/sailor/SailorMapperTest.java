package com.esaillog.sailor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import static org.assertj.core.api.Assertions.assertThat;

import com.esaillog.cruise.Cruise;

class SailorMapperTest {
    private final SailorMapper sailorMapper = Mappers.getMapper(SailorMapper.class);

    @Test
    void shouldMapSailorToSailorDto() {
        UUID sailorId = UUID.randomUUID();
        UUID cruiseId = UUID.randomUUID();

        Cruise cruise = new Cruise();
        cruise.setId(cruiseId);

        Sailor sailor = new Sailor();
        sailor.setId(sailorId);
        sailor.setFirstName("Jan");
        sailor.setLastName("Kowalski");
        sailor.setEmail("jan.kowalski@example.com");
        sailor.setCruises(Set.of(cruise));
        sailor.setSkipperedCruises(new HashSet<>());

        SailorDto sailorDto = sailorMapper.toSailorDto(sailor);

        assertThat(sailorDto).isNotNull();
        assertThat(sailorDto.id()).isEqualTo(sailorId.toString());
        assertThat(sailorDto.firstName()).isEqualTo("Jan");
        assertThat(sailorDto.lastName()).isEqualTo("Kowalski");
        assertThat(sailorDto.email()).isEqualTo("jan.kowalski@example.com");

        assertThat(sailorDto.cruisesIds()).isNotNull();
        assertThat(sailorDto.cruisesIds()).hasSize(1);
        assertThat(sailorDto.cruisesIds()).containsExactly(cruiseId);
        assertThat(sailorDto.skipperedCruisesIds()).isNotNull().isEmpty();
    }

    @Test
    void shouldMapSailorDtoToSailor() {
        UUID sailorId = UUID.randomUUID();
        UUID cruiseId1 = UUID.randomUUID();
        UUID cruiseId2 = UUID.randomUUID();

        SailorDto sailorDto = new SailorDto(
                sailorId.toString(),
                "Anna",
                "Nowak",
                "anna.nowak@example.com",
                Set.of(cruiseId1, cruiseId2),
                Set.of(cruiseId1)
        );

        Sailor sailor = sailorMapper.toSailor(sailorDto);

        assertThat(sailor).isNotNull();
        assertThat(sailor.getId()).isEqualTo(sailorId);
        assertThat(sailor.getFirstName()).isEqualTo("Anna");
        assertThat(sailor.getLastName()).isEqualTo("Nowak");
        assertThat(sailor.getEmail()).isEqualTo("anna.nowak@example.com");

        assertThat(sailor.getCruises()).isNotNull().isEmpty();
        assertThat(sailor.getSkipperedCruises()).isNotNull().isEmpty();
    }

    @Test
    void shouldReturnEmptySetWhenCruisesAreNull() {
        Sailor sailor = new Sailor();
        sailor.setId(UUID.randomUUID());
        sailor.setFirstName("Test");
        sailor.setLastName("User");
        sailor.setEmail("test@example.com");
        sailor.setCruises(null); // Ustawiamy null
        sailor.setSkipperedCruises(null); // Ustawiamy null

        SailorDto sailorDto = sailorMapper.toSailorDto(sailor);

        assertThat(sailorDto).isNotNull();
        assertThat(sailorDto.cruisesIds()).isNotNull().isEmpty();
        assertThat(sailorDto.skipperedCruisesIds()).isNotNull().isEmpty();
    }
    
    @Test
    void shouldMapSailorToSailorDtoWithNullId() {
        Sailor sailor = new Sailor();
        sailor.setId(null);
        sailor.setFirstName("Jan");
        sailor.setLastName("Kowalski");
        sailor.setEmail("jan.kowalski@example.com");
        sailor.setCruises(new HashSet<>());
        sailor.setSkipperedCruises(new HashSet<>());

        SailorDto sailorDto = sailorMapper.toSailorDto(sailor);

        assertThat(sailorDto).isNotNull();
        assertThat(sailorDto.id()).isNull();
    }

    @Test
    void shouldMapSailorDtoToSailorWithNullId() {
        SailorDto sailorDto = new SailorDto(
                null,
                "Anna",
                "Nowak",
                "anna.nowak@example.com",
                Set.of(),
                Set.of()
        );

        Sailor sailor = sailorMapper.toSailor(sailorDto);

        assertThat(sailor).isNotNull();
        assertThat(sailor.getId()).isNull();
    }
    
}
