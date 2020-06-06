package com.example.networkingapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver notificationReceiver;
    public NotificationListener trying;
    public Button enableButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enableButton = findViewById(R.id.enable);
        enableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));
            }
        });

        //Starting Service to Listen the notifications.
        Intent intent = new Intent(this, NotificationListener.class);
        this.startService(intent);

        //Starting Service to Check if the Notification Listener Is active.
        startService(new Intent(this, NotificationCollectorMonitorService.class));

        trying = new NotificationListener(this);
        StatusBarNotification[] notification = trying.getActiveNotifications();

//        for

        notificationReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String pack = intent.getStringExtra("package");
//                String title = intent.getStringExtra("title");
//                String text = intent.getStringExtra("text");
                Log.i("PACK", "onReceive:" + pack);
                Toast.makeText(context,"Broadcast Received",Toast.LENGTH_SHORT).show();
            }
        };

        IntentFilter intentFilter = new IntentFilter("Msg");
        this.registerReceiver(notificationReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(notificationReceiver);
    }
}