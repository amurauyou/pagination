package com.murauyou.pagination;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Author: Anton Murauyou
 * Date: 10/23/14
 * Time: 13:24
 */
public class PagedRequestTest {

    @Test
    public void testInitializationFailures() {

        PagedRequest.fromStartWithLimit(0);

        PagedRequest.fromStartWithPageSize(0);

        PagedRequest.ofOffsetAndLimit(0, 0);

        PagedRequest.ofPageNumberAndPageSize(1, 0);

        try {
            PagedRequest.fromStartWithLimit(-1);
            fail("Limit can't be set to -1");
        } catch (IllegalArgumentException ex) {
            // Not tracked
        }

        try {
            PagedRequest.fromStartWithPageSize(-1);
            fail("Page size can't be set to -1");
        } catch (IllegalArgumentException ex) {
            // Not tracked
        }

        try {
            PagedRequest.ofOffsetAndLimit(0, -1);
            fail("Limit can't be set to -1");
        } catch (IllegalArgumentException ex) {
            // Not tracked
        }

        try {
            PagedRequest.ofPageNumberAndPageSize(1, -1);
            fail("Page size can't be set to -1");
        } catch (IllegalArgumentException ex) {
            // Not tracked
        }

        try {
            PagedRequest.ofOffsetAndLimit(-1, 10);
            fail("Offset can't be set to -1");
        } catch (IllegalArgumentException ex) {
            // Not tracked
        }

        try {
            PagedRequest.ofPageNumberAndPageSize(0, 10);
            fail("Page number can't be set to 0");
        } catch (IllegalArgumentException ex) {
            // Not tracked
        }

        try {
            PagedRequest.ofPageNumberAndPageSize(-1, 10);
            fail("Page number can't be set to -1");
        } catch (IllegalArgumentException ex) {
            // Not tracked
        }
    }

    @Test
    public void testOffsetLimitPagination() {
        Pagination pagination;

        pagination = PagedRequest.fromStartWithLimit(8);
        assertEquals(0, pagination.getOffset());
        assertEquals(8, pagination.getLimit());
        assertEquals(1, pagination.getPageNumber());
        assertEquals(8, pagination.getPageSize());

        pagination = PagedRequest.ofOffsetAndLimit(0, 10);
        assertEquals(0, pagination.getOffset());
        assertEquals(10, pagination.getLimit());
        assertEquals(1, pagination.getPageNumber());
        assertEquals(10, pagination.getPageSize());

        pagination = PagedRequest.ofOffsetAndLimit(13, 5);
        assertEquals(13, pagination.getOffset());
        assertEquals(5, pagination.getLimit());
        assertEquals(3, pagination.getPageNumber());
        assertEquals(5, pagination.getPageSize());
    }

    @Test
    public void testPageNumberSizePagination() {
        Pagination pagination;

        pagination = PagedRequest.fromStartWithPageSize(7);
        assertEquals(0, pagination.getOffset());
        assertEquals(7, pagination.getLimit());
        assertEquals(1, pagination.getPageNumber());
        assertEquals(7, pagination.getPageSize());

        pagination = PagedRequest.ofPageNumberAndPageSize(1, 12);
        assertEquals(0, pagination.getOffset());
        assertEquals(12, pagination.getLimit());
        assertEquals(1, pagination.getPageNumber());
        assertEquals(12, pagination.getPageSize());

        pagination = PagedRequest.ofPageNumberAndPageSize(13, 5);
        assertEquals(60, pagination.getOffset());
        assertEquals(5, pagination.getLimit());
        assertEquals(13, pagination.getPageNumber());
        assertEquals(5, pagination.getPageSize());
    }

    @Test
    public void testPaginationCommonFeatures() throws NoPageAvailableException {
        Pagination pagination;

        pagination = PagedRequest.fromStartWithPageSize(7);
        assertEquals(0, pagination.getFirst());
        assertEquals(6, pagination.getLast());

        pagination = PagedRequest.fromStartWithLimit(9);
        assertEquals(0, pagination.getFirst());
        assertEquals(8, pagination.getLast());

        pagination = PagedRequest.ofOffsetAndLimit(13, 5);
        assertEquals(13, pagination.getFirst());
        assertEquals(17, pagination.getLast());

        pagination = PagedRequest.ofPageNumberAndPageSize(4, 5);
        assertEquals(15, pagination.getFirst());
        assertEquals(19, pagination.getLast());

        assertEquals(PagedRequest.ofOffsetAndLimit(8, 5), PagedRequest.ofOffsetAndLimit(13, 5).getPrevious());

        assertEquals(PagedRequest.ofOffsetAndLimit(10, 5), PagedRequest.ofPageNumberAndPageSize(4, 5).getPrevious());

        assertEquals(PagedRequest.ofOffsetAndLimit(18, 5), PagedRequest.ofOffsetAndLimit(13, 5).getNext());
        assertEquals(PagedRequest.ofOffsetAndLimit(20, 5), PagedRequest.ofPageNumberAndPageSize(4, 5).getNext());

        try {
            assertEquals(PagedRequest.ofOffsetAndLimit(0, 5), PagedRequest.ofOffsetAndLimit(3, 5).getPrevious());
            fail("No previous pages exist. Currently on the very first page.");
        } catch (NoPageAvailableException ex) {
            // No previous page really was there
        }

        try {
            assertEquals(PagedRequest.ofOffsetAndLimit(0, 5), PagedRequest.ofPageNumberAndPageSize(1, 5).getPrevious());
            fail("No previous pages exist. Currently on the very first page.");
        } catch (NoPageAvailableException ex) {
            // No previous page really was there
        }

    }

}
