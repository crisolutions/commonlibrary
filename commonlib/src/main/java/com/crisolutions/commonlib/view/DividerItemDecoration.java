package com.crisolutions.commonlib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private int skipCount;
    private int marginStart = 0;
    private int marginEnd = 0;
    private boolean lineAfterLastElement;
    private boolean lineBeforeFirstElement;
    private boolean horizontal = false;
    private Drawable mDivider;
    private int headerViewType;
    private List<Integer> noDividerAfter;

    public DividerItemDecoration(Context context) {
        final TypedArray styledAttributes = context.obtainStyledAttributes(ATTRS);
        mDivider = styledAttributes.getDrawable(0);
        styledAttributes.recycle();
    }

    public DividerItemDecoration(
            Context context, int resId, boolean lineAfterLastElement, boolean lineBeforeFirstElement,
            boolean horizontal, float marginStart, float marginEnd, int headerViewType, int skipCount,
            List<Integer> noDividerAfter) {
        mDivider = ContextCompat.getDrawable(context, resId);
        this.lineAfterLastElement = lineAfterLastElement;
        this.lineBeforeFirstElement = lineBeforeFirstElement;
        this.horizontal = horizontal;
        this.marginStart = (int) marginStart;
        this.marginEnd = (int) marginEnd;
        this.headerViewType = headerViewType;
        this.skipCount = skipCount;
        this.noDividerAfter = noDividerAfter;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        int childCount = parent.getChildCount();

        if (childCount == 0) {
            return;
        }

        if (horizontal) {
            int top = parent.getPaddingTop();
            int bottom = parent.getHeight() - parent.getPaddingBottom();

            if (lineBeforeFirstElement && isFirstItemVisible(parent, true)) {
                mDivider.setBounds(0, top + marginStart, bottom - marginEnd, mDivider.getIntrinsicWidth());
                mDivider.draw(c);
            }

            for (int i = 0; i < (lineAfterLastElement ? childCount : childCount - 1); i++) {

                View child = parent.getChildAt(i);

                if (isHeader(parent, child) ||
                        isLastItemInGroup(parent, child) ||
                        shouldSkip(parent, child) ||
                        isNoDividerAfter(parent, child)) {
                    continue;
                }

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int left = child.getRight() + params.rightMargin - mDivider.getIntrinsicHeight();
                int right = left + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top + marginStart, right, bottom - marginEnd);
                mDivider.draw(c);
            }

        } else {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            if (lineBeforeFirstElement && isFirstItemVisible(parent, false)) {
                mDivider.setBounds(left + marginStart, 0, right - marginEnd, mDivider.getIntrinsicHeight());
                mDivider.draw(c);
            }

            for (int i = 0; i < (lineAfterLastElement ? childCount : childCount - 1); i++) {
                View child = parent.getChildAt(i);

                if (isHeader(parent, child) ||
                        isLastItemInGroup(parent, child) ||
                        shouldSkip(parent, child) ||
                        isNoDividerAfter(parent, child)) {
                    continue;
                }

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin - mDivider.getIntrinsicHeight();
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left + marginStart, top, right - marginEnd, bottom);
                mDivider.draw(c);
            }
        }
    }

    private boolean isFirstItemVisible(RecyclerView parent, boolean horizontal) {
        return parent.getChildCount() > 0 && (horizontal ? (parent.getChildAt(0).getLeft() > 0) :
                (parent.getChildAt(0).getTop() >= 0));
    }

    private boolean isLastItemInGroup(RecyclerView parent, View child) {
        if (parent.getChildAdapterPosition(child) >= 0) {
            if (parent.getChildAdapterPosition(child) + 1 < parent.getAdapter().getItemCount()) {
                return parent.getAdapter().getItemViewType(parent.getChildAdapterPosition(child) + 1) == headerViewType;
            }
        }
        return false;
    }

    private boolean isHeader(RecyclerView parent, View child) {
        return parent.getChildAdapterPosition(child) >= 0 && parent.getAdapter().getItemViewType(parent
                .getChildAdapterPosition(child)) == headerViewType;

    }

    private boolean isNoDividerAfter(RecyclerView parent, View child) {
        return parent.getChildAdapterPosition(child) >= 0 &&
                noDividerAfter.contains(parent.getAdapter().getItemViewType(parent.getChildAdapterPosition(child)));
    }

    private boolean shouldSkip(ViewGroup parent, View child) {
        if (skipCount == 0) {
            return false;
        }

        RecyclerView.ViewHolder viewHolder = ((RecyclerView) parent).findContainingViewHolder(child);
        return viewHolder != null && viewHolder.getAdapterPosition() < skipCount;
    }

    public static final class Builder {

        private Context context;
        private int resId;
        private boolean lineAfterLastElement;
        private boolean lineBeforeFirstElement;
        private boolean horizontal;
        private float marginStart;
        private float marginEnd;
        private int headerViewType;
        private int skipCount;

        private final List<Integer> noDividerAfter = new ArrayList<>();

        public Builder context(Context context) {
            this.context = context;
            return this;
        }

        public Builder dividerResId(int resId) {
            this.resId = resId;
            return this;
        }

        public Builder lineAfterLastElement(boolean lineAfterLastElement) {
            this.lineAfterLastElement = lineAfterLastElement;
            return this;
        }

        public Builder lineBeforeFirstElement(boolean lineBeforeFirstElement) {
            this.lineBeforeFirstElement = lineBeforeFirstElement;
            return this;
        }

        public Builder horizontal(boolean horizontal) {
            this.horizontal = horizontal;
            return this;
        }

        public Builder marginStart(float marginStart) {
            this.marginStart = marginStart;
            return this;
        }

        public Builder marginEnd(float marginEnd) {
            this.marginEnd = marginEnd;
            return this;
        }

        /**
         * Set to skip adding dividers after the first `{@code skipCount}` items.
         */
        public Builder skip(int skipCount) {
            this.skipCount = skipCount;
            return this;
        }

        /**
         * If the list has headers and you don't want to draw dividers before/after the header, pass viewType of the
         * header.
         *
         * @param headerViewType viewType of the view to skip dividers before, within, and after.
         */
        public Builder headerViewType(int headerViewType) {
            this.headerViewType = headerViewType;
            return this;
        }

        /**
         * Set this type to view where you don't want any dividers to appear within this viewType but proceeding
         * divider will still be present.
         *
         * @param noDividerAfterType viewType of the view to skip dividers with it.
         */
        public Builder noDividerAfter(int noDividerAfterType) {
            noDividerAfter.add(noDividerAfterType);
            return this;
        }

        public DividerItemDecoration build() {
            return new DividerItemDecoration(context, resId, lineAfterLastElement, lineBeforeFirstElement, horizontal,
                    marginStart, marginEnd, headerViewType, skipCount, noDividerAfter);
        }
    }
}

