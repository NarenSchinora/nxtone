package com.myappf.nxtone




import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlin.reflect.typeOf

class chatsrecycleadapter(val act: Context, val listus:MutableList<Userdata>) :RecyclerView.Adapter<chatsrecycleadapter.onlyimages>() {



    private var fullitmes = ArrayList<Userdata>()

    private var holderarray = ArrayList<onlyimages>()

    init {

        fullitmes =   listus as  ArrayList<Userdata>
    }



    inner class onlyimages(v:View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        val roundph = v.findViewById<ImageView>(R.id.usrpic)
        val mytext = v.findViewById<TextView>(R.id.textimage)
        val gonetext = v.findViewById<TextView>(R.id.gonetext)
        val cards = v.findViewById<ImageView>(R.id.cards)



        init {
            v.setOnClickListener(this)


        }

        override fun onClick(view: View?) {
            val activity = act as onclickitem
            val pos = bindingAdapterPosition
            if (pos != -1){
                activity.clickitem(pos, view)



            }


        }



    }




    interface onclickitem{
        fun clickitem(pos:Int, item: View?)
        fun GetArrayOfViews(holderArray: ArrayList<onlyimages>)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): onlyimages {
        val inflation = LayoutInflater.from(act).inflate(R.layout.rec_photo,parent,false)
        return onlyimages(inflation)
    }

    override fun onBindViewHolder(holder: onlyimages, position: Int) {
        val activity = act as onclickitem
        val usertext = fullitmes[position].username
        val userimage = fullitmes[position].prophoto
        var userimagee = ""

        if (holder in holderarray){

        }else{
            holderarray.add(holder)
        }
        activity.GetArrayOfViews(holderarray)



        if (userimage.contains("nothing")){
            val gd = (holder.roundph.getBackground() as GradientDrawable).mutate()
            val mycolor = gd as GradientDrawable
            mycolor.setColor(Color.parseColor("#03b6fc"))
            userimagee = fullitmes[position].nameimage
            holder.mytext.text = userimagee

        }else{

            holder.roundph.setImageBitmap(null)
            insertimage(holder.roundph,holder.mytext,userimage)

        }
        holder.gonetext.text = usertext
    }

    private fun insertimage(image:ImageView,text:TextView, userimg:String){
        text.text = ""
        Glide.
        with(act).asBitmap().
        load(userimg.toUri()).
        override(200,200).
        circleCrop().
        placeholder(R.drawable.avatar).
        error(R.drawable.avatar).into(image)


    }

    override fun getItemCount() = fullitmes.size



    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }




}