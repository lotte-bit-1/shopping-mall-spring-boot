package com.bit.shoppingmall.app.mapper;

import com.bit.shoppingmall.app.dto.member.request.LoginDto;
import com.bit.shoppingmall.app.dto.member.response.OrderMemberDetail;
import com.bit.shoppingmall.app.entity.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {
    Optional<Member> select(Long id);

    Optional<LoginDto> selectByEmailAndPassword(LoginDto dto);

    Optional<Member> selectByEmail(String email);

    Optional<OrderMemberDetail> selectAddressAndCouponById(Long id);

    List<Member> selectAll();

    int countByEmail(String email);

    int insert(Member member);

    int update(Member member);

    int delete(Long id);

}
