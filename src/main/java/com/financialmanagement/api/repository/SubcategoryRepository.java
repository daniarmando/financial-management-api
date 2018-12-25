package com.financialmanagement.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.financialmanagement.api.model.Subcategory;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {	

	public List<Subcategory> findByParentCategoryIdAndChildCategoryActiveIsTrue(Long parentCategoryId);
	
}
