package com.myappf.nxtone

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class register : functions() {

    private  val fbdata =   FirebaseDatabase.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)



        val username = findViewById<TextInputLayout>(R.id.xt)
        val email = findViewById<TextInputLayout>(R.id.txts)
        val mobile = findViewById<TextInputLayout>(R.id.xst)
        val password = findViewById<TextInputLayout>(R.id.ttz)
        val confirmpassword = findViewById<TextInputLayout>(R.id.ttsx)
        val bregister = findViewById<Button>(R.id.button)
        val scalefrom = AnimationUtils.loadAnimation(this,R.anim.scalefrom)




        val linaw = findViewById<LinearLayout>(R.id.dss)
        linaw.visibility = View.INVISIBLE
        bregister.visibility = View.INVISIBLE
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }else{
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
            )


        }



        email.editText?.doOnTextChanged { text, start, before, count ->
            if (text.toString().isEmpty()){
                email.error = "Please enter Email Address"
            }else{
                email.error = null
            }
        }

        animateviewwithdelay(bregister,2000,scalefrom)


        username.editText?.doOnTextChanged { text, start, before, count ->
            if (text.toString().isEmpty()){
                username.error = "Please enter Username"
            }else{
                username.error = null
            }
        }

        password.editText?.doOnTextChanged { text, start, before, count ->
            if (text.toString().length> 20){
                password.error = "Password exceeds given limit"
            }else if (text.toString().isEmpty()){
                password.error = "Please enter Password"
            }else{
                password.error = null
            }

        }


        confirmpassword.editText?.doOnTextChanged { text, start, before, count ->


            if (password.editText?.text.toString().isEmpty()){
                confirmpassword.error = "Please enter Password first"
            }else if (password.editText?.text.toString().isNotEmpty()){
                if (text.toString().length > 20) {
                    confirmpassword.error = "Password exceeds given limit"
                }
                else if(text.toString().isEmpty()){
                    confirmpassword.error = "Please confirm the Password"
                }else{
                    confirmpassword.error = null

                }
            }


        }


        mobile.editText?.doOnTextChanged { text, start, before, count ->

            if (text.toString().length> 10){
                mobile.error = "Please enter valid mobile number"
            }else if (text.toString().isEmpty()){
                mobile.error = "Please enter Mobile number"
            }else{
                mobile.error = null
            }

        }


        val reganim = AnimationUtils.loadAnimation(this,R.anim.med)


        linaw.setOnClickListener {
            startActivity(Intent(this,login::class.java))
            overridePendingTransition(R.anim.rest,R.anim.rips)
            finish()
        }

      animateviewwithdelay(linaw,400,reganim)

        fun check() :Boolean{
            var bool = false
            if(confirmpassword.editText?.text.toString().isEmpty()){
                bool = false
                confirmpassword.error = "Please confirm the Password"

            }
            if (email.error == null && username.error == null && mobile.error == null && password.error == null && confirmpassword.error == null){
                if (password.editText?.text.toString().equals(confirmpassword.editText?.text.toString())){
                    bool = true
                }else{

                    password.editText?.text?.clear()
                    confirmpassword.editText?.text?.clear()
                    confirmpassword.error = "Password Mismatch"

                }

            }

                if (email.editText?.text.toString().isEmpty()){
                    bool = false
                    email.error = "Please enter Email Address"

                }
                if(username.editText?.text.toString().isEmpty()){
                    bool = false
                    username.error = "Please enter Username"

                }

                if(mobile.editText?.text.toString().isEmpty()){
                    bool = false
                    mobile.error = "Please enter Mobile number"

                }else if (mobile.editText?.text.toString().length<10){
                    mobile.error = "Please enter a valid mobile number"

                }

                if (password.editText?.text.toString().isEmpty()){
                    bool = false
                    password.error = "Please enter Password"

                }

            return bool
        }

        fun clearfoc(){
            confirmpassword.clearFocus()
            password.clearFocus()
            username.clearFocus()
            email.clearFocus()
            mobile.clearFocus()
        }




        bregister.setOnClickListener {
            clearfoc()
            if (check()){
                progrees("Processing")
                val emailis = if (email.editText?.text.toString().contains("@gmail.com")) email.editText?.text.toString().trim() else {email.editText?.text.toString() +"@gmail.com"}.trim()
                val passwrodis = password.editText?.text.toString()

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailis,passwrodis)
                    .addOnCompleteListener {
                        if (it.isSuccessful){
                            var usermage = ""
                            for (o in username.editText?.text.toString().trim().split(" ")){
                                if (o.isNotEmpty() || o != null) {
                                    usermage += o[0].toUpperCase()
                                }

                            }

                            val userformat = Userdata(username.editText?.text.toString(),it.result!!.user!!.uid.toString(),mobile.editText?.text.toString().toLong(),usermage)
                            getuserdetails(userformat)






                        }else


                        {
                            hideit()
                            bregister.visibility = View.INVISIBLE

                            snacj("${it.exception!!.message.toString()}","red"){
                                delay(200){
                                    bregister.visibility = View.VISIBLE
                                    bregister.startAnimation(scalefrom)

                                }

                            }


                        }

                    }



            }
        }
    }


    override fun onBackPressed() {
        startActivity(Intent(this,login::class.java))
        overridePendingTransition(R.anim.rest,R.anim.rips)
        finish()
        super.onBackPressed()
    }

    private fun getcurruser(): String {
        val curruserinc = FirebaseAuth.getInstance().currentUser
        var currus = if (curruserinc == null) "null" else curruserinc.uid.toString()
        return currus
    }

   private fun getuserdetails (mydata:Userdata) {
        val userdataref =  fbdata.getReference("/myusers/${getcurruser()}")
        userdataref.setValue(mydata).addOnSuccessListener { result ->
            hideit()
            val intentd = Intent(this,prophotoselector::class.java)
            intentd.putExtra(values.uid,getcurruser())
            startActivity(intentd)
            overridePendingTransition(R.anim.pendi2,R.anim.pendinganim)
            finish()

        }.addOnFailureListener{ except ->
            hideit()
            println("${except.message.toString()}")

        }

    }
}

