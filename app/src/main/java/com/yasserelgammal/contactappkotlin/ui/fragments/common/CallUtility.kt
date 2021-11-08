package com.yasserelgammal.contactappkotlin.ui.fragments.common

import android.Manifest
import android.content.Context
import pub.devrel.easypermissions.EasyPermissions

object CallUtility {
    // handle permissions
    fun hasCallPermission(context: Context) =
            EasyPermissions.hasPermissions(
                context,
                Manifest.permission.CALL_PHONE)
}