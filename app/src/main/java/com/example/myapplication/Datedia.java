package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Datedia implements DatePicker.OnDateChangedListener, TimePicker.OnTimeChangedListener
{
    private String dateTime;
    private Context context;
    private DateCallBack listener;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private View view;
    public Datedia(Context context, DateCallBack listener) {
        this.context = context;
        this.listener = listener;
        //初始化视图中的控件
        initPicker();
        //初始化Dialog
        initDialog();

    }
    private void initPicker() {
        view = LayoutInflater.from(context).inflate(R.layout.datedia, null);

        datePicker = (DatePicker) view.findViewById(R.id.date);
        timePicker = (TimePicker) view.findViewById(R.id.time);

        Calendar calendar = Calendar.getInstance();
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), this);
        timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(this);
    }
    public void initDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                listener.onClick(dateTime);
            }
        });
        builder.setNegativeButton("取消", null).create();
        builder.show();
        onDateChanged(null, 0, 0, 0);
    }

    private void hidYear(DatePicker mDatePicker) {
        Field[] datePickerFields = mDatePicker.getClass().getDeclaredFields();
        for (Field datePickerField : datePickerFields) {

            if ("mYearSpinner".equals(datePickerField.getName())) {
                datePickerField.setAccessible(true);
                Object dayPicker = new Object();
                try {
                    dayPicker = datePickerField.get(mDatePicker);
                } catch (IllegalAccessException | IllegalArgumentException e) {
                    e.printStackTrace();
                }
                ((View) dayPicker).setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        onDateChanged(null, 0, 0, 0);
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(datePicker.getYear(), datePicker.getMonth(),
                datePicker.getDayOfMonth(), timePicker.getCurrentHour(),
                timePicker.getCurrentMinute());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateTime = sdf.format(calendar.getTime());
    }

    //采用接口回调的方式 获取点击dialog的'设置'之后的数据
    public interface DateCallBack {
        public void onClick(String date);
    }
}
