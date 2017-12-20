package com.penryn.snippet.models

import io.realm.RealmObject

/**
 * Created by hoangnhat on 2017-09-04.
 */
class Snippet(var name: String = "", var value: String = "") : RealmObject()
