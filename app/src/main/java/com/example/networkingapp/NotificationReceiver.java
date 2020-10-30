package com.example.networkingapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Objects;

public class NotificationReceiver extends BroadcastReceiver {
    public Socket socket;
    public DataOutputStream dataOutputStream;
    public BufferedReader bufferedReader;

    public NotificationReceiver() {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Log.i("NOtificationReceiver","Constructor called");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        socket = new Socket("192.168.43.105", 3000);
                        dataOutputStream = new DataOutputStream(socket.getOutputStream());
                        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
                    dataOutputStream.writeChars("HELLO WORLD");
                    String x = bufferedReader.readLine();
                    Log.i("Content ACK",x);
//                    if ()
                    Log.i("Notification Content",intent.getStringExtra("text"));
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
