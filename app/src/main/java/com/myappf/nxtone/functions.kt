package com.myappf.nxtone

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.widget.SearchView
import android.widget.TextView
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.Delayed

open class functions : AppCompatActivity() {

    private lateinit var progressd:Dialog
  fun snacj(msg:String, color:String,aftercomplete: () -> Unit): Boolean {
        val snack = Snackbar.make(findViewById(android.R.id.content),msg, Snackbar.LENGTH_LONG)
        val snackvie = snack.view
      snackvie.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener{
          override fun onViewAttachedToWindow(v: View?) {

          }

          override fun onViewDetachedFromWindow(v: View?) {
              aftercomplete()

          }

      })
      if (color.equals("red")) {
          snackvie.setBackgroundColor(Color.parseColor("#ED4545"))
      }else{
          snackvie.setBackgroundColor(Color.parseColor("#10BEE0"))
      }
        snack.setTextColor(Color.parseColor("#FFFFFF"))
        snack.show()
        return false
    }


   fun progrees(texttoshow:String): Boolean {
       progressd = Dialog(this)
        progressd.setContentView(R.layout.progress)
        progressd.setCancelable(false)
        progressd.window?.setBackgroundDrawableResource(
            android.R.color.transparent
        )
       progressd.findViewById<TextView>(R.id.texw).text = texttoshow

        progressd.setCanceledOnTouchOutside(false)
        progressd.show()
        return false

    }

    fun hideit(): Boolean {
        progressd.dismiss()
        return false

    }

    fun animateviewwithdelay(vw: View,millisecond: Long,anim:Animation){
        @Suppress("DEPRECATION")
        Handler().postDelayed(
            {
                vw.visibility = View.VISIBLE
                vw.startAnimation(anim)
            },
            millisecond
        )

    }



    fun delay(millisecond: Long,delay: () -> Unit){
        @Suppress("DEPRECATION")
        Handler().postDelayed(
            {
            delay()
            },
            millisecond
        )



    }

    fun View.search(aftersearch: (cons:String) -> Unit){
        val serachview = this as SearchView
        serachview.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false

            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    aftersearch(newText)
                }
                return false
            }

        })

    }

    fun String.hasCharacters(then:() -> Unit){
        if (this.isNotBlank()){
            then()
        }
    }

    open fun <S> List<S>.satisfies(values:List<S>, block:(S,S) -> Boolean): Boolean{
        var checkBool = false
        this.forEach { anyType ->
            if(block(anyType,values[this.indexOf(anyType)])) checkBool = true else return false.also { checkBool = it }
        }
        return checkBool
    }

}