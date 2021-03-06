package com.penryn.snippet

import android.arch.persistence.room.Room
import android.content.Context
import android.os.Bundle
import android.service.voice.VoiceInteractionSession
import android.service.voice.VoiceInteractionSessionService
import android.util.Log
import android.view.inputmethod.InputMethodManager
import com.penryn.snippet.database.SnippetAppDatabase

/**
 * Created by hoangnhat on 2017-09-03.
 */

class SnippetSessionService : VoiceInteractionSessionService() {
    private val TAG: String = SnippetSessionService::class.java.simpleName

    override fun onNewSession(bundle: Bundle): VoiceInteractionSession {
        Log.d(TAG, "onNewSession called!")

        return SnippetSession(
            this,
            Room.databaseBuilder(
                applicationContext,
                SnippetAppDatabase::class.java,
                "apps"
            ).build(),
            packageManager,
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        )
    }
}
