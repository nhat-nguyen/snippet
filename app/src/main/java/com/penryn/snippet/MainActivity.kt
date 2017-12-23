package com.penryn.snippet

import android.app.Activity
import android.arch.persistence.room.Room
import android.os.Bundle
import android.widget.Toast
import com.penryn.snippet.database.SnippetAppDatabase
import com.penryn.snippet.extensions.getLaunchableApplications
import io.realm.internal.SyncObjectServerFacade
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {
    private lateinit var database: SnippetAppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        set_default_assistant.setOnClickListener {

        }

        database = Room.databaseBuilder(
            SyncObjectServerFacade.getApplicationContext(),
            SnippetAppDatabase::class.java,
            "apps"
        ).build()

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
}
