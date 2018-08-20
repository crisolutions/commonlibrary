package com.crisolutions.commonlib.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public final class UiUtils {

    private static final String NAV_DRAWER_ICON_ANIM = "progress";
    private static final String ERROR_MESSAGE_URL = "Requested URL cannot be found.";
    private static final float METERS_TO_MILES_RATIO = 0.000621371192f;

    private UiUtils() {
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    public static void openExternalLink(@NonNull Context context, String url, @Nullable String errorMessage) {
        String formattedUrl = url;
        if (!StringUtils.isEmpty(formattedUrl)) {
            if (!url.startsWith("http")) {
                formattedUrl = "http://" + url;
            }

            try {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(formattedUrl));
                context.startActivity(browserIntent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, StringUtils.isEmpty(errorMessage) ? ERROR_MESSAGE_URL : errorMessage,
                    Toast.LENGTH_SHORT).show();
        }
    }

    public static void openSettings(@NonNull final Context context) {
        final Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(@NonNull Context context, float dp) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(@NonNull Context context, float px) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    /**
     * @return returns {@link Color#BLACK} if color can not be found
     */
    public static int getColorFromAttribute(@NonNull Activity activity, int[] attrs) {
        int color = 0;

        try {
            int themeId = activity.getPackageManager().getActivityInfo(activity.getComponentName(), 0).theme;
            TypedArray ta = activity.obtainStyledAttributes(themeId, attrs);
            color = ta.getColor(0, Color.BLACK); //I set Black as the default color
            ta.recycle();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return color;
    }

    public static float metersToMiles(float i) {
        return i * METERS_TO_MILES_RATIO;
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static int getStatusBarHeight(@NonNull Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static void animateToBackIndicator(@NonNull Toolbar toolbar, int drawerColor) {
        DrawerArrowDrawable drawerArrow = new DrawerArrowDrawable(toolbar.getContext());
        drawerArrow.setColor(ContextCompat.getColor(toolbar.getContext(), drawerColor));
        toolbar.setNavigationIcon(drawerArrow);
        ObjectAnimator.ofFloat(drawerArrow, NAV_DRAWER_ICON_ANIM, 0, 1).start();
    }

    public static void animateToDrawerIndicator(
            @NonNull Toolbar toolbar, int drawerColor, @Nullable final Runnable completionRunnable) {
        DrawerArrowDrawable drawerArrow = new DrawerArrowDrawable(toolbar.getContext());
        drawerArrow.setColor(ContextCompat.getColor(toolbar.getContext(), drawerColor));
        toolbar.setNavigationIcon(drawerArrow);
        ObjectAnimator animator = ObjectAnimator.ofFloat(drawerArrow, NAV_DRAWER_ICON_ANIM, 1, 0);
        if (completionRunnable != null) {
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    completionRunnable.run();
                }
            });
        }
        animator.start();
    }

    public static void showDrawerIcon(@NonNull Toolbar toolbar, int drawerColor) {
        DrawerArrowDrawable drawerArrow = new DrawerArrowDrawable(toolbar.getContext());
        drawerArrow.setColor(ContextCompat.getColor(toolbar.getContext(), drawerColor));
        toolbar.setNavigationIcon(drawerArrow);
        drawerArrow.setProgress(0);
    }

    /**
     * Don't remove this method even if it has no usages. During development we use this method to notify testers
     * about a future feature that's not linked to current work yet.
     */
    public static void notImplemented(@NonNull Context context) {
        Toast.makeText(context, "This feature not implemented yet", Toast.LENGTH_SHORT).show();
    }

    public static void enableDisableView(@NonNull View view, boolean enabled) {
        view.setEnabled(enabled);

        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;

            for (int idx = 0; idx < group.getChildCount(); idx++) {
                enableDisableView(group.getChildAt(idx), enabled);
            }
        }
    }
}
