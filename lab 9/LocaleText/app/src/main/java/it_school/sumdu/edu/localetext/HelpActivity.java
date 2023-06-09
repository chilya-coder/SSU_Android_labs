package it_school.sumdu.edu.localetext;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Help screen that provides a floating action button
 * to start dialing a phone number.
 */
public class HelpActivity extends AppCompatActivity {

    // TAG for the dial logging message.
    private static final String TAG = HelpActivity.class.getSimpleName();

    /**
     * Creates the view with a floating action button and click listener.
     *
     * @param savedInstanceState Bundle with activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            String phoneNumber = getString(R.string.support_phone);
            callSupportCenter(phoneNumber);
        });
    }

    /**
     * Sends an intent to dial the phone number using ACTION_DIAL.
     *
     * @param phoneNumber Phone number string
     */
    @SuppressLint("QueryPermissionsNeeded")
    private void callSupportCenter(String phoneNumber) {
        // Format the phone number for dialing.
        String formattedNumber = String.format("tel: %s", phoneNumber);
        // Create the intent.
        Intent dialIntent = new Intent(Intent.ACTION_DIAL);
        // Set the formatted phone number as data for the intent.
        dialIntent.setData(Uri.parse(formattedNumber));
        // If package resolves to an app, send intent.
        if (dialIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(dialIntent);
        } else {
            Log.e(TAG, getString(R.string.dial_log_message));
        }
    }
}