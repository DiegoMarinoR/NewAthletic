package com.ecommerce.library.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import lombok.Data;

@Data

@Entity
@Table(name = "customers", uniqueConstraints = @UniqueConstraint(columnNames = { "username", "image", "phone_number" }))
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id")
	private Long id;

	@Size(min = 3, max = 15, message = "First name should have 3-15 characters")
	private String firstName;
	@Size(min = 3, max = 15, message = "Last name should have 3-15 characters")
	private String lastName;
	private String username;
	private String country;
	@Column(name = "phone_number")
	private String phoneNumber;
	private String address;

	private String password;
	@Lob
	@Column(name = "image", columnDefinition = "MEDIUMBLOB")
	private String image;

	@Column(name = "city")
	private String city;

	@OneToOne(mappedBy = "customer")
	private ShoppingCart shoppingCart;
	@OneToMany(mappedBy = "customer")
	private List<Order> orders;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "customers_roles", joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "customer_id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
	private Collection<Role> roles;

	public Customer(Long id,
			@Size(min = 3, max = 15, message = "First name should have 3-15 characters") String firstName,
			@Size(min = 3, max = 15, message = "Last name should have 3-15 characters") String lastName,
			String username, String country, String phoneNumber, String address, String password, String image,
			String city, ShoppingCart shoppingCart, List<Order> orders, Collection<Role> roles) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.country = country;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.password = password;
		this.image = image;
		this.city = city;
		this.shoppingCart = shoppingCart;
		this.orders = orders;
		this.roles = roles;
	}

	public Customer() {
		super();
	}

}
