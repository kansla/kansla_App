package com.example.androidproject.DTO;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoadMsgDTO {
    @SerializedName("first_email")
    private String first_email;
    @SerializedName("second_email")
    private String second_email;
    @SerializedName("count")
    private int count;
    @SerializedName("room")
    private String room;
    @SerializedName("chat_line")
    private List<ChatLine> chatLine;

    public LoadMsgDTO(String first_email, String second_email) {
        this.first_email = first_email;
        this.second_email = second_email;
    }

    @Override
    public String toString() {
        return "LoadMsgDTO{" +
                "first_email='" + first_email + '\'' +
                ", second_email='" + second_email + '\'' +
                ", count=" + count +
                ", room=" + room +
                ", chatLine=" + chatLine +
                '}';
    }

    public class ChatLine {
        @SerializedName("name")
        private String name;
        @SerializedName("line_text")
        private String msg;
        @SerializedName("created_at")
        private String date;

        @Override
        public String toString() {
            return "ChatLine{" +
                    "name='" + name + '\'' +
                    ", msg='" + msg + '\'' +
                    ", date='" + date + '\'' +
                    '}';
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

    public String getFirst_email() {
        return first_email;
    }

    public void setFirst_email(String first_email) {
        this.first_email = first_email;
    }

    public String getSecond_email() {
        return second_email;
    }

    public void setSecond_email(String second_email) {
        this.second_email = second_email;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public List<ChatLine> getChatLine() {
        return chatLine;
    }

    public void setChatLine(List<ChatLine> chatLine) {
        this.chatLine = chatLine;
    }
}
