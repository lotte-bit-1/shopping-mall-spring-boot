package com.bit.shoppingmall.app.service.order.manager;

import com.bit.shoppingmall.app.dto.cart.CartAndProductDto;
import com.bit.shoppingmall.app.entity.ProductAndMemberCompositeKey;
import com.bit.shoppingmall.app.exception.order.OrderCartDeleteException;
import com.bit.shoppingmall.app.mapper.CartMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderCartManager {

  private final CartMapper cartMapper;

  public List<CartAndProductDto> determineCartAndProductDtos(Long memberId) {
    return cartMapper.selectAllCartAndProductByMember(memberId);
  }

  public void deleteAll(
          List<ProductAndMemberCompositeKey> productAndMemberCompositeKeys) {
    int deletedRow = cartMapper.bulkDelete(productAndMemberCompositeKeys);
    if (deletedRow != productAndMemberCompositeKeys.size()) {
      throw new OrderCartDeleteException();
    }
  }
}
