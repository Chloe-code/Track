package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.altbeacon.beacon.service.BeaconService;

public class BeaconBroadCast extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("Again Starting the service");
        //Again starting the service
        context.startService(new Intent(context, BeaconService.class));
    }
}
