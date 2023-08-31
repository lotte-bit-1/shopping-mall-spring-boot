package com.bit.shoppingmall.app.mapper;

import com.bit.shoppingmall.app.entity.Cart;
import com.bit.shoppingmall.app.entity.ProductAndMemberCompositeKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CartMapperTest {

  @Autowired
  private CartMapper dao;

  @BeforeEach
  void init() {
    Cart cart = Cart.cartBuilder(new ProductAndMemberCompositeKey(1L,6L),5L);
      dao.insert(cart);
  }

  @Test
  void selectAllCartAndProductByMember() {
  }

  @Test
  void selectAllByMember() {
  }

  @Test
  void getCartTotalPage() {
  }

  @Test
  void bulkDelete() {
  }

  @Test
  void selectAll() {
  }

  @Test
  void select() {
  }

  @Test
  void insert() {
  }

  @Test
  void update() {
  }

  @Test
  void testUpdate() {
  }

  @Test
  void delete() {
  }
}