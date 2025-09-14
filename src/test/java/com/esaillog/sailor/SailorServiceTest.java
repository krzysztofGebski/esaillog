package com.esaillog.sailor;

import com.esaillog.error.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SailorServiceTest {

    @Mock
    private SailorRepository sailorRepository;

    @InjectMocks
    private SailorService sailorService;

    private Sailor sailor;
    private UUID sailorId;

    @BeforeEach
    void setUp() {
        sailorId = UUID.randomUUID();
        sailor = new Sailor();
        sailor.setId(sailorId);
        sailor.setFirstName("John");
        sailor.setLastName("Doe");
        sailor.setEmail("john.doe@example.com");
    }

    @Test
    void findAll_shouldReturnListOfSailors() {
        when(sailorRepository.findAll()).thenReturn(List.of(sailor));

        List<Sailor> sailors = sailorService.findAll();

        assertFalse(sailors.isEmpty());
        assertEquals(1, sailors.size());
        assertEquals("John", sailors.get(0).getFirstName());
        verify(sailorRepository, times(1)).findAll();
    }

    @Test
    void findById_whenSailorExists_shouldReturnSailor() {
        when(sailorRepository.findById(sailorId)).thenReturn(Optional.of(sailor));

        Sailor foundSailor = sailorService.findById(sailorId);

        assertNotNull(foundSailor);
        assertEquals(sailorId, foundSailor.getId());
        verify(sailorRepository, times(1)).findById(sailorId);
    }

    @Test
    void findById_whenSailorDoesNotExist_shouldThrowEntityNotFoundException() {
        UUID nonExistentId = UUID.randomUUID();
        when(sailorRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> sailorService.findById(nonExistentId));
        verify(sailorRepository, times(1)).findById(nonExistentId);
    }

    @Test
    void save_shouldReturnSavedSailor() {
        when(sailorRepository.save(any(Sailor.class))).thenReturn(sailor);

        Sailor savedSailor = sailorService.save(new Sailor());

        assertNotNull(savedSailor);
        assertEquals("John", savedSailor.getFirstName());
        verify(sailorRepository, times(1)).save(any(Sailor.class));
    }

    @Test
    void update_shouldUpdateAndReturnSailor() {
        Sailor updatedDetails = new Sailor();
        updatedDetails.setFirstName("Jane");
        updatedDetails.setLastName("Smith");

        when(sailorRepository.findById(sailorId)).thenReturn(Optional.of(sailor));
        when(sailorRepository.save(any(Sailor.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Sailor result = sailorService.update(sailorId, updatedDetails);

        assertNotNull(result);
        assertEquals("Jane", result.getFirstName());
        assertEquals("Smith", result.getLastName());
        verify(sailorRepository, times(1)).findById(sailorId);
        verify(sailorRepository, times(1)).save(any(Sailor.class));
    }
}
