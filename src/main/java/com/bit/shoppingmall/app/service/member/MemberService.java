package com.bit.shoppingmall.app.service.member;

import com.bit.shoppingmall.app.dto.member.request.MemberRegisterDto;
import com.bit.shoppingmall.app.entity.Encryption;
import com.bit.shoppingmall.app.entity.Member;
import com.bit.shoppingmall.app.mapper.EncryptionMapper;
import com.bit.shoppingmall.app.mapper.MemberMapper;
import com.bit.shoppingmall.app.utils.CipherUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;
    private final EncryptionMapper encryptionMapper;

    public Boolean register(MemberRegisterDto dto) throws Exception {
        String salt = createSalt();
        String hashedPassword = createHashedPassword(dto.getPassword(), salt);

        Member member = dto.toEntity(hashedPassword);
        memberMapper.insert(member);

        Encryption encryption = Encryption.from(member, salt);
        int result = encryptionMapper.insert(encryption);

        return result == 1 ? true : false;
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
