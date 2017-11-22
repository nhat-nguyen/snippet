package com.penryn.snippet.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

/**
 * Created by hoangnhat on 2017-09-04.
 */

class App(
    @Required
    @PrimaryKey
    val packageName: String,

    @Required val label: String
) : RealmObject()
