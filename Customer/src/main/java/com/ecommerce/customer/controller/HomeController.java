package com.ecommerce.customer.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.ProductService;

@Controller
public class HomeController {
	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public String home(Model model, Principal principal, HttpSession session) {
		if (principal != null) {
			session.setAttribute("username", principal.getName());
			Customer customer = customerService.findByUsername(principal.getName());
			if (customer.getShoppingCart() != null) {
				ShoppingCart cart = customer.getShoppingCart();
				session.setAttribute("totalItems", cart.getTotalItems());
			}

		} else {
			session.removeAttribute("username");
		}
		return "home";
	}

	@GetMapping("/")
	public String index(Model model, Principal principal, HttpSession session) {
		if (principal != null) {
			session.setAttribute("username", principal.getName());
			Customer customer = customerService.findByUsername(principal.getName());
			if (customer.getShoppingCart() != null) {
				ShoppingCart cart = customer.getShoppingCart();
				session.setAttribute("totalItems", cart.getTotalItems());
			}
		} else {
			session.removeAttribute("username");
		}
		List<Category> categories = categoryService.findAll();
		List<ProductDto> productDtos = productService.findAll();
		model.addAttribute("categories", categories);
		model.addAttribute("products", productDtos);
		return "index";
	}
}
