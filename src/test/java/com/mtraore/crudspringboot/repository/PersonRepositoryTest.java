package com.mtraore.crudspringboot.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.mtraore.crudspringboot.entity.Person;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    void shouldGetAllPersons() {
        List<Person> persons = personRepository.findAll();
        assertEquals(3, persons.size());
        assertEquals("John Doe", persons.get(0).getName());
    }

    @Test
    void shouldGetPersonById() {
        Person person = personRepository.findById(1L).get();
        assertEquals("John Doe", person.getName());
        assertEquals("Paris", person.getCity());
        assertEquals("123-456-7890", person.getPhoneNumber());
    }

    @Test
    void shouldSavePerson() {
        Person person = new Person();
        person.setName("Mohamed");
        person.setCity("Mantes-La-Jolie");
        person.setPhoneNumber("142-569-8542");
        Person savedPerson = personRepository.save(person);
        assertNotNull(savedPerson.getId());
        assertEquals("Mohamed", savedPerson.getName());
        assertEquals("Mantes-La-Jolie", savedPerson.getCity());
        assertEquals("142-569-8542", savedPerson.getPhoneNumber());
    }

    @Test
    void shouldUpdatePerson() {
        Person person = personRepository.findById(1L).get();
        person.setCity("Lyon");
        Person savedPerson = personRepository.save(person);
        assertEquals("Lyon", savedPerson.getCity());
    }

    @Test
    void shouldDeletePerson() {
        personRepository.deleteById(3L);
        Optional<Person> person = personRepository.findById(3L);
        assertFalse(person.isPresent());
    }

}