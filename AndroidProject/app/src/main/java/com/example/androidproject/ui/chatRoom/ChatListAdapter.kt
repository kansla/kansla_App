package com.example.androidproject.ui.chatRoom

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidproject.BR
import com.example.androidproject.databinding.ItemChatListBinding

class ChatListAdapter : RecyclerView.Adapter<ChatListAdapter.MyViewHolder>() {
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
            /*val intent = Intent(activity, ChatRoomActivity::class.java)
            intent.putExtra("fName", chat.name)
            intent.putExtra("fImg", chat.lastChat)
            intent.putExtra("fImg", chat.img)
            activity.startActivity(intent)
            activity.finish()*/
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