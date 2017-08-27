package com.example.raj.qrtowifi;

/**
 * Created by raj on 20/8/17.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;


public class TopSectionFragment extends Fragment {


    public itemAdapter adap;
    public ListView lv;
    View v;
    public Context cx;


    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.43.203:3000");
        } catch (URISyntaxException e) {}
    }

    TopSectionListener activityCommander;
    public interface TopSectionListener{
        public void createMeme(String top,String bottom);

        }
        @Override
        public void onAttach(Context context)
        {
            super.onAttach(context);



            Activity activity = getActivity();
            try {
                activityCommander = (TopSectionListener) activity;
            }catch(ClassCastException e)
            {
                //throw new ClassCastException(activity.toString());
               // Toast.makeText(getContext(),"",Toast.LENGTH_SHORT).show();
            }


        }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {

            Toast.makeText(getContext(),"Previous State Exisits",Toast.LENGTH_SHORT).show();
            //Restore the fragment's state here
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save the fragment's state here
    }
    @Override
    public void onCreate(Bundle SavedInstances)
    {
        super.onCreate(null);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//Toast.makeText(getContext(),val,Toast.LENGTH_SHORT).show();

        //mSocket.connect();
        setRetainInstance(true);

View view = inflater.inflate(R.layout.top_segment_fragment,container,false);
lv = (ListView) view.findViewById(R.id.itemsMenu);
        adap = new itemAdapter(getContext(),R.layout.row);
        v = view;
        String strtext = getArguments().getString("jsontext");
        lv.setAdapter(adap);
        try {
           JSONArray root = new JSONArray(strtext);
            int rootposition = 0;
            while(rootposition<root.length())
            {
                if(((RealHome)getContext()).foodit.size()==0) {

                    int childposition = 0;
                    while(childposition<root.length())
                    {
                        JSONObject ele = root.getJSONObject(childposition);
                        foodItem tempItem = new foodItem(ele.getInt("itemid"), (float) ele.getDouble("price"), ele.getString("name"), ele.getString("shortdescription"),"");
                        adap.add(tempItem);
                        childposition++;

                    }
                }
                rootposition++;

            }

        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        return  view;

    }

}
