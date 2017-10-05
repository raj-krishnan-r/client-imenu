package com.example.raj.qrtowifi;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import com.google.zxing.Result;
import java.util.ArrayList;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
public class QrScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ZXingScannerView mscanner;
    ListView lv;
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
        String encodedresult = sresult;

        final String[] particular = sresult.split(",");


        if (particular[0].equals("imenu")) {
            Toast.makeText(getApplicationContext(), "Wifi SSID : " + particular[1] + "\nWifi Password : " + particular[2] + "\nIP : " + particular[3]+"\nTable Id : "+particular[4], Toast.LENGTH_SHORT).show();

            int tableid = Integer.parseInt(particular[4]);
            new wifi2Ip(getApplicationContext(), tableid,particular[3]).execute(particular[1],particular[2],particular[3]);

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
    boolean order;
    int count;
    String orderid;
    public foodItem(int idd, float pr, String na, String desc,String orderid)
    {
        this.id=idd;
        this.price = pr;
        this.name=na;
        this.descriptions=desc;
        this.orderid=orderid;
        order=false;
        count=0;
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
    public String getOrderid()
    {
        return this.orderid;
    }
    public void setOrder(Boolean t)
    {
        this.order=t;
    }

    public void setCount(int t)
    {
        this.count=t;
    }
    public int getCount()
    {
        return this.count;
    }
    public boolean getOrder()
    {
        return this.order;
    }
};

