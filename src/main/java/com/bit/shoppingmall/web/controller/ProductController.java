package com.bit.shoppingmall.web.controller;

import com.bit.shoppingmall.app.dto.member.response.MemberDetail;
import com.bit.shoppingmall.app.dto.product.response.ProductDetailWithCategory;
import com.bit.shoppingmall.app.service.product.ProductService;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
  private final ProductService service;

  @GetMapping("/{p_id}/detail")
  public String productDetail(@PathVariable Long p_id, HttpSession session, Model model) {
    Long memberId = -1L;

    MemberDetail loginMember = (MemberDetail) session.getAttribute("loginMember");
    if (loginMember != null) memberId = loginMember.getId();
    ProductDetailWithCategory productDetail = service.getProductDetail(memberId, p_id);
    model.addAttribute("detail", productDetail.getDetail());
    model.addAttribute("categories", productDetail.getCategory());
    return "product/detail";
  }
}
