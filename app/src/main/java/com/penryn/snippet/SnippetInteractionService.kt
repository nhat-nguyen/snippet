package com.penryn.snippet

import android.content.Intent
import android.os.Bundle
import android.service.voice.VoiceInteractionService
import android.util.Log

/**
 * Created by hoangnhat on 2017-09-03.
 */

class SnippetInteractionService : VoiceInteractionService() {
    private val TAG: String = SnippetInteractionService::class.java.simpleName

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand called!")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onReady() {
        super.onReady()
        Log.d(TAG, "onReady called!")
    }

    override fun showSession(args: Bundle?, flags: Int) {
        super.showSession(args, flags)
        Log.d(TAG, "showSession called!")
    }
}
