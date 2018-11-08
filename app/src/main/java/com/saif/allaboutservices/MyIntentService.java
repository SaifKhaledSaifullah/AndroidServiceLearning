package com.saif.allaboutservices;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

public class MyIntentService extends IntentService {

    private static String TAG=MyIntentService.class.getSimpleName();
    public MyIntentService() {
        super("My Worker Thread");

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"onCreate: running on "+Thread.currentThread().getName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.e(TAG,"onHandleIntent: running on "+Thread.currentThread().getName());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Intent bcrIntent = new Intent("action.service.to.activity");
        bcrIntent.putExtra("startServiceResult", "Intent Service Executed");
        sendBroadcast(bcrIntent);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestroy: running on "+Thread.currentThread().getName());
    }
}
