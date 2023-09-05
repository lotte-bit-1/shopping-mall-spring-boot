package com.bit.shoppingmall.web.controller;

import com.bit.shoppingmall.app.dto.cart.AllCartProductInfoDtoWithPagination;
import com.bit.shoppingmall.app.dto.member.response.MemberDetail;
import com.bit.shoppingmall.app.service.cart.CartService;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CartController {

  private final CartService cartService;

  @ModelAttribute("memberId")
  public Long memberId() {
    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    HttpSession httpSession = attr.getRequest().getSession();
    return ((MemberDetail) httpSession.getAttribute("loginMember")).getId();
  }


  @GetMapping("/carts")
  public String getCart(@ModelAttribute("memberId") Long memberId, Model model) {
    log.info("request memberInfo: " + memberId);
    AllCartProductInfoDtoWithPagination productsInCart = cartService.getCartByMember(
        memberId);
    if (productsInCart != null) {
      model.addAttribute("productList",
          productsInCart.getCartProductInfoDto().getCartProductDtoList());
      model.addAttribute("totalPrice", productsInCart.getCartProductInfoDto().getTotalPrice());
      model.addAttribute("pagination", productsInCart.getPaging());
      log.info("List of model: " + model.getAttribute("productList"));
    }
    return "cart/cart";
  }


}
