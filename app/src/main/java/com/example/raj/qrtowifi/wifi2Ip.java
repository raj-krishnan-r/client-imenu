package com.example.raj.qrtowifi;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by raj on 15/8/17.
 */

public class wifi2Ip extends AsyncTask<String,String,String> {
Context cxt;
    int tid;
public wifi2Ip(Context context,int tableid)
{
    cxt=context;
    tid = tableid;
}
    protected String doInBackground(String... args) {
        WifiConfiguration wifiConfig = new WifiConfiguration();
        wifiConfig.SSID = String.format("\"%s\"", args[0]);
        wifiConfig.preSharedKey = String.format("\"%s\"",args[1]);

        final WifiManager wifiManager = (WifiManager) cxt.getSystemService(WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);
//remember id
        int netId = wifiManager.addNetwork(wifiConfig);
        wifiManager.disconnect();
        wifiManager.enableNetwork(netId, true);
        wifiManager.reconnect();
        return args[2];

    }
    protected void onPostExecute(String res)
    {
        URL url;
        try {
            url = new URL("http://" + res + ":3000/listings");
            new getData(cxt,tid).execute(url);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }



}
