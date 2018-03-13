package com.crisolutions.commonlib.utils;

import android.support.annotation.Nullable;

import java.util.Collection;
import java.util.List;

public final class CollectionUtils {

    private CollectionUtils() {
    }

    public static boolean isEmpty(@Nullable Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    @Nullable
    public static <T> T find(Collection<T> collection, T key) {
        for (T t : collection) {
            if (t.equals(key)) {
                return t;
            }
        }

        return null;
    }

    public static <T> T lastItem(List<T> list){
        if (isEmpty(list)){
            return null;
        } else {
            return list.get(list.size()-1);
        }
    }
}