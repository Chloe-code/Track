package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MessageActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Button btn = findViewById(R.id.fri_set);
        btn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                openSetFriend();
            }
        });




       /* Toolbar message =  findViewById(R.id.messagetoolbar);
        setSupportActionBar(message);


        getSupportActionBar().setTitle("Annie Yang");
        message.setSubtitle("@lilycollins");
        message.setLogo(R.mipmap.ic_launcher);

       /* Spinner spinner = findViewById(R.id.fri_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.fri_mode,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(this);*/

    }



   /* @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }*/
   public void openSetFriend(){
       Intent intent = new Intent(this, SetFriend.class);
       startActivity(intent);

   }
}
