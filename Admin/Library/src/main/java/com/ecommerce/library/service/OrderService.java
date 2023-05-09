package com.ecommerce.library.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ecommerce.library.dto.OrderDto;
import com.ecommerce.library.model.Order;
import com.ecommerce.library.model.ShoppingCart;

public interface OrderService {

	/* Admin */
	List<OrderDto> findAll();

	Order update(OrderDto orderDto);

	OrderDto getById(Long id);

	Page<OrderDto> pageOrders(int pageNo);

	Page<OrderDto> searchOrders(int pageNo, String keyword);

	/* Customer */
	void saveOrder(ShoppingCart cart);

	void acceptOrder(Long id);

	void cancelOrder(Long id);
}
