package com.myappf.nxtone

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


class search : functions(),recyclerviewadapter.onclickitem {

    private var j = 0
    private var arr  = ArrayList<Userdata>().toMutableList()
    private  var animistrue:Boolean = true

    private  lateinit var adapter:recyclerviewadapter
    private lateinit var ourfilteredlist:ArrayList<Userdata>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


        val lintop = findViewById<LinearLayout>(R.id.toptobot)
        val searchit = findViewById<android.widget.SearchView>(R.id.searchit)
        val anime = AnimationUtils.loadAnimation(this,R.anim.toptobot)
        val refresh = findViewById<SwipeRefreshLayout>(R.id.refresh)
        val bototop = findViewById<LinearLayout>(R.id.bototop)
        var i = 1
        val curruser = FirebaseAuth.getInstance().currentUser?.uid.toString()

        val profname = findViewById<TextView>(R.id.txbor)
        val profdescription = findViewById<TextView>(R.id.txbors)




        progrees("Please Wait..")
        lintop.visibility = View.INVISIBLE


        val recles = findViewById<RecyclerView>(R.id.recl)
         fun getdata(cont: Context): MutableList<Userdata> {
            val getdir = FirebaseDatabase.getInstance()
            val data = getdir.getReference("/myusers")
            var reclarray = ArrayList<Userdata>().toMutableList()
            data.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {


                    for (dat in snapshot.children) {
                        val userfr = dat.getValue(Userdata::class.java)
                        if (userfr != null) {
                            if (userfr.useruid != curruser) {
                                reclarray.add(userfr)
                                if (userfr !in arr) {
                                    arr.add(userfr)

                                }
                            }


                        }
                    }

                    adapter = recyclerviewadapter(cont, reclarray)
                    recles.adapter = adapter
                    hideit()
                    if (i==1) {
                        animateviewwithdelay(lintop, 50, anime)
                        i=2
                    }
                    recles.startLayoutAnimation()


                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

            return reclarray

        }
        ourfilteredlist = arr as ArrayList<Userdata>



        searchit.search {
            adapter.filter.filter(it)
            if (it.isBlank()){
                ourfilteredlist = arr as ArrayList<Userdata>
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



        getdata(this)
        recles.layoutManager = LinearLayoutManager(this)
        recles.setHasFixedSize(true)


        refresh.setOnRefreshListener {

                progrees("Just a moment..")
                getdata(this)
                refresh.isRefreshing = false
                searchit.setQuery("", true)
                searchit.clearFocus()




        }









    }





    override fun clickitem(pos: Int) {
        if (animistrue) {
            getheight(pos)
            delay(800) {
                val recless = findViewById<RecyclerView>(R.id.recl)
                val onelement = ArrayList<Userdata>().toMutableList()
                for (i in 0.until(ourfilteredlist.size)) {
                    if (i == pos) {
                        onelement.add(ourfilteredlist[i])
                    }
                }
                val adap = recyclerviewadapter(this, onelement)
                recless.adapter = adap


            }
            animistrue = false

        }else{

        }




    }

    override fun passfilteredlist(fillist: ArrayList<Userdata>) {
        ourfilteredlist = fillist
        val recle = findViewById<RecyclerView>(R.id.recl)
        val adp = recyclerviewadapter(this,ourfilteredlist)
        recle.adapter = adp


    }



    private fun getheight(posi:Int){
        val conslay = findViewById<LinearLayout>(R.id.toptobot)
        val totalheight = conslay.measuredHeight
        val textx = findViewById<TextView>(R.id.td)
        val textheight = textx.measuredHeight
        val viewheight = findViewById<View>(R.id.divider).measuredHeight
        val linlay = findViewById<LinearLayout>(R.id.linlat)
        val linlayheight = linlay.measuredHeight

        val subheight = 40F + textheight + linlayheight + 5F + 40F + 15F + viewheight +15F
        val heightbypercent = totalheight - subheight
        nice(posi,heightbypercent,this)

    }

    private fun nice(position:Int,heightbypercent:Float,co:Context) {

        val conslay = findViewById<LinearLayout>(R.id.toptobot)

        val lintop = findViewById<LinearLayout>(R.id.toptobot)
        val bototop = findViewById<LinearLayout>(R.id.bototop)
        val seracv = findViewById<LinearLayout>(R.id.line)
        val imgv  = findViewById<ImageView>(R.id.photo)
        val pro = findViewById<TextView>(R.id.txbor)
        val remage = findViewById<TextView>(R.id.rimage)
        val lintopfar = AnimationUtils.loadAnimation(this,R.anim.lintopfor)
        val back = findViewById<ImageButton>(R.id.back)




        back.setOnClickListener {
                animistrue = true
                val refresh = findViewById<SwipeRefreshLayout>(R.id.refresh)
                refresh.isEnabled = true
                j = 0
                recycleronboard()

            bototop.startAnimation(lintopfar)
            lintopfar.setAnimationListener(object :Animation.AnimationListener{
                override fun onAnimationStart(animation: Animation?) {

                }

                override fun onAnimationEnd(animation: Animation?) {
                   seracv.visibility = View.VISIBLE
                }

                override fun onAnimationRepeat(animation: Animation?) {

                }

            })
            bototop.visibility = View.GONE
            lintop.animate().apply {
                duration = 1000
                translationYBy(-(heightbypercent))

            }



        }



        if (heightbypercent != 0F) {
            if (j == 0) {

                val refresh = findViewById<SwipeRefreshLayout>(R.id.refresh)
                refresh.isEnabled = false
                lintop.animate().apply {
                    duration = 800
                    translationYBy(heightbypercent)
                }
                imgv.layoutParams.height = (conslay.measuredHeight * 0.21).toInt()
                imgv.layoutParams.width = (conslay.measuredHeight * 0.21).toInt()
                j=1

                if (ourfilteredlist[position].prophoto.equals("nothing")){
                    Glide.with(co).load(R.drawable.forphoto).circleCrop()
                        .placeholder(R.drawable.avatar).error(R.drawable.avatar)
                        .dontAnimate().into(imgv)
                    val gd = (imgv.getBackground() as GradientDrawable).mutate()
                    val mycolor = gd as GradientDrawable
                    mycolor.setColor(Color.parseColor("#03b6fc"))
                    remage.text = ourfilteredlist[position].nameimage


                }else {
                    remage.text = ""
                    val gd = (imgv.getBackground() as GradientDrawable).mutate()
                    val mycolor = gd as GradientDrawable
                    mycolor.setColor(Color.WHITE)
                    Glide.with(co).load(ourfilteredlist[position].prophoto).circleCrop()
                        .placeholder(R.drawable.avatar).error(R.drawable.avatar)
                        .dontAnimate().into(imgv)

                }
                pro.text = ourfilteredlist[position].username
                seracv.visibility = View.INVISIBLE
                bototop.startAnimation(AnimationUtils.loadAnimation(co,R.anim.bototop))
                bototop.visibility = View.VISIBLE

            }

        }
    }

    private fun recycleronboard(){
        val srch = findViewById<SearchView>(R.id.searchit)
        srch.setQuery("",true)
        srch.clearFocus()
        val recycler = findViewById<RecyclerView>(R.id.recl)
        recycler.adapter = adapter



    }


}