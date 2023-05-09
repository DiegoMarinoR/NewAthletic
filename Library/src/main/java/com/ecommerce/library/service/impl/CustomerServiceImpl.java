package com.ecommerce.library.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.repository.CustomerRepository;
import com.ecommerce.library.repository.RoleRepository;
import com.ecommerce.library.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private RoleRepository repository;

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public CustomerDto save(CustomerDto customerDto) {

		Customer customer = new Customer();
		customer.setFirstName(customerDto.getFirstName());
		customer.setLastName(customerDto.getLastName());
		customer.setUsername(customerDto.getUsername());
		customer.setPassword(customerDto.getPassword());
		customer.setAddress(customerDto.getAddress());
		customer.setCity(customerDto.getCity());
		customer.setCountry(customerDto.getCountry());
		customer.setPhoneNumber(customerDto.getPhoneNumber());
		customer.setRoles(Arrays.asList(repository.findByName("CUSTOMER")));

		Customer customerSave = customerRepository.save(customer);
		return mapperDTO(customerSave);
	}

	@Override
	public Customer findByUsername(String username) {
		return customerRepository.findByUsername(username);
	}

	@Override
	public Customer saveInfor(Customer customer) {
		Customer customer1 = customerRepository.findByUsername(customer.getUsername());
		customer1.setAddress(customer.getAddress());
		customer1.setCity(customer.getCity());
		customer1.setCountry(customer.getCountry());
		customer1.setPhoneNumber(customer.getPhoneNumber());
		return customerRepository.save(customer1);
	}

	private CustomerDto mapperDTO(Customer customer) {
		CustomerDto customerDto = new CustomerDto();
		customerDto.setFirstName(customer.getFirstName());
		customerDto.setLastName(customer.getLastName());
		customerDto.setPassword(customer.getPassword());
		customerDto.setUsername(customer.getUsername());
		return customerDto;
	}

	/* Admin */
	@Override
	public List<Customer> findAll() {
		return customerRepository.findAll();
	}

	private List<CustomerDto> transfer(List<Customer> customers) {
		List<CustomerDto> customerDtoList = new ArrayList<>();
		for (Customer customer : customers) {
			CustomerDto customerDto = new CustomerDto();
			customerDto.setId(customer.getId());
			customerDto.setFirstName(customer.getFirstName());
			customerDto.setLastName(customer.getLastName());
			customerDto.setUsername(customer.getUsername());
			customerDto.setAddress(customer.getAddress());
			customerDto.setCity(customer.getCity());
			customerDto.setPhoneNumber(customer.getPhoneNumber());
			customerDto.setCountry(customer.getCountry());

			customerDtoList.add(customerDto);
		}
		return customerDtoList;
	}
}
