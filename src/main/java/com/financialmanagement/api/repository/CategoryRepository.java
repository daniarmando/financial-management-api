package com.financialmanagement.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.financialmanagement.api.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	public List<Category> findByActive(Boolean active);
	public List<Category> findBySubcategory(Boolean active);	

}
