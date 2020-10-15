package com.github.boybeak.binder

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Toast
import com.github.boybeak.noaidl.INoAIDL
import com.github.boybeak.noaidl.NoAIDLService
import com.github.boybeak.withaidl.IWithAIDL
import com.github.boybeak.withaidl.WithAIDLService

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    private var withAIDL: IWithAIDL? = null
    private val withConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            service ?: return
            withAIDL = IWithAIDL.Stub.asInterface(service)
            Toast.makeText(this@MainActivity, "connected to WithAIDLService", Toast.LENGTH_SHORT).show()
            Log.v(TAG, "onServiceConnected withAIDL service=${withAIDL}")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
        }
    }

    private var noAIDL: INoAIDL? = null
    private val noConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            service ?: return
            noAIDL = INoAIDL.Companion.Stub.asInterface(service)
            Toast.makeText(this@MainActivity, "connected to NoAIDLService", Toast.LENGTH_SHORT).show()
            Log.v(TAG, "onServiceConnected noAIDL service=${noAIDL}")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onWithAIDL(v: View) {
        if (withAIDL != null) {
            Toast.makeText(this, "Already connected", Toast.LENGTH_SHORT).show()
            return
        }
        bindService(Intent(this, WithAIDLService::class.java), withConnection, Context.BIND_AUTO_CREATE)
    }

    fun onNoAIDL(v: View) {
        if (noAIDL != null) {
            Toast.makeText(this, "Already connected", Toast.LENGTH_SHORT).show()
            return
        }
        bindService(Intent(this, NoAIDLService::class.java), noConnection, Context.BIND_AUTO_CREATE)
    }

    fun sayHiWithAIDL(v: View) {
        if (withAIDL == null) {
            Toast.makeText(this, "Click WITHAIDL button first", Toast.LENGTH_SHORT).show()
            return
        }
        withAIDL?.sayHi("you")
    }

    fun sayHiNoAIDL(v: View) {
        if (noAIDL == null) {
            Toast.makeText(this, "Click NOAIDL button first", Toast.LENGTH_SHORT).show()
            return
        }
        noAIDL?.sayHi("you")
    }

}