package com.murauyou.pagination;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Author: Anton Murauyou
 * Date: 10/23/14
 * Time: 12:55
 */
public class PagedResponse<E> implements ResponsePage<E> {
    private final List<E> source;
    private final Pagination pagination;

    protected PagedResponse(List<E> source, Pagination pagination) {
        if (source == null) {
            throw new IllegalArgumentException("Source list cannot be null");
        }
        if (pagination == null) {
            throw new IllegalArgumentException("Pagination cannot be null");
        }
        if (source.size() > pagination.getLimit()) {
            source = source.subList(0, pagination.getLimit());
        }

        this.source = source;
        this.pagination = pagination;
    }

    @Override
    public Pagination getPagination() {
        return pagination;
    }

    @Override
    public List<E> getPage() {
        return source;
    }

    @Override
    public int size() {
        return source.size();
    }

    @Override
    public boolean isEmpty() {
        return source.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return source.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return source.iterator();
    }

    @Override
    public Object[] toArray() {
        return source.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return source.toArray(a);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return source.containsAll(c);
    }

    @Override
    public E get(int index) {
        if (index >= pagination.getOffset() + source.size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + (pagination.getOffset() + source.size()));
        }

        if (index < pagination.getOffset()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Offset: " + pagination.getOffset());
        }

        return source.get(toRealIndex(index));
    }

    @Override
    public int indexOf(Object o) {
        int index = source.indexOf(o);
        if (index != -1) {
            index += pagination.getOffset();
        }
        return index;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = source.lastIndexOf(o);
        if (index != -1) {
            index += pagination.getOffset();
        }
        return index;
    }

    @Override
    public ListIterator<E> listIterator() {
        return source.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return source.listIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        int fromRealIndex = toRealIndex(fromIndex);
        int toRealIndex = toRealIndex(toIndex);

        return source.subList(fromRealIndex, toRealIndex);
    }

    private int toRealIndex(int indexInPagination) {
        return indexInPagination - pagination.getOffset();
    }

    public static <E> PagedResponse<E> ofResponsePage(List<E> source, Pagination pagination) {
        return new PagedResponse<E>(source, pagination);
    }
}
