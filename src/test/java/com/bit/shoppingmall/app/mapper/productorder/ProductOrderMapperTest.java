package com.bit.shoppingmall.app.mapper.productorder;

import com.bit.shoppingmall.app.entity.*;
import com.bit.shoppingmall.app.enums.OrderStatus;
import com.bit.shoppingmall.app.mapper.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductOrderMapperTest {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProductOrderMapper productOrderMapper;

    @Autowired
    private MemberMapper memberMapper;

    private static Long memberId;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductImageMapper productImageMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @BeforeEach
    void beforeEach() {
        Member member = Member.builder().email("test@naver.com").password("test123").name("상셀").money(1000000L).build();
        memberMapper.insert(member);
        memberId = member.getId();
    }

    @Test
    @DisplayName("주문 생성 - 정상 처리")
    void insert() throws Exception {
        // given
        Category category = Category.builder().name("카테고리 1").level(1).build();
        categoryMapper.insert(category);

        Product product = Product.builder().categoryId(category.getId()).name("물건").description("물건 상세정보").price(10000L).quantity(10L).code("CODE").build();
        productMapper.insert(product);

        ProductImage productImage = ProductImage.builder().productId(product.getId()).url("https://example.com").isThumbnail(true).build();
        productImageMapper.insert(productImage);

        Order order = Order.builder().memberId(memberId).status(OrderStatus.PENDING.name()).build();
        orderMapper.insert(order);

        // when
        ProductOrder productOrder = ProductOrder.builder()
                .orderId(order.getId()).productId(product.getId()).price(product.getPrice()).quantity(1L).build();
        int insertedRow = productOrderMapper.insert(productOrder);

        // then
        assertThat(insertedRow).isSameAs(1);
        assertThat(productOrder.getId()).isNotNull();
    }
}
