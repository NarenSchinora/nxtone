package com.myappf.nxtone

import android.app.Activity
import android.app.Instrumentation
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.core.operation.Merge
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.security.Permission
import java.util.jar.Manifest

class prophotoselector : functions() {
    private lateinit var urifromimage:Uri
    private var context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prophotoselector)



        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }else{
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
            )


        }

        val skip = findViewById<TextView>(R.id.tx)
        val proceed = findViewById<Button>(R.id.proceed)
        val changephoto = findViewById<Button>(R.id.changephoto)
        val selecctphoto = findViewById<Button>(R.id.selectphoto)
        val  intent = Intent(Intent.ACTION_GET_CONTENT,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type ="image/*"


        selecctphoto.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                startActivityForResult(intent,200)

            }else{
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    100
                )
            }


        }

        changephoto.setOnClickListener {
            startActivityForResult(intent,200)
        }










    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 100){
            if (grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                val selecctphoto = findViewById<Button>(R.id.selectphoto)
                selecctphoto.performClick()

            }else{
                snacj("Please grant the permission to continue further..","red"){}
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 200 && resultCode == Activity.RESULT_OK){
            if (data!=null){
                val selecctphoto = findViewById<Button>(R.id.selectphoto)
                val proceed = findViewById<Button>(R.id.proceed)
                val changephoto = findViewById<Button>(R.id.changephoto)
                val text1 = findViewById<TextView>(R.id.tdxx)
                val text2 = findViewById<TextView>(R.id.tdz)

                val photoselection = findViewById<ImageView>(R.id.photo)
                urifromimage = data.data!!
                Glide.
                with(context).
                load(urifromimage).
                circleCrop().
                placeholder(R.drawable.avatar).
                error(R.drawable.avatar).
                into(photoselection)
                text2.text = "Well Done!"
                text1.text = "Your profile picture is set.Let's move on!"
                selecctphoto.visibility = View.INVISIBLE
                proceed.visibility = View.VISIBLE
                changephoto.visibility = View.VISIBLE

                proceed.setOnClickListener{
                    imgtocloud(urifromimage)

                }





            }

        }
    }

    private fun imgtocloud(Urlimage:Uri) {
        val infouid = intent.getStringExtra(values.uid)
        val cloudref = FirebaseStorage.getInstance().getReference("/images/${infouid}")
        if (Urlimage != null) {
            cloudref.putFile(Urlimage!!).addOnSuccessListener {
                it.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                    uploadurltodb(it.toString())
                }



            }.addOnFailureListener {
                snacj("{${it.message}", "red") {}

            }
        }


    }

    private fun uploadurltodb(strurl:String) {
        val updateuserphoto = HashMap<String,Any>()
        val infouid = intent.getStringExtra(values.uid)
        val db = FirebaseDatabase.getInstance().getReference("/${values.mysers}/${infouid}")
        println(infouid)
        updateuserphoto[values.prophoto] = strurl
        db.updateChildren(updateuserphoto).addOnSuccessListener {
            println("updated buddy")
        }.addOnFailureListener {
            println("${it.message}")
        }


    }






}