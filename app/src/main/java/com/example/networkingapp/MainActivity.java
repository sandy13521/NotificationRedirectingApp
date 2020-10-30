package com.example.networkingapp;

import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import com.google.android.material.snackbar.Snackbar;
import java.io.DataOutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    public Button enableButton;
    public Button startButton;
    public Button createButton;

    public int REQUEST_ENABLE_BT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enableButton = findViewById(R.id.enable);
        startButton = findViewById(R.id.start);
        createButton = findViewById(R.id.create);

        //Enables the the app to listen to the notifications.
        enableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));
            }
        });

        // Turns on Bluetooth when start button is clicked.
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (bluetoothAdapter == null) {
                Snackbar.make(findViewById(R.id.main), "Bluetooth is not supported in your Phone", Snackbar.LENGTH_LONG).show();
            }
            else if (!bluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                Intent discoverableIntent =
                        new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                startActivity(discoverableIntent);
            }
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            NotificationManager nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationCompat.Builder ncomp = new NotificationCompat.Builder(MainActivity.this);
            ncomp.setContentTitle("My Notification");
            ncomp.setContentText("Notification Listener Service Example");
            ncomp.setTicker("My Notification Tests");
            ncomp.setSmallIcon(R.drawable.ic_launcher_background);
            ncomp.setAutoCancel(true);
            nManager.notify((int) System.currentTimeMillis(), ncomp.build());
            }
        });
        startService(new Intent(this,NotificationListener.class));
        // Starting this Service to resolve issues like NotificationListenerService Getting Disconnected automatically.
        startService(new Intent(this, NotificationCollectorMonitorService.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK){
            Toast.makeText(MainActivity.this,"Bluetooth Enabled ", Toast.LENGTH_SHORT).show();
        }
        else{
            Snackbar.make(findViewById(R.id.main), "You need to turn on Bluetooth to use this app.", Snackbar.LENGTH_INDEFINITE).show();
        }
    }

     @Override protected void onStop(){
        super.onStop();
         Toast.makeText(MainActivity.this,"Application is being closed.", Toast.LENGTH_SHORT).show();
     }
}