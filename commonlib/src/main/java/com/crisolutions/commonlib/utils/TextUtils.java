package com.crisolutions.commonlib.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

public final class TextUtils {

    private static final char DOT = '\u2022';

    private TextUtils() {
    }

    public static int getTextWidth(@NonNull TextView textView) {
        textView.measure(0, 0);
        return textView.getMeasuredWidth();
    }

    public static CharSequence maskText(@Nullable String text, int visibleChars) {
        return maskText(text, visibleChars, DOT);
    }

    public static CharSequence maskText(@Nullable String text, int visibleChars, char maskCharacter) {
        return maskText(text, visibleChars, maskCharacter, -1);
    }

    public static CharSequence maskText(@Nullable String text, int visibleChars, int maxLength) {
        return maskText(text, visibleChars, DOT, maxLength);
    }

    public static CharSequence maskText(@Nullable String text, int visibleChars, char maskCharacter, int maxLength) {
        if (text == null) {
            return "";
        }
        if (text.length() <= visibleChars) {
            return text;
        }
        if (maxLength != -1 && maxLength < visibleChars) {
            throw new IllegalArgumentException(
                    "Cannot have a max length be shorter than the visible character count");
        }
        return new MaskedCharSequence(text, visibleChars, maskCharacter, maxLength);
    }

    private static final class MaskedCharSequence implements CharSequence {

        private final CharSequence original;
        private final int visibleLength;
        private final char maskedCharacter;
        private final int resolvedLength;

        MaskedCharSequence(
                CharSequence original, int visibleLength, char maskedCharacter, int maxLength) {
            this.original = original;
            this.visibleLength = visibleLength;
            this.maskedCharacter = maskedCharacter;
            resolvedLength = maxLength != -1 ? Math.min(maxLength, original.length()) : original.length();
        }

        @Override
        public int length() {
            return resolvedLength;
        }

        @Override
        public char charAt(int index) {
            if (index < resolvedLength - visibleLength) {
                return maskedCharacter;
            }
            return original.charAt(resolveIndex(index));
        }

        @Override
        public CharSequence subSequence(int start, int end) {
            throw new IllegalStateException("Cannot sub-sequence masked text");
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < resolvedLength; i++) {
                stringBuilder.append(charAt(i));
            }
            return stringBuilder.toString();
        }

        private int resolveIndex(int requestedIndex) {
            return requestedIndex + (original.length() - resolvedLength);
        }
    }
}
