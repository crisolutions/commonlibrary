package com.crisolutions.commonlibraryktx

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Color
import android.net.Uri
import android.provider.Settings
import android.support.v4.content.ContextCompat
import android.support.v7.graphics.drawable.DrawerArrowDrawable
import android.support.v7.widget.Toolbar
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

class UiTools {
    companion object {
        const val ERROR_MESSAGE_URL = "Requested URL cannot be found."
        const val METERS_TO_MILES_RATIO = 0.000621371192f
        const val NAV_DRAWER_ICON_ANIM = "progress"

        fun getScreenWidth() = Resources.getSystem().displayMetrics.widthPixels

        fun getScreenHeight() = Resources.getSystem().displayMetrics.heightPixels

        fun getStatusbarHeight(context: Context): Int {
            var result = 0
            val resourceId = context.resources.getIdentifier("status_bar_heigh", "dimen", "android")
            if (resourceId > 0) {
                result = context.resources.getDimensionPixelSize(resourceId)
            }
            return result
        }
    }
}

fun String.openExternalLink(context: Context, errorMessage: String) {
    if (!this.isEmpty()) {
        var formattedUrl = this
        if (this.startsWith("http")) {
            formattedUrl = "http://$this"
        }

        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(formattedUrl)))
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    } else {
        Toast.makeText(context, if(errorMessage.isEmpty()) UiTools.ERROR_MESSAGE_URL else errorMessage, Toast.LENGTH_SHORT).show()
    }
}

fun Context?.openSettings() {
    this?.let {
        val intent = Intent()
        val uri: Uri = Uri.fromParts("package", it.packageName, null)
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.data = uri
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        it.startActivity(intent)
    }
}

fun Float.convertDpToPixel(context: Context): Float {
    val resources = context.resources
    val metrics = resources.displayMetrics
    return this * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun Float.converPixelsToDp(context: Context): Float {
    val resources = context.resources
    val metrics = resources.displayMetrics
    return this / (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
}

fun IntArray.getColorFromAttribute(activity: Activity): Int {
    var color = 0
    try {
        val themeId = activity.packageManager.getActivityInfo(activity.componentName, 0).theme
        val typedArray = activity.obtainStyledAttributes(themeId, this)
        color = typedArray.getColor(0, Color.BLACK)
        typedArray.recycle()
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return color
}

fun Float.metersToMiles(): Float = this * UiTools.METERS_TO_MILES_RATIO

fun Toolbar.animateToBackIndicator(drawerColor: Int) {
    val drawerArrow = DrawerArrowDrawable(this.context)
    drawerArrow.color = ContextCompat.getColor(this.context, drawerColor)
    this.navigationIcon = drawerArrow
    ObjectAnimator.ofFloat(drawerArrow, UiTools.NAV_DRAWER_ICON_ANIM, 0f, 1f).start()
}

fun Toolbar.animateToDrawerIndicator(drawerColor: Int, completionRunnable: Runnable?) {
    val drawerArrow = DrawerArrowDrawable(this.context)
    drawerArrow.color = ContextCompat.getColor(this.context, drawerColor)
    this.navigationIcon = drawerArrow
    val animator = ObjectAnimator.ofFloat(drawerArrow, UiTools.NAV_DRAWER_ICON_ANIM, 1f, 0f)

    completionRunnable?.let {
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                completionRunnable.run()
            }
        })
    }
    animator.start()
}

fun Toolbar.showDrawerIcon(drawerColor: Int) {
    val drawerArrow = DrawerArrowDrawable(this.context)
    drawerArrow.color = ContextCompat.getColor(this.context, drawerColor)
    this.navigationIcon = drawerArrow
    drawerArrow.progress = 0f
}

fun Context.notImplemented() = Toast.makeText(this, "This feature is not implemented yet", Toast.LENGTH_SHORT).show()

fun View?.enableDisableView(enabled: Boolean) {
    this?.let {
        it.isEnabled = enabled

        if (it is ViewGroup) {

            for (i in 0 until it.childCount) {
                it.getChildAt(i).enableDisableView(enabled)
            }
        }
    }
}