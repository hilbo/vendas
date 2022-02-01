package com.vendas.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.digester.ArrayStack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.vendas.entities.User;
import com.vendas.repositories.UserRepository;
import com.vendas.services.exceptions.ServiceDataIntegrityViolationException;
import com.vendas.services.exceptions.ServiceNotFoundException;
import com.vendas.tools.validatories.Validator;
import com.vendas.tools.validatories.implementations.ValidateUser;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	private ValidateUser validUser = new ValidateUser();
	@Qualifier
	private Validator validator = validUser;

	public UserService() {
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User findById(Long id) {
		User user;
		try {
			user = userRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new ServiceNotFoundException("Usuário / Cliente não encontrado !");
		}
		return user;
	}

	public List<User> findByName(String name) {
		List<User> users = new ArrayList<>();
		users.addAll(userRepository.findBynameIgnoreCase(name));
		validUser.validList(users);
		return users;
	}

	public List<User> findByEmail(String name) {
		List<User> users = new ArrayList<>();
		users.addAll(userRepository.findByemail(name));
		validUser.validList(users);
		return users;
	}

	public List<User> search(String atribut, String value) {
		return mountSearch(atribut, value);
	}

	public User insert(User user, HttpServletRequest request) {
		validator.valid(user, request);
		validUser.ValidAge(user.getBirthday(), request);
		return userRepository.save(user);
	}

	public void delete(Long id) {
		try {
			userRepository.delete(findById(id));
		} catch (DataIntegrityViolationException e) {
			throw new ServiceDataIntegrityViolationException("Não foi possível excluir o Usuário !!");
		}
	}

	public User update(Long id, User user, HttpServletRequest request) {
		return userRepository.save(userUpdate(id, user, request));
	}

	private User userUpdate(Long id, User user, HttpServletRequest request) {
		User usr;
		usr = findById(id);
		if (user.getName() != null) {
			usr.setName(user.getName());
		}
		if (user.getEmail() != null) {
			usr.setEmail(user.getEmail());
		}
		if (user.getPhone() != null) {
			usr.setPhone(user.getPhone());
		}
		validator.valid(usr, request);
		return usr;
	}

	private List<User> mountSearch(String atribut, String value) {
		List<User> users = new ArrayStack<>();
		if (atribut.contains("email")) {
			users = userRepository.findByEmailContainingIgnoreCase(value);
		}
		if (atribut.contains("name")) {
			users = userRepository.findByNameContainingIgnoreCase(value);
		}
		validUser.validList(users);
		return users;
	}
}
