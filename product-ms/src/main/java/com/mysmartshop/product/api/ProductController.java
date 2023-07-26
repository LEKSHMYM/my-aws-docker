package com.mysmartshop.product.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysmartshop.product.model.Product;
import com.mysmartshop.product.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {
	@Autowired
	private ProductService productservice;
	@GetMapping
	public String greet() {
		return "Message from Product Service";
	}
	@PostMapping
	public Product addProduct(@RequestBody Product product) {
		return productservice.addProduct(product);
		
	}
	@GetMapping("getbyid/{id}")
	public Product getProductDetails(@PathVariable String id) {
		return productservice.getProductDetails(id);
		}
	@GetMapping("/getall")
	public List<Product> getAvailableProducts(){
		return productservice.getAvailableProducts();
	}
	@DeleteMapping("/{id}")
	public void removeProduct(@PathVariable String id) {
		 productservice.removeProduct(id);
	}

}
