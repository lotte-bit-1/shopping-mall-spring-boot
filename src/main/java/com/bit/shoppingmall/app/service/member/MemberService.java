package com.bit.shoppingmall.app.service.member;

import com.bit.shoppingmall.app.dto.member.request.MemberRegisterDto;
import com.bit.shoppingmall.app.dto.member.response.MemberDetail;
import com.bit.shoppingmall.app.entity.Encryption;
import com.bit.shoppingmall.app.entity.Member;
import com.bit.shoppingmall.app.exception.member.MemberEntityNotFoundException;
import com.bit.shoppingmall.app.mapper.EncryptionMapper;
import com.bit.shoppingmall.app.mapper.MemberMapper;
import com.bit.shoppingmall.app.utils.CipherUtil;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;
    private final EncryptionMapper encryptionMapper;

    @Transactional
    public Boolean addMember(MemberRegisterDto dto) throws Exception {
        String salt = createSalt();
        String hashedPassword = createHashedPassword(dto.getPassword(), salt);

        Member member = dto.toEntity(hashedPassword);
        memberMapper.insert(member);

        Encryption encryption = Encryption.from(member, salt);
        int result = encryptionMapper.insert(encryption);

        return result == 1 ? true : false;
    }

    public MemberDetail findMember(Long id) {
        Member member =
                memberMapper.select(id).orElseThrow(MemberEntityNotFoundException::new);
        return MemberDetail.of(member);
    }

    public Boolean isDuplicatedEmail(String email) {
        int result = memberMapper.countByEmail(email);
        return result == 0 ? true : false;
    }

    private String createHashedPassword(String password, String salt) throws Exception {
        CipherUtil.getSHA256(password, salt);
        return new String(CipherUtil.getSHA256(password, salt)).replaceAll(" ", "");
    }

    private String createSalt() throws NoSuchAlgorithmException {
        byte[] key = CipherUtil.generateKey("AES", 128);
        String salt = CipherUtil.byteArrayToHex(key);
        return salt;
    }
}
