package com.bit.shoppingmall.app.service.address;

import com.bit.shoppingmall.app.entity.Address;
import com.bit.shoppingmall.app.exception.address.AddressInsertException;
import com.bit.shoppingmall.app.mapper.AddressMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AddressService {
  private final AddressMapper addressMapper;

  @Transactional
  public void addAddress(Address addr, Long memberId) {
    Address address =
        Address.builder()
            .memberId(memberId)
            .addrDetail(addr.getAddrDetail())
            .roadName(addr.getRoadName())
            .zipCode(addr.getZipCode())
            .isDefault(false)
            .build();
    int insert = addressMapper.insert(address);
    if (insert == 0) throw new AddressInsertException();
  }
}