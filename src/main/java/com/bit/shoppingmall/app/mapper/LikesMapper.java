package com.bit.shoppingmall.app.mapper;

import com.bit.shoppingmall.app.dto.likes.request.LikesSelectForPage;
import com.bit.shoppingmall.app.entity.Likes;
import com.bit.shoppingmall.app.entity.ProductAndMemberCompositeKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface LikesMapper {

    Optional<Likes> select(ProductAndMemberCompositeKey productAndMemberCompositeKey);

    List<Long> selectall(LikesSelectForPage likesSelectForPage);

    Long selectTotalCount(Long id);

    int insert(Likes likes);

    int delete(ProductAndMemberCompositeKey productAndMemberCompositeKey);

}
