package com.ecommerce.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecommerce.library.model.OrderDetail;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

	@Query(value = "select od from OrderDetail od inner join Order o on o.id = od.order.id where o.id = ?1")
	List<OrderDetail> getOrderDetailInOrder(Long orderId);
}
