package com.bit.shoppingmall.app.service.product;

import com.bit.shoppingmall.app.dto.category.response.SubCategory;
import com.bit.shoppingmall.app.dto.paging.Pagination;
import com.bit.shoppingmall.app.dto.product.ParameterForProductList;
import com.bit.shoppingmall.app.dto.product.ParameterForSearchProductForKeyword;
import com.bit.shoppingmall.app.dto.product.ParameterForSubCategorySearch;
import com.bit.shoppingmall.app.dto.product.ProductDetail;
import com.bit.shoppingmall.app.dto.product.ProductDetailParameter;
import com.bit.shoppingmall.app.dto.product.ProductListItem;
import com.bit.shoppingmall.app.dto.product.response.ProductDetailWithCategory;
import com.bit.shoppingmall.app.dto.product.response.ProductListWithPagination;
import com.bit.shoppingmall.app.entity.Category;
import com.bit.shoppingmall.app.exception.category.CategoryNotFoundException;
import com.bit.shoppingmall.app.exception.product.ProductKeywordNotMatchException;
import com.bit.shoppingmall.app.exception.product.ProductNotFoundException;
import com.bit.shoppingmall.app.mapper.CategoryMapper;
import com.bit.shoppingmall.app.mapper.ProductMapper;
import java.util.ArrayList;
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

  /**
   * product list pagination private method
   *
   * @param currentPage 현재 페이지
   * @param productListItems 조회된 상품 목록
   * @return 페이지에 뿌려질 데이터
   */
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

  /**
   * 상품 상세 페이지 정보
   *
   * @param memberId 사용자 id
   * @param productId 상품 id
   * @return 상세 페이지에 뿌려질 정보
   */
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

  /**
   * 상품 전체 리스트 - 페이징, 가격순
   *
   * @param userId 사용자 id
   * @param currentPage 현재 페이지
   * @return 리스트에 뿌려질 정보 (상품 리스트, 카테고리 lv1)
   */
  public ProductListWithPagination getProductListByPrice(Long userId, int currentPage) {
    List<ProductListItem> productListItems =
        productMapper.selectProductListOrderByPrice(
            ParameterForProductList.getProductListParameter(userId, currentPage));
    return ProductListWithPagination.makeListWithPaging(productListItems, currentPage);
  }

  /**
   * 키워드 기준 상품 정보 찾기
   *
   * @param keyword 사용자 입력 키워드
   * @param memberId 사용자 id
   * @param currentPage 현재 페이지
   * @return 검색 기록 페이지에 뿌려질 데이터
   */
  public ProductListWithPagination getProductsByKeyword(
      String keyword, Long memberId, int currentPage) {
    List<ProductListItem> productListItems =
        productMapper.searchByWord(
            ParameterForSearchProductForKeyword.getMapperParameter(keyword, memberId, currentPage));
    if (productListItems.isEmpty()) throw new ProductKeywordNotMatchException();
    return ProductListWithPagination.makeListWithPaging(productListItems, currentPage);
  }

  /**
   * 카테고리 이름 하위 상품 리스트 조회
   *
   * @param categoryName 카테고리 이름
   * @param memberId 사용자 id
   * @param currentPage 현재 페이지
   * @return 페이지에 보여줄 리스트
   */
  public ProductListWithPagination getProductListByCategoryName(
      String categoryName, Long memberId, int currentPage) {
    List<SubCategory> categories = categoryMapper.selectSubcategory(categoryName);

    List<Long> idListItems = new ArrayList<>();
    for (SubCategory s : categories) {
      if (s.getHigh() == null && s.getMiddle() == null) idListItems.add(s.getLow());
      else if (s.getHigh() == null) idListItems.add(s.getMiddle());
      else idListItems.add(s.getHigh());
    }
    List<ProductListItem> productListItems =
        productMapper.searchSubCategoryProduct(
            ParameterForSubCategorySearch.getParameterForSubCategorySearch(
                idListItems, memberId, currentPage));
    if (productListItems.isEmpty()) throw new ProductNotFoundException();
    return ProductListWithPagination.makeListWithPaging(productListItems, currentPage);
  }
}
