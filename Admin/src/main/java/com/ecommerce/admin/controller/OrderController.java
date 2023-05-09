package com.ecommerce.admin.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.library.dto.OrderDto;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.OrderDetail;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.OrderDetailService;
import com.ecommerce.library.service.OrderService;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderDetailService orderDetailService;

	@Autowired
	private CustomerService customerService;

	@GetMapping("/orders")
	public String orders(Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/login";
		}
		List<OrderDto> orderDtoList = orderService.findAll();
		model.addAttribute("title", "Pedidos");
		model.addAttribute("orders", orderDtoList);
		model.addAttribute("size", orderDtoList.size());
		return "order/orders";
	}

	@GetMapping("/orders/{pageNo}")
	public String ordersPage(@PathVariable("pageNo") int pageNo, Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/login";
		}
		Page<OrderDto> orders = orderService.pageOrders(pageNo);
		model.addAttribute("title", "Pedidos");
		model.addAttribute("size", orders.getSize());
		model.addAttribute("totalPages", orders.getTotalPages());
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("orders", orders);
		return "order/orders";
	}

	@GetMapping("/update-order/{id}")
	public String updateProductForm(@PathVariable("id") Long id, Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/login";
		}
		model.addAttribute("title", "Actualizar Pedido");
		List<Customer> customer = customerService.findAll();
		OrderDto orderDto = orderService.getById(id);
		model.addAttribute("customers", customer);
		model.addAttribute("orderDto", orderDto);
		return "order/update-order";
	}

	@PostMapping("/update-order/{id}")
	public String processUpdate(@PathVariable("id") Long id, @ModelAttribute("productDto") OrderDto orderDto,
			RedirectAttributes attributes) {
		try {
			orderService.update(orderDto);
			attributes.addFlashAttribute("success", "¡Actualizado exitosamente!");
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addFlashAttribute("error", "¡Error al actualizar!");
		}
		return "redirect:/orders/0";

	}

	@GetMapping("/search-orders/{pageNo}")
	public String searchOrders(@PathVariable("pageNo") int pageNo, @RequestParam("keyword") String keyword, Model model,
			Principal principal) {
		if (principal == null) {
			return "redirect:/login";
		}
		Page<OrderDto> orders = orderService.searchOrders(pageNo, keyword);
		model.addAttribute("title", "Search Result");
		model.addAttribute("orders", orders);
		model.addAttribute("size", orders.getSize());
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", orders.getTotalPages());
		return "order/result-orders";
	}

	@RequestMapping(value = "/delete-order/{id}", method = { RequestMethod.PUT, RequestMethod.GET })
	public String cancelOrder(@PathVariable("id") Long id, RedirectAttributes attributes) {
		try {
			orderService.cancelOrder(id);
			attributes.addFlashAttribute("success", "¡Pedido Eliminado Exitosamente!");
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addFlashAttribute("error", "¡Error al eliminar!");
		}
		return "redirect:/orders/0";
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
		return "order/show-order";
	}

}
