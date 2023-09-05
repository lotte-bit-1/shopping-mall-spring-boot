package com.bit.shoppingmall.app.mapper;

import com.bit.shoppingmall.app.dto.cart.CartAndProductDto;
import com.bit.shoppingmall.app.entity.Cart;
import com.bit.shoppingmall.app.entity.ProductAndMemberCompositeKey;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CartMapper {

  List<CartAndProductDto> selectAllCartAndProductByMember(Long memberId);

  Optional<Cart> select(ProductAndMemberCompositeKey compKey);

  List<Cart> selectAllByMember(Long memberId);

  Long getCartTotalPage(Long memberId);

  int bulkDelete(List<ProductAndMemberCompositeKey> productAndMemberCompositeKeys);

  List<Cart> selectAll(ProductAndMemberCompositeKey compKey);

  int insert(Cart cart);

  int update(Cart cart);

  int delete(ProductAndMemberCompositeKey compKey);
}
