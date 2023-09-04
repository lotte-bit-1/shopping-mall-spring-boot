package com.bit.shoppingmall.app.service.product;

import com.bit.shoppingmall.app.dto.category.response.SubCategory;
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

    return ProductDetailWithCategory.getProductDetail(categories, productDetail);
  }

  /**
   * 상품 전체 리스트 - 페이징, 가격순
   *
   * @param userId 사용자 id
   * @param currentPage 현재 페이지
   * @return 리스트에 뿌려질 정보 (상품 리스트, 카테고리 lv1)
   */
  public ProductListWithPagination getProductListByPrice(Long userId, int currentPage) {
    ParameterForProductList productListParameter =
        ParameterForProductList.getProductListParameter(userId, currentPage);
    List<ProductListItem> productListItems =
        productMapper.selectProductListOrderByPrice(productListParameter);
    int listOrderByPriceTotalCount =
        productMapper.selectProductListOrderByPriceTotalCount(productListParameter);

    return ProductListWithPagination.makeListWithPaging(
        productListItems, listOrderByPriceTotalCount, currentPage);
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
    ParameterForSearchProductForKeyword mapperParameter =
        ParameterForSearchProductForKeyword.getMapperParameter(keyword, memberId, currentPage);
    List<ProductListItem> productListItems = productMapper.searchByWord(mapperParameter);
    if (productListItems.isEmpty()) throw new ProductKeywordNotMatchException();
    int byWordTotalCount = productMapper.searchByWordTotalCount(mapperParameter);

    return ProductListWithPagination.makeListWithPaging(
        productListItems, byWordTotalCount, currentPage);
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
    ParameterForSubCategorySearch parameterForSubCategorySearch =
        ParameterForSubCategorySearch.getParameterForSubCategorySearch(
            idListItems, memberId, currentPage);
    List<ProductListItem> productListItems =
        productMapper.searchSubCategoryProduct(parameterForSubCategorySearch);
    if (productListItems.isEmpty()) throw new ProductNotFoundException();

    int categoryProductTotalCount =
        productMapper.searchSubCategoryProductTotalCount(parameterForSubCategorySearch);

    return ProductListWithPagination.makeListWithPaging(
        productListItems, categoryProductTotalCount, currentPage);
  }
}
