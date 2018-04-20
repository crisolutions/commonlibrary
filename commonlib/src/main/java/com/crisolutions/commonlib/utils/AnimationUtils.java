package com.crisolutions.commonlib.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import static android.view.View.MeasureSpec.UNSPECIFIED;
import static android.view.View.MeasureSpec.makeMeasureSpec;

public final class AnimationUtils {

    private static final int DEFAULT_DURATION = 400;

    private AnimationUtils() {
    }

    public static void animateHeight(View view, boolean collapse) {
        animateHeight(view, collapse, null);
    }

    public static void animateHeight(View view, boolean collapse, @Nullable Runnable animationFinishedCallback) {
        if (collapse) {
            collapseView(view, true, false, animationFinishedCallback);
        } else {
            expandView(view, true, false, animationFinishedCallback);
        }
    }

    public static void animateWidth(View view, boolean collapse, boolean instant) {
        if (collapse) {
            collapseView(view, false, instant, null);
        } else {
            expandView(view, false, instant, null);
        }
    }

    private static void collapseView(
            final View view,
            final boolean forHeight,
            boolean instant,
            @Nullable final Runnable animationFinishedCallback) {
        int value = forHeight ? view.getHeight() : view.getWidth();
        ValueAnimator animator = ValueAnimator.ofInt(value, 0);
        if (instant) {
            ViewGroup.LayoutParams params = view.getLayoutParams();
            if (forHeight) {
                params.height = 0;
            } else {
                params.width = 0;
            }
            view.setLayoutParams(params);
        } else {
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(View.GONE);
                    if (animationFinishedCallback != null) {
                        animationFinishedCallback.run();
                    }
                }
            });
            animator.addUpdateListener(valueAnimator -> {
                ViewGroup.LayoutParams params = view.getLayoutParams();
                if (forHeight) {
                    params.height = (int) valueAnimator.getAnimatedValue();
                } else {
                    params.width = (int) valueAnimator.getAnimatedValue();
                }
                view.setLayoutParams(params);
            });
            animator.setDuration(DEFAULT_DURATION).start();
        }
    }

    private static void expandView(
            final View view, final boolean forHeight, boolean instant, @Nullable final Runnable animationFinishedCallback) {
        view.setVisibility(View.VISIBLE);
        view.measure(makeMeasureSpec(0, UNSPECIFIED), makeMeasureSpec(0, UNSPECIFIED));
        int value = forHeight ? view.getMeasuredHeight() : view.getMeasuredWidth();
        if (instant) {
            ViewGroup.LayoutParams params = view.getLayoutParams();
            if (forHeight) {
                params.height = value;
            } else {
                params.width = value;
            }
            view.setLayoutParams(params);
        } else {
            ValueAnimator animator = ValueAnimator.ofInt(0, value);
            if (animationFinishedCallback != null) {
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animationFinishedCallback.run();
                    }
                });
            }
            animator.addUpdateListener(valueAnimator -> {
                ViewGroup.LayoutParams params = view.getLayoutParams();
                if (forHeight) {
                    params.height = (int) valueAnimator.getAnimatedValue();
                } else {
                    params.width = (int) valueAnimator.getAnimatedValue();
                }
                view.setLayoutParams(params);
            });
            animator.setDuration(DEFAULT_DURATION).start();
        }
    }
}
