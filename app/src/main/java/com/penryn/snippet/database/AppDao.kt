package com.penryn.snippet.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.penryn.snippet.models.App
import io.reactivex.Single

/**
 * Created by hoangnhat on 2017-12-24.
 */
@Dao
interface AppDao {
    @Query("SELECT * FROM apps")
    fun getAll(): Single<List<App>>

    @Insert
    fun insertAll(vararg users: App)

    @Delete
    fun delete(app: App)

    @Query("DELETE FROM apps")
    fun dropTable()
}
