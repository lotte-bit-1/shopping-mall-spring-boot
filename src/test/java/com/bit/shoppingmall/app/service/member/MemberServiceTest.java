package com.bit.shoppingmall.app.service.member;

import com.bit.shoppingmall.app.dto.member.request.MemberRegisterDto;
import com.bit.shoppingmall.app.dto.member.response.MemberDetail;
import com.bit.shoppingmall.app.exception.member.MemberEntityNotFoundException;
import com.bit.shoppingmall.app.mapper.MemberMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
        Boolean result = memberService.addMember(dto);

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
        memberService.addMember(dto);

        //then
        assertThatThrownBy(() ->
                memberService.addMember(dto2)).isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    @DisplayName("회원 아이디(pk)로 내 정보 조회를 할 수 있다.")
    void getMemberDetail() throws Exception {
        // given
        MemberRegisterDto memberRegisterDto = createMemberRegisterDto();
        memberService.addMember(memberRegisterDto);
        Long expectedMemberId = 2L;
        // when
        MemberDetail memberDetail = memberService.findMember(expectedMemberId);
        // then
        assertThat(memberDetail.getId()).isEqualTo(expectedMemberId);
    }

    @Test
    @DisplayName("존재하지 않는 회원 아이디로 내정보 조회 시 예외가 발생한다.")
    void getMemberDetail_fail() throws Exception {
        // given
        long memberId = 1000L;

        // when then
        assertThatThrownBy(() ->
                memberService.findMember(memberId)).isInstanceOf(MemberEntityNotFoundException.class);
    }

    private MemberRegisterDto createMemberRegisterDto() {
        String email = "user01@naver.com";
        String password = "user1234";
        String name = "비트롯데";
        return new MemberRegisterDto(email, password, name);
    }

}