package com.bit.shoppingmall.app.mapper.category;

import static org.assertj.core.api.Assertions.assertThat;

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
import java.util.List;
import java.util.logging.Logger;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class CategoryDaoTest {
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
  @DisplayName("카테고리 조회 - level 1")
  void selectCategory() {
    List<Category> categories = categoryMapper.selectFirstCategory();
    assertThat(categories.size()).isEqualTo(1);
  }
}
