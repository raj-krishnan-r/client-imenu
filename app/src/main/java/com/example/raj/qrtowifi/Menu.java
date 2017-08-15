package com.example.raj.qrtowifi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class Menu extends AppCompatActivity {
public itemAdapter adap;
    public  ListView lv;
    public TextView tv_tableid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        lv = (ListView) findViewById(R.id.itemsMenu);
        tv_tableid = (TextView) findViewById(R.id.tableid);


        if(lv==null)
        {
            Toast.makeText(getApplication(),"B",Toast.LENGTH_LONG).show();
        }
        adap = new itemAdapter(getApplicationContext(),R.layout.row);
        lv.setAdapter(adap);
        Intent intent = getIntent();
        String jsonresult = intent.getStringExtra("jsonresult");
        String tableid = String.valueOf(intent.getIntExtra("tableid",0));


        tv_tableid.setText(tableid);

        Toast.makeText(getApplicationContext(),jsonresult,Toast.LENGTH_LONG).show();
        try {
            int position = 0;
            int size = 0;
                JSONArray rj = new JSONArray(jsonresult);

            Toast.makeText(getApplicationContext(),String.valueOf(size),Toast.LENGTH_SHORT).show();
            while(position<rj.length()) {
                foodItem tempItem = new foodItem(1, 75, rj.getString(position), "Some stuff about the dish.");
                adap.add(tempItem);
                position++;
            }
            } catch (JSONException e1) {
            e1.printStackTrace();
        }



    }
}
