package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import de.hdodenhof.circleimageview.CircleImageView;

public class finishsetup extends AppCompatActivity {

    private CircleImageView finishsetup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finishsetup);
        finishsetup = (CircleImageView) findViewById(R.id.finishsetup);
        finishsetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gomain = new Intent(finishsetup.this, MainActivity.class);
                startActivity(gomain);
            }
        });
    }
}
