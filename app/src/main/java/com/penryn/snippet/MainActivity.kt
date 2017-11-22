package com.penryn.snippet

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        set_default_assistant.setOnClickListener {

        }

        rebuild_index.setOnClickListener {
            Realm.getDefaultInstance().executeTransactionAsync({
                it.insertOrUpdate(AppsManager.getApps(this.packageManager))
            }, {
               Toast.makeText(this, "Synchronization succeeded!", Toast.LENGTH_SHORT).show()
            }, {
                Toast.makeText(this, "Synchronization failed!", Toast.LENGTH_SHORT).show()
            })
        }
    }
}
