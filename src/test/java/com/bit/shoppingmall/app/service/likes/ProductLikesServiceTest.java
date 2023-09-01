package com.bit.shoppingmall.app.service.likes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.bit.shoppingmall.app.dto.likes.response.LikesListWithPagination;
import com.bit.shoppingmall.app.entity.Category;
import com.bit.shoppingmall.app.entity.Likes;
import com.bit.shoppingmall.app.entity.Member;
import com.bit.shoppingmall.app.entity.Product;
import com.bit.shoppingmall.app.entity.ProductAndMemberCompositeKey;
import com.bit.shoppingmall.app.entity.ProductImage;
import com.bit.shoppingmall.app.mapper.CategoryMapper;
import com.bit.shoppingmall.app.mapper.LikesMapper;
import com.bit.shoppingmall.app.mapper.MemberMapper;
import com.bit.shoppingmall.app.mapper.ProductImageMapper;
import com.bit.shoppingmall.app.mapper.ProductMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductLikesServiceTest {

  @Autowired
  LikesMapper likesMapper;
  @Autowired
  MemberMapper memberMapper;
  @Autowired
  ProductMapper productMapper;
  @Autowired
  CategoryMapper categoryMapper;
  @Autowired
  ProductImageMapper productImageMapper;
  @Autowired
  ProductLikesService likesService;

  ProductAndMemberCompositeKey compKey = ProductAndMemberCompositeKey.builder().memberId(1L)
      .productId(1L).build();
  Member member;
  Product product;
  Product product2;
  Product product3;

  @BeforeEach
  void before() {
    member = Member.builder().email("test@naver.com").password("test123").name("상셀")
        .money(1000000L).build();
    memberMapper.insert(member);

    Category category = Category.builder().name("컴퓨터").level(1).build();
    categoryMapper.insert(category);

    product = Product.builder().name("갤럭시북 20").description("설명").price(10000L).quantity(100L)
        .code("PRODUCT-001").categoryId(category.getId()).build();
    product2 = Product.builder().name("아이맥 20").description("설명2").price(10000L).quantity(100L)
        .code("PRODUCT-002").categoryId(category.getId()).build();
    product3 = Product.builder().name("갤럭시 데스크탑 20").description("설명3").price(10000L).quantity(100L)
        .code("PRODUCT-003").categoryId(category.getId()).build();
    productMapper.insert(product);
    productMapper.insert(product2);
    productMapper.insert(product3);

    ProductImage productImage = ProductImage.builder().productId(product.getId()).url("img_url")
        .isThumbnail(true).build();
    ProductImage productImage2 = ProductImage.builder().productId(product2.getId()).url("img_url")
        .isThumbnail(true).build();
    productImageMapper.insert(productImage);
    productImageMapper.insert(productImage2);

    likesMapper.insert(Likes.builder().memberId(member.getId()).productId(product.getId()).build());
    likesMapper.insert(
        Likes.builder().memberId(member.getId()).productId(product2.getId()).build());
  }

  @AfterEach
  void after() {

  }

  @DisplayName("회원 찜목록 조회 테스트")
  @Test
  void getMemberLikes() throws Exception {
    LikesListWithPagination memberLikes = likesService.getMemberLikes(member.getId(), 1);

    assertEquals(memberLikes.getList().get(0).getName(), "갤럭시북 20");
    assertEquals(memberLikes.getList().get(1).getName(), "아이맥 20");
  }

  @DisplayName("회원 찜 추가 테스트")
  @Test
  void addLikes() throws Exception {
    int res = likesService.addLikes(
        ProductAndMemberCompositeKey.builder().memberId(member.getId()).productId(product3.getId())
            .build());

    assertEquals(res, 1);
  }

  @DisplayName("회원 찜 삭제 테스트")
  @Test
  void removeLikes() throws Exception {
    // 회원 id 1, 상품 id 2인 찜 정보 삭제
    int res =
        likesService.removeLikes(
            ProductAndMemberCompositeKey.builder().memberId(member.getId())
                .productId(product2.getId()).build());

    // 삭제 로직 작동여부 파악
    assertEquals(res, 1);
  }

  @DisplayName("회원 찜 벌크 삭제 테스트")
  @Test
  void removeSomeLikes() throws Exception {
    // 회원 id 1, 상품 id 1 ~ 3 세팅
    List<ProductAndMemberCompositeKey> keyList = new ArrayList<>();
    keyList.add(
        ProductAndMemberCompositeKey.builder().memberId(member.getId()).productId(product.getId())
            .build());
    keyList.add(
        ProductAndMemberCompositeKey.builder().memberId(member.getId()).productId(product2.getId())
            .build());
    keyList.add(
        ProductAndMemberCompositeKey.builder().memberId(member.getId()).productId(product3.getId())
            .build());

    // 회원 id 1, 상품 id 3
    likesService.addLikes(keyList.get(2));

    int res = likesService.removeSomeLikes(keyList);

    // 삭제 로직 작동여부 파악
    assertEquals(res, 3);
  }
}
