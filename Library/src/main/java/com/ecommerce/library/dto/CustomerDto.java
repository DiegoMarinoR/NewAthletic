package com.ecommerce.library.dto;

import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

	private Long id;

	@Size(min = 3, max = 15, message = "El nombre debe tener entre 3 a 15 carácteres")
	private String firstName;

	@Size(min = 3, max = 15, message = "El apellido debe tener entre 3 a 15 carácteres")
	private String lastName;

	private String username;

	@Size(min = 5, max = 20, message = "La contraseña debe tener entre 5 a 20 carácteres")
	private String password;

	private String repeatPassword;

	@Size(min = 8, max = 10, message = "El teléfono debe tener 9 carácteres")
	private String phoneNumber;

	@Size(min = 10, max = 50, message = "La dirección debe tener entre 10 a 50 carácteres")
	private String address;

	@Size(min = 3, max = 15, message = "La ciudad debe tener entre 3 a 15 carácteres")
	private String city;

	@Size(min = 3, max = 15, message = "El país debe tener entre 3 a 15 carácteres")
	private String country;

}
