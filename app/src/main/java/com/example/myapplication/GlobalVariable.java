package com.example.myapplication;

import android.app.Application;

public class GlobalVariable extends Application {
    private String Gmail = "ok";

    public void setGmail(String gmail) {
        this.Gmail = gmail;
    }
    public String getGmail() { return Gmail; }
}
