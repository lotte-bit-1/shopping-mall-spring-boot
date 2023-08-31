package com.bit.shoppingmall.app.mapper;

import com.bit.shoppingmall.app.entity.Address;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressMapper {
  int delete(Long id);

  int update(Address address);

  int insert(Address address);

  List<Address> selectAll();

  Address select(Long id);
}
