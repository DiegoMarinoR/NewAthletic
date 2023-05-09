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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.ProductService;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/products")
	public String products(Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/login";
		}
		List<ProductDto> productDtoList = productService.findAll();
		model.addAttribute("title", "Productos");
		model.addAttribute("products", productDtoList);
		model.addAttribute("size", productDtoList.size());
		return "product/products";
	}

	@GetMapping("/products/{pageNo}")
	public String productsPage(@PathVariable("pageNo") int pageNo, Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/login";
		}
		Page<ProductDto> products = productService.pageProducts(pageNo);
		model.addAttribute("title", "Productos");
		model.addAttribute("size", products.getSize());
		model.addAttribute("totalPages", products.getTotalPages());
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("products", products);
		return "product/products";
	}

	@GetMapping("/search-result/{pageNo}")
	public String searchProducts(@PathVariable("pageNo") int pageNo, @RequestParam("keyword") String keyword,
			Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/login";
		}
		Page<ProductDto> products = productService.searchProducts(pageNo, keyword);
		model.addAttribute("title", "Search Result");
		model.addAttribute("products", products);
		model.addAttribute("size", products.getSize());
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", products.getTotalPages());
		return "product/result-products";
	}

	@GetMapping("/add-product")
	public String addProductForm(Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/login";
		}
		List<Category> categories = categoryService.findAllByActivated();
		model.addAttribute("categories", categories);
		model.addAttribute("product", new ProductDto());
		return "product/add-product";
	}

	@PostMapping("/save-product")
	public String saveProduct(@ModelAttribute("product") ProductDto productDto,
			@RequestParam("imageProduct") MultipartFile imageProduct, RedirectAttributes attributes) {
		try {
			productService.save(imageProduct, productDto);
			attributes.addFlashAttribute("success", "¡Añadido exitosamente!");
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addFlashAttribute("error", "¡Error al añadir!");
		}
		return "redirect:/products/0";
	}

	@GetMapping("/update-product/{id}")
	public String updateProductForm(@PathVariable("id") Long id, Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/login";
		}
		model.addAttribute("title", "Update products");
		List<Category> categories = categoryService.findAllByActivated();
		ProductDto productDto = productService.getById(id);
		model.addAttribute("categories", categories);
		model.addAttribute("productDto", productDto);
		return "product/update-product";
	}

	@PostMapping("/update-product/{id}")
	public String processUpdate(@PathVariable("id") Long id, @ModelAttribute("productDto") ProductDto productDto,
			@RequestParam("imageProduct") MultipartFile imageProduct, RedirectAttributes attributes) {
		try {
			productService.update(imageProduct, productDto);
			attributes.addFlashAttribute("success", "¡Actualizado exitosamente!");
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addFlashAttribute("error", "¡Error al actualizar!");
		}
		return "redirect:/products/0";

	}

	@RequestMapping(value = "/enable-product/{id}", method = { RequestMethod.PUT, RequestMethod.GET })
	public String enabledProduct(@PathVariable("id") Long id, RedirectAttributes attributes) {
		try {
			productService.enableById(id);
			attributes.addFlashAttribute("success", "¡Activado exitosamente!");
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addFlashAttribute("error", "¡Error al activar!");
		}
		return "redirect:/products/0";
	}

	@RequestMapping(value = "/delete-product/{id}", method = { RequestMethod.PUT, RequestMethod.GET })
	public String deletedProduct(@PathVariable("id") Long id, RedirectAttributes attributes) {
		try {
			productService.deleteById(id);
			attributes.addFlashAttribute("success", "¡Eliminado Exitosamente!");
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addFlashAttribute("error", "¡Error al borrar!");
		}
		return "redirect:/products/0";
	}
}
