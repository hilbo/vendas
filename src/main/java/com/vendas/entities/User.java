package com.vendas.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Component
@Entity
@Table(name = "tb_user")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	//@NotBlank(message = "name,O Nome não pode ser branco ou nulo !")
	private String name;
	//@NotBlank(message = "email,O email não pode ser branco ou nulo !")
	//@Email(message = "email,Email inválido !")
	private String email;
	//@NotBlank(message = "phone,O Telefone não pode ser branco ou nulo !")
	//@Size(min = 8, max = 8, message = "O telefone deve ter 8 dígitos !")
	private String phone;
	//@NotBlank(message = "password,A senha não pode ser branco ou nulo !")
	private String password;
	private LocalDateTime birthday;
	
	@JsonIgnore
	@OneToMany(mappedBy = "client")
	private List<Order> Orders ;
	
	public User() {
	}

	public User(Long id, String name, String email, String phone, String password, LocalDateTime birthday) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.birthday = birthday;
	}
	
	public User(String name, String email, String phone, LocalDateTime birthday) {
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.birthday = birthday;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
	return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public LocalDateTime getBirthday() {
		
		
		return birthday;
	}

	public void setBirthday(LocalDateTime birthday) {
		this.birthday = birthday;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
