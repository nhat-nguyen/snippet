package com.penryn.snippet.extensions

import android.content.Context
import android.os.Handler
import android.os.Looper

/**
 * Created by hoangnhat on 2017-12-22.
 */
fun Context.runOnUiThread(runnable: Runnable) {
    if (Looper.myLooper() == mainLooper) {
        runnable.run()
    } else {
        Handler(mainLooper).post(runnable)
    }
}
