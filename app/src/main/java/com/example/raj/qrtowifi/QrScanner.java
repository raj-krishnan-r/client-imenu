package com.example.raj.qrtowifi;

import android.app.ListActivity;
import android.content.ClipData;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.R.attr.key;

public class QrScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ZXingScannerView mscanner;
    ListView lv;
    public itemAdapter adap;
    public Context context;
    public ArrayList<foodItem> m_parts = new ArrayList<foodItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lv = (ListView) findViewById(R.id.itemsMenu);


        setContentView(R.layout.activity_qr_scanner);

        context=this;
        mscanner = new ZXingScannerView(this);
        setContentView(mscanner);
        mscanner.setResultHandler(this);
        mscanner.startCamera();
    }

    protected void onPause() {
        super.onPause();
        mscanner.stopCamera();

    }

    @Override
    public void handleResult(Result result) {


        String sresult = result.getText();
        //String encodedresult="imensu"+sresult;
        String encodedresult = sresult;

        final String[] particular = sresult.split(",");


        if (particular[0].equals("imenu")) {
            Toast.makeText(getApplicationContext(), "Wifi SSID : " + particular[1] + "\nWifi Password : " + particular[2] + "\nIP : " + particular[3]+"\nTable Id : "+particular[4], Toast.LENGTH_SHORT).show();

         int tableid = Integer.parseInt(particular[4]);
         new wifi2Ip(getApplicationContext(), tableid).execute(particular[1],particular[2],particular[3]);

            /*URL url = null;
            try {
                url = new URL("http://" + particular[3] + ":3000/listings");
                new getData(context,adap).execute(url);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }*/
        } else {
            Toast.makeText(getApplicationContext(), "You scanned an invalid QR code !\nPlease scan the valid one.", Toast.LENGTH_SHORT).show();
            mscanner.stopCamera();
            setContentView(mscanner);
            mscanner.setResultHandler(this);
            mscanner.startCamera();
        }
    }
public boolean wifiYes()
{
    ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
    if (activeNetwork != null) { // connected to the internet
        if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
            Toast.makeText(getApplicationContext(), activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();

            return true;
            // connected to wifi
        } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {

            // connected to the mobile provider's data plan
            Toast.makeText(getApplicationContext(), activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
            return true;

        }
    } else {
        // not connected to the internet
        return false;
    }
    return false;

}


}

class foodItem{
    int id;
    String name;
    String descriptions;
    float price;
    public foodItem(int idd, float pr, String na, String desc)
    {
        this.id=idd;
        this.price = pr;
        this.name=na;
        this.descriptions=desc;
    }
    public String getName()
    {
        return this.name;
    }
    public String getDescriptions()
    {
        return this.descriptions;
    }
    public float getPrice()
    {
        return this.price;
    }
    public int getId()
    {
        return this.id;
    }

};

