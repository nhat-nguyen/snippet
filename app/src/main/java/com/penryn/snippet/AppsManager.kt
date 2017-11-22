package com.penryn.snippet

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.penryn.snippet.models.App

/**
 * Created by hoangnhat on 2017-09-04.
 */

object AppsManager {
    fun getApps(pm: PackageManager): List<App> {
        return pm.getInstalledApplications(PackageManager.GET_META_DATA)
                .filter { pm.getLaunchIntentForPackage(it.packageName) != null }
                .map { App(it.packageName, pm.getApplicationLabel(it).toString()) }
    }

    private fun isSystemPackage(applicationInfo: ApplicationInfo): Boolean {
        return (applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0
    }
}
