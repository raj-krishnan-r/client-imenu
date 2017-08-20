package com.example.raj.qrtowifi;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.Reader;
import com.google.zxing.qrcode.encoder.QRCode;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by raj on 14/8/17.
 */

public class getData extends AsyncTask<URL,String,String> {
    Context c;
    Activity rec;

    int tableid;

    public getData(Context as,int table)
    {
        c=as;
        tableid=table;
    }
    public getData(Context as)
    {
        c=as;
    }



    @Override
    protected String doInBackground(URL...args) {

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(args[0].openStream()));

String inputLine,resultString="";
            while ((inputLine = in.readLine())!=null)
            {
                resultString+=inputLine;
            }
            return resultString;
        } catch (IOException e) {
            e.printStackTrace();
            return args[0].toString()+","+tableid;
        }

    }
    public void onPostExecute(String result)
    {
           // Toast.makeText(c,result,Toast.LENGTH_LONG).show();

if(result.substring(0,1).equals("[")) {
    Intent intent = new Intent(c, RealHome.class);
    intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
    intent.putExtra("jsonresult", result);
    intent.putExtra("tableid", tableid);
    c.startActivity(intent);
}
else
{
    String[] box = result.split(",");
    try {
        URL ur = new URL(box[0]);

        int tableid = Integer.parseInt(box[1]);
        new getData(c,tableid).execute(ur);

    } catch (MalformedURLException e) {
        e.printStackTrace();
    }

}
    }
}
