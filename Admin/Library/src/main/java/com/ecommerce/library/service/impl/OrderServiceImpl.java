package com.ecommerce.library.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.library.dto.OrderDto;
import com.ecommerce.library.model.CartItem;
import com.ecommerce.library.model.Order;
import com.ecommerce.library.model.OrderDetail;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.repository.CartItemRepository;
import com.ecommerce.library.repository.OrderDetailRepository;
import com.ecommerce.library.repository.OrderRepository;
import com.ecommerce.library.repository.ShoppingCartRepository;
import com.ecommerce.library.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	/* Admin */
	@Override
	public List<OrderDto> findAll() {
		List<Order> products = orderRepository.findAll();
		List<OrderDto> productDtoList = transfer(products);
		return productDtoList;
	}

	@Override
	public Order update(OrderDto orderDto) {
		try {
			Order order = orderRepository.getById(orderDto.getId());
			order.setDeliveryDate(orderDto.getDeliveryDate());
			order.setTotalPrice(orderDto.getTotalPrice());
			order.setShippingFee(orderDto.getShippingFee());
			order.setOrderStatus(orderDto.getOrderStatus());
			order.setNotes(orderDto.getNotes());
			order.setCustomer(orderDto.getCustomer());
			return orderRepository.save(order);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public OrderDto getById(Long id) {
		Order order = orderRepository.getById(id);
		OrderDto orderDto = new OrderDto();
		orderDto.setId(order.getId());
		orderDto.setOrderDate(order.getOrderDate());
		orderDto.setDeliveryDate(order.getDeliveryDate());
		orderDto.setTotalPrice(order.getTotalPrice());
		orderDto.setShippingFee(order.getShippingFee());
		orderDto.setOrderStatus(order.getOrderStatus());
		orderDto.setNotes(order.getNotes());
		orderDto.setCustomer(order.getCustomer());
		orderDto.setOrderDetailList(order.getOrderDetailList());
		return orderDto;
	}

	private List<OrderDto> transfer(List<Order> orders) {
		List<OrderDto> orderDtoList = new ArrayList<>();
		for (Order order : orders) {
			OrderDto orderDto = new OrderDto();
			orderDto.setId(order.getId());
			orderDto.setOrderDate(order.getOrderDate());
			orderDto.setDeliveryDate(order.getDeliveryDate());
			orderDto.setTotalPrice(order.getTotalPrice());
			orderDto.setShippingFee(order.getShippingFee());
			orderDto.setOrderStatus(order.getOrderStatus());
			orderDto.setNotes(order.getNotes());
			orderDto.setCustomer(order.getCustomer());
			orderDto.setOrderDetailList(order.getOrderDetailList());
			orderDtoList.add(orderDto);
		}
		return orderDtoList;
	}

	/* Customer */

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ShoppingCartRepository cartRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

	@Override
	public void saveOrder(ShoppingCart cart) {
		Order order = new Order();
		order.setOrderStatus("PENDIENTE");
		order.setOrderDate(new Date());
		order.setCustomer(cart.getCustomer());
		order.setTotalPrice(cart.getTotalPrices());
		List<OrderDetail> orderDetailList = new ArrayList<>();
		for (CartItem item : cart.getCartItem()) {
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrder(order);
			orderDetail.setQuantity(item.getQuantity());
			orderDetail.setProduct(item.getProduct());
			orderDetail.setUnitPrice(item.getProduct().getCostPrice());
			orderDetailRepository.save(orderDetail);
			orderDetailList.add(orderDetail);
			cartItemRepository.delete(item);
		}
		order.setOrderDetailList(orderDetailList);
		cart.setCartItem(new HashSet<>());
		cart.setTotalItems(0);
		cart.setTotalPrices(0);
		cartRepository.save(cart);
		orderRepository.save(order);
	}

	@Override
	public void acceptOrder(Long id) {
		Order order = orderRepository.getById(id);
		order.setDeliveryDate(new Date());
		order.setOrderStatus("EN ENVIO");
		orderRepository.save(order);

	}

	@Override
	public void cancelOrder(Long id) {
		orderRepository.deleteById(id);
	}

	@Override
	public Page<OrderDto> pageOrders(int pageNo) {
		Pageable pageable = PageRequest.of(pageNo, 5);
		List<OrderDto> orders = transfer(orderRepository.findAll());
		Page<OrderDto> orderPages = toPage(orders, pageable);
		return orderPages;
	}

	@Override
	public Page<OrderDto> searchOrders(int pageNo, String keyword) {
		Pageable pageable = PageRequest.of(pageNo, 5);
		List<OrderDto> orderDtoList = transfer(orderRepository.searchOrdersList(keyword));
		Page<OrderDto> orders = toPage(orderDtoList, pageable);
		return orders;
	}

	private Page toPage(List<OrderDto> list, Pageable pageable) {
		if (pageable.getOffset() >= list.size()) {
			return Page.empty();
		}
		int startIndex = (int) pageable.getOffset();
		int endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size()) ? list.size()
				: (int) (pageable.getOffset() + pageable.getPageSize());
		List subList = list.subList(startIndex, endIndex);
		return new PageImpl(subList, pageable, list.size());
	}

}
