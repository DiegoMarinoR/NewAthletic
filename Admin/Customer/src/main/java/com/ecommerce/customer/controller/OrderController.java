package com.ecommerce.customer.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.library.dto.OrderDto;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.Order;
import com.ecommerce.library.model.OrderDetail;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.OrderDetailService;
import com.ecommerce.library.service.OrderService;

@Controller
public class OrderController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderDetailService orderDetailService;

	@GetMapping("/check-out")
	public String checkout(Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/login";
		}
		String username = principal.getName();
		Customer customer = customerService.findByUsername(username);
		if (customer.getPhoneNumber().trim().isEmpty() || customer.getAddress().trim().isEmpty()
				|| customer.getCity().trim().isEmpty() || customer.getCountry().trim().isEmpty()) {

			model.addAttribute("customer", customer);
			model.addAttribute("error", "You must fill the information after checkout!");
			return "account";
		} else {
			model.addAttribute("customer", customer);
			ShoppingCart cart = customer.getShoppingCart();
			model.addAttribute("cart", cart);
		}
		return "checkout";
	}

	@GetMapping("/order")
	public String order(Principal principal, Model model) {
		if (principal == null) {
			return "redirect:/login";
		}
		String username = principal.getName();
		Customer customer = customerService.findByUsername(username);
		List<Order> orderList = customer.getOrders();
		model.addAttribute("orders", orderList);
		return "order";
	}

	@GetMapping("/save-order")
	public String saveOrder(Principal principal) {
		if (principal == null) {
			return "redirect:/login";
		}
		String username = principal.getName();
		Customer customer = customerService.findByUsername(username);
		ShoppingCart cart = customer.getShoppingCart();
		orderService.saveOrder(cart);
		return "redirect:/order";
	}

	@RequestMapping(value = "/cancel-order/{id}", method = { RequestMethod.PUT, RequestMethod.GET })
	public String cancelOrder(@PathVariable("id") Long id, RedirectAttributes attributes) {
		try {
			orderService.cancelOrder(id);
			attributes.addFlashAttribute("success", "¡Pedido Cancelado Exitosamente!");
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addFlashAttribute("error", "¡Error al Cancelar!");
		}
		return "redirect:/order";
	}

	@GetMapping("/show-order/{id}")
	public String showOrderItems(@PathVariable("id") Long orderId, Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/login";
		}
		OrderDto orderDto = orderService.getById(orderId);
		List<OrderDetail> orderDetails = orderDetailService.getOrderDetailInOrder(orderId);
		model.addAttribute("title", "Mostrar detalles de Pedido");
		// model.addAttribute("totalItems", order.getTotalItems());
		// model.addAttribute("subTotal", order.getTotalPrices());
		model.addAttribute("orderDetails", orderDetails);
		model.addAttribute("orderDto", orderDto);
		return "/show-order";
	}

}
