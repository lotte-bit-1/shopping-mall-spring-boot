package com.bit.shoppingmall.app.service.likes;

import com.bit.shoppingmall.app.dto.likes.request.LikesSelectForPage;
import com.bit.shoppingmall.app.dto.likes.response.LikesListWithPagination;
import com.bit.shoppingmall.app.dto.paging.Pagination;
import com.bit.shoppingmall.app.dto.product.ProductListItemOfLike;
import com.bit.shoppingmall.app.entity.Likes;
import com.bit.shoppingmall.app.entity.ProductAndMemberCompositeKey;
import com.bit.shoppingmall.app.exception.likes.LikesEntityDuplicateException;
import com.bit.shoppingmall.app.exception.likes.LikesEntityNotFoundException;
import com.bit.shoppingmall.app.mapper.LikesMapper;
import com.bit.shoppingmall.app.mapper.ProductMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductLikesService {

  private final LikesMapper likesMapper;
  private final ProductMapper productMapper;

  public LikesListWithPagination getMemberLikes(Long memberId, Integer curPage) throws Exception {
    try {
      int perPage = 5;
      int totalPage = (int) Math.ceil((float) likesMapper.selectTotalCount(memberId) / perPage);

      int start = (curPage - 1) * perPage;
      LikesSelectForPage likesSelectForPage =
          LikesSelectForPage.builder().memberId(memberId).start(start).PerPage(perPage).build();

      List<Long> productIdList = likesMapper.selectall(likesSelectForPage);
      if (productIdList.isEmpty()) {
        throw new LikesEntityNotFoundException();
      }

      List<ProductListItemOfLike> memberLikesList =
          productMapper.selectProductListItemOfLike(productIdList);

      Pagination paging =
          Pagination.builder()
              .currentPage(Math.toIntExact(curPage))
              .totalPage(totalPage)
              .perPage(perPage)
              .build();

      return LikesListWithPagination.builder().list(memberLikesList).paging(paging).build();
    } catch (LikesEntityNotFoundException e) {
      e.printStackTrace();
      return LikesListWithPagination.builder().build();
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception(e.getMessage());
    }
  }

  public int addLikes(ProductAndMemberCompositeKey productAndMemberCompositeKey) throws Exception {
    int res = 0;
    try {
      res =
          likesMapper.insert(
              new Likes(
                  productAndMemberCompositeKey.getMemberId(),
                  productAndMemberCompositeKey.getProductId())
          );
    } catch (DataIntegrityViolationException e) {
      e.printStackTrace();
      throw new LikesEntityDuplicateException();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    return res;
  }

  public int removeLikes(ProductAndMemberCompositeKey productAndMemberCompositeKey) throws Exception {
    int res = 0;
    try {
      res = likesMapper.delete(productAndMemberCompositeKey);
      if (res == 0) {
        throw new LikesEntityNotFoundException();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return res;
  }

  public int removeSomeLikes(List<ProductAndMemberCompositeKey> keyList) throws Exception {
    int res = 0;
    try {
      for (ProductAndMemberCompositeKey key : keyList) {
        res = likesMapper.delete(key) > 0 ? res + 1 : res;
      }
      if (res == 0) {
        throw new LikesEntityNotFoundException();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return res;
  }
}
