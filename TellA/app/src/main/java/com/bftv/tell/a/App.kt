package com.bftv.tell.a

import android.app.Application

/**
 * Author by Less on 17/11/21.
 */
class App : Application() {

    companion object {
        var sApp: Application ?= null
    }

    override fun onCreate() {
        super.onCreate()
        sApp = this
    }

}