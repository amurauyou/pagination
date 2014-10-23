package com.murauyou.pagination;

/**
 * Author: Anton Murauyou
 * Date: 10/23/14
 * Time: 12:56
 *
 * The following pagination interface works both ways: with offset + limit and page number + page size pairs.
 * Pagination object is used to page requests for lists where elements index, as usually, start with 0
 */
public interface Pagination extends OffsetLimitPagination, PageNumberSizePagination {

    /**
     * Returns first element index. Elements index starts from 0
     *
     * @return first element index
     */
    int getFirst();

    /**
     * Returns last included in page element index. Elements index starts from 0
     *
     * @return last element index in page
     */
    int getLast();

    /**
     * Creates a new pagination object representing the next page
     *
     * @return new pagination object with offset shifted by offset+limit
     */
    Pagination getNext();

    /**
     * Creates a new pagination object representing the previous page
     *
     * @return new pagination object with offset shifted by offset-limit
     */
    Pagination getPrevious() throws NoPageAvailableException;

}
