package com.mtraore.crudspringboot.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.mtraore.crudspringboot.controller.PersonController;
import com.mtraore.crudspringboot.entity.Person;
import com.mtraore.crudspringboot.service.PersonService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
class PersonControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private PersonService personService;

        @Test
        void shouldReturnAllPersons() throws Exception {
                Person p1 = new Person(1L, "John Doe", "New York", "123-456-7890");
                Person p2 = new Person(2L, "Jane Smith", "Miami", "103-486-7890");
                when(personService.findAll()).thenReturn(List.of(p1, p2));

                mockMvc.perform(get("/api/persons"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].name").value("John Doe"));
        }

        @Test
        void shouldReturnPersonById() throws Exception {
                Person p = new Person(1L, "John Doe", "New York", "123-456-7890");
                when(personService.findById(1L)).thenReturn(p);

                mockMvc.perform(get("/api/persons/1"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.name").value("John Doe"))
                                .andExpect(jsonPath("$.city").value("New York"))
                                .andExpect(jsonPath("$.phoneNumber").value("123-456-7890"));
        }

        @Test
        void shouldReturnNotFoundWhenPersonDoesNotExist() throws Exception {
                when(personService.findById(99L)).thenReturn(null); // Non-existent ID

                mockMvc.perform(get("/api/persons/99"))
                                .andExpect(status().isNotFound());
        }

        @Test
        void shouldReturnCreatedPerson() throws Exception {
                String json = """
                                {
                                "id": 1,
                                "name": "John Doe",
                                "city": "New York",
                                "phoneNumber": "103-486-7890"
                                }
                                """;
                Person p = new Person(1L, "John Doe", "New York", "103-486-7890");
                when(personService.saveOrUpdate(p)).thenReturn(p);

                mockMvc.perform(post("/api/persons").contentType(MediaType.APPLICATION_JSON).content(json))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.id").value(1));
        }

        @Test
        void shouldReturnUpdatedPerson() throws Exception {
                String json = """
                                {
                                "id": 1,
                                "name": "John Doe",
                                "city": "Los Angeles",
                                "phoneNumber": "103-486-7890"
                                }
                                """;
                Person existingPerson = new Person(1L, "John Doe", "New York", "103-486-7890");
                Person updatedPerson = new Person(1L, "John Doe", "Los Angeles", "103-486-7890");
                when(personService.findById(1L)).thenReturn(existingPerson);
                when(personService.saveOrUpdate(updatedPerson)).thenReturn(updatedPerson);

                mockMvc.perform(put("/api/persons/1").contentType(MediaType.APPLICATION_JSON).content(json))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.city").value("Los Angeles"));
        }

        @Test
        void shouldReturnNotFoundWhenUpdatingNonExistentPerson() throws Exception {
                String json = """
                                {
                                    "id": 99,
                                    "name": "Non Existent",
                                    "city": "Nowhere",
                                    "phoneNumber": "000-000-0000"
                                }
                                """;

                when(personService.findById(99L)).thenReturn(null);

                mockMvc.perform(put("/api/persons/99")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                                .andExpect(status().isNotFound());
        }

        @Test
        void shouldDeletePerson() throws Exception {
                Person p = new Person(1L, "John Doe", "New York", "103-486-7890");
                when(personService.findById(1L)).thenReturn(p);

                mockMvc.perform(delete("/api/persons/1"))
                                .andExpect(status().isOk());
        }

        @Test
        void shouldReturnNotFoundWhenDeletingNonExistentPerson() throws Exception {
                when(personService.findById(99L)).thenReturn(null);

                mockMvc.perform(delete("/api/persons/99"))
                                .andExpect(status().isNotFound());
        }
}