package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;

public class pop_window_view extends PopupWindow {

    private Button camera, photo, cancel;
    private View view;

    public pop_window_view(final Activity context, View.OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.popwindow, null);
        camera = (Button) view.findViewById(R.id.icon_btn_camera);
        photo = (Button) view.findViewById(R.id.icon_btn_choose);
        cancel = (Button) view.findViewById(R.id.icon_btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            { dismiss(); }
        });
        photo.setOnClickListener(itemsOnClick);
        camera.setOnClickListener(itemsOnClick);
        this.setContentView(view);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        //this.setAnimationStyle(R.style.AnimationPreview);
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));

        final WindowManager.LayoutParams wlBackground = context.getWindow().getAttributes();
        wlBackground.alpha = 0.7f;
        context.getWindow().setAttributes(wlBackground);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                wlBackground.alpha = 1.0f;
                context.getWindow().setAttributes(wlBackground);
            }
        });

        view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = view.findViewById(R.id.ll_pop).getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height)
                    { dismiss(); }
                }
                return true;
            }
        });
    }

}
//public class pop_window_view extends AppCompatActivity
//{
    //@Override
    //protected void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.popwindow);

        //DisplayMetrics dm = new DisplayMetrics();
        //getWindowManager().getDefaultDisplay().getMetrics(dm);
        //int width = dm.widthPixels;
        //int height = dm.heightPixels;

        //getWindow().setGravity(Gravity.BOTTOM);
        //getWindow().setLayout((int)(width*.5),(int)(height*.3));
    //}
//}
