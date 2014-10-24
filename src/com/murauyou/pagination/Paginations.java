package com.murauyou.pagination;

import java.util.List;

/**
 * Created by Anton Murauyou on 10/24/14.
 */
public final class Paginations {

    private Paginations() {
    }

    public static Pagination fromStartWithLimit(int limit) {
        return PagedRequest.fromStartWithLimit(limit);
    }

    public static Pagination ofOffsetAndLimit(int offset, int limit) {
        return PagedRequest.ofOffsetAndLimit(offset, limit);
    }

    public static Pagination fromStartWithPageSize(int pageSize) {
        return PagedRequest.fromStartWithPageSize(pageSize);
    }

    public static Pagination ofPageNumberAndPageSize(int pageNumber, int pageSize) {
        return PagedRequest.ofPageNumberAndPageSize(pageNumber, pageSize);
    }

    public static <E> ResponsePage<E> ofResponsePage(List<E> source, Pagination pagination) {
        return PagedResponse.ofResponsePage(source, pagination);
    }

    public static <E> TotalAwareResponsePage<E> ofResponseAndSizeAndPagination(List<E> source, Pagination pagination, int totalSize) {
        return TotalAwarePagedResponse.ofResponseAndSizeAndPagination(source, pagination, totalSize);
    }

}
