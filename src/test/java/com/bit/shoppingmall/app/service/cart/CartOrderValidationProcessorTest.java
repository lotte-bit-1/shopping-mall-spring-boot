package com.bit.shoppingmall.app.service.cart;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bit.shoppingmall.app.entity.Cart;
import com.bit.shoppingmall.app.entity.ProductAndMemberCompositeKey;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CartOrderValidationProcessorTest {


    @Test
    void testProcess() throws Exception {
        CartOrderValidationProcessor processor = new CartOrderValidationProcessor();
        processor.process(Cart.cartBuilder(new ProductAndMemberCompositeKey(1L,6L),2L));


    }
}