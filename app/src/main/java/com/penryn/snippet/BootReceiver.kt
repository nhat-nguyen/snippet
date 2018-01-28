package com.penryn.snippet

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

/**
 * Created by hoangnhat on 2018-01-27.
 */
class BootReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null || context == null) return

        Toast.makeText(context, "Boot completed!", Toast.LENGTH_LONG).show()
    }
}
