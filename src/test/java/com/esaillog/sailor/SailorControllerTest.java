package com.esaillog.sailor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.esaillog.error.EntityNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@WebMvcTest(SailorController.class)
class SailorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SailorService sailorService;

    @MockitoBean
    private SailorMapper sailorMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private Sailor sailor;
    private SailorDto sailorDto;
    private UUID sailorId;

    @BeforeEach
    void setUp() {
        sailorId = UUID.randomUUID();

        sailor = new Sailor();
        sailor.setId(sailorId);
        sailor.setFirstName("Will");
        sailor.setLastName("Turner");
        sailor.setEmail("will.turner@example.com");

        sailorDto = new SailorDto(
                sailorId.toString(),
                "Will",
                "Turner",
                "will.turner@example.com",
                Collections.emptySet(),
                Collections.emptySet());
    }

    @Test
    void getAll_shouldReturnListOfSailors() throws Exception {
        when(sailorService.findAll()).thenReturn(List.of(sailor));
        when(sailorMapper.toSailorDto(any(Sailor.class))).thenReturn(sailorDto);

        mockMvc.perform(get("/sailors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstName").value("Will"));
    }

    @Test
    void getById_whenSailorExists_shouldReturnSailor() throws Exception {
        when(sailorService.findById(sailorId)).thenReturn(sailor);
        when(sailorMapper.toSailorDto(sailor)).thenReturn(sailorDto);

        mockMvc.perform(get("/sailors/{id}", sailorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sailorId.toString()))
                .andExpect(jsonPath("$.firstName").value("Will"));
    }

    @Test
    void getById_whenSailorDoesNotExist_shouldReturnNotFound() throws Exception {
        UUID nonExistentId = UUID.randomUUID();
        when(sailorService.findById(nonExistentId)).thenThrow(new EntityNotFoundException("Sailor not found"));

        mockMvc.perform(get("/sailors/{id}", nonExistentId))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_shouldCreateSailorAndReturnCreated() throws Exception {
        SailorDto newSailorDto = new SailorDto(null, "New", "Sailor", "new@sailor.com", Set.of(), Set.of());
        Sailor newSailor = new Sailor();
        Sailor savedSailor = new Sailor();
        savedSailor.setId(sailorId);

        SailorDto savedSailorDto = new SailorDto(sailorId.toString(), "New", "Sailor", "new@sailor.com", Set.of(),
                Set.of());

        when(sailorMapper.toSailor(any(SailorDto.class))).thenReturn(newSailor);
        when(sailorService.save(newSailor)).thenReturn(savedSailor);
        when(sailorMapper.toSailorDto(savedSailor)).thenReturn(savedSailorDto);

        String location = ServletUriComponentsBuilder.fromPath("/sailors").path("/{id}").buildAndExpand(sailorId)
                .toUriString();

        mockMvc.perform(post("/sailors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newSailorDto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost" + location))
                .andExpect(jsonPath("$.id").value(sailorId.toString()));
    }

    @Test
    void create_withInvalidData_shouldReturnBadRequest() throws Exception {
        SailorDto invalidDto = new SailorDto(null, "", "Sailor", "bad@request.com", Set.of(), Set.of());

        mockMvc.perform(post("/sailors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_shouldUpdateSailorAndReturnOk() throws Exception {
        SailorDto updateDto = new SailorDto(sailorId.toString(), "Updated", "Name", "updated@name.com", Set.of(),
                Set.of());
        Sailor updatedSailor = new Sailor();
        Sailor savedSailor = new Sailor();

        when(sailorMapper.toSailor(any(SailorDto.class))).thenReturn(updatedSailor);
        when(sailorService.update(sailorId, updatedSailor)).thenReturn(savedSailor);
        when(sailorMapper.toSailorDto(savedSailor)).thenReturn(updateDto);

        mockMvc.perform(put("/sailors/{id}", sailorId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Updated"))
                .andExpect(jsonPath("$.lastName").value("Name"));
    }

    @Test
    void update_whenSailorDoesNotExist_shouldReturnNotFound() throws Exception {
        UUID nonExistentId = UUID.randomUUID();
        SailorDto updateDto = new SailorDto(nonExistentId.toString(), "Non", "Existent", "a@b.com", Set.of(), Set.of());
        Sailor updatedSailor = new Sailor();

        when(sailorMapper.toSailor(any(SailorDto.class))).thenReturn(updatedSailor);
        when(sailorService.update(nonExistentId, updatedSailor))
                .thenThrow(new EntityNotFoundException("Sailor not found"));

        mockMvc.perform(put("/sailors/{id}", nonExistentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_shouldDeleteSailorAndReturnNoContent() throws Exception {
        doNothing().when(sailorService).delete(sailorId);

        mockMvc.perform(delete("/sailors/{id}", sailorId))
                .andExpect(status().isNoContent());
    }
}
