package com.penryn.snippet.database

import android.arch.persistence.room.*
import com.penryn.snippet.models.App

/**
 * Created by hoangnhat on 2017-12-22.
 */
@Database(entities = arrayOf(App::class), version = 1, exportSchema = false)
abstract class SnippetAppDatabase : RoomDatabase() {
    abstract fun appDao() : AppDao
}
