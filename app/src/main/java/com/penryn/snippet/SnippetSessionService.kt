package com.penryn.snippet

import android.content.Context
import android.os.Bundle
import android.service.voice.VoiceInteractionSession
import android.service.voice.VoiceInteractionSessionService
import android.view.inputmethod.InputMethodManager

/**
 * Created by hoangnhat on 2017-09-03.
 */

class SnippetSessionService : VoiceInteractionSessionService() {
    override fun onNewSession(bundle: Bundle): VoiceInteractionSession {
        return SnippetSession(
            this,
            packageManager,
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        )
    }
}
