package com.bit.shoppingmall.app.mapper;

import com.bit.shoppingmall.app.entity.Category;
import com.bit.shoppingmall.app.entity.Product;
import java.util.List;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

  @BeforeEach
  void beforeEach() {
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
  }
}
