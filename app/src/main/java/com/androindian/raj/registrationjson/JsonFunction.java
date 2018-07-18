package com.androindian.raj.registrationjson;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JsonFunction {

    public static String getJsonFromUrlparam(String url, String para){

        JSONObject jsonObject=null;
        StringBuffer sb = null;
        try {
            URL jurl=new URL(url);

                HttpURLConnection httpURLConnection=
                        (HttpURLConnection) jurl.openConnection();

                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setConnectTimeout(4000);
                httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty("Accept-Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            //httpURLConnection.setRequestProperty("","");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.connect();

            OutputStream mOutPut = new BufferedOutputStream(httpURLConnection.getOutputStream());
            mOutPut.write(para.getBytes());
            mOutPut.flush();

            InputStream is=new BufferedInputStream(httpURLConnection.getInputStream());
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
            sb=new StringBuffer();
            String line;
            while ((line=bufferedReader.readLine())!=null)
            {
                sb.append(line+"\n");
            }
            is.close();

            Log.i("json",sb.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();

        }
        return sb.toString();
    }

}
