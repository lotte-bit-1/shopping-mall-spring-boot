package com.bit.shoppingmall.app.mapper.likes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.bit.shoppingmall.app.dto.likes.request.LikesSelectForPage;
import com.bit.shoppingmall.app.entity.Category;
import com.bit.shoppingmall.app.entity.Likes;
import com.bit.shoppingmall.app.entity.Member;
import com.bit.shoppingmall.app.entity.Product;
import com.bit.shoppingmall.app.entity.ProductAndMemberCompositeKey;
import com.bit.shoppingmall.app.mapper.CategoryMapper;
import com.bit.shoppingmall.app.mapper.LikesMapper;
import com.bit.shoppingmall.app.mapper.MemberMapper;
import com.bit.shoppingmall.app.mapper.ProductMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.dao.DuplicateKeyException;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LikesMapperTest {

  @Autowired
  LikesMapper likesMapper;
  @Autowired
  MemberMapper memberMapper;
  @Autowired
  ProductMapper productMapper;
  @Autowired
  CategoryMapper categoryMapper;

  Likes likes = Likes.builder().memberId(1L).productId(1L).build();
  ProductAndMemberCompositeKey compKey = ProductAndMemberCompositeKey.builder().memberId(1L)
      .productId(1L).build();

  @BeforeEach
  void before() throws Exception {
    Member member = Member.builder().email("test@naver.com").password("test123").name("상셀")
        .money(1000000L).build();
    memberMapper.insert(member);
    Category category = Category.builder().name("카테고리1").level(1).build();
    categoryMapper.insert(category);
    Product product = Product.builder().name("상품").description("설명").price(10000L).quantity(100L)
        .code("PRODUCT-001").categoryId(1L).build();
    Product product2 = Product.builder().name("상품2").description("설명2").price(10000L).quantity(100L)
        .code("PRODUCT-002").categoryId(1L).build();
    productMapper.insert(product);
    productMapper.insert(product2);
  }

  @DisplayName("찜 추가 테스트")
  @Test
  public void insert() throws Exception {
    int res = likesMapper.insert(likes);

    assertTrue(res == 1);
  }

  @DisplayName("찜 중복 추가 테스트")
  @Test
  public void insertDuplicate() throws Exception {
    assertThrows(DuplicateKeyException.class, () -> {
      int res1 = likesMapper.insert(likes);
      int res2 = likesMapper.insert(likes);
    });
  }

  @DisplayName("찜 삭제 테스트")
  @Test
  public void delete() throws Exception {
    int insertRes = likesMapper.insert(likes);
    int deleteRes = likesMapper.delete(compKey);

    assertTrue(deleteRes == 1);
  }

  @DisplayName("찜 중복 삭제 테스트")
  @Test
  public void deleteDuplicate() throws Exception {
    int insertRes = likesMapper.insert(likes);
    int deleteRes = likesMapper.delete(
        compKey);
    int deleteRes2 = likesMapper.delete(
        compKey);

    assertTrue(deleteRes == 1);
    assertTrue(deleteRes2 == 0);
  }

  @DisplayName("찜 조회 테스트")
  @Test
  public void select() throws Exception {
    int inputRes = likesMapper.insert(likes);
    Likes selectLikes = likesMapper.select(compKey).get();

    assertEquals(likes.getMemberId(), selectLikes.getMemberId());
    assertEquals(likes.getProductId(), selectLikes.getProductId());
  }

  @DisplayName("회원 찜 목록 조회")
  @Test
  public void selectAll() throws Exception {
    likesMapper.insert(likes);
    likesMapper.insert(Likes.builder().productId(2L).memberId(1L).build());

    List<Long> list = likesMapper.selectall(
        LikesSelectForPage.builder().memberId(1L).start(0).PerPage(5).build());

    long idx = 1L;
    for (Long productId : list) {
      assertEquals(productId, idx++);
    }
  }
}
