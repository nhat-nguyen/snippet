package com.penryn.snippet.extensions

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.penryn.snippet.models.App

/**
 * Created by hoangnhat on 2017-09-04.
 */

fun PackageManager.getLaunchableApplications(): List<App> {
    return getInstalledApplications(PackageManager.GET_META_DATA)
            .filter { getLaunchIntentForPackage(it.packageName) != null }
            .map { App(it.packageName, getApplicationLabel(it).toString()) }
}

fun ApplicationInfo.isSystemPackage(): Boolean {
    return (flags and ApplicationInfo.FLAG_SYSTEM) != 0
}
