package com.bit.shoppingmall.app.mapper.product;

import com.bit.shoppingmall.app.dto.product.ProductListItemOfLike;
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

@SpringBootTest
@Transactional
public class ProductDaoTest {

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
  void mapperTest() {
    int productListTotalPage = productMapper.getProductListTotalPage(0);
    log.info(String.valueOf(productListTotalPage));
    Assertions.assertEquals(2, productListTotalPage);
  }

  @Test
  void selectProductDetail() {
    List<Product> products = productMapper.selectAllProduct();
    log.info(products.toString());
    Assertions.assertEquals(20, products.size());
  }

  @Test
  @DisplayName("찜 목록을 위한 상품 정보")
  void likeOfProduct() {
    // fixme: likes 수정되면 다시 조회
    List<Long> ids = Arrays.asList(1L, 2L);
    List<ProductListItemOfLike> productListItemOfLikes =
        productMapper.selectProductListItemOfLike(ids);
    //    List<Long> selectall =
    //        likesMapper.selectall(
    //            LikesSelectForPage.builder().memberId(1L).start(0).PerPage(2).build());
    //    Assertions.assertEquals(2, selectall.size());
    log.info(productListItemOfLikes.toString());
    Assertions.assertEquals(2, productListItemOfLikes.size());
  }
}
