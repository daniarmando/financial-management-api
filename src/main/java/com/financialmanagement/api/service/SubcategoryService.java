package com.financialmanagement.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.financialmanagement.api.model.Category;
import com.financialmanagement.api.model.Subcategory;
import com.financialmanagement.api.repository.CategoryRepository;
import com.financialmanagement.api.repository.SubcategoryRepository;
import com.financialmanagement.api.service.exception.CategoryNonExistentOrInactiveException;
import com.financialmanagement.api.service.exception.SubcategoryNonExistentOrInactiveException;

@Service
public class SubcategoryService {
	
	@Autowired
	private SubcategoryRepository subcategoryRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public Subcategory save(Subcategory subcategory) {
		validateParentCategory(subcategory.getParentCategory().getId());
		validateChildCategory(subcategory.getChildCategory().getId());
		return subcategoryRepository.save(subcategory);
	}
	
	private void validateParentCategory(Long parentCategoryId) {
		Optional<Category> category = null;
		
		if (parentCategoryId != null) {
			category = categoryRepository.findById(parentCategoryId);
		}
		
		if (!category.isPresent() || category.get().isInactive()) {
			throw new CategoryNonExistentOrInactiveException();
		}
	}
	
	private void validateChildCategory(Long childCategoryId) {
		Optional<Category> category = null;
		
		if (childCategoryId != null) {
			category = categoryRepository.findById(childCategoryId);
		}
		
		if (!category.isPresent() || category.get().isInactive()) {
			throw new SubcategoryNonExistentOrInactiveException();
		}
	}
		
}
