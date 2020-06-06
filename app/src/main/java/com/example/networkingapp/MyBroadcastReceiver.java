package com.example.networkingapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends android.content.BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String pack = intent.getStringExtra("package");
//                String title = intent.getStringExtra("title");
//                String text = intent.getStringExtra("text");

        Log.i("PACK", "onReceive:" + pack);
        Toast.makeText(context,"Broadcast Received",Toast.LENGTH_SHORT).show();
    }
}
