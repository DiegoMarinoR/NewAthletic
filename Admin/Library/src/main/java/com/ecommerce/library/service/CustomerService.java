package com.ecommerce.library.service;

import java.util.List;

import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.model.Customer;

public interface CustomerService {

	/* Admin */
	List<Customer> findAll();

	/* Customer */
	CustomerDto save(CustomerDto customerDto);

	Customer findByUsername(String username);

	Customer saveInfor(Customer customer);
}
