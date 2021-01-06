package com.example.androidproject.ui.chatting

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget
import com.example.androidproject.R
import java.text.SimpleDateFormat
import java.util.*

class ChatAdapter (val context: Context, val arrayList: ArrayList<ChatModel>)
    :  RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal lateinit var preferences: SharedPreferences

    fun addItem(item: ChatModel) {//아이템 추가
        if (arrayList != null) {
            arrayList.add(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        //getItemViewType 에서 뷰타입 1을 리턴받았다면 내채팅레이아웃을 받은 Holder를 리턴
        if(viewType == 1){
            view = LayoutInflater.from(context).inflate(R.layout.item_my_chat, parent, false)
            return Holder(view)
        }
        //getItemViewType 에서 뷰타입 2을 리턴받았다면 상대채팅레이아웃을 받은 Holder2를 리턴
        else{
            view = LayoutInflater.from(context).inflate(R.layout.item_your_chat, parent, false)
            return Holder2(view)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size

    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
        preferences = context.getSharedPreferences("auto", Context.MODE_PRIVATE)
        //onCreateViewHolder에서 리턴받은 뷰홀더가 Holder라면 내채팅, item_my_chat의 뷰들을 초기화 해줌


        if (viewHolder is Holder) {
            (viewHolder as Holder).chat_Text?.setText(arrayList.get(i).script)
            (viewHolder as Holder).chat_Time?.setText(simpleDate(arrayList.get(i).date_time))
        }
        //onCreateViewHolder에서 리턴받은 뷰홀더가 Holder2라면 상대의 채팅, item_your_chat의 뷰들을 초기화 해줌
        else if(viewHolder is Holder2) {
                (viewHolder as Holder2).chat_You_Image?.setImageResource(R.drawable.profile)
            (viewHolder as Holder2).chat_You_Name?.setText(preferences.getString("second_name",""))
            (viewHolder as Holder2).chat_Text?.setText(arrayList.get(i).script)
            (viewHolder as Holder2).chat_Time?.setText(simpleDate(arrayList.get(i).date_time))
        }

    }

    fun simpleDate (str : String) : String{
        Log.e("str", str.substring(11,16))
        var n : Int = if(str.substring(11,16).contains("-")) 1 else 2
        var hh :Int =0
        var mm :Int =0
        var result:String = ""
        when(n){
            1 -> {
                hh = str.substring(11,16).split("-")[0].toInt()
                mm = str.substring(11,16).split("-")[1].toInt()
                hh = if(hh>=12) hh -12 else hh
                hh+=9
                hh = if(hh>=12) hh -12 else hh
                hh = if(hh ==0) hh +12 else hh
            }
            2 ->{
                hh = str.substring(11,16).split(":")[0].toInt()
                mm = str.substring(11,16).split(":")[1].toInt()
                hh = if(hh>=12) hh -12 else hh
                hh+=9
                hh = if(hh>=12) hh -12 else hh
                hh = if(hh ==0) hh +12 else hh
            }
        }

        if(hh<10 && mm<10){
            result = "0"+hh+":0"+mm
        }else if (hh<10 && mm>=10){
            result = "0"+hh+":"+mm
        }else if(hh>=10 && mm <10){
            result = ""+hh+":0"+mm
        }else{
            result = ""+hh+":"+mm
        }
        return result
    }

    //내가친 채팅 뷰홀더
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //친구목록 모델의 변수들 정의하는부분
        val chat_Text = itemView?.findViewById<TextView>(R.id.chat_Text)
        val chat_Time = itemView?.findViewById<TextView>(R.id.chat_Time)
    }

    //상대가친 채팅 뷰홀더
    inner class Holder2(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //친구목록 모델의 변수들 정의하는부분
        val chat_You_Image = itemView?.findViewById<ImageView>(R.id.chat_You_Image)
        val chat_You_Name = itemView?.findViewById<TextView>(R.id.chat_You_Name)
        val chat_Text = itemView?.findViewById<TextView>(R.id.chat_Text)
        val chat_Time = itemView?.findViewById<TextView>(R.id.chat_Time)


    }

    override fun getItemViewType(position: Int): Int {//여기서 뷰타입을 1, 2로 바꿔서 지정해줘야 내채팅 너채팅을 바꾸면서 쌓을 수 있음
        preferences = context.getSharedPreferences("auto", Context.MODE_PRIVATE)

        //내 아이디와 arraylist의 name이 같다면 내꺼 아니면 상대꺼
        Log.e("test", arrayList.get(position).name)

        return if (arrayList.get(position).email == preferences.getString("inputId","") || arrayList.get(position).name == preferences.getString("inputId","")) {
            1
        } else {
            2
        }
    }
}