package com.bit.shoppingmall.app.service.product;

import com.bit.shoppingmall.app.dto.paging.Pagination;
import com.bit.shoppingmall.app.dto.product.ParameterForSearchProductForKeyword;
import com.bit.shoppingmall.app.dto.product.ProductDetail;
import com.bit.shoppingmall.app.dto.product.ProductDetailParameter;
import com.bit.shoppingmall.app.dto.product.ProductListItem;
import com.bit.shoppingmall.app.dto.product.ProductListParameter;
import com.bit.shoppingmall.app.dto.product.response.ProductDetailWithCategory;
import com.bit.shoppingmall.app.dto.product.response.ProductListWithPagination;
import com.bit.shoppingmall.app.entity.Category;
import com.bit.shoppingmall.app.exception.category.CategoryNotFoundException;
import com.bit.shoppingmall.app.exception.product.ProductKeywordNotMatchException;
import com.bit.shoppingmall.app.exception.product.ProductNotFoundException;
import com.bit.shoppingmall.app.mapper.CategoryMapper;
import com.bit.shoppingmall.app.mapper.ProductMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
  private final ProductMapper productMapper;
  private final CategoryMapper categoryMapper;

  private ProductListWithPagination getProductListWithPagination(
      int currentPage, List<ProductListItem> productListItems) {
    int productListTotalPage = productMapper.getProductListTotalPage(currentPage);
    Pagination pagination =
        Pagination.builder()
            .currentPage(currentPage)
            .perPage(9)
            .totalPage(productListTotalPage)
            .build();
    ProductListWithPagination result =
        ProductListWithPagination.builder().item(productListItems).paging(pagination).build();
    return result;
  }

  public ProductDetailWithCategory getProductDetail(Long memberId, Long productId) {
    ProductDetail productDetail =
        productMapper
            .selectProductDetail(
                ProductDetailParameter.builder().productId(productId).memberId(memberId).build())
            .orElseThrow(ProductNotFoundException::new);

    List<Category> categories = categoryMapper.selectCategoryOfProduct(productId);
    if (categories.isEmpty()) {
      throw new CategoryNotFoundException();
    }
    ProductDetailWithCategory productDetailWithCategory =
        ProductDetailWithCategory.getProductDetail(categories, productDetail);

    return productDetailWithCategory;
  }

  public ProductListWithPagination getProductListByPrice(Long userId, int currentPage) {
    int offset = (currentPage - 1) * 9;
    List<ProductListItem> productListItems =
        productMapper.selectProductListOrderByPrice(
            ProductListParameter.builder().userId(userId).offset(offset).build());
    return getProductListWithPagination(currentPage, productListItems);
  }

  public ProductListWithPagination getProductsByKeyword(
      String keyword, Long memberId, int currentPage) {
    int offset = (currentPage - 1) * 9;
    List<ProductListItem> productListItems =
        productMapper.searchByWord(
            ParameterForSearchProductForKeyword.builder()
                .keyword(keyword)
                .userId(memberId)
                .offset(offset)
                .build());
    if (productListItems.isEmpty()) throw new ProductKeywordNotMatchException();
    return getProductListWithPagination(currentPage, productListItems);
  }
}
