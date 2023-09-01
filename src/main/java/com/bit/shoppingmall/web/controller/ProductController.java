package com.bit.shoppingmall.web.controller;

import com.bit.shoppingmall.app.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("product")
public class ProductController {
  private final ProductService service;


}
