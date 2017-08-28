package com.example.raj.qrtowifi;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class RealHome extends FragmentActivity {
    String jsonresult;
    public String tableid;
    int menuid = 0;
    String hostip;
    //DataBank
    public List<itemOrder> orders = new ArrayList<>();
    public List<foodItem> foodit = new ArrayList<>();
    public List<orderRecieved> recievedOrders = new ArrayList<>();
    ArrayList<category> fullpack = new ArrayList<>();

    //Socket Definition
    public Socket mSocket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_home);
        Intent intent = getIntent();
        jsonresult = intent.getStringExtra("jsonresult");
        tableid=intent.getStringExtra("tableid");
        hostip = intent.getStringExtra("hostip");
        try {
            mSocket = IO.socket("http://"+hostip+":3000");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        mSocket.connect();
        mSocket.emit("registerUser",tableid);

        TextView tid = (TextView)findViewById(R.id.tableid);
        tid.setText(tableid);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        MenuFragment tfg;
        tfg = new MenuFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("jsontext", jsonresult);
        tfg.setArguments(bundle);
        transaction.replace(R.id.content,tfg).commit();


         Emitter.Listener orderAcknowledge = new Emitter.Listener() {
             @Override
             public void call(final Object... args) {

                 RealHome.this.runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         try {
                             JSONObject ack = new JSONObject(String.valueOf(args[0]));


                             int i = 0;
                             int  p = 0;
                             String itemname = null;
                             String status = null;

                             while(p<fullpack.size())
                             {
                                 int q =  0;
                                 while(q<fullpack.get(p).items.size())
                                 {
                                     if(fullpack.get(p).items.get(q).getId()==ack.getInt("itemid"))
                                     {
                                         itemname=fullpack.get(p).items.get(q).getName();
                                         break;

                                     }
                                     q++;
                                 }


                                 p++;
                             }
                             while(i!=foodit.size())
                             {
                                 if(foodit.get(i).getId()==ack.getInt("itemid"))
                                 {
                                     itemname=foodit.get(i).getName();
                                 }
                                 i++;
                             }


                             if(ack.getString("status").equals("5min"))
                             {
                                status = "Can be delivered in 5 minutes";
                             }
                             else if(ack.getString("status").equals("10min"))
                             {
                                 status = "Can be delivered in 10 minutes";
                             }
                             else if(ack.getString("status").equals("now"))
                             {
                                 status = "Can be delivered now.";
                             }
                             else
                             {
                                 status = "Order rejected";

                             }
                             Toast.makeText(getApplicationContext(),"Order for "+itemname+" attended (See Notifications).",Toast.LENGTH_SHORT).show();
                             recievedOrders.add(new orderRecieved(ack.getInt("itemid"),status,itemname,ack.getInt("itemcount"),ack.getString("orderid")));



                         } catch (JSONException e) {
                             e.printStackTrace();
                         }



                         //Order Placed Tune
                         Uri defaultRingtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                         MediaPlayer mediaPlayer = new MediaPlayer();

                         try {
                             mediaPlayer.setDataSource(getApplicationContext(), defaultRingtoneUri);
                             mediaPlayer.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
                             mediaPlayer.prepare();
                             mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                                 @Override
                                 public void onCompletion(MediaPlayer mp)
                                 {
                                     mp.release();
                                 }
                             });
                             mediaPlayer.start();
                         } catch (IllegalArgumentException e) {
                             e.printStackTrace();
                         } catch (SecurityException e) {
                             e.printStackTrace();
                         } catch (IllegalStateException e) {
                             e.printStackTrace();
                         } catch (IOException e) {
                             e.printStackTrace();
                         }



                         if(menuid==2) {
                             NotificationFragment nfg;
                             nfg = new NotificationFragment();
                             FragmentManager fragmentManager = getSupportFragmentManager();
                             FragmentTransaction transaction = fragmentManager.beginTransaction();
                             transaction.replace(R.id.content, nfg).commit();
                         }


                     }
                 });
             }

             public void call(final Objects... args) {

             }

        };

        mSocket.on("ackRec", orderAcknowledge);

    }



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            MenuFragment tfg;
            tfg = new MenuFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putString("jsontext", jsonresult);
            tfg.setArguments(bundle);

           // transaction.replace(R.id.content,new TopSectionFragment()).commit();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    menuid=0;
                    transaction.replace(R.id.content,tfg).commit();
                    return true;
                case R.id.navigation_dashboard:
menuid=1;
                    return true;
                case R.id.navigation_notifications:
                    menuid=2;
                    NotificationFragment nf;
                    nf = new NotificationFragment();
                    tfg.setArguments(bundle);
                    transaction.replace(R.id.content,nf).commit();

                    return true;
            }
            return false;
        }

    };


    public void createMeme(String top, String bottom) {

    }



}
