package com.bit.shoppingmall.app.dao;

import com.bit.shoppingmall.app.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberDaoTest {

    @Autowired MemberDao memberDao;

    @Test
    void test() {
        Member select = memberDao.select(6L);
        System.out.println(select.getEmail());
    }

}