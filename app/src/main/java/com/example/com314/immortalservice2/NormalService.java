package com.example.com314.immortalservice2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

public class NormalService extends Service {
    Thread mThread;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        //알람 해제
        unregisterAlram();
        super.onCreate();
    }

    private void unregisterAlram() {
        Intent i=new Intent(NormalService.this,ServiceMonitor.class);
        i.setAction(ServiceMonitor.ACTION_RESTART);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(getApplicationContext(),0,i,0);
        AlarmManager am=(AlarmManager)getSystemService(ALARM_SERVICE);
        am.cancel(pendingIntent);
    }

    @Override
    public void onDestroy() {
        //알람 등록
        registerAlram();
        super.onDestroy();
    }
    private void registerAlram() {
        Intent i=new Intent(NormalService.this,ServiceMonitor.class);
        i.setAction(ServiceMonitor.ACTION_RESTART);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(getApplicationContext(),0,i,0);

        long firstTime = SystemClock.elapsedRealtime();
        firstTime += 2000; // 2초후 알람.
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime, 3000, pendingIntent);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //쓰레드 돌리기.!
        Log.d( "set-onStart", "Service is started. Normal Thread running ");
        if(mThread !=null){
            try {
                mThread.interrupt();
            }
            catch( Exception e) {
                Log.d( "set-onStart", " Thread Stop " + e.getMessage());
            }
        }
        mThread=null;
        if(mThread==null){
            mThread = new MyThread();
            mThread.start();
        }


        return super.onStartCommand(intent, flags, startId);
    }
    class MyThread extends Thread{
        private int s = 0;
        public MyThread() {
            Log.d( "set-onStart", "worker is created");
        }
        @Override
        public void run() {
            Log.d( "set-onStart", " Service Running ");
            while(true && !this.currentThread().isInterrupted()) {
                Log.d( "set-onStart", " LL : " + (s++) );
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
            Log.d( "set-onStart", " Terminated thread :: " + this);
        }
    }
}
