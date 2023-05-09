package com.ecommerce.library.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.library.dto.OrderDetailDto;
import com.ecommerce.library.model.OrderDetail;
import com.ecommerce.library.repository.OrderDetailRepository;
import com.ecommerce.library.service.OrderDetailService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

	/* Admin */
	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Override
	public List<OrderDetail> getOrderDetailInOrder(Long orderId) {
		return orderDetailRepository.getOrderDetailInOrder(orderId);
	}

	@Override
	public List<OrderDetailDto> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
