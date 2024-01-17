package com.myappf.nxtone

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.animation.doOnEnd
import androidx.core.net.toUri
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.io.InputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class chats : functions(),chatsrecycleadapter.onclickitem,realchatmessages.onclickitem,
    View.OnLongClickListener {
    private var arrf = ArrayList<Userdata>()
    private lateinit var adapter:chatsrecycleadapter
    private lateinit var cadapter:realchatmessages
    private var linecount = 0
    private var change:Int = -2
    private var usp = 0
    private var path = ""
    private var plueterp = 0
    private var jade = false
    private  var isp = 0
    private var clicked = false
    private var noticelinecount = 0
    private var noht = 0
    private var interp = 0
    private var cypher = false
    private var fromedits = false
    private lateinit var mybuts:View
    private lateinit var myself:Userdata
    private lateinit var curruser:String
    private lateinit var viewHolderArray: ArrayList<chatsrecycleadapter.onlyimages>
    private var position = 0
    private var initial = 1
    private lateinit var mychatrecl:RecyclerView
    private var animbool = false
    private var exactrecpos = -4
    private var chatlaytype = 0
    private var removal = false
    private var chatitempos = 0
    private var chattytexty  = ""
    private var typofind = 0
    private var editing = false
    private var replytext = ""
    private var quickbool = false
    private var conceal = false
    private var inserted = false
    private var go = false
    private var new_messages_for_scroll = 0
    private var initiate = false
    private var uuids = ""
    private var unids = ""
    private var emsg = ""
    private var deleteduid = ""
    private var reply_uid = ""
    private var lastmsg:WithViewType? = null
    private var single = false
    private var text:String = ""
    private var getrequiredposition:Int = 0
    private val RECIEVED_REQUEST = 500

    private lateinit var quickmsg:TextView
    private lateinit var quickcancel:ImageView
    private lateinit var liya:RelativeLayout
    private lateinit var lina:LinearLayout
    private lateinit var linea:LinearLayout
    private lateinit var lins:LinearLayout
    private lateinit var quickrep:LinearLayout
    private lateinit var chatrec:RecyclerView
    private lateinit var layManager: LinearLayoutManager
    private lateinit var justOb:IMGIFRecievernSender


    private var childlistener:ChildEventListener? = null
    private var valuelistener:ValueEventListener? = null


    private lateinit var cancel:FloatingActionButton
    private lateinit var fab1:ExtendedFloatingActionButton
    private lateinit var fab2:ExtendedFloatingActionButton
    private lateinit var fab3:ExtendedFloatingActionButton
    private lateinit var fab4:ExtendedFloatingActionButton
    private lateinit var down:ExtendedFloatingActionButton

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chats)


        justOb = IMGIFRecievernSender()
        liya = findViewById(R.id.liya)
        chatrec = findViewById(R.id.recle)
        lins = findViewById(R.id.linsa)
        val voice = findViewById<Button>(R.id.flot)
        val people = findViewById<ImageButton>(R.id.bazck)
        linea = findViewById(R.id.lit)
        val chattext = findViewById<EditText>(R.id.edi)
        cancel = findViewById(R.id.myedits)
        val float = findViewById<FloatingActionButton>(R.id.floats)
        val chat = findViewById<FloatingActionButton>(R.id.chat)
        val cat = findViewById<FloatingActionButton>(R.id.cat)
        val chattochat = findViewById<FloatingActionButton>(R.id.edzt)
        mychatrecl = findViewById(R.id.recc)
        val clipboard: ClipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        lina = findViewById(R.id.linas)
        val  snkie = findViewById<CoordinatorLayout>(R.id.snack)
        quickrep = findViewById(R.id.lsz)
        quickmsg = findViewById(R.id.quicyk)
        quickcancel = findViewById(R.id.canc)
        val quickanan = AnimationUtils.loadAnimation(this,R.anim.quickrepback)
        val cancelit = AnimationUtils.loadAnimation(this,R.anim.cancel)
        val downcn = AnimationUtils.loadAnimation(this,R.anim.scaleto)
        val scrolist = listOf(RecyclerView.SCROLL_STATE_DRAGGING,RecyclerView.SCROLL_STATE_SETTLING)


        val getimg = Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        getimg.type = "image/*"


        fab1 = findViewById(R.id.cact)
        fab2 = findViewById(R.id.cazt)
        fab3 = findViewById(R.id.caxt)
        fab4 = findViewById(R.id.cavt)
        down = findViewById(R.id.down)

        val wrapup:ViewGroup.LayoutParams = linea.layoutParams
        val quickan  = AnimationUtils.loadAnimation(this,R.anim.fastanim)


        val scalfrom = ValueAnimator.ofFloat(0f,1f).setDuration(400)
        val scalto = ValueAnimator.ofFloat(1f,0f).setDuration(400)
        val agaanim = ValueAnimator.ofFloat(0f,1f).setDuration(1000)

        agaanim.addUpdateListener {
            voice.scaleX = it.animatedValue as Float
        }
        agaanim.interpolator = BounceInterpolator()
        agaanim.start()


        scalfrom.addUpdateListener {
            val scalval = it.animatedValue as Float
            mybuts.scaleX = scalval
            mybuts.scaleY = scalval
        }
        scalfrom.interpolator = BounceInterpolator()

        scalto.addUpdateListener {
            val scalval = it.animatedValue as Float
            mybuts.scaleX = scalval
            mybuts.scaleY = scalval
        }
        scalto.interpolator = AccelerateDecelerateInterpolator()






        fab4.setOnClickListener {
            quickbool = true
            replytext = chattytexty
            linavisibilility()
            quickmsg.text = chattytexty.trim()
            quickrep.startAnimation(quickan)
            quickrep.visibility = View.VISIBLE
        }

        quickcancel.setOnClickListener{
            replytext = ""
            quickrep.startAnimation(quickanan)
            quickrep.visibility = View.GONE
        }

        class LinearLayoutManagerWrapper(
            context: Context?,
            orientation: Int,
            reverseLayout: Boolean
        ) : LinearLayoutManager(context, orientation, reverseLayout) {

            override fun supportsPredictiveItemAnimations(): Boolean {
                return false
            }

        }

        val mLayoutManager: RecyclerView.LayoutManager =
            LinearLayoutManagerWrapper(this, LinearLayoutManager.VERTICAL, false)

        fab1.setOnClickListener {
            linavisibilility()
            val clip = ClipData.newPlainText("Message", chattytexty)
            clipboard.setPrimaryClip(clip)
            val snack = Snackbar.make(snkie,"Copied Successfully", 600)
            val snackvie = snack.view
            val sjn = snackvie.layoutParams
            sjn.width = ViewGroup.LayoutParams.WRAP_CONTENT
            snackvie.setBackgroundResource(R.drawable.snackbaru)
            snack.setTextColor(Color.parseColor("#FFFFFF"))
            snack.show()
        }


        fun wrapit(){
            wrapup.height = noht
            linea.layoutParams = wrapup
            noticelinecount = 0
            linecount = 0

        }

        fun superwrap(heigh:Int,line:Int,noticecount:Int){
            wrapup.height = noht + heigh
            linea.layoutParams = wrapup
            noticelinecount = noticecount
            linecount = line

        }

        down.setOnClickListener{
            down.shrink()
            mychatrecl.smoothScrollToPosition(cadapter.itemCount - 1)
            new_messages_for_scroll = 0
        }


        mychatrecl.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                linavisibilility()
                if (down.isExtended && newState in scrolist){
                    down.shrink()
                }else if(newState == RecyclerView.SCROLL_STATE_IDLE && new_messages_for_scroll > 0){
                    down.extend()
                }

            }


            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (layManager.findLastVisibleItemPosition() == cadapter.itemCount - 1) {
                    println("hello")
                    if (inserted){
                        inserted = false
                        messageseen(stream = true)
                    }

                    if (single){
                        single = false
                        messageseen()

                    }

                    if (liya.isVisible) {
                        go = false
                        liya.startAnimation(downcn)
                        liya.visibility = View.GONE
                        new_messages_for_scroll = 0
                    }

                }

                if(layManager.findLastVisibleItemPosition() < cadapter.itemCount - 2){
                    if (!liya.isVisible) {
                        down.shrink()
                        go = true
                        liya.visibility = View.VISIBLE
                        liya.startAnimation(cancelit)

                    }

                }


            }
        })


        mychatrecl.setOnTouchListener(object :View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (event?.action == MotionEvent.ACTION_UP){
                    linscheck()
                    linavisibilility()
                }
                return false
            }
        })




        fun canel(){
            fromedits = false
            agaanim.start()
            float.visibility = View.INVISIBLE
            chattochat.visibility = View.GONE
            chattext.clearFocus()
            if (chattext.text.isNullOrBlank()){
                mybuts = cat
                scalfrom.start()
                mybuts.visibility = View.VISIBLE
                cat.isEnabled = true
            }else {
                chat.visibility = View.VISIBLE
                chattext.text.clear()
            }
            wrapit()
            cancel.isEnabled = false
            delay(150){
                cancel.startAnimation(AnimationUtils.loadAnimation(this,R.anim.cancelbot))
                cancel.visibility = View.GONE
            }
        }



        fab3.setOnClickListener {
            chattochat.visibility = View.INVISIBLE
            fromedits = true
            plueterp = 1
            interp = 1
            edits(chattytexty)
            linavisibilility()
            cancel.startAnimation(cancelit)
            cancel.visibility = View.VISIBLE
            cancel.isEnabled = true

        }

        chattochat.setOnClickListener {
            hideKeyboardFrom(this,it)
            if (listOf(chattext.text.toString(),uuids,unids).satisfies(Collections.nCopies(3,true).toList()){ a,_ -> a.toString().isNotBlank() } && !chattext.text.toString().contentEquals(chattytexty))  {
                editit(arrf[position],uuids,unids,chattext.text.trim().toString(),reply_uid)
            }
            canel()
        }



        cadapter = realchatmessages(this)
        curruser = FirebaseAuth.getInstance().currentUser?.uid.toString()
        mychatrecl.layoutManager = mLayoutManager
        mychatrecl.setHasFixedSize(true)
        mychatrecl.adapter = cadapter
        layManager = mychatrecl.layoutManager as LinearLayoutManager
        layManager.stackFromEnd = true




        down.shrink()
        shrinkall()


        fab1.setOnLongClickListener(this)
        fab2.setOnLongClickListener(this)
        fab3.setOnLongClickListener(this)
        fab4.setOnLongClickListener(this)


        fab2.setOnClickListener {
            linavisibilility()
            if (exactrecpos > -1) {
                if (chatlaytype.equals(values.me)) {
                    deleteitem(arrf[position],uuids,unids,reply_uid)
                }

            }

        }

        chattext.setOnTouchListener { _, event ->
            if (event?.action == MotionEvent.ACTION_UP) {
                linscheck()
            }
            false
        }



        fun getdata(cont: Context): MutableList<Userdata> {
            val getdir = FirebaseDatabase.getInstance()
            val data = getdir.getReference("/myusers")
            val reclarray = ArrayList<Userdata>().toMutableList()

            data.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dat in snapshot.children) {
                        val userfr = dat.getValue(Userdata::class.java)
                        if (userfr != null) {
                            if (userfr.useruid != curruser) {
                                reclarray.add(userfr)
                                if (userfr !in arrf) {
                                    arrf.add(userfr)
                                }
                            }else{
                                myself = userfr

                            }


                        }
                    }

                    adapter = chatsrecycleadapter(cont,arrf)
                    chatrec.adapter = adapter

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

            return reclarray

        }

        val bc = findViewById<ImageButton>(R.id.bc)

        bc.setOnClickListener {
            linscheck()
        }


        people.setOnClickListener {
            liya.visibility = View.GONE
            hideKeyboardFrom(this,it)

            when{
                cancel.isVisible ->{
                    cancel.performClick()
                    checking()
                }
                quickrep.isVisible ->{
                    delay(400) {
                        quickcancel.performClick()
                        checking()
                    }
                }
                lina.isVisible ->{
                    linavisibilility()
                    checking()
                }

                else ->  checking()
            }


        }

        chat.setOnClickListener {
            hideKeyboardFrom(this,it)
            if (quickbool){
                quickrep.visibility = View.GONE
                quickbool = false
            }

            if (chattext.text.isNotBlank()) {
                delay(300) {
                    if (liya.isVisible && go){
                        down.shrink()
                        go = false
                        mychatrecl.scrollToPosition(cadapter.itemCount - 1)
                    }
                    savechattodb(arrf[position], chattext.text.toString().trim())
                    chattext.clearFocus()
                    chattext.text.clear()
                    wrapit()
                }

            }else if(noht>0){
                chattext.text.clear()
                chattext.clearFocus()
                wrapit()

            }


        }

        cat.setOnClickListener {
            hideKeyboardFrom(this,it)
            if (arrf.size > 0) {
                startActivityForResult(getimg,RECIEVED_REQUEST)
            }
        }

        getdata(this)
        chatrec.layoutManager = LinearLayoutManager(this)
        chatrec.setHasFixedSize(true)

        cancel.setOnClickListener {
            if (cancel.isVisible) {
                canel()
            }
        }

        chattext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (usp == 0) {
                    noht = linea.height
                    interp = 1
                    typofind = 1

                }



                if (s?.trim()?.length != 0) {
                    if (!fromedits && interp == 1) {
                        if (!cypher) {
                            float.visibility = View.GONE
                            interp = 0
                            mybuts = cat
                            scalto.start()
                            cat.isEnabled = false
                            scalto.doOnEnd {
                                mybuts.visibility = View.INVISIBLE
                                mybuts = chat
                                scalfrom.start()
                                mybuts.visibility = View.VISIBLE
                                chat.isEnabled = true
                            }
                        }
                    } else if (fromedits && plueterp == 1) {
                        float.visibility = View.GONE
                        plueterp = 0
                        if (chat.isVisible) {
                            mybuts = chat
                            scalto.start()

                        } else if (cat.isVisible) {
                            mybuts = cat
                            scalto.start()
                        } else {
                            mybuts = chattochat
                            mybuts.isEnabled = true
                            scalfrom.start()
                            mybuts.visibility = View.VISIBLE
                        }


                        if (scalto.isRunning) {
                            scalto.doOnEnd {
                                chat.visibility = View.GONE
                                cat.visibility = View.GONE
                                mybuts = chattochat
                                scalfrom.start()
                                mybuts.visibility = View.VISIBLE
                                mybuts.isEnabled = true

                            }
                        }


                    }

                }

                if (s.isNullOrBlank()) {
                    if (fromedits == false) {
                        agaanim.start()
                        float.visibility = View.INVISIBLE
                        if (chat.isVisible) {
                            interp = 1
                            mybuts = chat
                            chat.isEnabled = false
                            scalto.start()
                            scalto.doOnEnd {
                                mybuts.visibility = View.INVISIBLE
                                mybuts = cat
                                scalfrom.start()
                                mybuts.visibility = View.VISIBLE
                                cat.isEnabled = true

                            }
                        }





                    }else {
                        float.visibility = View.GONE
                        plueterp = 1
                        mybuts = chattochat
                        mybuts.isEnabled = false
                        mybuts.visibility = View.GONE
                        val paras: ViewGroup.LayoutParams = linea.layoutParams
                        paras.height = noht
                        linea.layoutParams = paras


                    }


                }



                usp = 1
                linscheck()


                when(chattext.lineCount){
                    1,2 -> {
                        superwrap(0,0,0)
                    }

                    3 ->{
                        superwrap(52,1,3)

                    }
                    4->{
                        superwrap(52*2,2,4)
                    }
                    else -> {
                        superwrap(52*3,3,5)

                    }
                }



                if (chattext.lineCount > 2 + linecount && chattext.lineCount < 6) {
                    linecount += 1
                    noticelinecount = chattext.lineCount
                    val parameters: ViewGroup.LayoutParams = linea.layoutParams
                    parameters.height = linea.height + 52
                    linea.layoutParams = parameters


                }else if(chattext.lineCount in 2 until noticelinecount) {
                    noticelinecount -= 1
                    linecount -= 1
                    val parameters: ViewGroup.LayoutParams = linea.layoutParams
                    parameters.height = linea.height - 52
                    linea.layoutParams = parameters

                }else if(noticelinecount == 2){
                    noticelinecount = 0

                }


            }

            override fun afterTextChanged(s: Editable?) {

            }

        })






    }

    override fun clickitem(pos: Int, item: View?) {
        position = pos
        if (change.equals(position)){
            clicked = true
        }else{
            cadapter.clear()
            refoff()
            clicked = change == -2
            change = position
            isp = 0
            jade = true
            typofind = 1
            exportchattomob(arrf[pos],this)

        }


        val usxpic = findViewById<ImageView>(R.id.usxrpic)
        val tdtext = findViewById<TextView>(R.id.tdbt)
        val tz = findViewById<TextView>(R.id.tz)
        val imp = chatrec.layoutManager


        val card = item?.findViewById<ImageView>(R.id.cards)
        val smoothScroller: SmoothScroller = object : LinearSmoothScroller(this) {
            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_ANY
            }
        }

        smoothScroller.targetPosition = pos
        getrequiredposition = pos


        /*Upper Part of the Recycler View*/
        imp?.findViewByPosition(pos + 1)?.let {
            if (imp.isViewPartiallyVisible(
                    it,
                    true,
                    true
                )
            ) {
                if ( pos - 1 != RecyclerView.NO_POSITION) {
                    smoothScroller.targetPosition = pos - 1
                }else{
                    smoothScroller.targetPosition = pos
                }
            }
        }

        /*Lower Part of the Recycler View*/
        imp?.findViewByPosition(pos - 1)?.let {
            if (imp.isViewPartiallyVisible(
                    it,
                    true,
                    true
                )
            ) {
                if ( pos + 1 < arrf.size) {
                    smoothScroller.targetPosition = pos + 1
                }else{
                    smoothScroller.targetPosition = pos
                }
            }
        }
        chatrec.layoutManager?.startSmoothScroll(smoothScroller)




        if (arrf[pos].prophoto.equals("nothing")){
            tz.text = arrf[pos].nameimage
            usxpic.setImageResource(R.drawable.forphoto)

        }else{
            tz.text = ""
            Glide.
            with(this).asBitmap().
            load(arrf[pos].prophoto.toUri()).
            circleCrop().
            placeholder(R.drawable.avatar).
            error(R.drawable.avatar).into(usxpic)
        }

        tdtext.text = arrf[pos].username



        for (ViewHolder in viewHolderArray){
            if (ViewHolder.cards.isVisible) {
                ViewHolder.cards.visibility = View.INVISIBLE
            }
            }

        card?.apply {
            this.visibility = View.VISIBLE
            val layoutanim = ValueAnimator
                .ofInt(0, card.width)
                .setDuration(500)
            layoutanim.addUpdateListener { animation -> // get the value the interpolator is at
                val value = animation.animatedValue as Int
                val paramsz:ViewGroup.LayoutParams = card.layoutParams
                paramsz.width = value
                this.layoutParams = paramsz
                this.requestLayout()
            }
            val set = AnimatorSet()
            set.play(layoutanim)
            set.interpolator = OvershootInterpolator( 6f)
            if (clicked && initial==1){
                set.start()
                initial = 2
            }else if (!clicked){
                set.start()
            }

        }

    }


    override fun GetArrayOfViews(holderArray: ArrayList<chatsrecycleadapter.onlyimages>) {
        viewHolderArray = holderArray
    }


    private fun checking(){
        val reverse = AnimationUtils.loadAnimation(this,R.anim.reverse)
        val recleanim = AnimationUtils.loadAnimation(this,R.anim.recleanim)
        when {
            lins.isInvisible ->{
                chatrec.scrollToPosition(getrequiredposition)
                lins.startAnimation(recleanim)
                lins.visibility = View.VISIBLE
            }
            lins.isVisible ->{
                lins.startAnimation(reverse)
                lins.visibility = View.INVISIBLE
            }

        }
    }





    fun hideKeyboardFrom(context: Context, view: View) {
        val imm: InputMethodManager =
            context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


    override fun longclick(pos: Int, getchatlayouttype:Int,view:View?) {

        val getlist = cadapter.senduuid(pos)
        uuids = getlist[0]
        unids = getlist[1]
        reply_uid = getlist[2]
        exactrecpos = pos
        chatlaytype = getchatlayouttype


        replytext = ""
        liya.visibility = View.GONE
        cancel.performClick()
        quickrep.visibility = View.GONE

        linscheck()

        chattytexty = (view as TextView).text.toString()
        hideKeyboardFrom(this,lina)

        if (lina.isVisible){
            if (chatitempos != pos){
                linavisibilility()
            }
        }else{
            chatitempos = pos
            when (getchatlayouttype) {
                values.me -> {
                    fab2.setIconResource(R.drawable.ic_baseline_delete_24)
                    fab3.show()
                }
                values.otheruser -> {
                    fab2.setIconResource(R.drawable.ic_baseline_layers_clear_24)
                    fab3.hide()
                }

            }



            val lastitem = layManager.findLastVisibleItemPosition()
            val firsitem = layManager.findFirstVisibleItemPosition()
            if (pos == firsitem) {
                mychatrecl.smoothScrollToPosition(firsitem)
            } else if (pos == lastitem) {
                mychatrecl.smoothScrollToPosition(lastitem)
            }

            val ann: LayoutAnimationController =
                AnimationUtils.loadLayoutAnimation(this, R.anim.lame)

            linea.visibility = View.GONE
            lina.layoutAnimation = ann
            lina.startLayoutAnimation()
            mychatrecl.clearFocus()
            lina.visibility = View.VISIBLE
            lina.clearAnimation()
            animbool = false
        }



    }


    private fun surrogatecheck(surr:String) : Boolean{
        var emotes = false
        for (i in surr){
            if (i.isSurrogate()){
                emotes = true
            }else if (i.isLetterOrDigit()){
                emotes = false
                break
            }

        }
        return emotes
    }

    override fun singleclick(positio: Int,v:View?,viewType: Int) {

        linscheck()
        if (v != null) {
            val grd = (v.background as GradientDrawable).mutate()
            val butta = grd as GradientDrawable
            val thirty = floattodp(30F).toFloat()
            butta.gradientType = GradientDrawable.LINEAR_GRADIENT
            val vs:View?
            val cs:View?
            val bs:View?
            val emoticon:View?
            val surrogate: View?

            if (viewType.equals(values.me)) {
                vs = find_id(positio,R.id.tia) as TextView
                cs = find_id(positio,R.id.bdd) as CoordinatorLayout
                surrogate = find_id(positio,R.id.nowcanyoustopme) as TextView
                bs = find_id(positio,R.id.xwxx) as ImageView
                emoticon =  find_id(positio,R.id.foremote) as ImageView

                if (vs.text.isNotBlank()) {
                    if (cs.isVisible) {
                        if (surrogatecheck(surrogate.text.toString())){
                            butta.colors = intArrayOf(Color.TRANSPARENT,Color.TRANSPARENT)
                        }else {
                            butta.colors = intArrayOf(
                                Color.parseColor("#F9D423"),
                                Color.parseColor("#e65c00")
                            )
                        }
                        cs.visibility = View.GONE
                        butta.cornerRadii = Collections.nCopies(8,thirty).toFloatArray()
                        if (bs.isVisible) {
                            gravsetter(bs, true, false, true)
                        }

                        if (emoticon.isInvisible){
                            emoticon.visibility = View.VISIBLE
                        }
                    } else {
                        if (surrogatecheck(surrogate.text.toString())){
                            butta.colors = intArrayOf(Color.TRANSPARENT,Color.TRANSPARENT)
                        }else {
                            butta.colors = intArrayOf(Color.WHITE, Color.WHITE)
                        }
                        butta.cornerRadii = floatArrayOf(thirty,thirty,0F,0F,thirty,thirty,thirty,thirty)
                        if (bs.isVisible) {
                            gravsetter(bs, true, true, false)
                        }
                        cs.visibility = View.VISIBLE
                        if (emoticon.isVisible){
                            emoticon.visibility = View.INVISIBLE
                        }

                    }
                }
            }else{
                vs = find_id(positio,R.id.tis) as TextView
                cs = find_id(positio,R.id.cdd) as CoordinatorLayout
                bs = find_id(positio,R.id.xwx) as ImageView
                surrogate = find_id(positio,R.id.nowcayoubeatme) as TextView
                emoticon = find_id(positio,R.id.forems) as ImageView

                if (vs.text.isNotBlank()){
                    if (cs.isVisible) {
                        if (surrogatecheck(surrogate.text.toString())){
                            butta.colors = intArrayOf(Color.TRANSPARENT,Color.TRANSPARENT)
                        }else {
                            butta.colors = intArrayOf(
                                Color.parseColor("#e65c00"),
                                Color.parseColor("#f9d423")
                            )
                        }
                        cs.visibility = View.GONE
                        butta.cornerRadii = Collections.nCopies(8,thirty).toFloatArray()
                        if (bs.isVisible) {
                            gravsetter(bs, true, true, false)
                        }

                        if (emoticon.isInvisible){
                            emoticon.visibility = View.VISIBLE
                        }

                    } else {
                        if (surrogatecheck(surrogate.text.toString())){
                            butta.colors = intArrayOf(Color.TRANSPARENT,Color.TRANSPARENT)
                        }else {
                            butta.colors = intArrayOf(Color.WHITE, Color.WHITE)
                        }
                        butta.cornerRadii = floatArrayOf(0F,0F,thirty,thirty,thirty,thirty,thirty,thirty)
                        if (bs.isVisible) {
                            gravsetter(bs, true, false, true)
                        }
                        cs.visibility = View.VISIBLE
                        if (emoticon.isVisible){
                            emoticon.visibility = View.INVISIBLE
                        }

                    }
                }

            }
        }

    }


    private fun find_id(takepos:Int,id:Int): View {
        return mychatrecl.layoutManager?.findViewByPosition(takepos)!!
            .findViewById(id)
    }

    override fun click_fromreply(okay: Int) {
        if (okay != values.no_reply_position){
            mychatrecl.smoothScrollToPosition(okay)
        }
    }


    private fun gravsetter(v:View?,top:Boolean,start:Boolean,end:Boolean){
        val arams = (v?.layoutParams) as CoordinatorLayout.LayoutParams
        when(true){
            top and end ->{
                arams.gravity = Gravity.TOP  or Gravity.END
            }
            top and start ->{
                arams.gravity = Gravity.TOP or  Gravity.START
            }


        }
        v.layoutParams = arams

    }
    private fun floattodp(flo: Float) : Int{
        return ((flo * android.content.res.Resources.getSystem().displayMetrics.density) + 0.5f).toInt()

    }




    private fun savechattodb(theotheruserdata:Userdata,chatlocal:String,image:Boolean = false){

        if (chatlocal.isNotBlank()) {
            val simp = SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z")
            val cdate: String = simp.format(Date())
            val uuid = UUID.randomUUID().toString() + cdate + cadapter.itemCount
            var fromuuid = ""
            var imgInt = 0

            val otheruseruid = theotheruserdata.useruid
            val getrefernce = FirebaseDatabase.getInstance().getReference("/${values.chats}/$curruser $otheruseruid").push()
            val opprefernce = FirebaseDatabase.getInstance().getReference("/${values.chats}/$otheruseruid $curruser").push()


            val mykey = getrefernce.key.toString()
            val yourkey = opprefernce.key.toString()
            if (replytext.isNotBlank()){
                fromuuid = reply_uid
            }

            if (image){
                imgInt = 1
            }

            val mychat  = chatuser(chatlocal, curruser,otheruseruid,reply = replytext,unid = uuid,mychatdbkey = mykey,otherchatdbkey = yourkey,from_unid = fromuuid,othermsgseen = values.read,image = imgInt)
            val otherchat = chatuser(chatlocal, curruser,otheruseruid,reply = replytext,unid = uuid,mychatdbkey = yourkey,otherchatdbkey = mykey,from_unid = fromuuid,messageseen = values.read,image = imgInt)



            getrefernce.setValue(mychat)
            opprefernce.setValue(otherchat)

            replytext = ""

        }

    }

    private fun exportchattomob(theotheruserdata:Userdata,cont:Context){
        val recay = ArrayList<WithViewType>()
        val otheruseruid = theotheruserdata.useruid
        path = "/${values.chats}/$curruser $otheruseruid"
        val refred = FirebaseDatabase.getInstance().getReference("/${values.chats}/$curruser $otheruseruid")
        childlistener  = refred.addChildEventListener(object :ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                removal = false
                editing = false

                val mychats = snapshot.getValue(chatuser::class.java)
                if (mychats !=null){
                    if (mychats.from_me == curruser){
                            if (mychats.image == 0) {
                                recay.add(WithViewType(mychats, values.me, myself))
                            }else{
                                recay.add(WithViewType(mychats, values.imageme, myself))
                            }
                        }else if (mychats.from_me == arrf[position].useruid){
                            if (mychats.image == 0) {
                                recay.add(WithViewType(mychats, values.otheruser, theotheruserdata))
                            }else{
                                recay.add(WithViewType(mychats, values.imageother, theotheruserdata))
                            }

                        }

                }
                initiate = true

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

                editing = true
                val mychats = snapshot.getValue(chatuser::class.java)
                if (mychats != null) {
                    if (mychats.from_me == curruser && mychats.to_user == arrf[position].useruid) {

                        val boollis = cadapter.filterit(mychats.unid,mychats.message)
                        val editfiltered = boollis[0]
                        val truth = boollis[1]
                        var t_edits = false

                        if (listOf(mychats.edit,truth,editfiltered,emsg).satisfies(listOf(0,true,-1,mychats.message)){a,b -> a != b}) {
                            println("from curr:${mychats.message}")
                            var lastseen = emptyList<Any>()
                            emsg = mychats.message
                            if (editfiltered == cadapter.itemCount - 1){
                                lastseen = listOf(values.unread,values.read)
                            }
                            cadapter.changetext(editfiltered as Int, mychats.message, mychats.edit,lastseen)
                            cadapter.editreplypos(mychats.unid,mychats.message)
                            t_edits = true
                            }

                            if (listOf(mychats.messageseen,editfiltered,t_edits).satisfies(
                                    listOf(1,cadapter.itemCount - 1,false)){a,b -> a==b}) {
                                println("comes in msgseen")
                                cadapter.msgseen(mychats.messageseen)
                            }

                            if (conceal){
                                recay[exactrecpos].User.visible = mychats.visible
                                conceal = false
                            }

                        }else if (mychats.from_me == arrf[position].useruid) {

                            if (mychats.edit == 1 ) {
                                val boollis = cadapter.filterit(mychats.unid,mychats.message)
                                val editfiltered = boollis[0]
                                val truth = boollis[1]

                                if (listOf(truth,editfiltered,emsg).satisfies(listOf(true,-1,mychats.message)){a,b -> a != b}) {
                                    println("from other:${mychats.message}")
                                    if (editfiltered == cadapter.itemCount - 1 && mychats.othermsgseen == values.unread){
                                        println("just once")
                                        if (liya.isVisible){
                                            single = true
                                        }else{
                                            messageseen()
                                        }
                                    }
                                    emsg = mychats.message
                                    cadapter.changetext(editfiltered as Int, mychats.message, mychats.edit)
                                    cadapter.editreplypos(mychats.unid,mychats.message)
                                }
                            }



                        }


                }

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                removal = true


                val mychats = snapshot.getValue(chatuser::class.java)
                if (mychats !=null){
                        if (mychats.from_me == curruser && mychats.to_user == arrf[position].useruid){
                            val deletefiltered = cadapter.filterit(mychats.unid)[0]
                            if (deletefiltered != -1 && deleteduid != mychats.unid) {
                                deleteduid = mychats.unid
                                cadapter.remove(deletefiltered as Int)
                                cadapter.deletereplypos(mychats.unid)
                                cadapter.checkifitslast(deletefiltered,curruser,mychats.messageseen)
                            }

                        }else if (mychats.from_me == arrf[position].useruid) {
                            val deletefiltered = cadapter.filterit(mychats.unid)[0]
                            if (deletefiltered != -1 && deleteduid != mychats.unid) {
                                if (cadapter.getitem(deletefiltered as Int).User.othermsgseen == 0 && liya.isVisible){
                                    if (new_messages_for_scroll >0){
                                        new_messages_for_scroll -= 1
                                        settxt()
                                    }

                                    if (new_messages_for_scroll == 0 && down.isExtended){
                                        down.shrink()
                                    }
                                }
                                deleteduid = mychats.unid
                                cadapter.remove(deletefiltered)
                                cadapter.deletereplypos(mychats.unid)
                                cadapter.checkifitslast(deletefiltered,curruser,mychats.messageseen)

                            }
                        }


                }

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {



            }

            override fun onCancelled(error: DatabaseError) {

            }

        })


        valuelistener =  refred.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (isp == 0){
                    removal = false
                    editing = false
                    initiate = false
                    cadapter.submit(recay)

                }else if(isp == 1 && otheruseruid == arrf[position].useruid ){
                    if (initiate && !removal && !editing) {
                        println("${recay.size} :entering")
                        cadapter.notifyItemRangeInserted(cadapter.itemCount - 1, 1)
                        initiate = false

                        if (recay[recay.size - 1].User.from_me == curruser) {
                            mychatrecl.scrollToPosition(cadapter.itemCount - 1)
                            if (liya.isVisible) {
                                down.shrink()
                                liya.startAnimation(
                                    AnimationUtils.loadAnimation(
                                        cont,
                                        R.anim.quickrepback
                                    )
                                )
                                liya.visibility = View.GONE
                                new_messages_for_scroll = 0
                            }
                        } else {
                            println("it comes hrer")
                            new_messages_for_scroll += 1
                            inserted = true
                            if (liya.isVisible) {
                                if (new_messages_for_scroll > 0) {
                                    settxt()
                                }
                            } else {
                                println("happens")
                                mychatrecl.scrollToPosition(cadapter.itemCount - 1)
                                inserted = false
                                messageseen()
                                new_messages_for_scroll = 0
                            }
                        }
                    }else if (removal){
                        deleteduid = ""
                        removal = false
                        exactrecpos = -4


                    }else if (editing){
                        emsg = ""
                        editing = false
                        exactrecpos = -4

                    }
                }

                if (jade) {
                    if (recay.size > 0){
                        lastmsg = recay[recay.size - 1]
                        when{
                            lastmsg?.useme?.useruid != curruser && lastmsg?.User?.othermsgseen == 0 -> {
                                messageseen(stream = true)

                            }
                        }
                        mychatrecl.scrollToPosition(cadapter.itemCount - 1)
                    }

                    jade = false
                }
                isp = 1




            }

            override fun onCancelled(error: DatabaseError) {

            }

        })


    }

    private fun settxt(){
        text = "\uD83D\uDCAC  +${new_messages_for_scroll}"
        down.text = text
        down.extend()
    }


    private fun deletereply(listreplies:ArrayList<WithViewType>){
        val otheruseruid = arrf[position].useruid
        val updaterep = HashMap<String, Any>()
        updaterep[values.reply] = ""

        for (repelement in listreplies){
            val keyf = repelement.User.mychatdbkey
            val otherkey = repelement.User.otherchatdbkey

            val refed = FirebaseDatabase.getInstance()
                .getReference("/${values.chats}/$curruser $otheruseruid/${keyf}")
            val opprfernce = FirebaseDatabase.getInstance()
                .getReference("/${values.chats}/$otheruseruid $curruser/${otherkey}")

            refed.updateChildren(updaterep)
            opprfernce.updateChildren(updaterep)

        }

    }


    private fun editreply(mlistreplies:ArrayList<WithViewType>,mess:String){

        val otheruseruid = arrf[position].useruid
        val updatereply = HashMap<String, Any>()
        updatereply[values.reply] = mess

        for (replelement in mlistreplies){
            val keyf = replelement.User.mychatdbkey
            val otherkey = replelement.User.otherchatdbkey

            val refed = FirebaseDatabase.getInstance()
                .getReference("/${values.chats}/$curruser $otheruseruid/${keyf}")
            val opprfernce = FirebaseDatabase.getInstance()
                .getReference("/${values.chats}/$otheruseruid $curruser/${otherkey}")

            refed.updateChildren(updatereply)
            opprfernce.updateChildren(updatereply)
        }



    }




    private fun messageseen(stream: Boolean = false){
        val otheruserid = arrf[position].useruid

        val seenmap = HashMap<String, Any>()
        seenmap[values.msg] = values.read

        val otherseenmap = HashMap<String,Any>()
        otherseenmap[values.othermsgseen] = values.read


        if (stream){
            val doublemsg = cadapter.msgstream(curruser)
            val streammsg = doublemsg[0]
            val indexmsg = doublemsg[1]

            println(streammsg.toList())
            for (msgele in 0.until(streammsg.size)) {
                println("msg seen point taken")
                msgsee(streammsg[msgele] as WithViewType,otheruserid,seenmap,otherseenmap)
                cadapter.onlymsg(indexmsg[msgele] as Int)
            }

        }else{
            val lastmsg = cadapter.returnlastmsg()
            msgsee(lastmsg,otheruserid,seenmap,otherseenmap)
            cadapter.onlymsg(cadapter.itemCount - 1)
        }

    }

    private fun msgsee(element:WithViewType,otheruserid:String,seenmap:HashMap<String,Any>,otherseenmap:HashMap<String,Any>){

        val otherkey = element.User.otherchatdbkey
        val mykey = element.User.mychatdbkey

        val othermsgseen = FirebaseDatabase.getInstance()
            .getReference("/${values.chats}/$curruser $otheruserid/${mykey}")

        val seen = FirebaseDatabase.getInstance()
            .getReference("/${values.chats}/$otheruserid $curruser/${otherkey}")

        seen.updateChildren(seenmap)
        othermsgseen.updateChildren(otherseenmap)
    }


    private fun shrinkall(){
        fab1.shrink()
        fab2.shrink()
        fab3.shrink()
        fab4.shrink()
    }


    private fun conceal(theotheruserdata:Userdata,positi:Int){
        conceal = true
        val picpos = positi
        val otheruseruid = theotheruserdata.useruid
        val refred = FirebaseDatabase.getInstance().getReference("/${values.chats}/$curruser $otheruseruid").get()
        var i = 0

        refred.addOnSuccessListener {
            it.children.forEach {
                val chatdata = it.key
                i += 1
                if (chatdata != null) {
                    if (i == picpos + 1) {
                        val refed = FirebaseDatabase.getInstance()
                            .getReference("/${values.chats}/$curruser $otheruseruid/${chatdata}")
                        val visimap = HashMap<String,Any>()
                        visimap[values.visible] = values.hide
                        refed.updateChildren(visimap)

                    }

                }
            }

        }


    }


    private fun deleteitem(theotheruserdata:Userdata,sid:String,uids:String,usds:String){
        val otheruseruid = theotheruserdata.useruid
        val mykey = sid
        val otherkey = uids

        val refed = FirebaseDatabase.getInstance()
            .getReference("/${values.chats}/$curruser $otheruseruid/${mykey}")
        val opprfernce = FirebaseDatabase.getInstance()
            .getReference("/${values.chats}/$otheruseruid $curruser/${otherkey}")

        val fi_replies = cadapter.filter_replies(usds)
        if (fi_replies.isNotEmpty()) {
            deletereply(fi_replies as ArrayList<WithViewType>)
        }

        refed.removeValue()
        opprfernce.removeValue()

    }



    private fun editit(theotheruserdata:Userdata,sid:String,uids:String,newmessage:String,uds:String){
        val otheruseruid = theotheruserdata.useruid
        val new = newmessage
        val mykey = sid
        val otherkey = uids

        val newhashforme = HashMap<String, Any>()
        newhashforme[values.message] = new
        newhashforme[values.edit] = 1

        val newhashforot = HashMap<String, Any>()
        newhashforot[values.message] = new
        newhashforot[values.edit] = 1

        if (cadapter.filterit(uds)[0] == cadapter.itemCount - 1){
            println("safe zone")
            newhashforme[values.msg] = values.unread
            newhashforot[values.othermsgseen] = values.unread
        }

        val refxed = FirebaseDatabase.getInstance()
            .getReference("/${values.chats}/$curruser $otheruseruid/${mykey}")

        val opprfernce = FirebaseDatabase.getInstance()
            .getReference("/${values.chats}/$otheruseruid $curruser/${otherkey}")

        refxed.updateChildren(newhashforme).addOnSuccessListener {
            val filtered_replies = cadapter.filter_replies(uds)
            if (filtered_replies.isNotEmpty()) {
                editreply(filtered_replies as ArrayList<WithViewType>, newmessage)
            }
        }
        opprfernce.updateChildren(newhashforot)

    }


    private fun settyping(otheruserdata:Userdata,typo:Int){
        val otheruserid = otheruserdata.useruid
        val metyping = FirebaseDatabase.getInstance().getReference("/${values.typing}/$curruser $otheruserid")
        val otherusertyping = FirebaseDatabase.getInstance().getReference("/${values.typing}/$otheruserid $curruser")
        val typinghash = HashMap<String,Any>()


        if (typo == 1) {
            typinghash[curruser] = values.istyping
            metyping.setValue(typinghash)
            otherusertyping.setValue(typinghash)
        }else{
            typinghash[curruser] = values.nottyping
            metyping.setValue(typinghash)
            otherusertyping.setValue(typinghash)
        }

    }



    @SuppressLint("SetTextI18n")
    private fun edits(chatzl: String){
        val chatty = findViewById<EditText>(R.id.edi)
        chatty.text.clearSpans()
        chatty.setText(chatzl.trim())
    }




    override fun onLongClick(v: View?): Boolean {
        if (v != null){
            when(v.id){
                /*fab1*/
                R.id.cact ->{
                    if (fab1.isExtended){
                        fab1.shrink()

                    }else{
                        fab2.shrink()
                        fab3.shrink()
                        fab4.shrink()
                        fab1.extend()
                    }


                }

                /*fab2*/
                R.id.cazt ->{
                    if (fab2.isExtended){
                        fab2.shrink()

                    }else{
                        fab1.shrink()
                        fab3.shrink()
                        fab4.shrink()
                        fab2.extend()

                    }


                }

                /*fab3*/
                R.id.caxt ->{
                    if (fab3.isExtended){
                        fab3.shrink()

                    }else{
                        fab1.shrink()
                        fab2.shrink()
                        fab4.shrink()
                        fab3.extend()

                    }


                }

                /*fab4*/
                R.id.cavt ->{
                    if (fab4.isExtended){
                        fab4.shrink()

                    }else{
                        fab1.shrink()
                        fab2.shrink()
                        fab3.shrink()
                        fab4.extend()

                    }


                }

            }

        }
        return false
    }



    private fun refoff(){
        path.hasCharacters {
            val ref = FirebaseDatabase.getInstance().getReference(path)
            valuelistener?.let { ref.removeEventListener(it) }
            childlistener?.let { ref.removeEventListener(it) }
            valuelistener = null
            childlistener = null
            path = ""
        }

    }

    private fun linavisibilility(){
        if (lina.isVisible){
            shrinkall()
            lina.visibility = View.GONE
            linea.visibility = View.VISIBLE
        }
    }

    private fun linscheck(){
        if (lins.isVisible){
            checking()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RECIEVED_REQUEST && resultCode == Activity.RESULT_OK){
            if (data != null){
                try {
                    val dataAsInputStream: InputStream? = contentResolver.openInputStream(data.data!!)
                    val imgBitmap: Bitmap = BitmapFactory.decodeStream(dataAsInputStream)
                    savechattodb(arrf[position],justOb.ImgToBase64(imgBitmap),true)

                }catch (e: Exception){
                    println("${e.message}")
                }

            }
        }

    }
}
