package com.bit.shoppingmall.app.service.product;

import com.bit.shoppingmall.app.dto.product.ProductDetail;
import com.bit.shoppingmall.app.dto.product.ProductDetailParameter;
import com.bit.shoppingmall.app.dto.product.response.ProductDetailWithCategory;
import com.bit.shoppingmall.app.entity.Category;
import com.bit.shoppingmall.app.exception.category.CategoryNotFoundException;
import com.bit.shoppingmall.app.exception.product.ProductNotFoundException;
import com.bit.shoppingmall.app.mapper.CategoryMapper;
import com.bit.shoppingmall.app.mapper.ProductMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
  private final ProductMapper productMapper;
  private final CategoryMapper categoryMapper;

  public ProductDetailWithCategory getProductDetail(Long memberId, Long productId) {
    Optional<ProductDetail> productDetail =
        Optional.ofNullable(
            productMapper
                .selectProductDetail(
                    ProductDetailParameter.builder()
                        .productId(productId)
                        .memberId(memberId)
                        .build())
                .orElseThrow(ProductNotFoundException::new));

    List<Category> categories = categoryMapper.selectCategoryOfProduct(productId);
    if (categories.isEmpty()) {
      throw new CategoryNotFoundException();
    }

    return ProductDetailWithCategory.getProductDetail(categories, productDetail.get());
  }
}
