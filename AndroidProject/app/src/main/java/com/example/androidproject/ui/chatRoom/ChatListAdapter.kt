package com.example.androidproject.ui.chatRoom

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidproject.BR
import com.example.androidproject.databinding.ItemChatListBinding
import com.example.androidproject.ui.chatting.ChattingActivity

class ChatListAdapter : RecyclerView.Adapter<ChatListAdapter.MyViewHolder>() {
    internal lateinit var preferences: SharedPreferences
    /*private var chatList: List<ChatList>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        val binding = DataBindingUtil.inflate<ItemChatListBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_chat_list, parent, false)
        return ChatListViewHolder(binding)
    }
    inner class ChatListViewHolder(private val binding: ItemChatListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ChatList) {
            binding.chat = data
            binding.executePendingBindings()
        }
    }
    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        holder.bind(chatList!![position])
    }
    override fun getItemCount(): Int {
        return chatList!!.size
    }*/
    private var chatList: List<ChatList?>? = null
    private lateinit var activity:Activity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemChatListBinding =
            ItemChatListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val chat = chatList!![position]!!
        holder.bind(chat)
        holder.itemView.setOnClickListener {
            Log.e("click", chat.name)
            Log.e("click", chat.lastChat)
            val intent = Intent(holder.itemView?.context, ChattingActivity::class.java)
            preferences = holder.itemView.context.getSharedPreferences("auto", Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putString("second_email", chat.email)
            editor.putString("second_name", chat.name)
            editor.apply()
            Log.e("gg" , preferences.getString("second_email","")!!)
            /*intent.putExtra("fName", chat.name)
            intent.putExtra("fImg", chat.lastChat)
            intent.putExtra("fImg", chat.img)*/
            holder.itemView.context.startActivity(intent)
        }
    }

    fun setItem(chat: List<ChatList?>?) {
        if (chat == null) {
            return
        }
        chatList = chat
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return chatList!!.size
    }

    class MyViewHolder(var binding: ItemChatListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: ChatList?) {
            binding.setVariable(BR.chat, chat)
        }
    }
}