package com.bit.shoppingmall.app.mapstruct;

import com.bit.shoppingmall.app.dto.address.AddressInfo;
import com.bit.shoppingmall.app.dto.address.AddressInfo.AddressInfoBuilder;
import com.bit.shoppingmall.app.entity.Address;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-04T19:08:47+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.20 (Oracle Corporation)"
)
@Component
public class AddressMapStructMapperImpl implements AddressMapStructMapper {

    @Override
    public AddressInfo toAddressInfo(Address address) {
        if ( address == null ) {
            return null;
        }

        AddressInfoBuilder addressInfo = AddressInfo.builder();

        addressInfo.id( address.getId() );
        addressInfo.roadName( address.getRoadName() );
        addressInfo.addrDetail( address.getAddrDetail() );

        return addressInfo.build();
    }

    @Override
    public List<AddressInfo> toAddressInfoList(List<Address> addresses) {
        if ( addresses == null ) {
            return null;
        }

        List<AddressInfo> list = new ArrayList<AddressInfo>( addresses.size() );
        for ( Address address : addresses ) {
            list.add( toAddressInfo( address ) );
        }

        return list;
    }
}
