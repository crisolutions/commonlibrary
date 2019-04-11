package com.crisolutions.commonlib.utils.permissions

import android.Manifest

enum class PermissionType(
        val permissionString: String
) {
    LOCATION(Manifest.permission.ACCESS_FINE_LOCATION),
    CAMERA(Manifest.permission.CAMERA);
}
