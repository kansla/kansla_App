package com.example.androidproject.ui.dashboard

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.androidproject.R
import androidx.appcompat.app.AppCompatActivity
import com.example.androidproject.ui.Chatting.ChattingActivity

class ChatRoomFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    internal lateinit var preferences: SharedPreferences

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_chat_room, container, false)

        preferences = activity?.getSharedPreferences("USERSIGN", Context.MODE_PRIVATE)!!
        val editor = preferences!!.edit()

        val button : Button = root.findViewById(R.id.button)
        val editText : EditText = root.findViewById(R.id.editText)

        button.setOnClickListener{
            editor.putString("name", editText.text.toString())
            editor.apply()
            val intent = Intent(context, ChattingActivity::class.java)
            startActivity(intent)
        }

        return root
    }
}