package com.saif.allaboutservices;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.util.Log;

public class MyService extends Service {
    private static String TAG=MyService.class.getSimpleName();
    private ResultReceiver receiver;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"onCreate: running on "+Thread.currentThread().getName());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG,"onStartCommand: running on "+Thread.currentThread().getName());
        receiver=intent.getParcelableExtra("rslt");
        new BackgroundTask().execute();
        return START_STICKY;

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestroy: running on "+Thread.currentThread().getName());
    }
    private class BackgroundTask extends AsyncTask<Void,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e(TAG,"onPreExecute: running on "+Thread.currentThread().getName());
        }

        @Override
        protected String doInBackground(Void... voids) {
            Log.e(TAG,"doInBackground: running on "+Thread.currentThread().getName());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello World";
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            Log.e(TAG,"onProgressUpdate: running on "+Thread.currentThread().getName());
            stopSelf();
        }

        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);
            Log.e(TAG,"onPostExecute: running on "+Thread.currentThread().getName());
            Bundle bundle=new Bundle();
            bundle.putString("return",str);
            receiver.send(1,bundle);

        }
    }
}
