package com.vendas.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "payment")
public class Payment implements Serializable{
	static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	private LocalDateTime instant;
	
	private Long orderCod;
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "order_id")
	private Order order;
		
	public Payment(){
	}

	public Payment(Long id, LocalDateTime instant, Long orderCod) {
		this.id = id;
		this.instant = instant;
		this.orderCod = orderCod;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public LocalDateTime getInstant() {
		return instant;
	}

	public void setInstant(LocalDateTime instant) {
		this.instant = instant;
	}
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	public void setOrderCod(Long orderCod) {
		this.orderCod = orderCod;
	}

	public Long getOrderCod() {
		return orderCod;
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
		Payment other = (Payment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}

