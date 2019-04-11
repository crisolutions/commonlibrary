package com.crisolutions.commonlib.utils.permissions

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED

object PermissionCheckerUtil {
    @TargetApi(Build.VERSION_CODES.M)
    @JvmStatic
    fun hasPermission(
            permissionType: PermissionType,
            context: Context?
    ): Boolean {
        return when {
            Build.VERSION.SDK_INT < Build.VERSION_CODES.M -> true
            context != null ->
                PermissionChecker.checkSelfPermission(context, permissionType.permissionString) == PERMISSION_GRANTED
            else -> false
        }
    }
}
