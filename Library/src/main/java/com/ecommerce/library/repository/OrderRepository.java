package com.ecommerce.library.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecommerce.library.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	/* Admin */
	@Query("select o from Order o")
	Page<Order> pageOrder(Pageable pageable);

	@Query("select o from Order o where o.customer.username like %?1% or o.orderStatus like %?1%")
	Page<Order> searchOrder(String keyword, Pageable pageable);

	@Query("select o from Order o where o.customer.username like %?1% or o.orderStatus like %?1%")
	List<Order> searchOrdersList(String keyword);
}
