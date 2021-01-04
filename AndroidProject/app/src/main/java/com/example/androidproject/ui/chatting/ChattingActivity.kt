package com.example.androidproject.ui.chatting

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidproject.R
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class ChattingActivity : AppCompatActivity() {
    internal lateinit var preferences: SharedPreferences
    private lateinit var chating_Text: EditText
    private lateinit var chat_Send_Button: Button
    lateinit var chat_recyclerview : RecyclerView


    private var hasConnection: Boolean = false
    private var thread2: Thread? = null
    private var startTyping = false
    private var time = 2

    //private var mSocket: Socket = IO.socket("[your server url]")
    private var mSocket: Socket = IO.socket("http://f1e4a07bc110.ngrok.io/")

    //리사이클러뷰
    var arrayList = arrayListOf<ChatModel>()
    val mAdapter = ChatAdapter(this, arrayList)

    var roomNumber : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatting)

        preferences = getSharedPreferences("auto", Context.MODE_PRIVATE)

        val actionBar = supportActionBar
        actionBar?.title = preferences.getString("second_email", "")

        chat_recyclerview= findViewById(R.id.recyclerview)

        //어댑터 선언
        chat_recyclerview.adapter = mAdapter
        //레이아웃 매니저 선언
        val lm = LinearLayoutManager(this)
        chat_recyclerview.layoutManager = lm
        chat_recyclerview.setHasFixedSize(true)//아이템이 추가삭제될때 크기측면에서 오류 안나게 해줌

        chat_Send_Button = findViewById<Button>(R.id.send_btn)
        chating_Text = findViewById<EditText>(R.id.editChat)


        if (savedInstanceState != null) {
            hasConnection = savedInstanceState.getBoolean("hasConnection")
        }

        if (hasConnection) {

        } else {
            //소켓연결
            mSocket.connect()


            //서버에 신호 보내는거같음 밑에 에밋 리스너들 실행
            //socket.on은 수신
            mSocket.on("connect user", onNewUser)
            mSocket.on("chat message", onNewMessage)

            val userId = JSONObject()
            try {
                userId.put("first_email", preferences.getString("inputId", ""))
                userId.put("second_email", preferences.getString("second_email",""))
                Log.e("username",preferences.getString("inputId", "") + " Connected")

                //socket.emit은 메세지 전송임
                mSocket.emit("connect user", userId)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }

        hasConnection = true

        chat_Send_Button.setOnClickListener {
            //아이템 추가 부분
            sendMessage()

        }
        chat_recyclerview.post(Runnable { 
            run(){
                chat_recyclerview.scrollToPosition((chat_recyclerview.adapter as ChatAdapter).itemCount-1)
            }
        })
    }

    internal var onNewMessage: Emitter.Listener = Emitter.Listener { args ->
        runOnUiThread(Runnable {
            val data = args[0] as JSONObject
            val name: String
            val script: String
            val profile_image: String
            val date_time: String
            try {
                Log.e("asdasd", data.toString())
                name = data.getString("name")
                script = data.getString("script")
                profile_image = data.getString("profile_image")
                date_time = data.getString("date_time")


                val format = ChatModel(name, script, profile_image, date_time)
                mAdapter.addItem(format)
                mAdapter.notifyDataSetChanged()
                // 메세지가 올라올때마다 스크롤 최하단으로 보내기
                chat_recyclerview.scrollToPosition(((chat_recyclerview.adapter?.itemCount ?: Int) as Int) - 1)
                Log.e("new me",name )
            } catch (e: Exception) {
                return@Runnable
            }
        })
    }

    //어플 키자마자 서버에  connect user 하고 프로젝트에 on new user 실행
    internal var onNewUser: Emitter.Listener = Emitter.Listener { args ->
        runOnUiThread(Runnable {
            val length = args.size

            if (length == 0) {
                return@Runnable
            }
            //Here i'm getting weird error..................///////run :1 and run: 0
            var username = args[0].toString()
            try {
                val `object` = JSONObject(username)
                preferences = getSharedPreferences("auto", Context.MODE_PRIVATE)
                val editor = preferences!!.edit()
                roomNumber = `object`.getString("room")
                editor.putString("roomName", roomNumber)
                Log.e("roomName", `object`.getString("room")+ " roomName이다.")
            } catch (e: JSONException) {
                e.printStackTrace()
            }

        })
    }


    fun sendMessage() {
        preferences = getSharedPreferences("auto", Context.MODE_PRIVATE)
        val now = System.currentTimeMillis()
        val date = Date(now)
        //나중에 바꿔줄것
        val sdf = SimpleDateFormat("yyyy-MM-dd")

        val getTime = sdf.format(date)

        val message = chating_Text.getText().toString().trim({ it <= ' ' })
        if (TextUtils.isEmpty(message)) {
            return
        }
        chating_Text.setText("")
        val jsonObject = JSONObject()
        try {
            jsonObject.put("name", preferences.getString("inputId", ""))
            jsonObject.put("script", message)
            jsonObject.put("profile_image", "example")
            jsonObject.put("date_time", getTime)
            jsonObject.put("room", roomNumber)
            Log.e("roomname2", roomNumber+" roomName2이다.")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        Log.e("챗룸", "sendMessage: 1" + mSocket.emit("chat message", jsonObject))
        Log.e("sendmmm", preferences.getString("inputId", "")!!)
        
    }
}