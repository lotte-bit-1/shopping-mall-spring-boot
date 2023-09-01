package com.bit.shoppingmall.app.controller;

import com.bit.shoppingmall.app.service.cart.CartService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;


@AllArgsConstructor
@NoArgsConstructor
@Controller
public class CartController {

  private CartService cartService;
}
