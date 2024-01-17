package com.myappf.nxtone



import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.media.Image
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.net.toUri
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.cloud.datastore.core.number.IndexNumberDecoder

class realchatmessages(val act: Context) :RecyclerView.Adapter<RecyclerView.ViewHolder>() {




    private var fullitmes = ArrayList<WithViewType>()
    private var lastbefpre = ArrayList<RecyclerView.ViewHolder>()
    private var OneObj = IMGIFRecievernSender()

    init {
        this.lastbefpre = ArrayList()
        this.fullitmes = ArrayList()
    }


    fun submit(mlist:ArrayList<WithViewType>){
        this.fullitmes = mlist
        notifyItemRangeInserted(0,mlist.size)

    }

    fun remove(itspos:Int){
        this.fullitmes.removeAt(itspos)
        notifyItemRangeRemoved(itspos,1)
    }

    fun onlymsg(index:Int){
        this.fullitmes[index].User.othermsgseen = 1
    }

    fun checkifitslast(spo:Int,acceptuid:String,takeint:Int){
        if (spo != 0){
            val check:Boolean = spo - 1 == this.fullitmes.size - 1 && this.fullitmes[spo - 1].useme.useruid == acceptuid
            if(check) {
                 if (takeint == 1){
                    println("enters")
                    this.fullitmes[spo -1].User.messageseen = 1
                    lastbefpre.clear()
                    notifyItemRangeChanged(spo-1,1)
                }else{
                    println("enters throug")
                    lastbefpre.clear()
                    notifyItemRangeChanged(spo-1,1)
                }
            }
        }


    }

    fun msgstream(user:String): List<List<Any>> {
        var indexlist = mutableListOf<Int>()
        var fillist = this.fullitmes.filter {
            it.User.messageseen == 1 && it.User.othermsgseen == 0 && it.useme.useruid != user
        }

        for (i in fillist){
            indexlist.add(this.fullitmes.indexOf(i))
        }

        return listOf(fillist,indexlist)
    }


    fun changetext(gipos:Int,mess:String,edit:Int,seen:List<Any> = emptyList()){
        if (fullitmes[gipos].User.message != mess) {
            this.fullitmes[gipos].User.message = mess
            this.fullitmes[gipos].User.edit = edit
            if (seen.isNotEmpty()){
                this.fullitmes[gipos].User.messageseen = seen[0] as Int
                this.fullitmes[gipos].User.othermsgseen = seen[1] as Int
            }
            notifyItemRangeChanged(gipos, 1)
        }

    }



    fun senduuid(gpos:Int): ArrayList<String> {
        return arrayListOf(fullitmes[gpos].User.mychatdbkey,fullitmes[gpos].User.otherchatdbkey,fullitmes[gpos].User.unid)
    }

    fun filterit(unid:String,msg:String = ""): List<Any> {
        var index = -1
        var istrue = false
        val filtered  = this.fullitmes.find {
            it.User.unid == unid
        }

        if (filtered != null){
            index = this.fullitmes.indexOf(filtered)
            if (filtered.User.message.contentEquals(msg) && msg.isNotEmpty()){
                    istrue = true
            }
        }
        return listOf<Any>(index,istrue)
    }

    fun msgseen(msg:Int){
        if (this.fullitmes[this.fullitmes.size - 1].User.messageseen == 0) {
            this.fullitmes[this.fullitmes.size - 1].User.messageseen = msg
            notifyItemRangeChanged(this.fullitmes.size - 1, 1)
        }
    }

    fun clear(){
        val listie = this.fullitmes.size
        this.fullitmes.clear()
        this.lastbefpre.clear()
        notifyItemRangeRemoved(0,listie)
    }

    fun getitem(positionitem:Int): WithViewType {
        return this.fullitmes[positionitem]
    }

    fun filter_replies(givunid:String): List<WithViewType> {
        return this.fullitmes.filter {
            it.User.reply.isNotEmpty() && it.User.from_unid == givunid
        }
    }

    fun editreplypos(ivunid:String,currmess:String): Boolean {
        val freps  = this.fullitmes.filter {
            it.User.reply.isNotEmpty() && it.User.from_unid == ivunid
        }
        if (freps.isNotEmpty()){
            for (i in freps){
                val positionf = fullitmes.indexOf(i)
                this.fullitmes[positionf].User.reply = currmess
                notifyItemRangeChanged(positionf,1)
            }
        }
        return false
    }


    fun deletereplypos(ivnid:String): Boolean {
        val freps  = this.fullitmes.filter {
            it.User.reply.isNotEmpty() && it.User.from_unid == ivnid
        }
        if (freps.isNotEmpty()){
            for (i in freps){
                val positionf = fullitmes.indexOf(i)
                this.fullitmes[positionf].User.reply = ""
                notifyItemRangeChanged(positionf,1)
            }
        }
        return false
    }

    fun returnlastmsg() : WithViewType {
        return this.fullitmes[fullitmes.size - 1]

    }




    inner class chats(v:View) : RecyclerView.ViewHolder(v),View.OnLongClickListener,View.OnClickListener {
        val teimage = v.findViewById<TextView>(R.id.tedsz)
        val image = v.findViewById<ImageView>(R.id.urpic)
        val chatme = v.findViewById<TextView>(R.id.tzx)
        val edits = v.findViewById<ImageView>(R.id.xwx)
        val reply_saa = v.findViewById<TextView>(R.id.tis)
        val insx = v.findViewById<CoordinatorLayout>(R.id.cdd)
        val canyou = v.findViewById<TextView>(R.id.nowcayoubeatme)
        val emote = v.findViewById<ImageView>(R.id.forems)


        init {
            chatme.setOnLongClickListener(this)
            canyou.setOnClickListener(this)
            canyou.setOnLongClickListener(this)
            reply_saa.setOnClickListener(this)
        }


        override fun onLongClick(v: View?): Boolean {
            val activity = act as onclickitem
            val pos = bindingAdapterPosition
            if (pos != -1){
                activity.longclick(pos,values.otheruser,v)
            }
            return false
        }

        override fun onClick(v: View?) {
            val activity = act as onclickitem
            when (v?.id) {
                canyou.id ->{
                    val pos = bindingAdapterPosition
                    if (pos != -1) {
                        activity.singleclick(pos, v, values.otheruser)
                    }
                }

                reply_saa.id ->{
                    val findindex = fullitmes.filter {
                        it.User.unid == fullitmes[bindingAdapterPosition].User.from_unid
                    }
                    val  reply_pos = fullitmes.indexOf(findindex[0])
                    activity.click_fromreply(reply_pos)
                }


            }
        }

    }

    inner class chatfrom(v:View) : RecyclerView.ViewHolder(v), View.OnLongClickListener,View.OnClickListener {
        val teixmage = v.findViewById<TextView>(R.id.tzxz)
        val imalge = v.findViewById<ImageView>(R.id.uspic)
        val chaltfrom = v.findViewById<TextView>(R.id.teax)
        val editsfrom = v.findViewById<ImageView>(R.id.xwxx)
        val read_see = v.findViewById<ImageView>(R.id.read_see)
        val reply_me = v.findViewById<TextView>(R.id.tia)
        val inx = v.findViewById<CoordinatorLayout>(R.id.bdd)
        val cani = v.findViewById<TextView>(R.id.nowcanyoustopme)
        val pending = v.findViewById<ImageView>(R.id.pendinh)
        val emoticons = v.findViewById<ImageView>(R.id.foremote)

        init {
            chaltfrom.setOnLongClickListener(this)
            cani.setOnClickListener(this)
            cani.setOnLongClickListener(this)
            reply_me.setOnClickListener(this)

        }

        override fun onLongClick(v: View?): Boolean {
            val activity = act as onclickitem
            val pos = bindingAdapterPosition
            if (pos != -1){
                activity.longclick(pos,values.me,v)
            }
            return false
        }

        override fun onClick(v: View?) {
            val activity = act as onclickitem
            when (v?.id) {
                cani.id -> {
                    val pos = bindingAdapterPosition
                    if (pos != -1) {
                        activity.singleclick(pos, v, values.me)
                    }
                }
                reply_me.id ->{
                    val findindex = fullitmes.filter {
                        it.User.unid == fullitmes[bindingAdapterPosition].User.from_unid
                    }
                    val  reply_pos = fullitmes.indexOf(findindex[0])
                    activity.click_fromreply(reply_pos)
                }
            }
        }

    }



    inner class imageme(v:View) : RecyclerView.ViewHolder(v) {
        val teximage = v.findViewById<TextView>(R.id.mytext)
        val imag = v.findViewById<ImageView>(R.id.mypic)
        val read = v.findViewById<ImageView>(R.id.read)
        val imagemessage = v.findViewById<ImageView>(R.id.sendim)
        val pendin = v.findViewById<ImageView>(R.id.pendin)

    }



    inner class imageother(v:View) : RecyclerView.ViewHolder(v) {
        val timage = v.findViewById<TextView>(R.id.texta)
        val imagi = v.findViewById<ImageView>(R.id.itspic)
        val imagemessag = v.findViewById<ImageView>(R.id.otim)

    }



    interface onclickitem{
        fun longclick(pos:Int,getchatlayouttype:Int,view:View?)
        fun singleclick(positio:Int,v:View?,viewType:Int)
        fun click_fromreply(okay:Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType.equals(values.otheruser)){
            chats(LayoutInflater.from(act).inflate(R.layout.chat_me,parent,false))
        }else if(viewType.equals(values.me)){
            chatfrom(LayoutInflater.from(act).inflate(R.layout.chat_hisher,parent,false))
        }else if (viewType.equals(values.imageme)){
            imageme(LayoutInflater.from(act).inflate(R.layout.imagehisher,parent,false))
        }else{
            imageother(LayoutInflater.from(act).inflate(R.layout.imageme,parent,false))
        }

    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val userimage = fullitmes[position].useme.prophoto
        val chattext = fullitmes[position].User.message

        if (holder is chats) {
            holder.insx.visibility = View.GONE

            if (position == fullitmes.size - 1 && lastbefpre.isNotEmpty()){
                val lastmholder = lastbefpre[0] as chatfrom
                if (!lastmholder.read_see.isGone) {
                    lastmholder.read_see.visibility = View.GONE
                }
            }


            val visi = fullitmes[position].User.visible
            val editvalue = fullitmes[position].User.edit

            if (userimage.contains("nothing")) {
                setimage(holder.image,holder.teimage,position)
            } else {
                insertimage(holder.image, holder.teimage, userimage)
            }

            if (editvalue == 1){
                val leyp =  holder.edits.layoutParams as CoordinatorLayout.LayoutParams
                leyp.gravity = Gravity.START or Gravity.TOP
                holder.edits.layoutParams = leyp
                holder.edits.visibility = View.VISIBLE
            }else{
                holder.edits.visibility = View.GONE
            }

            if (visi == 1){
                holder.itemView.visibility = View.GONE
                val param = holder.itemView.layoutParams
                param.height = 0
                param.width = 0
                holder.itemView.layoutParams = param
            }else{
                holder.itemView.visibility = View.VISIBLE
            }

            val reply  = fullitmes[position].User.reply

            if (reply.isNotBlank()){
                val gdd = (holder.canyou.background as GradientDrawable).mutate()
                val bu = gdd as GradientDrawable
                bu.gradientType = GradientDrawable.LINEAR_GRADIENT
                bu.orientation = GradientDrawable.Orientation.LEFT_RIGHT
                /*
                android:startColor="#e65c00"
                android:endColor="#F9D423"
                 */
                bu.colors = intArrayOf(Color.parseColor("#e65c00"),
                    Color.parseColor("#F9D423"))
                bu.cornerRadius = 83f


                holder.canyou.visibility = View.VISIBLE
                holder.chatme.visibility = View.GONE
                holder.reply_saa.text = reply
            }else{
                holder.canyou.visibility = View.GONE
                holder.chatme.visibility = View.VISIBLE
                holder.reply_saa.text = ""
            }


            var emotes = false
            for (i in fullitmes[position].User.message){
                if (i.isSurrogate()){
                    emotes = true
                }else if (i.isLetterOrDigit()){
                    emotes = false
                    break
                }


            }



            if (emotes){
                val good = (holder.chatme.background as GradientDrawable).mutate()
                val bood = (holder.canyou.background as GradientDrawable).mutate()

                val canycolor = bood as GradientDrawable
                val mycolorgoog = good as GradientDrawable

                mycolorgoog.setColor(Color.TRANSPARENT)
                canycolor.setColor(Color.TRANSPARENT)

                holder.chatme.setPadding(0,0,0,0)
                holder.chatme.setTextSize(TypedValue.COMPLEX_UNIT_SP,24f)
                holder.chatme.text = chattext

                holder.canyou.setPadding(0,0,0,0)
                holder.canyou.setTextSize(TypedValue.COMPLEX_UNIT_SP,24f)
                holder.canyou.text = chattext

                if (reply.isNotBlank()){
                    holder.emote.visibility = View.VISIBLE
                }else{
                    holder.emote.visibility = View.GONE
                }

            }else{
                if (fullitmes[position].User.reply.isBlank()){
                    val good = (holder.chatme.background as GradientDrawable).mutate()
                    val bood = (holder.canyou.background as GradientDrawable).mutate()

                    val mycolorgoog = good as GradientDrawable
                    val canycolor = bood as GradientDrawable

                    canycolor.setColor(Color.WHITE)
                    mycolorgoog.setColor(Color.WHITE)
                }
                val vsdp = ((13f * android.content.res.Resources.getSystem().displayMetrics.density) +0.5f).toInt()

                holder.chatme.setPadding(vsdp,vsdp,vsdp,vsdp)
                holder.chatme.setTextSize(TypedValue.COMPLEX_UNIT_SP,15f)
                holder.chatme.text = chattext

                holder.canyou.setPadding(vsdp,vsdp,vsdp,vsdp)
                holder.canyou.setTextSize(TypedValue.COMPLEX_UNIT_SP,15f)
                holder.canyou.text = chattext
            }

        }else if (holder is chatfrom){
            holder.inx.visibility = View.GONE

            msglogic(holder,position)

            val read = fullitmes[position].User.messageseen
            if (read == 1 && position == fullitmes.size - 1){
                holder.read_see.visibility = View.VISIBLE
            }else{
                holder.read_see.visibility = View.GONE
            }


            val visi = fullitmes[position].User.visible
            val editvalue = fullitmes[position].User.edit


            if (userimage.contains("nothing")) {
                setimage(holder.imalge,holder.teixmage,position)
            } else {
                insertimage(holder.imalge, holder.teixmage, userimage)
            }


            if (editvalue == 1){
                val leyp =  holder.editsfrom.layoutParams as CoordinatorLayout.LayoutParams
                leyp.gravity = Gravity.END or Gravity.TOP
                holder.editsfrom.layoutParams = leyp
                holder.editsfrom.visibility = View.VISIBLE
            }else{
                holder.editsfrom.visibility = View.GONE
            }


            if (visi == 1){
                holder.itemView.visibility = View.GONE
                val param = holder.itemView.layoutParams
                param.height = 0
                param.width = 0
                holder.itemView.layoutParams = param
            }else{
                holder.itemView.visibility = View.VISIBLE
            }


            val reply  = fullitmes[position].User.reply


            if (reply.isNotBlank()){
                val gdd = (holder.cani.background as GradientDrawable).mutate()
                val bu = gdd as GradientDrawable
                bu.gradientType = GradientDrawable.LINEAR_GRADIENT
                bu.orientation = GradientDrawable.Orientation.LEFT_RIGHT
                bu.colors = intArrayOf(Color.parseColor("#F9D423"),
                    Color.parseColor("#e65c00"))
                bu.cornerRadius = 83f


                holder.cani.visibility = View.VISIBLE
                holder.chaltfrom.visibility = View.GONE
                holder.reply_me.text = reply
            }else{
                holder.chaltfrom.visibility = View.VISIBLE
                holder.cani.visibility = View.GONE
                holder.reply_me.text = ""
            }

            var emotes = false
            for (i in fullitmes[position].User.message){
                if (i.isSurrogate()){
                    emotes = true
                }else if (i.isLetterOrDigit()){
                    emotes = false
                    break
                }

            }

            if (emotes){
                val good = (holder.chaltfrom.background as GradientDrawable).mutate()
                val bood = (holder.cani.background as GradientDrawable).mutate()

                val canicolor = bood as GradientDrawable
                val mycolorgoog = good as GradientDrawable

                mycolorgoog.setColor(Color.TRANSPARENT)
                canicolor.setColor(Color.TRANSPARENT)

                holder.chaltfrom.setPadding(0,0,0,0)
                holder.chaltfrom.setTextSize(TypedValue.COMPLEX_UNIT_SP,24f)
                holder.chaltfrom.text = chattext

                holder.cani.setPadding(0,0,0,0)
                holder.cani.setTextSize(TypedValue.COMPLEX_UNIT_SP,24f)
                holder.cani.text = chattext

                if (reply.isNotBlank()){
                    holder.emoticons.visibility = View.VISIBLE
                }else{
                    holder.emoticons.visibility = View.GONE
                }


            }else{
                if (fullitmes[position].User.reply.isBlank()){
                    val good = (holder.chaltfrom.background as GradientDrawable).mutate()
                    val bood = (holder.cani.background as GradientDrawable).mutate()

                    val canicolor = bood as GradientDrawable
                    val mycolorgoog = good as GradientDrawable

                    canicolor.setColor(Color.WHITE)
                    mycolorgoog.setColor(Color.WHITE)
                }
                val vsdp = ((13f * android.content.res.Resources.getSystem().displayMetrics.density) +0.5f).toInt()


                holder.chaltfrom.setPadding(vsdp,vsdp,vsdp,vsdp)
                holder.chaltfrom.setTextSize(TypedValue.COMPLEX_UNIT_SP,15f)
                holder.chaltfrom.text = chattext

                holder.cani.setPadding(vsdp,vsdp,vsdp,vsdp)
                holder.cani.setTextSize(TypedValue.COMPLEX_UNIT_SP,15f)
                holder.cani.text = chattext
            }

            holder.pending.visibility = View.GONE

        }else if (holder is imageme){

            holder.pendin.visibility = View.GONE
            msglogic(holder,position)

            val read = fullitmes[position].User.messageseen
            if (read == 1 && position == fullitmes.size - 1){
                holder.read.visibility = View.VISIBLE
            }else{
                holder.read.visibility = View.GONE
            }

            if (userimage.contains("nothing")) {
                setimage(holder.imag,holder.teximage,position)
            } else {
                insertimage(holder.imag, holder.teximage, userimage)
            }

            val myimage  = OneObj.Base64toImg(fullitmes[position].User.message)

            Glide.
            with(act).asBitmap().
            load(myimage).
            placeholder(R.drawable.placeholder).
            error(R.drawable.placeholder).into(holder.imagemessage)



        }else if (holder is imageother){
            if (userimage.contains("nothing")) {
                setimage(holder.imagi,holder.timage,position)
            } else {
                insertimage(holder.imagi, holder.timage, userimage)
            }

            val myimage  = OneObj.Base64toImg(fullitmes[position].User.message)

            Glide.
            with(act).asBitmap().
            load(myimage).
            placeholder(R.drawable.placeholder).
            error(R.drawable.placeholder).into(holder.imagemessag)

        }




    }




    private fun setimage(image: ImageView,textx: TextView,posi:Int){
        var userimagee = ""
        val gd = (image.background as GradientDrawable).mutate()
        val mycolor = gd as GradientDrawable
        mycolor.setColor(Color.parseColor("#03b6fc"))
        userimagee = fullitmes[posi].useme.nameimage
        textx.text = userimagee
    }


    private fun msglogic(hold:RecyclerView.ViewHolder,posi:Int){
        if (hold !in lastbefpre && posi == fullitmes.size - 1){
            lastbefpre.add(hold)
            if (lastbefpre.size > 1){
                for (i in 0.until(lastbefpre.size - 1)){
                    val holderaschat = lastbefpre[i]
                    if (holderaschat is chatfrom){
                        holderaschat.read_see.visibility = View.GONE
                    }else if (holderaschat is imageme){
                        holderaschat.read.visibility = View.GONE
                    }
                }
                lastbefpre.removeAt(0)
            }

        }
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
        return fullitmes[position].typeview
    }



}