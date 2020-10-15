package com.github.boybeak.withaidl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast

class WithAIDLService : Service() {

    companion object {
        private val TAG = WithAIDLService::class.java.simpleName
    }

    private val binder = object : IWithAIDL.Stub() {
        override fun sayHi(name: String?) {
            Toast.makeText(this@WithAIDLService, "Say hi to $name from $TAG", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBind(intent: Intent): IBinder {

        return binder.also {
            Log.v(TAG, "bind=$it")
        }
    }
}