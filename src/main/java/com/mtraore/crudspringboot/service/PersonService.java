package com.mtraore.crudspringboot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.mtraore.crudspringboot.entity.Person;
import com.mtraore.crudspringboot.repository.PersonRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findById(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    public Person saveOrUpdate(Person person) {
        return personRepository.save(person);
    }

    public void deleteById(Long id) {
        personRepository.deleteById(id);
    }
}
