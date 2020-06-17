package com.example.networkingapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

public class NotificationReceiver extends BroadcastReceiver {
    public Socket socket;
    public DataOutputStream dataOutputStream;

    public NotificationReceiver() {
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        socket = new Socket("192.168.43.22", 3000);
                        dataOutputStream = new DataOutputStream(socket.getOutputStream());
                        Log.i("Socket", "Created");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onReceive(Context context, final Intent intent) {
        if (Objects.equals(intent.getAction(), "com.notification")) {
            try {
                if (dataOutputStream!=null){
                    dataOutputStream.writeChars(intent.getStringExtra("text"));
                    Log.i("Notification Received", "Notification Sent");

                }
                else{

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.i("Invalid", "Intent");
        }
    }
}
