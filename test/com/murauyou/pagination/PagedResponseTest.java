package com.murauyou.pagination;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

/**
 * Created with IntelliJ IDEA.
 * User: Anton Murauyou
 * Date: 10/23/14
 * Time: 18:53
 */
public class PagedResponseTest {

    @Test
    public void testPagedResponseEdgeCases() throws NoPageAvailableException {
        List<String> pageList = new ArrayList<>(Arrays.asList("D", "C", "B", "A", "H", "G", "F", "E", "I"));

        try {
            Paginations.ofResponsePage(null, Paginations.ofOffsetAndLimit(15, 9));
            fail("Source list can't be null");
        } catch (IllegalArgumentException ex) {
            // Not tracked
        }

        try {
            Paginations.ofResponsePage(pageList, null);
            fail("Pagination can't be null");
        } catch (IllegalArgumentException ex) {
            // Not tracked
        }

        ResponsePage<String> responsePage = Paginations.ofResponsePage(pageList, Paginations.ofOffsetAndLimit(3, 7));

        assertEquals(7, responsePage.size()); // Response page gets cut off

        try {
            responsePage.get(1);
            fail("Too low for offset of 3");
        } catch (IndexOutOfBoundsException ex) {
            // Not tracked
        }

        try {
            responsePage.get(11);
            fail("Too high for offset of 3 and limit of 7");
        } catch (IndexOutOfBoundsException ex) {
            // Not tracked
        }
    }

    @Test
    public void testPagedResponse() throws NoPageAvailableException {
        List<String> pageList = new ArrayList<>(Arrays.asList("D", "C", "B", "A", "H", "G", "F", "E", "I"));

        ResponsePage<String> responsePage = Paginations.ofResponsePage(pageList, Paginations.ofOffsetAndLimit(15, 9));

        assertEquals(9, responsePage.size());
        assertFalse(responsePage.isEmpty());
        assertTrue(responsePage.contains("B"));
        assertFalse(responsePage.contains("K"));
        assertEquals("D", responsePage.get(15));
        assertEquals("C", responsePage.get(16));
        assertEquals("B", responsePage.get(17));
        assertEquals("A", responsePage.get(18));
        assertEquals("H", responsePage.get(19));
        assertEquals("G", responsePage.get(20));

        assertEquals(18, responsePage.indexOf("A"));
        assertEquals(20, responsePage.indexOf("G"));
        assertEquals(22, responsePage.indexOf("E"));
        assertEquals(-1, responsePage.indexOf("U"));
        assertEquals(23, responsePage.lastIndexOf("I"));
        assertEquals(21, responsePage.lastIndexOf("F"));
        assertEquals(15, responsePage.lastIndexOf("D"));
        assertEquals(-1, responsePage.lastIndexOf("V"));

        List<String> pageSubList = responsePage.subList(16, 19);
        assertEquals("C", pageSubList.get(0));
        assertEquals("B", pageSubList.get(1));
        assertEquals("A", pageSubList.get(2));


        responsePage = Paginations.ofResponsePage(pageList, Paginations.ofOffsetAndLimit(15, 19));
        assertEquals("F", responsePage.get(21));
        assertEquals("E", responsePage.get(22));
        assertEquals("I", responsePage.get(23));

        try {
            responsePage.get(24);
            fail("No more elements after index 23");
        } catch (IndexOutOfBoundsException ex) {
            // Not tracked
        }

    }

    @Test
    public void testTotalAwarePagedResponse() throws NoPageAvailableException {
        List<String> pageList = new ArrayList<>(Arrays.asList("D", "C", "B", "A", "H", "G", "F", "E", "I"));

        TotalAwareResponsePage<String> responsePage = Paginations.ofResponseAndSizeAndPagination(pageList, Paginations.ofOffsetAndLimit(15, 9), 105);

        assertEquals(105, responsePage.getTotalSize());

        assertEquals(9, responsePage.size());
        assertFalse(responsePage.isEmpty());
        assertEquals("D", responsePage.get(15));
        assertEquals("B", responsePage.get(17));
        assertEquals("A", responsePage.get(18));
        assertEquals("G", responsePage.get(20));
    }
}
