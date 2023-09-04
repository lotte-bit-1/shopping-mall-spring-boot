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
import lombok.extern.slf4j.Slf4j;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Slf4j
public class ProductListWithPagination {

  private List<ProductListItem> item;
  private Pagination paging;

  public static ProductListWithPagination makeListWithPaging(
      List<ProductListItem> item, int totalPage, int currentPage) {
    int perPage = 9;

    Pagination paging =
        Pagination.builder().perPage(perPage).totalPage(totalPage).currentPage(currentPage).build();
    return ProductListWithPagination.<List<ProductListItem>, Pagination>builder()
        .item(item)
        .paging(paging)
        .build();
  }
}
