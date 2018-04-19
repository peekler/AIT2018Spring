package hu.bme.aut.android.moneyconverter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpGetTask extends AsyncTask<String, Void, String> {

    public static final String FILTER_RESULT = "FILTER_RESULT";
    public static final String KEY_RESULT = "KEY_RESULT";
    private Context ctx;

    public HttpGetTask(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String result = "";

        HttpURLConnection hc = null;
        InputStream is = null;
        try {
            URL url = new URL(params[0]);
            hc = (HttpURLConnection) url.openConnection();

            hc.setConnectTimeout(5000);
            hc.setReadTimeout(5000);

            is = hc.getInputStream();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int ch;
            while ((ch=is.read()) != -1) {
                bos.write(ch);
            }

            result = new String(bos.toByteArray());

        } catch (Exception e) {
          e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (hc != null) {
                hc.disconnect();
            }
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        Intent intentBrResult = new Intent(FILTER_RESULT);
        intentBrResult.putExtra(KEY_RESULT,result);

        LocalBroadcastManager.getInstance(
            ctx).sendBroadcast(intentBrResult);
    }
}
