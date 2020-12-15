package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

import static java.lang.Double.parseDouble;

public class Login extends AppCompatActivity {

    private static final String TAG = "AndroidClarified";
    private SignInButton googleSignInButton;
    private GoogleSignInClient googleSignInClient;
    GlobalVariable gmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorAccent));
        googleSignInButton = findViewById(R.id.sign_in_button);
        setGooglePlusButtonText(googleSignInButton);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 101);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case 101:
                    try {
                        // The Task returned from this call is always completed, no need to attach a listener.
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        onLoggedIn(account);
                    } catch (ApiException e) {
                        // The ApiException status code indicates the detailed failure reason.
                        Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
                    }
                    break;
            }
    }
    private void onLoggedIn(final GoogleSignInAccount googleSignInAccount) {
        Thread thread = new Thread(){
            public void run() {
                String line = ws_test2.signupornot(googleSignInAccount.getEmail());
                //String line = ws_test2.signupornot("Apple@gmail.com");
                if (line.equals("error")==false) {
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    Log.v("test1","error=false");
                    intent.putExtra("gmail",googleSignInAccount.getEmail());
                    //intent.putExtra("gmail","Apple@gmail.com");
                    gmail = (GlobalVariable)getApplicationContext();
                    //gmail.setGmail("Apple@gmail.com");
                    gmail.setGmail(googleSignInAccount.getEmail());
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent = new Intent(getApplication(), Signup.class);
                    Log.v("test1","error=true");
                    intent.putExtra("gmail",googleSignInAccount.getEmail());
                    startActivity(intent);
                }
            }
        };
        thread.start();
    }
    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount alreadyloggedAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (alreadyloggedAccount != null) {
            Toast.makeText(this, "Already Logged In", Toast.LENGTH_SHORT).show();
            Log.v("test1",alreadyloggedAccount.getEmail());
            onLoggedIn(alreadyloggedAccount);
        } else {
            Log.d(TAG, "Not logged in");
            //onLoggedIn(alreadyloggedAccount);
        }
    }
    protected void setGooglePlusButtonText(SignInButton googleSignInButton) {
        for (int i = 0; i < googleSignInButton.getChildCount(); i++) {
            View v = googleSignInButton.getChildAt(i);
            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText("Sign  in  with  Google    ");
                tv.setTextSize(18);
                tv.setTypeface(null, Typeface.BOLD);
                return;
            }
        }
    }

}
