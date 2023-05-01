package it_school.sumdu.edu.asynctaskandasynctaskloader;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Link {
    private static final String LOG_TAG = Link.class.getSimpleName();
    private static final String HTTP = "http";
    private static final String HTTPS = "https";

    static String getSourceCode(Context context, String queryString, String transferProtocol){
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String htmlSourceCode = null;
        String[] protocol = context.getResources().getStringArray(R.array.http_array);

        try{
            Uri.Builder builder = Uri.parse(queryString).buildUpon();

            if (transferProtocol.equals(protocol[0])) {
                builder.scheme(HTTP);
            } else {
                builder.scheme(HTTPS);
            }

            URL requestURL = new URL(builder.build().toString());
            connection = (HttpURLConnection) requestURL.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            if (inputStream == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }

            if (stringBuilder.length() == 0) {
                return null;
            }

            htmlSourceCode = stringBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.d(LOG_TAG, htmlSourceCode);
        return htmlSourceCode;
    }
}
