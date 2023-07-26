package com.mysmartshop.cart.dto;

import java.util.List;

import com.mysmartshop.cart.model.CartItem;

import lombok.Data;

@Data
public class CartDetails {
	private List<CartItem> itemList;
	private float totalCartValue;

}
