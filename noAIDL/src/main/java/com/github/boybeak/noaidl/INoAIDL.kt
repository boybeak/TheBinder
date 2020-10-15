package com.github.boybeak.noaidl

import android.os.Binder
import android.os.IBinder
import android.os.IInterface
import android.os.Parcel
import android.util.Log

interface INoAIDL : IInterface {
    fun sayHi(name: String)

    companion object {
        private val TAG = INoAIDL::class.java.simpleName
        private val DESCRIPTOR = INoAIDL::class.java.name

        abstract class Stub : Binder(), INoAIDL {

            companion object {
                val TRANSACTION_sayHi = IBinder.FIRST_CALL_TRANSACTION + 0

                fun asInterface(binder: IBinder?): INoAIDL? {
                    if (binder == null) {
                        return null
                    }
                    val iin = binder.queryLocalInterface(DESCRIPTOR)
                    if (iin != null && iin is INoAIDL) {
                        Log.v(TAG, "asInterface find binder")
                        return iin
                    }
                    Log.v(TAG, "asInterface has to new proxy")
                    return Proxy(binder)
                }
            }

            init {
                attachInterface(this, DESCRIPTOR)
            }

            override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
                val descriptor = DESCRIPTOR
                return when(code) {
                    INTERFACE_TRANSACTION -> {
                        reply?.writeString(descriptor)
                        true
                    }
                    TRANSACTION_sayHi -> {
                        data.enforceInterface(descriptor)
                        val name = data.readString() ?: ""
                        sayHi(name)
                        reply?.writeNoException()
                        true
                    }
                    else -> {
                        super.onTransact(code, data, reply, flags)
                    }
                }
            }

            override fun asBinder(): IBinder {
                return this
            }
        }

        class Proxy(val remote: IBinder) : INoAIDL {

            override fun sayHi(name: String) {
                val _data = Parcel.obtain()
                val _reply = Parcel.obtain()
                try {
                    _data.writeInterfaceToken(DESCRIPTOR)
                    _data.writeString(name)
                    val _status = remote.transact(Stub.TRANSACTION_sayHi, _data, _reply, 0)
                    _reply.readException()
                } finally {
                    _data.recycle()
                    _reply.recycle()
                }
            }

            override fun asBinder(): IBinder {
                return remote
            }
        }
    }

}