package com.murauyou.pagination;

/**
 * Author: Anton Murauyou
 * Date: 10/23/14
 * Time: 12:56
 *
 * Pagination object driven by offset + limit pair.
 */
public interface OffsetLimitPagination {

    /**
     * Returns pagination offset. Offset starts from 0 and includes the first element
     *
     * @return pagination offset
     */
    int getOffset();

    /**
     * Returns pagination limit. Limit is always greater than 0 - used to return number of elements
     *
     * @return pagination limit
     */
    int getLimit();

}
