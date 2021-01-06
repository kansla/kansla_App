package com.example.androidproject.ui.chat

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidproject.BR
import com.example.androidproject.databinding.ItemMyChatBinding
import com.example.androidproject.databinding.ItemYourChatBinding

class ChatContentAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private var chatContent: List<ChatContent?>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            val binding:ItemMyChatBinding = ItemMyChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MyViewHolder(binding)
        }
        else {
            val binding:ItemYourChatBinding = ItemYourChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return YourViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chat = chatContent!![position]!!
        if(holder is MyViewHolder){
            holder.bind(chat)
        }
        else if (holder is YourViewHolder){
            holder.bind(chat)
        }
    }

    fun setItem(chat: List<ChatContent?>?) {
        if (chat == null) {
            return
        }
        chatContent = chat
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return chatContent!!.size
    }

    class MyViewHolder(var binding: ItemMyChatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: ChatContent?) {
            binding.setVariable(BR.myChat, chat)
        }
    }

    class YourViewHolder(var binding: ItemYourChatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: ChatContent?) {
            binding.setVariable(BR.friendChat, chat)
        }
    }

    override fun getItemViewType(position: Int): Int {
        Log.e("test", chatContent!![position]!!.sender_email)

        return if (chatContent!![position]!!.sender_email == "rlathdus"/*내 아이디와 같은지 확인*/) {
            1
        } else {
            2
        }
    }
}