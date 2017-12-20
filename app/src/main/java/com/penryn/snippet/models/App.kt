package com.penryn.snippet.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

/**
 * Created by hoangnhat on 2017-09-04.
 */

open class App(
    @Required
    @PrimaryKey
    var packageName: String = "",

    @Required
    var label: String = ""
) : RealmObject()
