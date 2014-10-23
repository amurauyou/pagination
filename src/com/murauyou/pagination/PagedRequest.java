package com.murauyou.pagination;

/**
 * Author: Anton Murauyou
 * Date: 10/23/14
 * Time: 12:55
 *
 * The following pagination implementation works both ways: with offset + limit and page number + page size pairs.
 * Pagination object is used to page requests for lists where elements index, as usually, start with 0
 */
public class PagedRequest implements Pagination {
    private final int offset;
    private final int limit;

    private PagedRequest(int offset, int limit) {
        if (offset < 0) {
            throw new IllegalArgumentException("Pagination offset can't be less than 0.");
        }
        if (limit < 0) {
            throw new IllegalArgumentException("Pagination limit can't be less than 0.");
        }

        this.offset = offset;
        this.limit = limit;
    }

    /**
     * Returns first element index. Elements index starts from 0
     *
     * @return first element index
     */
    @Override
    public int getFirst() {
        return offset;
    }

    /**
     * Returns last included in page element index. Elements index starts from 0
     *
     * @return last element index in page
     */
    @Override
    public int getLast() {
        return ((offset + limit) - 1);
    }

    /**
     * Returns pagination offset. Offset starts from 0 and includes the first element
     *
     * @return pagination offset
     */
    @Override
    public int getOffset() {
        return offset;
    }

    /**
     * Returns pagination limit. Limit is always greater than 0 - used to return number of elements
     *
     * @return pagination limit
     */
    @Override
    public int getLimit() {
        return limit;
    }

    /**
     * Returns pagination page number. Page number starts from 1
     *
     * @return pagination page number
     */
    @Override
    public int getPageNumber() {
        return (offset / limit) + 1;
    }

    /**
     * Returns pagination page size. Page size is always greater than 0 - used to return number of elements
     *
     * @return pagination page size
     */
    @Override
    public int getPageSize() {
        return limit;
    }

    /**
     * Creates a new pagination object representing the next page
     *
     * @return new pagination object with offset shifted by offset+limit
     */
    @Override
    public Pagination getNext() {
        return new PagedRequest(offset + limit, limit);
    }

    /**
     * Creates a new pagination object representing the previous page
     *
     * @return new pagination object with offset shifted by offset-limit
     */
    @Override
    public Pagination getPrevious() {
        if (limit >= offset) {
            //TODO: Make possible control this functionality to return first page or null or throw exception once lower bound is hit
            return new PagedRequest(0, limit);
        } else {
            return new PagedRequest(offset - limit, limit);
        }
    }

    /**
     * Creates a new pagination object of the very first page with specified limit
     *
     * @param limit pagination limit
     * @return new pagination object with offset = 0 (starting from the very beginning) and limit = 'limit'
     */
    public static Pagination fromStartWithLimit(int limit) {
        return new PagedRequest(0, limit);
    }

    /**
     * Creates a new pagination object of the very first page with specified page size
     *
     * @param pageSize pagination page size
     * @return new pagination object with page number = 1 (starting from the very beginning) and page size = 'pageSize'
     */
    public static Pagination fromStartWithPageSize(int pageSize) {
        if (pageSize < 0) {
            throw new IllegalArgumentException("Pagination page size can't be less than 0.");
        }

        return new PagedRequest(0, pageSize);
    }

    /**
     * Creates a new pagination object of the specified offset and limit
     *
     * @param offset pagination offset
     * @param limit pagination limit
     * @return new pagination object with offset = 'offset' and limit = 'limit'
     */
    public static Pagination ofOffsetAndLimit(int offset, int limit) {
        return new PagedRequest(offset, limit);
    }

    /**
     * Creates a new pagination object of the specified page number and page size
     *
     * @param pageNumber pagination page number
     * @param pageSize pagination page size
     * @return new pagination object with page number = 'pageNumber' and page size = 'pageSize'
     */
    public static Pagination ofPageNumberAndPageSize(int pageNumber, int pageSize) {
        if (pageNumber < 1) {
            throw new IllegalArgumentException("Pagination page number can't be less than 1.");
        }
        if (pageSize < 0) {
            throw new IllegalArgumentException("Pagination page size can't be less than 0.");
        }

        return new PagedRequest((pageNumber - 1) * pageSize, pageSize);
    }

    @Override
    @SuppressWarnings("")
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PagedRequest that = (PagedRequest) o;

        if (limit != that.limit) return false;
        if (offset != that.offset) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = offset;
        result = 31 * result + limit;
        return result;
    }

    @Override
    public String toString() {
        return "PagedRequest { " +
                "offset = " + getOffset() + ", limit = " + getLimit() +
                " / " +
                "page number = " + getPageNumber() + ", page size = " + getPageSize() +
                " }";
    }
}
