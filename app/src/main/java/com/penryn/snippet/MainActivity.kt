package com.penryn.snippet

import android.app.Activity
import android.app.ActivityManager
import android.arch.persistence.room.Room
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.service.voice.VoiceInteractionService
import android.widget.Toast
import com.penryn.snippet.database.SnippetAppDatabase
import com.penryn.snippet.extensions.getLaunchableApplications
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {
    private lateinit var database: SnippetAppDatabase

    private val ASSIST_SERVICE_PROBE = Intent(VoiceInteractionService.SERVICE_INTERFACE)
    private val ASSIST_ACTIVITY_PROBE = Intent(Intent.ACTION_ASSIST)

    private val classes = arrayOf(SnippetInteractionService::class.java, SnippetSessionService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        set_default_assistant.setOnClickListener {
            startActivity(Intent(Settings.ACTION_VOICE_INPUT_SETTINGS))
        }

        trigger_service.setOnClickListener {
            val intent = Intent(this, SnippetInteractionService::class.java)
            intent.setAction(Intent.ACTION_ASSIST)
            applicationContext.startService(intent)
            showAssist(Bundle())
//            packageManager.queryIntentServices(ASSIST_SERVICE_PROBE, PackageManager.GET_META_DATA)
//            packageManager.queryIntentActivities(ASSIST_ACTIVITY_PROBE, PackageManager.MATCH_DEFAULT_ONLY)
        }

        start_all_services.setOnClickListener {
            classes.map { Intent(this, it) }.forEach { startService(it) }
            Toast.makeText(this, "Started!", Toast.LENGTH_SHORT).show()
        }

        stop_all_services.setOnClickListener {
            classes.map { Intent(this, it) }.forEach { stopService(it) }
            Toast.makeText(this, "Stopped!", Toast.LENGTH_SHORT).show()
        }

        start_snippet_service.setOnClickListener {
            val serviceIntent = Intent(this, SnippetInteractionService::class.java)
            startService(serviceIntent)
            Toast.makeText(this, "Started!", Toast.LENGTH_SHORT).show()
        }

        start_snippet_session_service.setOnClickListener {
            val serviceIntent = Intent(this, SnippetSessionService::class.java)
            startService(serviceIntent)
            Toast.makeText(this, "Started!", Toast.LENGTH_SHORT).show()
        }

        snippet_service_running.setOnClickListener {
            if (isServiceRunning(SnippetInteractionService::class.java)) {
                Toast.makeText(this, "Yes!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No!", Toast.LENGTH_SHORT).show()
            }
        }

        snippet_session_service_running.setOnClickListener {
            if (isServiceRunning(SnippetInteractionService::class.java)) {
                Toast.makeText(this, "Yes!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No!", Toast.LENGTH_SHORT).show()
            }
        }

        database = Room.databaseBuilder(
            applicationContext,
            SnippetAppDatabase::class.java,
            "apps"
        ).build()

        // TODO: Needs to be synchronized
        rebuild_index.setOnClickListener {
            Thread(Runnable {
                database.appDao().dropTable()
                database.appDao().insertAll(*packageManager.getLaunchableApplications().toTypedArray())
                runOnUiThread {
                    Toast.makeText(this, "Synchronization succeeded!", Toast.LENGTH_SHORT).show()
                }
            }).start()
        }
    }

    private fun isServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return manager.getRunningServices(Integer.MAX_VALUE).any { serviceClass.name == it.service.className }
    }
}
