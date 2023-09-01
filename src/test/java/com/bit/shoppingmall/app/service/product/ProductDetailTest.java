package com.bit.shoppingmall.app.service.product;

import com.bit.shoppingmall.app.dto.product.response.ProductDetailWithCategory;
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
public class ProductDetailTest {
  Logger log = Logger.getLogger("product");
  @Autowired ProductService productService;
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
    String name = "name";
    Long price = 1000L;
    String desc = "설명";
    Long categoryId = 1L;
    Long qty = 100L;
    String code = "PROD-";

    for (int i = 0; i < 20; i++) {
      Product product =
          Product.builder()
              .name(name + i)
              .description(desc + i)
              .price(price + (9 * 1000))
              .categoryId(categoryId)
              .quantity(qty)
              .code(code + i)
              .build();
      productMapper.insert(product);
    }
    likesMapper.insert(Likes.builder().productId(1L).memberId(1L).build());
    likesMapper.insert(Likes.builder().productId(2L).memberId(1L).build());
    likesMapper.insert(Likes.builder().productId(3L).memberId(1L).build());

    imageMapper.insert(ProductImage.builder().productId(1L).url("url1").isThumbnail(true).build());
    imageMapper.insert(ProductImage.builder().productId(1L).url("url1").isThumbnail(false).build());
    imageMapper.insert(ProductImage.builder().productId(2L).url("url2").isThumbnail(true).build());
    imageMapper.insert(ProductImage.builder().productId(2L).url("url2").isThumbnail(false).build());
    imageMapper.insert(ProductImage.builder().productId(3L).url("url3").isThumbnail(true).build());
    imageMapper.insert(ProductImage.builder().productId(3L).url("url3").isThumbnail(false).build());
  }

  @Test
  @DisplayName("상품 상세 정보 조회")
  void productDetail() {
    ProductDetailWithCategory productDetail = productService.getProductDetail(1L, 1L);
    Assertions.assertEquals(1L, productDetail.getDetail().getId());
    Assertions.assertEquals("url1", productDetail.getDetail().getUrl());
    log.info(productDetail.toString());
  }
}
