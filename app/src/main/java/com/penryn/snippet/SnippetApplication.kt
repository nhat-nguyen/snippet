package com.penryn.snippet

import android.app.Application
import io.realm.Realm

/**
 * Created by hoangnhat on 2017-09-11.
 */
class SnippetApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}
