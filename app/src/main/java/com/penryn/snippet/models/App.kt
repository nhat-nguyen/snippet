package com.penryn.snippet.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by hoangnhat on 2017-09-04.
 */

@Entity(tableName = "apps")
class App(
    @PrimaryKey
    var packageName: String = "",

    @ColumnInfo(name = "label")
    var label: String = ""
)
