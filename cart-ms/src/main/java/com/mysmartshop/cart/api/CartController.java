package com.mysmartshop.cart.api;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.mysmartshop.cart.dto.CartDetails;
import com.mysmartshop.cart.model.CartItem;
import com.mysmartshop.cart.service.CartItemService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/api/cart")
public class CartController {
	
	@Autowired
	private CartItemService cartService;
	
//	@GetMapping
//	public String greet() {
//		return "Message from Cart Service";
//	}
	
	@GetMapping("/items")
	public CartDetails getCartDetails(){
		List<CartItem> items = cartService.getAllItems();
		float totalCartValue = cartService.calculateTotalCost();
		CartDetails cartDetails = new CartDetails();
		cartDetails.setItemList(items);
		cartDetails.setTotalCartValue(totalCartValue);
		return cartDetails;
		
	}

	@PostMapping("/items/product/{productId}")
	@CircuitBreaker(fallbackMethod = "addNewItemFallback",name = "cb-product")
	public List<CartItem> addNewItem(@PathVariable String productId){
		return cartService.addToCart(productId);
	}
	
	public List<CartItem> addNewItemFallback(@PathVariable String productId, Throwable t){
		System.err.println(t.getMessage());
		return new ArrayList<CartItem>();
	}
	
	

	@DeleteMapping("/items/product/{productId}")
	public List<CartItem> deleteItem(@PathVariable String productId){
		return cartService.removeFromCart(productId);
		
	}

}

