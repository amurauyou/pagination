package com.murauyou.pagination;

/**
 * Author: Anton Murauyou
 * Date: 10/23/14
 * Time: 12:57
 *
 * Pagination object driven by page number + page size.
 */
public interface PageNumberSizePagination {

    /**
     * Returns pagination page number. Page number starts from 1
     *
     * @return pagination page number
     */
    int getPageNumber();

    /**
     * Returns pagination page size. Page size is always greater than 0 - used to return number of elements
     *
     * @return pagination page size
     */
    int getPageSize();

}
