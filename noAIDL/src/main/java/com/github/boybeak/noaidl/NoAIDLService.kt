package com.github.boybeak.noaidl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast

class NoAIDLService : Service() {

    companion object {
        private val TAG = NoAIDLService::class.java.simpleName
    }

    private val binder = object : INoAIDL.Companion.Stub() {
        override fun sayHi(name: String) {
            Toast.makeText(this@NoAIDLService, "Say hi to $name from $TAG", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return binder.also {
            Log.v(TAG, "bind=$it")
        }
    }
}