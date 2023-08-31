package com.bit.shoppingmall.app.mapper;

import com.bit.shoppingmall.app.dto.cart.CartAndProductDto;
import com.bit.shoppingmall.app.entity.Cart;
import com.bit.shoppingmall.app.entity.ProductAndMemberCompositeKey;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CartMapper {

  List<CartAndProductDto> selectAllCartAndProductByMember(ProductAndMemberCompositeKey compKey);

  List<Cart> selectAllByMember(ProductAndMemberCompositeKey compKey);

  int getCartTotalPage();

  int bulkDelete(ProductAndMemberCompositeKey compKey);

  List<Cart> selectAll(ProductAndMemberCompositeKey compKey);

  Optional<Cart> select(ProductAndMemberCompositeKey compKey);

  int insert(Cart cart);

  int update(ProductAndMemberCompositeKey compKey);

  int update(ProductAndMemberCompositeKey compKey, Long updatedQuantity);

  int delete(ProductAndMemberCompositeKey compKey);
}
