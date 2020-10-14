package com.example.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.Timer;
import java.util.TimerTask;

public class notification_device extends Service {

    Timer timer;
    TimerTask timerTask;
    String TAG = "Timers";
    int Your_X_SECS = 5;
    private int NOTIFICATION_ID_HEADS_UP = 4;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        startTimer();
        return START_STICKY;
    }


    @Override
    public void onCreate() {
        //Log.e(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        //Log.e(TAG, "onDestroy");
        stoptimertask();
        super.onDestroy();
    }

    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();

    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        initializeTimerTask();

        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, 5000, Your_X_SECS * 10000); //
        //timer.schedule(timerTask, 5000,1000); //
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                        String title = "TrackDear 距離提醒";
                        String textContent = "Applekid 已經超過安全範圍了 請立即查看";
                        int smallIconResId = R.drawable.map;
                        int largeIconResId = R.drawable.alert;
                        long sendTime = System.currentTimeMillis();
                        boolean autoCancel = false;
                        Intent intent = new Intent(notification_device.this, MainActivity.class);
                        Intent intentArr[] = {intent};
                        PendingIntent pendingIntent = PendingIntent.getActivities(notification_device.this, 0, intentArr, PendingIntent.FLAG_CANCEL_CURRENT);

                        final NotificationCompat.Builder builder = getGeneralNotificationBuilder(title, textContent, smallIconResId, largeIconResId, autoCancel, sendTime);

                        builder.setDefaults(Notification.DEFAULT_ALL);
                        builder.setContentIntent(pendingIntent);
                        builder.setFullScreenIntent(pendingIntent, true);

                        Notification notification = builder.build();
                        notificationManager.notify(NOTIFICATION_ID_HEADS_UP, notification);
                    }
                });
            }
        };
    }
    private NotificationCompat.Builder getGeneralNotificationBuilder(String title, String textContent, int smallIconResId, int largeIconResId, boolean autoCancel, long sendTime)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(notification_device.this);

        builder.setSmallIcon(smallIconResId);
        BitmapDrawable bitmapDrawable = (BitmapDrawable)getDrawable(largeIconResId);
        Bitmap largeIconBitmap = bitmapDrawable.getBitmap();
        builder.setLargeIcon(largeIconBitmap);
        builder.setContentTitle(title);
        builder.setContentText(textContent);
        builder.setWhen(sendTime);
        builder.setAutoCancel(autoCancel);
        builder.setPriority(Notification.PRIORITY_MAX);
        return builder;
    }
}
