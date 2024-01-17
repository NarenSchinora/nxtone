package com.myappf.nxtone

import android.text.TextWatcher
import java.util.*

data class chatuser(
    var message:String = "",
    val from_me:String = "",
    val to_user:String = "",
    var messageseen:Int = 0,
    val abstime:Long = 0,
    var edit:Int = 0,
    var visible:Int = 0,
    var reply:String = "",
    val unid:String = "",
    var from_unid:String = "",
    var mychatdbkey:String = "",
    var otherchatdbkey:String = "",
    var othermsgseen:Int = 0,
    var image:Int = 0
)
