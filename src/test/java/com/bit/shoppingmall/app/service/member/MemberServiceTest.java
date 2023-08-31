package com.bit.shoppingmall.app.service.member;

import com.bit.shoppingmall.app.dto.member.request.MemberRegisterDto;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("회원정보를 입력 받아 회원가입을 한다.")
    void register() throws Exception {
        // given
        MemberRegisterDto dto = createMemberRegisterDto();
        //when
        Boolean result = memberService.register(dto);

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("회원 가입 중 이메일이 중복 되는 경우 예외가 발생한다.")
    void register_fail_DuplicatedEmail() throws Exception {
        // given

        MemberRegisterDto dto = createMemberRegisterDto();
        MemberRegisterDto dto2 = createMemberRegisterDto();

        //when
        memberService.register(dto);

        //then
        assertThatThrownBy(() ->
                memberService.register(dto2)).isInstanceOf(DuplicateKeyException.class);
    }

    private MemberRegisterDto createMemberRegisterDto() {
        String email = "user01@naver.com";
        String password = "user1234";
        String name = "비트롯데";
        return new MemberRegisterDto(email, password, name);
    }

}