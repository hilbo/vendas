package com.vendas.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.vendas.entities.Category;
import com.vendas.entities.Order;
import com.vendas.entities.OrderItem;
import com.vendas.entities.OrderStatus;
import com.vendas.entities.Product;
import com.vendas.entities.User;
import com.vendas.repositories.CategoryRepository;
import com.vendas.repositories.OrderItemRepository;
import com.vendas.repositories.OrderRepository;
import com.vendas.repositories.OrderStatusRepository;
import com.vendas.repositories.ProductRepository;
import com.vendas.repositories.UserRepository;

@Configuration
public class InicialBD implements CommandLineRunner {
		
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private OrderStatusRepository orderStatusRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderItemRepository orderItemRepository;
				
	@Override
	public void run(String... args) throws Exception {

		/*Inicio Lista de orderStatus*/
		orderItemRepository.deleteAll(orderItemRepository.findAll());
		orderRepository.deleteAll(orderRepository.findAll());
		productRepository.deleteAll(productRepository.findAll());
		categoryRepository.deleteAll(categoryRepository.findAll());
		userRepository.deleteAll(userRepository.findAll());
				
		OrderStatus orderStatusOPEN = new OrderStatus(1l, "OPEN");
		OrderStatus orderStatusWAITING_PAYMENT = new OrderStatus(2l, "WAITING_PAYMENT");
		OrderStatus orderStatusPAID = new OrderStatus(3l, "PAID");
		orderStatusRepository.saveAll(Arrays.asList(orderStatusOPEN, orderStatusWAITING_PAYMENT, orderStatusPAID));
				
		/*Inicio Lista de user*/
		userRepository.deleteAll(userRepository.findAll());
		User user = new User(null, "Hilbo Cardeira", "hilbo.cardeira@gmail.com", "946818842", "AB9m22d512", LocalDateTime.parse("1974-01-01T00:01"));
		User user2 = new User(null, "Hilbo Cardeira", "hilbo.cardeira@gmail.com", "946818842", "AB9m22d512", LocalDateTime.parse("1974-01-01T00:01"));
		userRepository.saveAll(Arrays.asList(user, user2));
		//User user02 = new User(null, "User02", "user02@user.com.br", 12345678, "password");
		//ur.save(user02);
		
		/*Inicio Lista de Category*/
		categoryRepository.deleteAll(categoryRepository.findAll());
		Category category = new Category(null, "Caategory01");
		Category category02 = new Category(null, "Caategory02");
		categoryRepository.saveAll (Arrays.asList(category, category02));
		
		/*Inicio Lista de product*/
		productRepository.deleteAll(productRepository.findAll());
		Product product = new Product(null, "Produto01", "DescProd01", 100.0, "UrlProd01");
		product.getCategory().add(category);
		productRepository.save (product);
		product.getCategory().add(category02);
		productRepository.save (product);
		
		/*Inicio Lista de Order*/
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		orderRepository.deleteAll(orderRepository.findAll());
		
		Order order = new Order(null, LocalDateTime.parse("28/12/2021 03:00:00", dtf), user, orderStatusOPEN, null);
		Order order1 = new Order(null, LocalDateTime.parse("28/12/2021 22:00:00", dtf), user, orderStatusPAID, null);
		Order order2 = new Order(null, LocalDateTime.parse("29/12/2021 03:00:00", dtf), user, orderStatusOPEN, null);
		orderRepository.saveAll(Arrays.asList(order, order1, order2));
		
		/*Inicio Lista de OrderItem*/
		OrderItem orderItem = new OrderItem(null, order.getId(), product, 1);
		orderItem.setOrder(order);
		orderItem.setPrice(product.getPrice());
		orderItem.setSubtotal();
		orderItemRepository.save(orderItem);
		
		/*Inicio Lista de Paymant*/
		//Payment payment = new Payment(null, "maneP", LocalDateTime.now(), order);
		//paymentRepository.save(payment);
		
		//order.setPayment(payment);
		//order01.setOrderStatus(oss.orderStatusPAID());
		orderRepository.save(order);
		
	}
}
