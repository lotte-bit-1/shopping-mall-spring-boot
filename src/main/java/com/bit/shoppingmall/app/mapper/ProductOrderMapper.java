package com.bit.shoppingmall.app.mapper;

import com.bit.shoppingmall.app.entity.ProductOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductOrderMapper {

    int insert(ProductOrder productOrder);

    List<ProductOrder> selectAllByOrderId(Long orderId);

    int bulkInsert(List<ProductOrder> productOrders);
}
