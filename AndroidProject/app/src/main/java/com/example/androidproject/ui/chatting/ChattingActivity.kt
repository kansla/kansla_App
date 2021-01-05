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
import com.example.androidproject.API.RetrofitHelper
import com.example.androidproject.DTO.ChatRoomDTO
import com.example.androidproject.DTO.LoadMsgDTO
import com.example.androidproject.R
import com.example.androidproject.ui.chatRoom.ChatList
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
    private var mSocket: Socket = IO.socket("http://4c9fb7d35aa4.ngrok.io/")

    //리사이클러뷰
    var arrayList = arrayListOf<ChatModel>()
    val mAdapter = ChatAdapter(this, arrayList)

    var roomNumber : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatting)

        preferences = getSharedPreferences("auto", Context.MODE_PRIVATE)

        val actionBar = supportActionBar
        actionBar?.title = preferences.getString("second_name", "")

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
            loadMessage()

            //소켓연결
            mSocket.connect()


            //서버에 신호 보내는거같음 밑에 에밋 리스너들 실행
            //socket.on은 수신
            mSocket.on("connect user", onNewUser)
            mSocket.on("chat message", onNewMessage)
            mSocket.on("leave", onNewLeave)

            /*val userId = JSONObject()
            try {
                userId.put("room", preferences.getString("roomName", "0"))
                roomNumber = preferences.getString("roomName", "").toString()
                Log.e("roomNAMe", roomNumber)
                Log.e("username",preferences.getString("inputId", "") + " Connected")

                //socket.emit은 메세지 전송임
                mSocket.emit("connect user", userId)
            } catch (e: JSONException) {
                e.printStackTrace()
            }*/

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

    override fun onDestroy() {
        super.onDestroy()
        leave()
        Log.e("onDestroy", "leave")
    }

    internal var onNewMessage: Emitter.Listener = Emitter.Listener { args ->
        runOnUiThread(Runnable {
            val data = args[0] as JSONObject
            val name: String
            val script: String
            val profile_image: String
            val date_time: String
            val email: String
            try {
                preferences = getSharedPreferences("auto", Context.MODE_PRIVATE)
                Log.e("asdasd", data.toString())
                name = preferences.getString("second_name","").toString()
                script = data.getString("script")
                profile_image = data.getString("profile_image")
                date_time = data.getString("date_time")
                email = data.getString("name")


                val format = ChatModel(name, script, profile_image, date_time, email)
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
                if(`object`.getString("first_email").equals(preferences.getString("inputId","")) &&
                        `object`.getString("second_email").equals(preferences.getString("second_email", ""))){
                    roomNumber = `object`.getString("room")
                    editor.putString("roomName", roomNumber)
                    editor.apply()
                    Log.e("roomNameㅗㅗㅗ", `object`.getString("room")+ " roomName이다.")
                }
                else if (`object`.getString("first_email").equals(preferences.getString("second_email","")) &&
                        `object`.getString("second_email").equals(preferences.getString("inputId", ""))){
                    roomNumber = `object`.getString("room")
                    editor.putString("roomName", roomNumber)
                    editor.apply()
                    Log.e("roomNameㅗㅗㅗ", `object`.getString("room")+ " roomName이다.")
                }

                Log.e("roomName", `object`.getString("room")+ " roomName이다.")
            } catch (e: JSONException) {
                e.printStackTrace()
            }

        })
    }
    internal var onNewLeave: Emitter.Listener = Emitter.Listener { args ->
        runOnUiThread(Runnable {
            val data = args[0] as JSONObject
            Log.e("data", data.toString())
        })
    }


    fun sendMessage() {
        preferences = getSharedPreferences("auto", Context.MODE_PRIVATE)
        val now = System.currentTimeMillis()
        val date = Date(now)
        //나중에 바꿔줄것
        val sdf = SimpleDateFormat("yyyy-MM-dd-hh-mm-ss")

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
            jsonObject.put("room", preferences.getString("roomName","0"))
            Log.e("roomname2", preferences.getString("roomName","0")+" roomName2이다.")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        Log.e("챗룸", "sendMessage: 1" + mSocket.emit("chat message", jsonObject))
        Log.e("sendmmm", preferences.getString("inputId", "")!!)
        
    }

    fun leave(){
        val jsonObject = JSONObject()
        try {
            jsonObject.put("room", preferences.getString("roomName","0"))
            Log.e("leave", jsonObject.toString())
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        mSocket.emit("leave", jsonObject)
    }

    fun loadMessage(){
        preferences = getSharedPreferences("auto", Context.MODE_PRIVATE)
        val editor = preferences!!.edit()
        var room : LoadMsgDTO = LoadMsgDTO(preferences.getString("inputId", ""), preferences.getString("second_email",""))
        var call: Call<LoadMsgDTO>? = RetrofitHelper.getApiService().chat_load(room)
        call?.enqueue(object : Callback<LoadMsgDTO> {
            override fun onResponse(call: Call<LoadMsgDTO>, response: Response<LoadMsgDTO>) {
                Log.e("성공입니당~",response.body().toString())
                var result : LoadMsgDTO? = response.body()
                var name : String
                var msg : String
                var email : String
                var date_time : String
                for (i in 0.. result!!.count-1){
                    name = result.chatLine.get(i).name
                    msg = result.chatLine.get(i).msg
                    date_time = result.chatLine.get(i).date
                    email = preferences.getString("second_email","").toString()
                    editor.putString("roomName", result.room.toString())
                    editor.apply()

                    Log.e("roomNAme", preferences.getString("roomName", "").toString())
                    Log.e("name", name)

                    val format = ChatModel(name, msg, "profileImage", date_time, email)
                    mAdapter.addItem(format)
                    mAdapter.notifyDataSetChanged()
                    // 메세지가 올라올때마다 스크롤 최하단으로 보내기
                    chat_recyclerview.scrollToPosition(((chat_recyclerview.adapter?.itemCount ?: Int) as Int) - 1)
                }
                val userId = JSONObject()
                try {
                    userId.put("room", preferences.getString("roomName", "0"))
                    roomNumber = preferences.getString("roomName", "").toString()
                    Log.e("roomNAMe", roomNumber)
                    Log.e("username",preferences.getString("inputId", "") + " Connected")

                    //socket.emit은 메세지 전송임
                    mSocket.emit("connect user", userId)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<LoadMsgDTO>, t: Throwable) {
                Log.e("d실패", t.message.toString())
            }

        })
    }
}