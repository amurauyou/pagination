package com.murauyou.pagination;

import org.junit.Assert;
import org.junit.Test;

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
            Assert.fail("Limit can't be set to -1");
        } catch (IllegalArgumentException ex) {
            // Not tracked
        }

        try {
            PagedRequest.fromStartWithPageSize(-1);
            Assert.fail("Page size can't be set to -1");
        } catch (IllegalArgumentException ex) {
            // Not tracked
        }

        try {
            PagedRequest.ofOffsetAndLimit(0, -1);
            Assert.fail("Limit can't be set to -1");
        } catch (IllegalArgumentException ex) {
            // Not tracked
        }

        try {
            PagedRequest.ofPageNumberAndPageSize(1, -1);
            Assert.fail("Page size can't be set to -1");
        } catch (IllegalArgumentException ex) {
            // Not tracked
        }

        try {
            PagedRequest.ofOffsetAndLimit(-1, 10);
            Assert.fail("Offset can't be set to -1");
        } catch (IllegalArgumentException ex) {
            // Not tracked
        }

        try {
            PagedRequest.ofPageNumberAndPageSize(0, 10);
            Assert.fail("Page number can't be set to 0");
        } catch (IllegalArgumentException ex) {
            // Not tracked
        }

        try {
            PagedRequest.ofPageNumberAndPageSize(-1, 10);
            Assert.fail("Page number can't be set to -1");
        } catch (IllegalArgumentException ex) {
            // Not tracked
        }
    }

    @Test
    public void testOffsetLimitPagination() {
        Pagination pagination;

        pagination = PagedRequest.fromStartWithLimit(8);
        Assert.assertEquals(0, pagination.getOffset());
        Assert.assertEquals(8, pagination.getLimit());
        Assert.assertEquals(1, pagination.getPageNumber());
        Assert.assertEquals(8, pagination.getPageSize());

        pagination = PagedRequest.ofOffsetAndLimit(0, 10);
        Assert.assertEquals(0, pagination.getOffset());
        Assert.assertEquals(10, pagination.getLimit());
        Assert.assertEquals(1, pagination.getPageNumber());
        Assert.assertEquals(10, pagination.getPageSize());

        pagination = PagedRequest.ofOffsetAndLimit(13, 5);
        Assert.assertEquals(13, pagination.getOffset());
        Assert.assertEquals(5, pagination.getLimit());
        Assert.assertEquals(3, pagination.getPageNumber());
        Assert.assertEquals(5, pagination.getPageSize());
    }

    @Test
    public void testPageNumberSizePagination() {
        Pagination pagination;

        pagination = PagedRequest.fromStartWithPageSize(7);
        Assert.assertEquals(0, pagination.getOffset());
        Assert.assertEquals(7, pagination.getLimit());
        Assert.assertEquals(1, pagination.getPageNumber());
        Assert.assertEquals(7, pagination.getPageSize());

        pagination = PagedRequest.ofPageNumberAndPageSize(1, 12);
        Assert.assertEquals(0, pagination.getOffset());
        Assert.assertEquals(12, pagination.getLimit());
        Assert.assertEquals(1, pagination.getPageNumber());
        Assert.assertEquals(12, pagination.getPageSize());

        pagination = PagedRequest.ofPageNumberAndPageSize(13, 5);
        Assert.assertEquals(60, pagination.getOffset());
        Assert.assertEquals(5, pagination.getLimit());
        Assert.assertEquals(13, pagination.getPageNumber());
        Assert.assertEquals(5, pagination.getPageSize());
    }

    @Test
    public void testPaginationCommonFeatures() {
        Pagination pagination;

        pagination = PagedRequest.fromStartWithPageSize(7);
        Assert.assertEquals(0, pagination.getFirst());
        Assert.assertEquals(6, pagination.getLast());

        pagination = PagedRequest.fromStartWithLimit(9);
        Assert.assertEquals(0, pagination.getFirst());
        Assert.assertEquals(8, pagination.getLast());

        pagination = PagedRequest.ofOffsetAndLimit(13, 5);
        Assert.assertEquals(13, pagination.getFirst());
        Assert.assertEquals(17, pagination.getLast());

        pagination = PagedRequest.ofPageNumberAndPageSize(4, 5);
        Assert.assertEquals(15, pagination.getFirst());
        Assert.assertEquals(19, pagination.getLast());

        Assert.assertEquals(PagedRequest.ofOffsetAndLimit(8, 5), PagedRequest.ofOffsetAndLimit(13, 5).getPrevious());
        Assert.assertEquals(PagedRequest.ofOffsetAndLimit(0, 5), PagedRequest.ofOffsetAndLimit(3, 5).getPrevious());

        Assert.assertEquals(PagedRequest.ofOffsetAndLimit(10, 5), PagedRequest.ofPageNumberAndPageSize(4, 5).getPrevious());
        Assert.assertEquals(PagedRequest.ofOffsetAndLimit(0, 5), PagedRequest.ofPageNumberAndPageSize(1, 5).getPrevious());

        Assert.assertEquals(PagedRequest.ofOffsetAndLimit(18, 5), PagedRequest.ofOffsetAndLimit(13, 5).getNext());
        Assert.assertEquals(PagedRequest.ofOffsetAndLimit(20, 5), PagedRequest.ofPageNumberAndPageSize(4, 5).getNext());

    }

}
