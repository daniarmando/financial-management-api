package com.financialmanagement.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.financialmanagement.api.model.Category;
import com.financialmanagement.api.model.Person;
import com.financialmanagement.api.repository.CategoryRepository;
import com.financialmanagement.api.repository.PersonRepository;
import com.financialmanagement.api.service.exception.PersonNonExistentOrInactiveException;

@Service
public class CategoryService {
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	PersonRepository personRepository;
	
	public Category save(Category category) {
		validateCategory(category);
		return categoryRepository.save(category);
	}		
	
	public Category update(Long id, Category category) {
		Category savedCategory = findById(id);
		
		BeanUtils.copyProperties(category, savedCategory, "id");
		
		return categoryRepository.save(savedCategory);
	}
		
	public void updatePropertyActive(Long id, Boolean active) {
		Category savedCategory = findById(id);
		savedCategory.setActive(active);
		categoryRepository.save(savedCategory);
	}
	
	public Category findById(Long id) {
		Optional<Category> savedCategory = categoryRepository.findById(id);
		if (!savedCategory.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return savedCategory.get();
	}
	
	private void validateCategory(Category category) {
		Optional<Person> person = null;
		
		if (category.getPerson().getId() != null) {
			person = personRepository.findById(category.getPerson().getId());
		}
		
		if (!person.isPresent() || person.get().isInactive()) {
			throw new PersonNonExistentOrInactiveException();
		}
	}	
		
}
