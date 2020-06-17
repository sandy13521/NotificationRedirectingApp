package com.example.networkingapp;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class NotificationListener extends NotificationListenerService {
    Context context;
    public static String notificationText;
    public NotificationReceiver receiver;

    public NotificationListener() {
        Log.i("Constructor", "called");
    }

    public NotificationListener(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Log.i("OnCreate", "onCreate: Listener Active");
        receiver = new NotificationReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.notification");
        this.registerReceiver(receiver, filter);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("BIND", "binded the listener");
        return super.onBind(intent);
    }

    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
        Log.i(TAG, "Listerner is Activated");
    }

    @Override
    public void onListenerDisconnected() {
        super.onListenerDisconnected();
        Log.i(TAG, "Listener is Deactivated");
    }

    @Override
    public StatusBarNotification[] getActiveNotifications(String[] keys) {
        Log.i("All", "reading");
        return super.getActiveNotifications(keys);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
//        super.onNotificationRemoved(sbn);
        Log.i(TAG, "**********  onNotificationRemoved");
        Log.i(TAG, "ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText + "\t" + sbn.getPackageName());
        if (sbn.getNotification().tickerText != null) {
            notificationText = sbn.getNotification().tickerText.toString();
            Intent intent = new Intent("com.notification");
            intent.putExtra("text", notificationText);
            Log.i("Notification",notificationText);
            sendBroadcast(intent);
            Log.i("BroadCast Remove", "Broadcasted");
        }
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
//        super.onNotificationPosted(sbn);
        Log.i(TAG, "**********  onNotificationPosted");
        Log.i(TAG, "ID :" + sbn.getNotification().tickerText + "\t" + sbn.getPackageName());
        if (sbn.getNotification().tickerText != null) {
            notificationText = sbn.getNotification().tickerText.toString();
            Intent intent = new Intent("com.notification");
            intent.putExtra("text", notificationText);
            Log.i("Notification",notificationText);
            sendBroadcast(intent);
            Log.i("BroadCast Posted", "Broadcasted");
        }
    }

    @Override
    public void onDestroy() {
        Log.i("Destroy", "des");
        super.onDestroy();
    }
}

// Package Names
// Receiving Call - com.android.incallui
// Missed Call - com.android.server.telecom
// Messages - com.android.mms
// Whatsapp - com.whatsapp
// Uber - com.ubercab
// Inshorts - com.nis.app
// Aarogya Setu - nic.goi.aarogyasetu
// YouTube - com.google.android.youtube

