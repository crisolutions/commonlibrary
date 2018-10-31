package com.crisolutions.commonlib.utils;

import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class CollectionUtils {

    private CollectionUtils() {
    }

    public static boolean isEmpty(@Nullable Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    @Nullable
    public static <T> T find(@NonNull Collection<T> collection, T key) {
        for (T t : collection) {
            if (t.equals(key)) {
                return t;
            }
        }
        return null;
    }

    @Nullable
    public static <T> T lastItem(List<T> list) {
        if (isEmpty(list)) {
            return null;
        } else {
            return list.get(list.size() - 1);
        }
    }
}
