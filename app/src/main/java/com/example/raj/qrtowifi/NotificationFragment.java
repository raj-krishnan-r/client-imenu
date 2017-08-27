package com.example.raj.qrtowifi;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;



public class NotificationFragment extends Fragment {

public orderAdapter adap;
    public ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        adap = new orderAdapter(getContext(),R.layout.notification_row);
        lv = (ListView) view.findViewById(R.id.nlv);
        lv.setAdapter(adap);

        int i = 0;

        /*while(i!=(((RealHome)getContext()).recievedOrders.size()))
        {
            int itemid = ((RealHome)getContext()).recievedOrders.get(i).getItemid();
            String Status = ((RealHome)getContext()).recievedOrders.get(i).getStatus();
            String name = ((RealHome)getContext()).recievedOrders.get(i).getName();
            int count = ((RealHome)getContext()).recievedOrders.get(i).getCount();
            adap.add(new orderRecieved(itemid,Status,name,count));
            i++;
        }*/


        return view;

    }

}
