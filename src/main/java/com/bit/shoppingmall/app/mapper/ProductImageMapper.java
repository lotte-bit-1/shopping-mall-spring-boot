package com.bit.shoppingmall.app.mapper;

import com.bit.shoppingmall.app.entity.ProductImage;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductImageMapper {
int delete(Long id);
  int insert(ProductImage image);
  int update(ProductImage image);
  ProductImage select(Long id);
  List<ProductImage> selectAll();
}
