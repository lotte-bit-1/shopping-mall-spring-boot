package com.bit.shoppingmall.app.service.order.manager;

import com.bit.shoppingmall.app.dto.product.response.ProductDetailForOrder;
import com.bit.shoppingmall.app.entity.Product;
import com.bit.shoppingmall.app.exception.order.OrderProductNotEnoughStockQuantityException;
import com.bit.shoppingmall.app.exception.order.OrderProductUpdateStockQuantityException;
import com.bit.shoppingmall.app.exception.product.ProductEntityNotFoundException;
import com.bit.shoppingmall.app.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProductManager {

  private final ProductMapper productMapper;

  public Product determineProduct(Long productId) {
    return productMapper
        .select(productId)
        .orElseThrow(ProductEntityNotFoundException::new);
  }

  public ProductDetailForOrder determineProductDetailForOrder(Long productId) {
    return productMapper.selectProductDetail(productId);
  }

  public void validateEnoughStockQuantity(Long stockQuantity, Long buyQuantity) {
    if (stockQuantity - buyQuantity < 0) {
      throw new OrderProductNotEnoughStockQuantityException();
    }
  }

  public void updateStockQuantity(Product product, Long quantity) {
    product.updateQuantity(quantity);
    if (productMapper.update(product) == 0) {
      throw new OrderProductUpdateStockQuantityException();
    }
  }
}
