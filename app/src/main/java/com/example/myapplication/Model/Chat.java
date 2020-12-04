package com.example.myapplication.Model;

public class Chat {

    private String sender;
    private String receiver;
    private String time;
    private String profileImg;
    private String userName;
    private boolean seen = false;

//, String receiver, String userName, String profileImg, String time

    public Chat(String sender) {
        this.sender = sender;
//        this.receiver = receiver;
        this.time = time;
//        this.userName = userName;
//        this.profileImg = profileImg;
//        this.seen = isSeen();
    }

    public Chat() {}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {return receiver;}

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getProfileImg() {return profileImg;}

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getTime(){return time; }

    public void setTime(String time) {this.time = time;}

    public boolean isSeen() {return seen;}

    public void setSeen(Boolean seen){this.seen = seen;}
}
