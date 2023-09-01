package com.bit.shoppingmall.app.mapper;

import com.bit.shoppingmall.app.dto.cart.CartAndProductDto;
import com.bit.shoppingmall.app.entity.Cart;
import com.bit.shoppingmall.app.entity.ProductAndMemberCompositeKey;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CartMapper {

  List<CartAndProductDto> selectAllCartAndProductByMember(Long memberId);

  List<Cart> selectAllByMember(Long memberId);

  int getCartTotalPage();

  int bulkDelete(List<ProductAndMemberCompositeKey> productAndMemberCompositeKeys);

  List<Cart> selectAll(ProductAndMemberCompositeKey compKey);

  Optional<Cart> select(ProductAndMemberCompositeKey compKey);

  int insert(Cart cart);

  int update(ProductAndMemberCompositeKey compKey);

  int update(ProductAndMemberCompositeKey compKey, Long updatedQuantity);

  int delete(ProductAndMemberCompositeKey compKey);
}
