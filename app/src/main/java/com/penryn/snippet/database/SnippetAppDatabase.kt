package com.penryn.snippet.database

import android.arch.persistence.room.*
import com.penryn.snippet.models.App

/**
 * Created by hoangnhat on 2017-12-22.
 */
@Database(entities = arrayOf(App::class), version = 1)
abstract class SnippetAppDatabase : RoomDatabase() {
    abstract fun appDao() : AppDao
}

@Dao
interface AppDao {
    @Query("SELECT * FROM apps")
    fun getAll(): List<App>

    @Insert
    fun insertAll(vararg users: App)

    @Delete
    fun delete(app: App)

    @Query("DELETE FROM apps")
    fun dropTable()
}
