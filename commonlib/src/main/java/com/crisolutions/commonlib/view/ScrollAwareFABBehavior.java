package com.crisolutions.commonlib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.crisolutions.commonlib.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

@SuppressWarnings("unused") // This class is used in resources.
public final class ScrollAwareFABBehavior extends FloatingActionButton.Behavior {
    public ScrollAwareFABBehavior(Context context, AttributeSet attrs) {
        super();
    }

    @Override
    public boolean onStartNestedScroll(
            @NonNull final CoordinatorLayout coordinatorLayout,
            @NonNull final FloatingActionButton child,
            @NonNull final View directTargetChild,
            @NonNull final View target,
            final int nestedScrollAxes
    ) {
        return true;
    }

    @Override
    public void onNestedScroll(
            @NonNull final CoordinatorLayout coordinatorLayout,
            @NonNull final FloatingActionButton child,
            @NonNull final View target,
            final int dxConsumed,
            final int dyConsumed,
            final int dxUnconsumed,
            final int dyUnconsumed
    ) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
            child.hide(new FloatingActionButton.OnVisibilityChangedListener() {
                @Override
                public void onHidden(FloatingActionButton fab) {
                    super.onHidden(fab);
                    ((View) fab).setVisibility(View.INVISIBLE);
                }
            });
        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
            if (child.getTag(R.id.fab_can_show) == null || ((boolean) child.getTag(R.id.fab_can_show))) {
                child.show();
            }
        }
    }
}
