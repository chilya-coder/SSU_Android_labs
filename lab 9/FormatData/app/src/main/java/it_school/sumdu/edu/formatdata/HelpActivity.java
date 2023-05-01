package it_school.sumdu.edu.formatdata;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class HelpActivity extends AppCompatActivity {

    // TAG for the dial logging message.
    private static final String TAG = HelpActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = getString(R.string.support_phone);
                callSupportCenter(phoneNumber);
            }
        });
    }

    private void callSupportCenter(String phoneNumber) {
        String formattedNumber = String.format("tel: %s", phoneNumber);
        Intent dialIntent = new Intent(Intent.ACTION_DIAL);
        dialIntent.setData(Uri.parse(formattedNumber));
        if (dialIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(dialIntent);
        } else {
            Log.e(TAG, getString(R.string.dial_log_message));
        }
    }
}