package com.vendas.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.vendas.entities.Category;
import com.vendas.entities.Product;
import com.vendas.repositories.ProductRepository;
import com.vendas.services.exceptions.ServiceDataIntegrityViolationException;
import com.vendas.services.exceptions.ServiceNotFoundException;
import com.vendas.tools.validatories.Validator;
import com.vendas.tools.validatories.implementations.ValidateProduct;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryService categoryService;
	private ValidateProduct validProduct = new ValidateProduct();
	@Qualifier
	private Validator validator = validProduct;
			
	public ProductService() {
	}

	public List<Product> findAll() {
		return productRepository.findAll();
	}

	public Product findById(Long id) {
		try {
			productRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new ServiceNotFoundException("Produto não encontrado !");
		}
		return productRepository.findById(id).get();
	}

	public List<Product> findByName(String name) {
		List<Product> producties = new ArrayList<>();
		producties.addAll(productRepository.findBynameIgnoreCase(name));
		validProduct.validList(producties);
		return producties;
	}
	
	public List<Product> search(String atribut, String value){
		return mountSearch(atribut, value);
	}
	
	public Product insert(Product product, HttpServletRequest request) {
		validator.valid(product, request);
		remountProductCategory(product);
		return productRepository.save(product);
	}

	public void delete(Long id) {
		try {
			productRepository.delete(findById(id));
		} catch (DataIntegrityViolationException e) {
			throw new ServiceDataIntegrityViolationException("Não foi possível excluir a Categoria !!");
		}
	}
	
	public Product update(Long id, Product product, HttpServletRequest request) {
		return productRepository.save(productUpdate(id, product, request));
	}

	public Product productUpdate(Long id, Product product, HttpServletRequest request) {
		Product productNew = findById(id);
		if (product.getName() != null) {
			productNew.setName(product.getName());
		}
		if (product.getDescription() != null) {
			productNew.setDescription(product.getDescription());
		}
		if (product.getPrice() != null) {
			productNew.setPrice(product.getPrice());
		}
		validator.valid(productNew, request);
		productNew.getCategory().clear();
		productNew.getCategory().addAll(remountProductCategory(product).getCategory());
		return productNew;
	}
	
	public Product remountProductCategory(Product product) {
		Product productNew = product;
		Set<Category> categories = new HashSet<>();
		for (Category category : product.getCategory()) {
			categories.add(categoryService.findById(category.getId()));
		}
		productNew.getCategory().clear();
		productNew.getCategory().addAll(categories);
		return productNew;
	}
	
	public List<Product> mountSearch(String atribut, String value) {
		List<Product> producties = new ArrayList<>();
		if (atribut.equals("name")) {
			producties.addAll(productRepository.searchByName(value));
		}
		if (atribut.equals("description")) {
			producties.addAll(productRepository.findByDescriptionContainingIgnoreCase(value));
		}
		validProduct.validList(producties);
		return producties;
	}
}
