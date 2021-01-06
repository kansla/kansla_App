package com.example.androidproject.ui.chat

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.androidproject.R

data class ChatContent(
    val sender_name:String,
    val sender_chat:String,
    val sender_profile:String,
    val sender_chat_time:String,
    val sender_email:String,
    val sender_emotion:String
)

@BindingAdapter("imgUrl")
fun bindImg(view: ImageView, imageUrl: String){
    Glide.with(view.context).load(imageUrl).error(R.drawable.profile).into(view)
}