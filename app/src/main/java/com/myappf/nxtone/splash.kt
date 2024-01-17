package com.myappf.nxtone

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowInsets
import android.view.WindowManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import io.grpc.internal.GrpcUtil

class splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }else{
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
            )


        }
        @Suppress("DEPRECATION")
        Handler().postDelayed(
            {
                if (FirebaseAuth.getInstance().currentUser?.uid == null){
                    startActivity(Intent(this,login::class.java))
                    finish()
                }else {
                    startActivity(Intent(this, chats::class.java))
                    finish()
                }
            },
        1000
        )





    }
}