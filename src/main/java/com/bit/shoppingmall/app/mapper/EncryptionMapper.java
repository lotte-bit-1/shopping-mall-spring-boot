package com.bit.shoppingmall.app.mapper;

import com.bit.shoppingmall.app.entity.Encryption;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface EncryptionMapper {

    Optional<Encryption> select(Long id);

    Optional<Encryption> selectByEmail(String email);

    List<Encryption> selectall();

    void insert(Encryption encryption);

    void update(Encryption encryption);

    void delete(Long id);


}
