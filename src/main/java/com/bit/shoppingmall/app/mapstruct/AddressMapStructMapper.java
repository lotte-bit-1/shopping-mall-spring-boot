package com.bit.shoppingmall.app.mapstruct;

import com.bit.shoppingmall.app.dto.address.AddressInfo;
import com.bit.shoppingmall.app.entity.Address;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AddressMapStructMapper {
  AddressMapStructMapper INSTANCE = Mappers.getMapper(AddressMapStructMapper.class);

  @Named("ADDR")
  AddressInfo toAddressInfo(Address address);

  @IterableMapping(qualifiedByName = "ADDR")
  List<AddressInfo> toAddressInfoList(List<Address> addresses);
}
