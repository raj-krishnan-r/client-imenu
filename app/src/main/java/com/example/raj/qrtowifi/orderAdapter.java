package com.example.raj.qrtowifi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class orderAdapter extends ArrayAdapter<orderRecieved>{

    private Context context;
    private TextView foodname,Status,Count;
    View pview,row;


    /* private Socket mSocket;
     {
         try {
             mSocket = IO.socket("http://192.168.43.203:3000");
         } catch (URISyntaxException e) {}
     }*/
    @Override
    public void add(orderRecieved obj)
    {
        ((RealHome)context).recievedOrders.add(obj);
        //fooditemslist.add(obj);
        super.add(obj);
    }
    public orderAdapter(Context context, int textViewResourceId,View parentView){
        super(context,textViewResourceId);
        this.context=context;
        this.pview = parentView;
        //mSocket.connect();



    }
    public orderAdapter(Context context, int textViewResourceId){
        super(context,textViewResourceId);
        this.context=context;
        //mSocket.connect();

    }

    public int getCount(){
        //return this.fooditemslist.size();\
        return ((RealHome)context).recievedOrders.size();
    }

    public orderRecieved getItem(int index)
    {

        return ((RealHome)context).recievedOrders.get(index);
    }

    public View getView(final int position, final View convertView, ViewGroup parent)
    {
        orderRecieved fdob = getItem(position);
        row = convertView;
        //((RealHome)context).orders.add(new itemOrder(1,1));
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.notification_row,parent,false);
        foodname = (TextView) row.findViewById(R.id.itemName);
        Status = (TextView) row.findViewById(R.id.Status);
        Count = (TextView) row.findViewById(R.id.count);

        foodname.setText(fdob.getName());
        Status.setText(fdob.getStatus());
        Count.setText("No's : "+fdob.count);
        //Toast.makeText(getContext(),fdob.getDescriptions(),Toast.LENGTH_SHORT).show();

        return row;

    }



}