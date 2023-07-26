package com.mysmartshop.cart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mysmartshop.cart.dto.Product;
import com.mysmartshop.cart.model.CartItem;
import com.mysmartshop.cart.repository.CartItemRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class CartItemServiceImpl implements CartItemService{
	@Autowired
	public CartItemRepository cartrepo;
    @Autowired
    RestTemplate productServiceClient;
	@Override
	public List<CartItem> addToCart(String productId) {
		// TODO Auto-generated method stub
		Optional<CartItem> checkItem=cartrepo.findByProductId(productId);
		if(!checkItem.isPresent()) {
			CartItem item=new CartItem();
			item.setProductId(productId);
			item.setQuantity(1);
			item.setTotalprice(fetchPrice(productId));
			cartrepo.save(item);
			}
		else {
			CartItem item=checkItem.get();
			updateQuantity(productId,item.getQuantity()+1);
		}
		return getAllItems();
	}

	@Override
	public List<CartItem> removeFromCart(String productId) {
		// TODO Auto-generated method stub
		Optional<CartItem> checkItem=cartrepo.findByProductId(productId);
		if(checkItem.isPresent()) {
			CartItem item=checkItem.get();
			cartrepo.delete(item);
		}
		return getAllItems();
	}

	@Override
	public List<CartItem> updateQuantity(String productId, int quantity) {
		// TODO Auto-generated method stub
		Optional<CartItem> checkItem=cartrepo.findByProductId(productId);
		if(checkItem.isPresent()) {
			CartItem item=checkItem.get();
			float unitPrice=item.getTotalprice()/item.getQuantity();
			item.setQuantity(quantity);
			item.setTotalprice(unitPrice*quantity);
			cartrepo.save(item);
			}
		return getAllItems();
	}

	@Override
	public float calculateTotalCost() {
		// TODO Auto-generated method stub
		return cartrepo.getTotalCartValue();
		
	}
	public List<CartItem> getAllItems(){
		return cartrepo.findAll();
	}
	
//	@CircuitBreaker(fallbackMethod="fetchPriceFallback",name="cb-product")
	public float fetchPrice(String productId) {
		Product product=productServiceClient.getForObject("http://product-ms/api/product/getbyid/"+productId,Product.class);
		if(product!=null) 
			return product.getPrice();
		return 0;
		
		                                                                                                                  
	}
//	private float fetchPriceFallback(String productId,Throwable t) {
//		Product product=new Product(productId,"Dummy Product",0,"A dummy product");
//		System.out.println("Response from Fallback");
//		System.out.println(product);
//		return product.getPrice();
//	}

}
