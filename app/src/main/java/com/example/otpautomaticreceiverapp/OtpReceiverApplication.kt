package com.example.otpautomaticreceiverapp

import android.app.Application
import android.util.Log

class OtpReceiverApplication: Application() {
    private val TAG = OtpReceiverApplication::class.java.simpleName
    override fun onCreate() {
        super.onCreate()
        val appSignatureHashHelper = AppSignatureHashHelper(this)
        Log.e(TAG, "HashKey: " + appSignatureHashHelper.appSignatures[0])
    }

}