package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class ChangeHeadImagePop extends PopupWindow implements View.OnClickListener {
    private Activity activity;
    private View popView;
    private View v_item1;
    private View v_item2;
    private View v_item3;



    private OnItemClickListener onItemClickListener;
    public enum MENUITEM {
        ITEM1, ITEM2, ITEM3
    }

    public ChangeHeadImagePop(final Activity activity) {
        super(activity);
        this.activity = activity;
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popView = inflater.inflate(R.layout.chose_headimage_popwind, null);
        this.setContentView(popView);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setTouchable(true);
        this.setOutsideTouchable(true);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        this.setBackgroundDrawable(dw);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });

        v_item1 = popView.findViewById(R.id.camera);
        v_item2 = popView.findViewById(R.id.photoAlbum);
        v_item3 = popView.findViewById(R.id.cancel);

        v_item1.setOnClickListener(this);
        v_item2.setOnClickListener(this);
        v_item3.setOnClickListener(this);
    }

    public void showLocation(View anchorView)
    { this.showAtLocation(anchorView, Gravity.BOTTOM , 0, 0); }

    private void setBackgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }

    public interface OnItemClickListener
    { void onClick(MENUITEM item, String str);}

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    { this.onItemClickListener = onItemClickListener; }

    @Override
    public void onClick(View v) {
        MENUITEM menuitem = null;
        String str = "";
        if (v == v_item1) {
            menuitem = MENUITEM.ITEM1;
            str = "one";
        } else if (v == v_item2) {
            menuitem = MENUITEM.ITEM2;
            str = "two";
        } else if (v == v_item3) {
            menuitem = MENUITEM.ITEM3;
            str = "three";
        }
        if (onItemClickListener != null) {
            onItemClickListener.onClick(menuitem, str);
        }
        dismiss();
    }
}
