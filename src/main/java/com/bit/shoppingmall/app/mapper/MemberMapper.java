package com.bit.shoppingmall.app.mapper;

import com.bit.shoppingmall.app.entity.Member;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

  Optional<Member> select(Long id);

  void insert(Member member);
}
