package com.vendas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vendas.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{

	
}
