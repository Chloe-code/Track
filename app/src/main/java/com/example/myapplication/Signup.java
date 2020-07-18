package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Signup extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    public static final String GOOGLE_ACCOUNT = "google_account";
    private TextView profileEmail, deviceid;
    private ViewFlipper viewFlipper;
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        profileEmail = findViewById(R.id.profile_email);
        deviceid = findViewById(R.id.deviceid);
        //GoogleSignInAccount googleSignInAccount = getIntent().getExtras().getParcelable(GOOGLE_ACCOUNT);
        //profileEmail.setText(googleSignInAccount.getEmail());
        viewFlipper = findViewById(R.id.view_flipper);
    }
    @Override
    public void handleResult(Result rawResult) {
        deviceid.setText(rawResult.getText());
    }
    public void nextstep(View v) {
        viewFlipper.setInAnimation(this, android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this, android.R.anim.slide_out_right);
        viewFlipper.showNext();
        if(viewFlipper.getCurrentView().getId()==R.id.matchdevice)
        {
            RelativeLayout rl = (RelativeLayout) findViewById(R.id.relative_scan_take_single);
            mScannerView = new ZXingScannerView(this);
            rl.addView(mScannerView);
            mScannerView.setResultHandler(this);
            mScannerView.startCamera();
            mScannerView.setSquareViewFinder(true);
            mScannerView.setLaserEnabled(false);
        }
    }

}
