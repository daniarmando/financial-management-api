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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.financialmanagement.api.event.ResourceCreatedEvent;
import com.financialmanagement.api.exceptionhandler.FinancialManagementExceptionHandler.Error;
import com.financialmanagement.api.model.Subcategory;
import com.financialmanagement.api.repository.SubcategoryRepository;
import com.financialmanagement.api.service.SubcategoryService;
import com.financialmanagement.api.service.exception.CategoryNonExistentOrInactiveException;
import com.financialmanagement.api.service.exception.SubcategoryNonExistentOrInactiveException;

@RestController
@RequestMapping("/subcategories")
public class SubcategoryResource {
	
	@Autowired
	private SubcategoryRepository subcategoryRepository;
	
	@Autowired 
	private SubcategoryService subcategoryService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private MessageSource messageSource;
	
	@PostMapping
	public ResponseEntity<Subcategory> create(@Valid @RequestBody Subcategory subcategory, HttpServletResponse response) {		
		Subcategory savedSubcategory = subcategoryService.save(subcategory);
		publisher.publishEvent(new ResourceCreatedEvent(this, response, savedSubcategory.getId()));;
		return ResponseEntity.status(HttpStatus.CREATED).body(savedSubcategory);				
	}

	@GetMapping()
	public List<Subcategory> search(@RequestParam(required = false) Long parentCategoryId) {
		if (parentCategoryId != null) {
			return subcategoryRepository.findByParentCategoryIdAndChildCategoryActiveIsTrue(parentCategoryId);
		} else {
			return subcategoryRepository.findAll();
		}			
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Subcategory> findById(@PathVariable Long id) {
		Optional<Subcategory> subcategory = subcategoryRepository.findById(id);
		return subcategory.isPresent() ? ResponseEntity.ok(subcategory.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		subcategoryRepository.deleteById(id);
	}	
	
	@ExceptionHandler({ CategoryNonExistentOrInactiveException.class })
	public ResponseEntity<Object> handleCategoryNonExistentOrInactiveException(CategoryNonExistentOrInactiveException ex) {
		String userMessage = messageSource.getMessage("category.nonexistent-or-inactive", null, LocaleContextHolder.getLocale());
		String developerMessage = ex.toString();
		List<Error> errors = Arrays.asList(new Error(userMessage, developerMessage));
		return ResponseEntity.badRequest().body(errors);
	}	
	
	@ExceptionHandler({ SubcategoryNonExistentOrInactiveException.class })
	public ResponseEntity<Object> handleSubcategoryNonExistentOrInactiveException(SubcategoryNonExistentOrInactiveException ex) {
		String userMessage = messageSource.getMessage("subcategory.nonexistent-or-inactive", null, LocaleContextHolder.getLocale());
		String developerMessage = ex.toString();
		List<Error> errors = Arrays.asList(new Error(userMessage, developerMessage));
		return ResponseEntity.badRequest().body(errors);
	}
	
}
