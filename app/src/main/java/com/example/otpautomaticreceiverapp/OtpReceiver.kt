package com.example.otpautomaticreceiverapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

class OtpReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent?.action) {
            val extras = intent.extras
            val status = extras!![SmsRetriever.EXTRA_STATUS] as Status?
            when (status!!.statusCode) {
                CommonStatusCodes.SUCCESS -> {
                    val message = extras[SmsRetriever.EXTRA_SMS_MESSAGE] as String
                    Log.e(TAG, "message: $message")
                    val otp = getOtp(message)
                    Log.e(TAG, "OTP: $otp")
                    otpListener?.onOTPReceived(otp)
                }
                CommonStatusCodes.TIMEOUT -> {
                }
            }
        }

    }

    private fun getOtp(sms: String): String {
        var isDigitFound = false
        val stringArr = sms.split("").toTypedArray()
        val builder = StringBuilder()
        for (string in stringArr) {
            if (string.matches(Regex("[0-9]+"))) {
                builder.append(string)
                isDigitFound = true
            } else {
                if (isDigitFound) {
                    return builder.toString()
                }
            }
        }
        return ""
    }

    companion object {
        private val TAG = OtpReceiverApplication::class.java.simpleName

        var otpListener: OTPReceiveListener? = null
    }

    interface OTPReceiveListener {
        fun onOTPReceived(otp: String?)
        fun onOTPNotReceived(message: String?)
    }
}

