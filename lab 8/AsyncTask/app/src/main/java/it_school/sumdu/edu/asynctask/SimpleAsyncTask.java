package it_school.sumdu.edu.asynctask;

import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

public class SimpleAsyncTask extends AsyncTask<Void, Integer, String> {

    private final WeakReference<TextView> mTextView;
    private final WeakReference<ProgressBar> mProgressBar;
    private static final int BAR_SIZE = 100;

    SimpleAsyncTask(TextView tv, ProgressBar bar) {
        mTextView = new WeakReference<>(tv);
        mProgressBar = new WeakReference<>(bar);
    }

    @Override
    protected String doInBackground(Void... voids) {
        Random random = new Random();
        int number = random.nextInt(50);
        int milli = number * 50;
        int chunkSize = milli / BAR_SIZE;
        for (int i = 0; i < BAR_SIZE; i++) {
            try {
                Thread.sleep(chunkSize);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publishProgress((i + 1) * 100 / BAR_SIZE);
        }
        return "Time elapsed: " + milli + " milliseconds";
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        mProgressBar.get().setProgress(values[0]);
        mTextView.get().setText(values[0] + "%");
    }

    @Override
    protected void onPostExecute(String result) {
        mTextView.get().setText(result);
    }
}