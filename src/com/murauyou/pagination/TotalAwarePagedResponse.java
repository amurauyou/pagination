package com.murauyou.pagination;

import java.util.List;

/**
 * Auhtor: Anton Murauyou
 * Date: 10/23/14
 * Time: 19:05
 */
public class TotalAwarePagedResponse<E> extends PagedResponse<E> implements TotalAwareResponsePage<E> {
    private final int totalSize;

    protected TotalAwarePagedResponse(List<E> source, Pagination pagination, int totalSize) {
        super(source, pagination);
        this.totalSize = totalSize;
    }

    @Override
    public int getTotalSize() {
        return totalSize;
    }

    public static <E> TotalAwarePagedResponse<E> ofResponseAndSizeAndPagination(List<E> source, Pagination pagination, int totalSize) {
        return new TotalAwarePagedResponse<E>(source, pagination, totalSize);
    }
}
