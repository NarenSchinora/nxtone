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
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.transition.Hold

class recyclerviewadapter(val act: Context,val listus:MutableList<Userdata>) :RecyclerView.Adapter<recyclerviewadapter.mytextandimages>(),Filterable {



    private var fullitmes = ArrayList<Userdata>()
    init {

         fullitmes =   listus as  ArrayList<Userdata>
    }


    inner class mytextandimages(v:View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        val usertext = v.findViewById<TextView>(R.id.usernames)
        val textforimage = v.findViewById<TextView>(R.id.textforimage)
        val imageforimage = v.findViewById<ImageView>(R.id.userpic)

        init {
            v.setOnClickListener(this)

        }

        override fun onClick(v: View?) {
            val activity = act as onclickitem
            val pos = adapterPosition
            if (pos != -1){
                activity.clickitem(pos)
            }


        }


    }




    interface onclickitem{
        fun clickitem(pos:Int)
        fun passfilteredlist(fillist:ArrayList<Userdata>)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mytextandimages {

        val inflation = LayoutInflater.from(act).inflate(R.layout.recycler,parent,false)
        return mytextandimages(inflation)

    }

    override fun onBindViewHolder(holder: mytextandimages, position: Int) {
            val usertext = fullitmes[position].username
            val userimage = fullitmes[position].prophoto
            var userimagee = ""
            if (userimage.contains("nothing")){
                val gd = (holder.imageforimage.background as GradientDrawable).mutate()
                val mycolor = gd as GradientDrawable
                mycolor.setColor(Color.parseColor("#03b6fc"))
                userimagee = fullitmes[position].nameimage
                holder.textforimage.text = userimagee



            }else{

                holder.imageforimage.setImageBitmap(null)
                insertimage(holder.imageforimage,holder.textforimage,userimage)

            }
            holder.usertext.text = usertext




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
    override fun getFilter(): Filter {
        return myfilter

    }

    private val myfilter = object : Filter(){
        override fun performFiltering(constraint: CharSequence?): FilterResults {

            var newlist = ArrayList<Userdata>()
            if (constraint.isNullOrEmpty()){
                newlist = listus as ArrayList<Userdata>
            }else{

                val checkstr = constraint.toString().toLowerCase()
                for (product in listus){
                    if (product.username.toLowerCase().contains(checkstr)){
                        newlist.add(product)

                    }


                }

            }

            val filRes = FilterResults()
            filRes.values = newlist
            return filRes




        }


        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if (results != null) {
                val activity = act as onclickitem
                activity.passfilteredlist(results?.values as ArrayList<Userdata>)
                notifyDataSetChanged()
            }

        }

    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }




}