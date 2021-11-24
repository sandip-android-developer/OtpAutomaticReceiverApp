package com.example.otpautomaticreceiverapp

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.demo_library.Calculator
import com.google.android.gms.auth.api.phone.SmsRetriever

class MainActivity : AppCompatActivity(), OtpReceiver.OTPReceiveListener {
    //<#> Your OTP is 123456.ytbOFvc2X2n //Message formate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startSmsUserConsent()
    }

    private fun startSmsUserConsent() {
        val appSignatureHelper = AppSignatureHashHelper(this)
        println("Hash key--" + appSignatureHelper.appSignatures[0])
        val client = SmsRetriever.getClient(this)
        //We can add sender phone number or leave it blank
        // I'm adding null here
        client.startSmsRetriever().addOnSuccessListener {
            Toast.makeText(
                applicationContext,
                "On Success",
                Toast.LENGTH_LONG
            ).show()
        }.addOnFailureListener {
            Toast.makeText(applicationContext, "On OnFailure", Toast.LENGTH_LONG).show()
        }
        OtpReceiver.otpListener = this
    }

    override fun onOTPReceived(otp: String?) {
        println("COmming--$otp")
        Toast.makeText(applicationContext, "COmming--$otp", Toast.LENGTH_LONG).show()
        findViewById<EditText>(R.id.edtOtp).setText(otp)
    }

    override fun onOTPNotReceived(message: String?) {
        Toast.makeText(applicationContext, "COmming--$message", Toast.LENGTH_LONG).show()
    }
}
