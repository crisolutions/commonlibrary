package com.crisolutions.commonlib.utils;

import android.os.Build;
import android.text.Html;

import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class StringUtils {

    private StringUtils() {
    }

    public static boolean isEmpty(@Nullable String... textArray) {
        if (textArray == null || textArray.length == 0) {
            return true;
        }

        for (String text : textArray) {
            if (text == null || text.trim().isEmpty()) {
                return true;
            }
        }

        return false;
    }

    public static boolean isNotEmpty(@Nullable String text) {
        return !isEmpty(text);
    }

    public static String arrayToMultiLineString(@Nullable List<String> list) {
        if (CollectionUtils.isEmpty(list)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (String s : list) {
            sb.append(s).append("\n");
        }

        return sb.toString().trim();
    }

    @Nullable
    public static String nullIfEmpty(@Nullable String value) {
        return isEmpty(value) ? null : value;
    }

    public static String safeString(@Nullable String value) {
        if (value == null) {
            return "";
        }
        return value;
    }

    public static String stripNonDigits(@Nullable String phoneNumber) {
        if (phoneNumber == null) {
            return null;
        }

        return phoneNumber.replaceAll("[\\D]", "");
    }

    public static boolean safeEquals(@Nullable String first, @Nullable String second) {
        if (first == null) {
            return second == null;
        }
        return second != null && first.equals(second);
    }

    @Nullable
    public static String capitalizeWords(@Nullable String input) {
        if (input != null) {
            String[] pieces = input.split(" ");
            StringBuilder sb = new StringBuilder();
            boolean first = true;
            for (String piece : pieces) {
                if (!first) {
                    sb.append(" ");
                }
                first = false;
                final Locale locale = Locale.getDefault();
                if (piece.length() > 1) {
                    sb.append(piece.substring(0, 1).toUpperCase(locale))
                            .append(piece.substring(1).toLowerCase(locale));
                } else {
                    sb.append(piece.toUpperCase(locale));
                }
            }
            return sb.toString();
        }
        return null;
    }

    public static String convertFromHtml(@NonNull String htmlEncodedText) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(htmlEncodedText, Html.FROM_HTML_MODE_COMPACT).toString();
        } else {
            //noinspection deprecation This else branch is needed until we bump our minSdkVersion to 24 or greater.
            return Html.fromHtml(htmlEncodedText).toString();
        }
    }
}
