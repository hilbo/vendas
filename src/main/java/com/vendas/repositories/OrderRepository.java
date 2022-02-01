package com.vendas.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vendas.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

	@Query("from Order o WHERE o.moment between ?1 and ?2")
	public List<Order> findPerDateTime(LocalDateTime momentInitial, LocalDateTime momentFinal );

	@Query("from Order o WHERE o.moment between ?1 and ?2 and lower(o.client.name) like %?3%")
	public List<Order> findPerNameAndDate(LocalDateTime momentInitial, LocalDateTime momentFinal, String clientName );

	@Query("from Order o WHERE o.moment <= ?1 and o.orderStatus = 1")
	public List<Order> clearOrderObsolet(LocalDateTime moment);
	
}
