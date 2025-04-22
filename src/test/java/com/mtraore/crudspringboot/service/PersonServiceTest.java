package com.mtraore.crudspringboot.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.mtraore.crudspringboot.entity.Person;
import com.mtraore.crudspringboot.repository.PersonRepository;
import com.mtraore.crudspringboot.service.PersonService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @Test
    void shouldReturnAllPersons() {
        Person p1 = new Person(1L, "John Doe", "New York", "123-456-7890");
        Person p2 = new Person(2L, "Jane Smith", "Miami", "103-486-7890");

        when(personRepository.findAll()).thenReturn(List.of(p1, p2));

        List<Person> persons = personService.findAll();

        assertThat(persons).hasSize(2).containsExactly(p1, p2);
    }

    @Test
    void shouldReturnPersonById() {
        Person p = new Person(1L, "John Doe", "New York", "123-456-7890");
        when(personRepository.findById(1L)).thenReturn(Optional.of(p));

        Person person = personService.findById(1L);

        assertThat(person).isEqualTo(p);
    }

    @Test
    void shouldReturnPersonOnSaveOrUpdate() {
        Person p = new Person(1L, "John Doe", "New York", "123-456-7890");
        when(personRepository.save(p)).thenReturn(p);

        Person person = personService.saveOrUpdate(p);

        assertThat(person).isEqualTo(p);
    }

    @Test
    void shouldDeletePerson() {
        personService.deleteById(1L);

        verify(personRepository).deleteById(1L);
    }
}