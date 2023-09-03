package com.bit.shoppingmall.app.dto.product.response;

import com.bit.shoppingmall.app.dto.category.response.ProductCategory;
import com.bit.shoppingmall.app.dto.product.ProductDetail;
import com.bit.shoppingmall.app.entity.Category;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Slf4j
public class ProductDetailWithCategory {

  private ProductCategory category;
  private ProductDetail detail;

  public static ProductDetailWithCategory getProductDetail(
      List<Category> categories, ProductDetail detail) {
    Map<Integer, String> category = new HashMap<>();
    int idx = 1;
    for (Category item : categories) {
      category.put(idx, item.getName());
      idx++;
    }
    log.info(category.toString());
    ProductCategory productCategory = ProductCategory.builder().categoryList(category).build();

    return ProductDetailWithCategory.builder().category(productCategory).detail(detail).build();
  }
}
