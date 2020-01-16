package com.myapp.android.citizen;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils
{
    private String headline="https://www.fast2sms.com/dev/bulk?" +
            "authorization=lriTgKpFAcnQtyudR4DJvoshqx8amXjC7NSL2e5WP6G1Z9BOUwdVZ23JOtCzbRyMnfD7YWUTGqs0KFxm&" +
            "sender_id=FSTSMS&";
    private String headline1="message=" ;
    private String headline2="&language=english&" +
            "route=p&" +
            "numbers=";
    public void sendverificationcode(String number,String otp){
        headline2=headline2+number;
        String message="Your%20One%20time%20password%20is:%20"+otp;
        headline1+=message;
        headline+=headline1+headline2;
        while (!extractNews())
            extractNews();
    }

    public boolean extractNews()  {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        URL u=null;
        try {
            u=new URL(headline);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String Json=null;
        try {
            Log.i("url: ",u.toString());
            Json=makehttprequest(u);
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean status=parsedJson(Json);
        if (status)
            return true;
        return false;


    }

    private static boolean parsedJson(String json) {

        try {
            JSONObject j=new JSONObject(json);
            String status=j.getString("return");
            if (status.equals("true"))
                return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String makehttprequest(URL u)throws IOException {

        String Json="";
        if(u==null)
        {
            return Json;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection= (HttpURLConnection) u.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode()==200)
            {
                inputStream=urlConnection.getInputStream();
                Json=readfromstream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (urlConnection!=null)
                urlConnection.disconnect();
            if (inputStream!=null)
                inputStream.close();
        }

        return Json;

    }

    private static String readfromstream(InputStream inputStream) throws IOException {

        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        Log.e("check","stream method");
        return output.toString();
    }
}

