package com.example.androidproject.ui.chatRoom

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.androidproject.R

data class ChatList(
    val name:String,
    val lastChat:String,
    val img:String
)

@BindingAdapter("imgUrl")
fun bindImg(view: ImageView, imageUrl: String){
    Glide.with(view.context).load(imageUrl).error(R.drawable.profile).into(view)
}