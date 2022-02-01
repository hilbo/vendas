package com.vendas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vendas.entities.OrderStatus;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long>{

	
}
