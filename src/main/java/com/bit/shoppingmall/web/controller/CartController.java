package com.bit.shoppingmall.web.controller;

import com.bit.shoppingmall.app.dto.cart.AllCartProductInfoDtoWithPagination;
import com.bit.shoppingmall.app.entity.ProductAndMemberCompositeKey;
import com.bit.shoppingmall.app.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
@RequestMapping("/cart")
public class CartController {

  private final CartService cartService;

  @DeleteMapping
  public String deleteCart(@RequestBody ProductAndMemberCompositeKey compKey) throws Exception {
    cartService.deleteItemInCart(compKey);
    return "forward:/";
  }

  @ResponseBody
  @GetMapping
  public String getCart(@RequestParam Long memberId, Model model) {
    AllCartProductInfoDtoWithPagination productsInCart = cartService.getCartByMember(memberId);
    model.addAttribute("productList",productsInCart);
    return "forward:/";
  }


}
