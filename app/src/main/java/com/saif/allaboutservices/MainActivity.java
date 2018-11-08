package com.saif.allaboutservices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tvStartServiceResult, tvIntentServiceResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvIntentServiceResult = (TextView) findViewById(R.id.tvIntentServiceResult);
        tvStartServiceResult = (TextView) findViewById(R.id.tvStartServiceResult);
    }

    // Start the Service
    public void startService(View view) {
        MyResultReceiver myResultReceiver = new MyResultReceiver(null);
        Intent intent = new Intent(MainActivity.this, MyService.class);
        intent.putExtra("rslt", myResultReceiver);
        startService(intent);
    }

    // Stop the Service
    public void stopService(View view) {
        Intent intent = new Intent(MainActivity.this, MyService.class);
        stopService(intent);
    }

    // Start the Intent Service
    public void startIntentService(View view) {
        Intent intent = new Intent(MainActivity.this, MyIntentService.class);
        startService(intent);
    }


    // To receive the data back from MyStartedService.java using BroadcastReceiver
    private BroadcastReceiver myStartedServiceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("My", "BCR Received on " + Thread.currentThread().getName());
            String result = intent.getStringExtra("startServiceResult");
            tvIntentServiceResult.setText(result);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.service.to.activity");
        registerReceiver(myStartedServiceReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(myStartedServiceReceiver);
    }

    private class MyResultReceiver extends ResultReceiver {

        public MyResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            Log.e("MyResultreceiver", Thread.currentThread().getName());
            if (resultCode == 1 && resultData != null) {
                final String result = resultData.getString("return");
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("Handler", Thread.currentThread().getName());
                        tvStartServiceResult.setText(result);
                    }
                });
            }
        }
    }
}
