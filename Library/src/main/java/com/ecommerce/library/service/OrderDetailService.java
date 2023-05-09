package com.ecommerce.library.service;

import java.util.List;

import com.ecommerce.library.dto.OrderDetailDto;
import com.ecommerce.library.model.OrderDetail;

public interface OrderDetailService {

	/* Admin */
	List<OrderDetailDto> findAll();

	List<OrderDetail> getOrderDetailInOrder(Long orderId);

}
