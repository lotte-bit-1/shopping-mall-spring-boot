package com.bit.shoppingmall.app.service.member;

import com.bit.shoppingmall.app.dto.member.request.LoginDto;
import com.bit.shoppingmall.app.dto.member.request.MemberRegisterDto;
import com.bit.shoppingmall.app.dto.member.response.MemberDetail;
import com.bit.shoppingmall.app.exception.member.LoginFailException;
import com.bit.shoppingmall.app.exception.member.MemberEntityNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("회원정보를 입력 받아 회원가입을 한다.")
    @Order(1)
    void register() throws Exception {
        // given
        MemberRegisterDto dto = createMemberRegisterDto();
        //when
        Boolean result = memberService.addMember(dto);

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("회원 가입 중 이메일이 중복 되는 경우 예외가 발생한다.")
    @Order(2)
    void register_fail_DuplicatedEmail() throws Exception {
        // given

        MemberRegisterDto dto = createMemberRegisterDto();
        MemberRegisterDto dto2 = createMemberRegisterDto();

        //when
        memberService.addMember(dto);

        //then
        assertThatThrownBy(() ->
                memberService.addMember(dto2)).isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    @DisplayName("회원 아이디(pk)로 내 정보 조회를 할 수 있다.")
    @Order(3)
    void getMemberDetail() throws Exception {
        // given
        MemberRegisterDto memberRegisterDto = createMemberRegisterDto();
        memberService.addMember(memberRegisterDto);
        Long expectedMemberId = 4L;
        // when
        MemberDetail memberDetail = memberService.findMember(expectedMemberId);
        // then
        assertThat(memberDetail.getId()).isEqualTo(expectedMemberId);
    }

    @Test
    @DisplayName("존재하지 않는 회원 아이디로 내정보 조회 시 예외가 발생한다.")
    @Order(4)
    void getMemberDetail_fail() throws Exception {
        // given
        long memberId = 1000L;

        // when then
        assertThatThrownBy(() ->
                memberService.findMember(memberId)).isInstanceOf(MemberEntityNotFoundException.class);
    }

    @Test
    @DisplayName("이메일 중복 검사 후 중복된 이메일이 없는 경우 true를 반환 한다.")
    @Order(5)
    void email_duplicate_success() throws Exception {
        // given
        MemberRegisterDto memberRegisterDto = createMemberRegisterDto();
        memberService.addMember(memberRegisterDto);
        String email = "user0003@naver.com";
        // when
        boolean result = memberService.isDuplicatedEmail(email);
        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("이메일 중복 검사 후 중복된 이메일이 있는 경우 false를 반환한다.")
    @Order(6)
    void email_duplicate_fail() throws Exception {
        // given
        MemberRegisterDto memberRegisterDto = createMemberRegisterDto();
        memberService.addMember(memberRegisterDto);
        String email = "user01@naver.com";
        // when
        boolean result = memberService.isDuplicatedEmail(email);
        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("데이터 베이스에 저장된 이메일과 패스워드로 로그인 성공 시 loginMember를 반환한다.")
    @Order(7)
    void login_success() throws Exception {
        // given
        MemberRegisterDto dto = createMemberRegisterDto();
        memberService.addMember(dto);

        LoginDto loginDto = new LoginDto(dto.getEmail(), dto.getPassword());
        // when
        MemberDetail loginMember = memberService.login(loginDto);
        // then
        assertThat(dto.getEmail()).isEqualTo(loginMember.getEmail());
    }

    @Test
    @DisplayName("로그인 실패 시 예외가 발생한다.")
    @Order(8)
    void login_fail() throws Exception {
        // given
        MemberRegisterDto dto = createMemberRegisterDto();
        memberService.addMember(dto);

        String failPassword = "failPassword";
        LoginDto loginDto = new LoginDto(dto.getEmail(), failPassword);

        // when then
        assertThatThrownBy(() -> memberService.login(loginDto)).isInstanceOf(LoginFailException.class);
    }

    private MemberRegisterDto createMemberRegisterDto() {
        String email = "user01@naver.com";
        String password = "user1234";
        String name = "비트롯데";
        return new MemberRegisterDto(email, password, name);
    }

}