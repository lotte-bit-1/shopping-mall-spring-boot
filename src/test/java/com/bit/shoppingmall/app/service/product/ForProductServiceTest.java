package com.bit.shoppingmall.app.service.product;

import com.bit.shoppingmall.app.dto.category.response.SubCategory;
import com.bit.shoppingmall.app.dto.paging.Pagination;
import com.bit.shoppingmall.app.dto.product.ParameterForProductList;
import com.bit.shoppingmall.app.dto.product.ParameterForSearchProductForKeyword;
import com.bit.shoppingmall.app.dto.product.ParameterForSubCategorySearch;
import com.bit.shoppingmall.app.dto.product.ProductItemQuantity;
import com.bit.shoppingmall.app.dto.product.ProductListItem;
import com.bit.shoppingmall.app.dto.product.response.ProductListWithPagination;
import com.bit.shoppingmall.app.entity.Category;
import com.bit.shoppingmall.app.entity.Likes;
import com.bit.shoppingmall.app.entity.Member;
import com.bit.shoppingmall.app.entity.Product;
import com.bit.shoppingmall.app.entity.ProductImage;
import com.bit.shoppingmall.app.mapper.CategoryMapper;
import com.bit.shoppingmall.app.mapper.LikesMapper;
import com.bit.shoppingmall.app.mapper.MemberMapper;
import com.bit.shoppingmall.app.mapper.ProductImageMapper;
import com.bit.shoppingmall.app.mapper.ProductMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class ForProductServiceTest {
  Logger log = Logger.getLogger("product");
  @Autowired ProductMapper productMapper;
  @Autowired CategoryMapper categoryMapper;
  @Autowired MemberMapper memberMapper;
  @Autowired LikesMapper likesMapper;
  @Autowired ProductImageMapper imageMapper;

  @BeforeEach
  void beforeEach() {
    memberMapper.insert(
        Member.builder().name("member1").email("email").password("1234").money(1000L).build());

    categoryMapper.insert(Category.builder().name("CATEGORY-1").level(1).parentId(null).build());
    categoryMapper.insert(Category.builder().name("CATEGORY-11").level(2).parentId(1L).build());
    categoryMapper.insert(Category.builder().name("CATEGORY-111").level(3).parentId(2L).build());
    categoryMapper.insert(Category.builder().name("CATEGORY-112").level(3).parentId(2L).build());
    categoryMapper.insert(Category.builder().name("CATEGORY-113").level(3).parentId(2L).build());
    String name = "name";
    Long price = 1000L;
    String desc = "설명";
    Long categoryId = 1L;
    Long qty = 100L;
    String code = "PROD-";

    Long productId = 1L;

    for (int i = 0; i < 20; i++) {
      Product product =
          Product.builder()
              .name(name + i)
              .description(desc + i)
              .price(price + (9 * 1000))
              .categoryId(categoryId + 1)
              .quantity(qty)
              .code(code + i)
              .build();
      productMapper.insert(product);

      ProductImage productImage1 =
          ProductImage.builder().productId(productId + i).url("url" + i).isThumbnail(true).build();
      ProductImage productImage2 =
          ProductImage.builder().productId(productId + i).url("url" + i).isThumbnail(false).build();
      imageMapper.insert(productImage1);
      imageMapper.insert(productImage2);
    }
    likesMapper.insert(Likes.builder().productId(1L).memberId(1L).build());
    likesMapper.insert(Likes.builder().productId(2L).memberId(1L).build());
    likesMapper.insert(Likes.builder().productId(3L).memberId(1L).build());
  }

  @Test
  @DisplayName("selectProductInfo test")
  void selectProductInfo() {
    List<Long> list = Arrays.asList(1L, 2L, 3L);
    List<ProductItemQuantity> productItemQuantities = productMapper.selectProductInfo(list);
    log.info(productItemQuantities.toString());
    Assertions.assertEquals(3, productItemQuantities.size());
  }

  @Test
  @DisplayName("전체 상품 조회")
  void selectAll() {
    List<Product> products = productMapper.selectAllProduct();
    Assertions.assertEquals(20, products.size());
  }

  @Test
  @DisplayName("상품 리스트 조회 - 상품 가격 기준")
  void selectProductByPrice() {
    List<ProductListItem> productListItems =
        productMapper.selectProductListOrderByPrice(
            ParameterForProductList.builder().userId(1L).offset(0).build());
    int productListTotalPage = productMapper.getProductListTotalPage(0);
    Pagination pagination =
        Pagination.builder().currentPage(0).perPage(9).totalPage(productListTotalPage).build();
    ProductListWithPagination build =
        ProductListWithPagination.builder().item(productListItems).paging(pagination).build();
    log.info(build.toString());
    Assertions.assertEquals(9, productListItems.size());
  }

  @Test
  @DisplayName("상품 키워드 조회")
  void selectProductByKeyword() {
    int currentPage = 1;
    String keyword = "2";
    Long userId = 1L;
    int offset = (currentPage - 1) * 9;
    List<ProductListItem> productListItems =
        productMapper.searchByWord(
            ParameterForSearchProductForKeyword.builder()
                .keyword(keyword)
                .userId(userId)
                .offset(offset)
                .build());
    Assertions.assertEquals(2, productListItems.size());
  }

  @Test
  @DisplayName("하위 카테고리 상품 조회")
  void selectSubCategoryProduct() {
    // todo: 서비스 테스트

    List<SubCategory> categories = categoryMapper.selectSubcategory("CATEGORY-1");

    List<Long> idListItems = new ArrayList<>();
    for (SubCategory s : categories) {
      if (s.getHigh() == null && s.getMiddle() == null) idListItems.add(s.getLow());
      else if (s.getHigh() == null) idListItems.add(s.getMiddle());
      else idListItems.add(s.getHigh());
    }
    List<ProductListItem> productListItems =
        productMapper.searchSubCategoryProduct(
            ParameterForSubCategorySearch.getParameterForSubCategorySearch(idListItems, 1L, 1));
    log.info(productListItems.toString());
  }
}
