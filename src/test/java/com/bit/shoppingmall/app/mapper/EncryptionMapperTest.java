package com.bit.shoppingmall.app.mapper;

import com.bit.shoppingmall.app.entity.Encryption;
import com.bit.shoppingmall.app.entity.Member;
import com.bit.shoppingmall.app.utils.CipherUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EncryptionMapperTest {

    @Autowired EncryptionMapper encryptionMapper;
    @Autowired MemberMapper memberMapper;

    @Test
    @DisplayName("생성된 salt 값과 회원의 아이디, 이메일을 가지고 encryption에 저장한다.")
    void encryption_insert_test() throws Exception {
        // given
        String salt = createSalt();
        Member member = createMember();
        memberMapper.insert(member);

        Encryption encryption = Encryption.from(member, salt);

        //when
        encryptionMapper.insert(encryption);
        //then
        Encryption getEncryption = encryptionMapper.selectByEmail(member.getEmail()).get();
        assertThat(salt).isEqualTo(getEncryption.getSalt());
    }

    private Member createMember() {
        return Member.builder().email("user01@naver.com").password("test").name("테스트").build();
    }

    private String createSalt() throws NoSuchAlgorithmException {
        byte[] key = CipherUtil.generateKey("AES", 128);
        String salt = CipherUtil.byteArrayToHex(key);
        return salt;
    }
}