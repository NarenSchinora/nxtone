package com.myappf.nxtone

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isEmpty
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.widget.doOnTextChanged
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class login : functions() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val txt1 = findViewById<TextInputLayout>(R.id.txt)
        val linear = findViewById<LinearLayout>(R.id.dh)
        val txt2 = findViewById<TextInputLayout>(R.id.tt)
        val mybutton = findViewById<Button>(R.id.mybutton)
        linear.visibility = View.GONE




        txt1.editText?.doOnTextChanged { text, start, before, count ->
            if (text.toString().isEmpty()){
                txt1.error = "Please enter Email Address"
            }else{
                txt1.error = null
            }
        }

        txt2.editText?.doOnTextChanged { text, start, before, count ->
            if (text.toString().length> 20){
                txt2.error = "Password exceeds given limit"
            }else if (text.toString().isEmpty()){
                txt2.error = "Please enter Password"
            }else{
                txt2.error = null
            }

        }








        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }else{
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
            )


        }


        val loginanim = AnimationUtils.loadAnimation(this,R.anim.mednew)
        linear.setOnClickListener {
            startActivity(Intent(this,register::class.java))
            overridePendingTransition(R.anim.reis,R.anim.res)
            finish()
        }

        animateviewwithdelay(linear,400,loginanim)




    fun nullcheck() : Boolean{
        var bool = false
        if (txt1.error == null && txt2.error == null){
            bool = true
        }
        if (txt1.editText?.text.toString().isEmpty()){
            bool = false
            txt1.error = "Please enter Email Address"

        }
        if(txt2.editText?.text.toString().isEmpty()){
            bool = false
            txt2.error = "Please enter Password"
        }

        return bool

    }
        fun clearfoc(){
            txt1.clearFocus()
            txt2.clearFocus()
        }

        mybutton.setOnClickListener {
            clearfoc()

            if (nullcheck()){
                progrees("Logging in..")
                val email = txt1.editText?.text.toString()
                val password = txt2.editText?.text.toString()

                FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnSuccessListener {
                    hideit()
                    val intentd = Intent(this,chats::class.java)
                    startActivity(intentd)
                    overridePendingTransition(R.anim.pendi2,R.anim.pendinganim)
                    finish()




                }.addOnFailureListener {
                    hideit()
                    linear.visibility = View.INVISIBLE

                        snacj("${it.message.toString()}","red"){
                            delay(200){
                                linear.visibility = View.VISIBLE
                                linear.startAnimation(loginanim)

                            }

                        }



                }



            }

        }



        }


    }







