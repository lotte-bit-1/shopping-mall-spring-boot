package com.bit.shoppingmall.web.controller;

import com.bit.shoppingmall.app.dto.member.response.MemberDetail;
import com.bit.shoppingmall.app.dto.product.response.ProductDetailWithCategory;
import com.bit.shoppingmall.app.dto.product.response.ProductListWithPagination;
import com.bit.shoppingmall.app.entity.Category;
import com.bit.shoppingmall.app.service.category.CategoryService;
import com.bit.shoppingmall.app.service.product.ProductService;
import java.util.List;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
  private final ProductService productService;
  private final CategoryService categoryService;

  /**
   * 사용자 id 세션에서 가져오기
   *
   * @param session http session
   * @return id
   */
  private static Long getaLong(HttpSession session) {
    Long memberId = -1L;

    MemberDetail loginMember = (MemberDetail) session.getAttribute("loginMember");
    if (loginMember != null) memberId = loginMember.getId();
    return memberId;
  }

  /**
   * 페이지 리스트 반환할 아이템 리스트와 카테고리 정보
   *
   * @param model model
   * @param productListWithPagination page information
   * @return page info
   */
  private String getStringForReturnPage(
      Model model, ProductListWithPagination productListWithPagination) {
    List<Category> firstLevelCategory = categoryService.getFirstLevelCategory();
    model.addAttribute("categories", firstLevelCategory);
    model.addAttribute("page", productListWithPagination.getPaging());
    model.addAttribute("products", productListWithPagination.getItem());
    return "product/list";
  }

  @GetMapping("/{p_id}/detail")
  public String productDetail(@PathVariable Long p_id, HttpSession session, Model model) {
    Long memberId = getaLong(session);
    ProductDetailWithCategory productDetail = productService.getProductDetail(memberId, p_id);
    model.addAttribute("detail", productDetail.getDetail());
    model.addAttribute("categories", productDetail.getCategory());
    return "product/detail";
  }

  @GetMapping("/{page}/list")
  public String productListByPrice(HttpSession session, Model model, @PathVariable int page) {
    Long memberId = getaLong(session);
    ProductListWithPagination productListByPrice =
        productService.getProductListByPrice(memberId, page);
    return getStringForReturnPage(model, productListByPrice);
  }

  @GetMapping("/{page}/search")
  public String productListByCategory(
      @RequestParam String keyword, @PathVariable int page, HttpSession session, Model model) {
    Long memberId = getaLong(session);
    ProductListWithPagination productsByKeyword =
        productService.getProductsByKeyword(keyword, memberId, page);
    return getStringForReturnPage(model, productsByKeyword);
  }

  @GetMapping("{page}/category")
  public String productListByCategoryName(
      @RequestParam String keyword, @PathVariable int page, HttpSession session, Model model) {
    Long memberId = getaLong(session);
    ProductListWithPagination productListByCategoryName =
        productService.getProductListByCategoryName(keyword, memberId, page);
    return getStringForReturnPage(model, productListByCategoryName);
  }
}
