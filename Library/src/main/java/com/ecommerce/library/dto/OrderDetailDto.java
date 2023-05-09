package com.ecommerce.library.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto {
	private Long id;
	private Date quantity;
	private double totalPrice;
	private double unitPrice;
	private String orderId;
	private String productId;

}