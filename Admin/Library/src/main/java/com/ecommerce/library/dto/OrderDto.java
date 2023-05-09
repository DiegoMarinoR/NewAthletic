package com.ecommerce.library.dto;

import java.util.Date;
import java.util.List;

import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.OrderDetail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
	private Long id;
	private Date orderDate;
	private Date deliveryDate;
	@Column(name = "total_price")
	private double totalPrice;
	@Column(name = "shipping_fee")
	private double shippingFee;
	private String orderStatus;
	private String notes;
	private Customer customer;
	private List<OrderDetail> orderDetailList;

}
