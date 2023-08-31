package com.bit.shoppingmall.app.mapper;

import com.bit.shoppingmall.app.entity.Member;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberDaoTest {

  @Autowired
  MemberMapper memberDao;

  @Test
  void test() {
    Member member = createMember();
    memberDao.insert(member);
    Optional<Member> select1 = memberDao.select(1L);
    System.out.println(select1.get().getEmail());
  }

  private Member createMember() {
    return Member.builder().email("user01@naver.com").password("test").name("테스트").build();
  }

}