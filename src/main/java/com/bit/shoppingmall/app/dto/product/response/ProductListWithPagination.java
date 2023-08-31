package com.bit.shoppingmall.app.dto.product.response;

import com.bit.shoppingmall.app.dto.paging.Pagination;
import com.bit.shoppingmall.app.dto.product.ProductListItem;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ProductListWithPagination {

  private List<ProductListItem> item;
  private Pagination paging;

  public static ProductListWithPagination makeListWithPaging(
      List<ProductListItem> item, Pagination pagination, int totalPages) {
    Pagination paging =
        Pagination.builder()
            .perPage(pagination.getPerPage())
            .totalPage(totalPages)
            .currentPage(pagination.getCurrentPage())
            .build();
    return ProductListWithPagination.<List<ProductListItem>, Pagination>builder()
        .item(item)
        .paging(paging)
        .build();
  }
}
