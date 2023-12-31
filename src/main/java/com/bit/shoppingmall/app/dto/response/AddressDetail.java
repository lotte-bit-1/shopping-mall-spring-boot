package com.bit.shoppingmall.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddressDetail {

  private Boolean isDefault;
  private String roadName;
  private String addrDetail;
  private String zipCode;
}
