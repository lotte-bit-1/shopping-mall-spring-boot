package com.bit.shoppingmall.app.dao;

import com.bit.shoppingmall.app.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@SpringBootTest
class MemberDaoTest {

    @Autowired
    MemberDao memberDao;

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