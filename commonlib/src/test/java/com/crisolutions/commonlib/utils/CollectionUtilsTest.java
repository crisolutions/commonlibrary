package com.crisolutions.commonlib.utils;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

public final class CollectionUtilsTest {

    @Test
    public void nullOrEmptyCollectionIsEmpty() {
        //noinspection ConstantConditions
        assertTrue(CollectionUtils.isEmpty(null));
        assertTrue(CollectionUtils.isEmpty(Collections.emptyList()));
    }

    @Test
    public void nonEmptyCollectionIsNotEmpty() {
        assertFalse(CollectionUtils.isEmpty(Collections.singletonList(1)));
    }

    @Test
    public void nullReturnedWhenCannotFindItem() {
        assertNull(CollectionUtils.find(Collections.emptyList(), 1));
        assertNull(CollectionUtils.find(Arrays.asList(1, 2, 3), 4));
    }

    @Test
    public void objectReturnedWhenFound() {
        assertEquals(new Integer(1), CollectionUtils.find(Arrays.asList(1, 2, 3), 1));
        assertEquals(new Integer(2), CollectionUtils.find(Arrays.asList(1, 2, 3), 2));
        assertEquals(new Integer(3), CollectionUtils.find(Arrays.asList(1, 2, 3), 3));
    }

    @Test
    public void nullReturnedWhenThereIsNoLastItem() {
        assertNull(CollectionUtils.lastItem(Collections.emptyList()));
    }

    @Test
    public void lastItemReturned() {
        assertEquals(new Integer(3), CollectionUtils.lastItem(Arrays.asList(1, 2, 3)));
    }
}