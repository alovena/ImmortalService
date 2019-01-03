package com.example.com314.immortalservice2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ServiceMonitor extends BroadcastReceiver {
    static String ACTION_RESTART="succes";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d( "seo-broadcast", " OnReceive() ");

        if(intent.getAction().equals(ACTION_RESTART)){
            Log.d( "seo-broadcast", " Service Monitor will start the NormalService");
            Intent i=new Intent(context,NormalService.class);
            context.startService(i);

        }
    }
}
