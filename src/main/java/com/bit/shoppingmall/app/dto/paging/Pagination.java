package com.bit.shoppingmall.app.dto.paging;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Pagination {

    private int currentPage; // 현재 페이지
    @Builder.Default
    private int perPage = 10; // 페이지당 보여줄 개수
    private int totalPage; // 전체 페이지
}
