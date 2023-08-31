package com.bit.shoppingmall.app.mapper;

import com.bit.shoppingmall.app.entity.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MemberMapper {
    Optional<Member> select(Long id);

    void insert(Member member);
}
