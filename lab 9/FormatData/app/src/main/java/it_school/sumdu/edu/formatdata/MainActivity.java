package it_school.sumdu.edu.formatdata;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    private int mInputQuantity = 1;
    private static final String COUNTRY_CODE_FR = "FR";
    private static final String COUNTRY_CODE_IL = "IL";
    private static final double EXCHANGE_RATE_FR = 0.86;
    private static final double EXCHANGE_RATE_IL = 3.64;
    private final NumberFormat mNumberFormat = NumberFormat.getInstance();
    private static final String TAG = MainActivity.class.getSimpleName();
    private double mPrice = 1.5;

    private NumberFormat mCurrencyFormat = NumberFormat.getCurrencyInstance();

    public MainActivity() {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpToolbar();
        setUpFloatingActionButton();
        setUpExpirationDate();
        setUpPrice();
        setUpQuantity();
        setUpCalculateButton();
    }
    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    private void setUpFloatingActionButton() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> showHelp());
    }
    private void setUpExpirationDate() {
        final Date myDate = new Date();
        final long expirationDate = myDate.getTime() + TimeUnit.DAYS.toMillis(5);
        myDate.setTime(expirationDate);

        String myFormattedDate = DateFormat.getDateInstance().format(myDate);
        TextView expirationDateView = findViewById(R.id.date);
        expirationDateView.setText(myFormattedDate);
    }
    private void setUpPrice() {
        String myFormattedPrice;
        String deviceLocale = Locale.getDefault().getCountry();

        if (deviceLocale.equals(COUNTRY_CODE_FR) || deviceLocale.equals(COUNTRY_CODE_IL)) {
            if (deviceLocale.equals(COUNTRY_CODE_FR)) {
                mPrice *= EXCHANGE_RATE_FR;
            } else {
                mPrice *= EXCHANGE_RATE_IL;
            }
        } else {
            mCurrencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        }
        myFormattedPrice = mCurrencyFormat.format(mPrice);

        TextView localePrice = findViewById(R.id.price);
        localePrice.setText(myFormattedPrice);
    }
    private void setUpQuantity() {
        final EditText enteredQuantity = findViewById(R.id.quantity);

        enteredQuantity.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideSoftKeyboard(v);
                try {
                    mInputQuantity = Objects.requireNonNull(mNumberFormat.parse(v.getText().toString())).intValue();
                    v.setError(null);
                } catch (ParseException e) {
                    Log.e(TAG, Log.getStackTraceString(e));
                    v.setError(getText(R.string.enter_number));
                    return false;
                }
                String myFormattedQuantity = mNumberFormat.format(mInputQuantity);
                v.setText(myFormattedQuantity);

                calculateTotalAmount();

                return true;
            }
            return false;
        });
    }
    private void setUpCalculateButton() {
        Button calculateButton = findViewById(R.id.calculate_button);
        calculateButton.setOnClickListener(view -> calculateTotalAmount());
    }
    private void calculateTotalAmount() {
        TextView totalAmountView = findViewById(R.id.total);
        double myTotalAmount = mPrice * mInputQuantity;
        totalAmountView.setText(mCurrencyFormat.format(myTotalAmount));
    }
    private void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    private void showHelp() {
        Intent helpIntent = new Intent(this, HelpActivity.class);
        startActivity(helpIntent);
    }
    @Override
    protected void onResume() {
        super.onResume();
        ((EditText) findViewById(R.id.quantity)).getText().clear();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_help:
                showHelp();
                return true;
            case R.id.action_language:
                Intent languageIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(languageIntent);
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}