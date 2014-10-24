package com.murauyou.pagination;

/**
 * Created with IntelliJ IDEA.
 * User: Anton Murauyou
 * Date: 10/23/14
 * Time: 19:30
 */
public interface TotalAwareResponsePage<E> extends ResponsePage<E> {

    int getTotalSize();

}
