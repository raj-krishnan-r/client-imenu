package com.example.raj.qrtowifi;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


public class MenuFragment extends Fragment {
    public menuAdapter adap;
    public ExpandableListView elv;
    View v;
    public Context cx;

    public interface TopSectionListener{
        public void createMeme(String top,String bottom);

    }
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);



        Activity activity = getActivity();
        try {
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

        View view = inflater.inflate(R.layout.fragment_menu,container,false);
        elv = (ExpandableListView) view.findViewById(R.id.elv);
        v = view;
        String strtext = getArguments().getString("jsontext");

        try {
            JSONArray mainroot = new JSONArray(strtext);

            int maini=0;

            if(((RealHome)getContext()).fullpack.size()==0) {

                while (maini < mainroot.length()) {

                    JSONObject bund = mainroot.getJSONObject(maini);
                    category tempcat = new category(bund.getString("categorytitle"), 12);
                    JSONArray items = bund.getJSONArray("items");
                    int root = 0;
                    while (root < items.length()) {
                        JSONObject temitem = items.getJSONObject(root);
                        foodItem tempfood = new foodItem(temitem.getInt("itemid"), (float) temitem.getDouble("price"), temitem.getString("name"), temitem.getString("shortdescription"), "");
                        tempcat.items.add(tempfood);
                        root++;

                    }

                    ((RealHome) getContext()).fullpack.add(tempcat);
                    maini++;

                }
            }
            adap = new menuAdapter(getContext(),((RealHome)getContext()).fullpack);
            elv.setAdapter(adap);

            elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPos, int childPos, long id) {

                    Toast.makeText(getContext(),"S",Toast.LENGTH_SHORT).show();
                    return false;
                }
            });


    } catch (JSONException e) {
            e.printStackTrace();
        }
        return  view;

    }}
