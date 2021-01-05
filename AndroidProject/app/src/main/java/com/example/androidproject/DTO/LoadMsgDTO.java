package com.example.androidproject.DTO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoadMsgDTO {
    @SerializedName("count")
    private int count;
    @SerializedName("chat_line")
    private List<ChatLine> chatLine;

    public class ChatLine {
        @SerializedName("name")
        private String name;
        @SerializedName("text")
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

    @Override
    public String toString() {
        return "LoadMsgDTO{" +
                "count=" + count +
                ", chatLine=" + chatLine +
                '}';
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ChatLine> getChatLine() {
        return chatLine;
    }

    public void setChatLine(List<ChatLine> chatLine) {
        this.chatLine = chatLine;
    }
}
