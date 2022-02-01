package com.vendas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vendas.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

	
}
