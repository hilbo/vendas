package com.vendas.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.vendas.entities.Category;
import com.vendas.repositories.CategoryRepository;
import com.vendas.services.exceptions.ServiceDataIntegrityViolationException;
import com.vendas.services.exceptions.ServiceNotFoundException;
import com.vendas.tools.validatories.Validator;
import com.vendas.tools.validatories.implementations.ValidateCategory;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	private ValidateCategory validateCategory  = new ValidateCategory();
	private Validator validator = validateCategory;
	
	public CategoryService() {
	}
	
	public List<Category> findAll(){
		return categoryRepository.findAll();
	}
	
	public Category findById(Long id) {
		try {
			categoryRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new ServiceNotFoundException("Categoria não encontrada !!");
		}
		return categoryRepository.findById(id).get();
	}
	
	public List<Category> findByName(String name) {
		List<Category> categories = new ArrayList<>();
		categories.addAll(categoryRepository.searchByName(name));
		validateCategory.validList(categories);
		return categories;
	}
	//
	public List<Category> search(String atribut, String value){
		return mountSearch(atribut, value);
	}
		
	public Category insert(Category category, HttpServletRequest request) {
		validator.valid(category, request);
		return categoryRepository.save(category);
	}
	
	public void delete(Long id) {
		try {
			categoryRepository.delete(findById(id));
		} catch (DataIntegrityViolationException e) {
			throw new ServiceDataIntegrityViolationException("Não foi possível excluir a Categoria !!");
		}
	}
	
	public Category update(Long id, Category category, HttpServletRequest request) {
		return categoryRepository.save(categoryUpdate(id, category, request));
	}
	
	private Category categoryUpdate(Long id, Category category, HttpServletRequest request) {
		Category cat;
			cat = findById(id);
			if (category.getName() != null) {
				cat.setName(category.getName());
			}
		validator.valid(cat, request);
		return cat;
	}
	
	public List<Category> mountSearch(String atribut, String value) {
		List<Category> categories = new ArrayList<>();
		if (atribut.equals("name")) {
			categories.addAll(categoryRepository.findByNameContainingIgnoreCase(value));
		}
		validateCategory.validList(categories);
		validateCategory.validList(categories);
		return categories;
	}
}
