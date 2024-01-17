package com.myappf.nxtone

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.provider.MediaStore
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.lang.Exception
import java.util.*

open class IMGIFRecievernSender:functions() {

   fun ImgToBase64(img:Bitmap): String {
        val btArOutputStream = ByteArrayOutputStream()
        img.compress(Bitmap.CompressFormat.PNG,100,btArOutputStream)
        val byteimage: ByteArray = btArOutputStream.toByteArray()
        val ImgString:String = Base64.encodeToString(byteimage,Base64.DEFAULT)
        return ImgString
    }

    fun Base64toImg(getEnStr:String): Bitmap {
        val byteArray:ByteArray = Base64.decode(getEnStr,Base64.DEFAULT)
        val recivedImg:Bitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
        return recivedImg
    }


    fun GIFparser(){

    }
}