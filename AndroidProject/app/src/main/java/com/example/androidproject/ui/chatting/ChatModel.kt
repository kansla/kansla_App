package com.example.androidproject.ui.chatting

class ChatModel(
    val name: String,
    val script: String,
    val profile_image: String,
    val date_time: String,
    val email: String,
    val emotion: String
){
    override fun toString(): String {
        return "ChatModel(name='$name', script='$script', profile_image='$profile_image', date_time='$date_time', email='$email', emotion='$emotion')"
    }
}