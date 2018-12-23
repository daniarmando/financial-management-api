package com.financialmanagement.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.financialmanagement.api.model.Person;
import com.financialmanagement.api.repository.PersonRepository;

@Service
public class PersonService {
	
	@Autowired
	private PersonRepository personRepository;
	
	public Person update(Long id, Person person) {
		Person savedPerson = findById(id);
		
		BeanUtils.copyProperties(person, savedPerson, "id");		
		
		return personRepository.save(savedPerson);
	}	
	
	public void updatePropertyActive(Long id, Boolean active) {
		Person savedPerson = findById(id);
		savedPerson.setActive(active);
		personRepository.save(savedPerson);
	}
		
	public Person findById(Long id) {
		Optional<Person> savedPerson = personRepository.findById(id);
		if (!savedPerson.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		
		return savedPerson.get();
	}
		
}
