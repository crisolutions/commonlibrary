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
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat

object UiTools {
    const val ERROR_MESSAGE_URL = "Requested URL cannot be found."
    const val MILES_PER_METER_RATIO = 0.000621371192f
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

fun String.openExternalLink(context: Context, errorMessage: String) {
    if (!isEmpty()) {
        var formattedUrl = this
        if (startsWith("http")) {
            formattedUrl = "http://$this"
        }

        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(formattedUrl)))
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    } else {
        Toast.makeText(context, if (errorMessage.isEmpty()) UiTools.ERROR_MESSAGE_URL else errorMessage, Toast.LENGTH_SHORT).show()
    }
}

fun Context.openSettings() {
    val intent = Intent()
    val uri: Uri = Uri.fromParts("package", packageName, null)
    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    intent.data = uri
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
    intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
    startActivity(intent)
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

fun Float.metersToMiles(): Float = this * UiTools.MILES_PER_METER_RATIO

fun Toolbar.animateToBackIndicator(drawerColor: Int) {
    val drawerArrow = DrawerArrowDrawable(context)
    drawerArrow.color = ContextCompat.getColor(context, drawerColor)
    navigationIcon = drawerArrow
    ObjectAnimator.ofFloat(drawerArrow, UiTools.NAV_DRAWER_ICON_ANIM, 0f, 1f).start()
}

fun Toolbar.animateToDrawerIndicator(drawerColor: Int, completionRunnable: Runnable?) {
    val drawerArrow = DrawerArrowDrawable(context)
    drawerArrow.color = ContextCompat.getColor(context, drawerColor)
    navigationIcon = drawerArrow
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

@Deprecated(
        message = "This will be removed in the next release",
        replaceWith = ReplaceWith("Use internal implementation"),
        level = DeprecationLevel.ERROR
)
fun Toolbar.showDrawerIcon(drawerColor: Int) {
    val drawerArrow = DrawerArrowDrawable(context)
    drawerArrow.color = ContextCompat.getColor(context, drawerColor)
    navigationIcon = drawerArrow
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
