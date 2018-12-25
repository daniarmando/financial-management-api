package com.financialmanagement.api.resource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.financialmanagement.api.event.ResourceCreatedEvent;
import com.financialmanagement.api.exceptionhandler.FinancialManagementExceptionHandler.Error;
import com.financialmanagement.api.model.Category;
import com.financialmanagement.api.repository.CategoryRepository;
import com.financialmanagement.api.service.CategoryService;
import com.financialmanagement.api.service.exception.PersonNonExistentOrInactiveException;

@RestController
@RequestMapping("/categories")
public class CategoryResource {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired 
	private MessageSource messageSource;
	
	@GetMapping
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}	
	
	@GetMapping("/active")
	public ResponseEntity<List<Category>> findByActive() {
		List<Category> category = categoryRepository.findByActive(true);
		return !category.isEmpty() ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();
	}
		
	@GetMapping("/inactive")
	public ResponseEntity<List<Category>> findByInactive() {		
		List<Category> category = categoryRepository.findByActive(false);
		return !category.isEmpty() ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();		
	}		
	
	@GetMapping("/{id}") 
	public ResponseEntity<Category> findById(@PathVariable Long id) {
		Optional<Category> category = categoryRepository.findById(id);
		return category.isPresent() ? ResponseEntity.ok(category.get()) : ResponseEntity.notFound().build();
	}	
	
	@PostMapping
	public ResponseEntity<Category> create(@Valid @RequestBody Category category, HttpServletResponse response) {
		Category savedCategory = categoryService.save(category);
		publisher.publishEvent(new ResourceCreatedEvent(this, response, savedCategory.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Category> update(@PathVariable Long id, @Valid @RequestBody Category category) {
		category.setId(id);
		Category savedCategory = categoryService.update(id, category);
		return ResponseEntity.ok(savedCategory);
	}
	
	@PutMapping("/{id}/active")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updatePropertyActive(@PathVariable Long id, @RequestBody Boolean active) {
		categoryService.updatePropertyActive(id, active);
	}				
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		categoryRepository.deleteById(id);
	}
	
	@ExceptionHandler({ PersonNonExistentOrInactiveException.class })
	public ResponseEntity<Object> handlePersonNonExistentOrInactiveException(PersonNonExistentOrInactiveException ex) {
		String userMessage = messageSource.getMessage("person.nonexistent-or-inactive", null, LocaleContextHolder.getLocale());
		String developerMessage = ex.toString();
		List<Error> errors = Arrays.asList(new Error(userMessage, developerMessage));
		return ResponseEntity.badRequest().body(errors);
	}	
	
}
