package it_school.sumdu.edu.broadcastreceivers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private final CustomReceiver mReceiver = new CustomReceiver();
    private static final String EXTRA_RANDOM_NUMBER = "it_school.sumdu.edu.broadcastreceivers.RANDOM_NUMBER";
    private static final String ACTION_CUSTOM_BROADCAST = "it_school.sumdu.edu.broadcastreceivers.ACTION_CUSTOM_BROADCAST";
    private TextView textView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter(ACTION_CUSTOM_BROADCAST));
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    public void sendCustomBroadcast(View view) {
        Intent customBroadcastIntent = new Intent(ACTION_CUSTOM_BROADCAST);
        int num = generateRandomNumber();
        textView.setText(String.format("Number is %s", num));
        customBroadcastIntent.putExtra(EXTRA_RANDOM_NUMBER, num);
        LocalBroadcastManager.getInstance(this).sendBroadcast(customBroadcastIntent);
    }

    private int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(20) + 1;
    }
}