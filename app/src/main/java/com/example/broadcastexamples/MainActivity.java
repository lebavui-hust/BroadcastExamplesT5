package com.example.broadcastexamples;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    MyReceiver receiver;
    DataReceiver dataReceiver;

    TextView textContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textContent = findViewById(R.id.text_content);

        receiver = new MyReceiver();
        registerReceiver(receiver, new IntentFilter(Intent.ACTION_SCREEN_ON));
        registerReceiver(receiver, new IntentFilter(Intent.ACTION_SCREEN_OFF));
        registerReceiver(receiver, new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED));

        dataReceiver = new DataReceiver();
        registerReceiver(dataReceiver, new IntentFilter("vn.edu.hust.SEND_DATA"));

        findViewById(R.id.button_open_2nd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        unregisterReceiver(dataReceiver);

        super.onDestroy();
    }

    class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                Log.v("TAG", "ACTION_SCREEN_ON");
            } else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                Log.v("TAG", "ACTION_SCREEN_OFF");
            } if (intent.getAction().equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
                Log.v("TAG", "ACTION_AIRPLANE_MODE_CHANGED");
            }
        }
    }

    class DataReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("vn.edu.hust.SEND_DATA")) {
                String content = intent.getStringExtra("DATA");
                textContent.setText(content);
            }
        }
    }
}