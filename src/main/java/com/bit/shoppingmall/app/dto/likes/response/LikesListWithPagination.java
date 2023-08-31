package com.bit.shoppingmall.app.dto.likes.response;

import com.bit.shoppingmall.app.dto.paging.Pagination;
import com.bit.shoppingmall.app.dto.product.ProductListItemOfLike;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class LikesListWithPagination {

    private List<ProductListItemOfLike> list;
    private Pagination paging;
}
