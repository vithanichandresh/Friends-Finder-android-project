package topsint.myfriendsfinder;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hp on 24/01/17.
 */

public class JSONHelper extends AsyncTask<Void,Void,String> {


    Context context;
    String myUrl;
    ProgressDialog progressDialog;
    OnAsyncLoader onAsyncLoader;
    HashMap<String,String> hashMap;

    public JSONHelper(Context context, String url, HashMap<String,String> hashMap, OnAsyncLoader onAsynckLoader)
    {
        this.context=context;
        myUrl=url;
        this.onAsyncLoader=onAsynckLoader;
        this.hashMap=hashMap;


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait a movement..");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        String result="";
        try {
            URL url = new URL(myUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            if (hashMap != null) {
                httpURLConnection.setReadTimeout(20000);
                httpURLConnection.setConnectTimeout(20000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(hashMap));

                writer.flush();
                writer.close();
                os.close();
            }
            if (httpURLConnection.getResponseCode() == 200) {
                InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
            }
            httpURLConnection.disconnect();
        } catch (MalformedURLException e) {
            Log.e("result", "Error = " + e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("result", "Error = " + e.toString());
            e.printStackTrace();
        }
        return result;

    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        try {
            onAsyncLoader.onResult(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        Log.e("url", result.toString());
        return result.toString();
    }

}
