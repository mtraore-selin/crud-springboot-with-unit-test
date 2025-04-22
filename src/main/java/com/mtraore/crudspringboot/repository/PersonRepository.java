package com.mtraore.crudspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mtraore.crudspringboot.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
