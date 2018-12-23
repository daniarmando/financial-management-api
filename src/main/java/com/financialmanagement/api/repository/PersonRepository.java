package com.financialmanagement.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.financialmanagement.api.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
