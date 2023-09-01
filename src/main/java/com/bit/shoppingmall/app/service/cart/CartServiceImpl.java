package com.bit.shoppingmall.app.service.cart;

import com.bit.shoppingmall.app.entity.Cart;
import com.bit.shoppingmall.app.entity.Product;
import com.bit.shoppingmall.app.entity.ProductAndMemberCompositeKey;
import com.bit.shoppingmall.app.exception.cart.ProductIsNotExistedInCartException;
import com.bit.shoppingmall.app.exception.product.ProductNotFoundException;
import com.bit.shoppingmall.app.mapper.CartMapper;
import com.bit.shoppingmall.app.mapper.MemberMapper;
import com.bit.shoppingmall.app.mapper.ProductMapper;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

  private final MemberMapper memberDao;
  private final ProductMapper productDao;
  private final CartMapper cartDao;
  private final DecreaseItemInCartStrategy decreaseItemStrategy;
  private final OutOfStockChecker outOfStockChecker;
  private final AddItemInCart addItemInCart;
  private final PutItemIntoCartStrategy increaseProductQuantity;
  private final CartOrderValidationProcessor validationProcessor;



  @Override
  public void order(Cart cart) {
    List<CartValidationCheckerService<Cart>> list = new ArrayList<>();
    list.add(new MemberExistChecker(memberDao));
    list.add(new ProductInCartChecker(cartDao));
    validationProcessor.setValidationChecker(list);
    validationProcessor.process(cart);
  }

  @Override
  public void putItemInCart(ProductAndMemberCompositeKey compKey, Long requestQuantity) {
    Product product = productDao.select(compKey.getProductId())
        .orElseThrow(ProductNotFoundException::new);
    outOfStockChecker.isStockEnough(product, requestQuantity);
    addItemInCart.add(compKey, requestQuantity);
  }

  @Override
  public void deleteItemInCart(ProductAndMemberCompositeKey compKey) {
    Cart cart = cartDao.select(compKey).orElseThrow(ProductIsNotExistedInCartException::new);
    cartDao.delete(Cart.getCompKey(cart));
  }

  @Override
  public void updateItemInCart(ProductAndMemberCompositeKey compKey, Long requestQuantity) {
    Cart cart = cartDao.select(compKey).orElseThrow(ProductIsNotExistedInCartException::new);
    if (requestQuantity > 0) {
      increaseProductQuantity.put(cart,requestQuantity);
    } else {
      decreaseItemStrategy.decrease(cart, requestQuantity);
    }
  }
}
