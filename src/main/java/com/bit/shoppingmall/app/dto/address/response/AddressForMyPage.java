package com.bit.shoppingmall.app.dto.address.response;

import com.bit.shoppingmall.app.dto.address.AddressInfo;
import com.bit.shoppingmall.app.entity.Address;
import com.bit.shoppingmall.app.mapstruct.AddressMapStructMapper;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class AddressForMyPage {
  private AddressInfo defaultAddress;
  private List<AddressInfo> subAddresses;

  public static AddressForMyPage getAddressListForMyPage(
      Address defualtAddress, List<Address> subAddresses) {
    AddressMapStructMapper instance = AddressMapStructMapper.INSTANCE;
    AddressInfo addressInfo = instance.toAddressInfo(defualtAddress);
    List<AddressInfo> addressInfos = instance.toAddressInfoList(subAddresses);
    return AddressForMyPage.builder()
        .defaultAddress(addressInfo)
        .subAddresses(addressInfos)
        .build();
  }
}
