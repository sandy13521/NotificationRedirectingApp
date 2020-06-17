package com.example.networkingapp;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.net.Socket;

public class SendNotification extends AsyncTask<Void, Void, Void> {
    public Socket socket;
    public DataOutputStream dataOutputStream;
    @Override
    protected Void doInBackground(Void... voids) {
//        try{
//             socket = new Socket("192.168.43.22",3000);
//             dataOutputStream  = new DataOutputStream(socket.getOutputStream());
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        return null;
    }
}
