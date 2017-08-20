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

import org.json.JSONArray;
import org.json.JSONException;

public class TopSectionFragment extends Fragment {
    public itemAdapter adap;
    public ListView lv;
    View v;



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
                Toast.makeText(getContext(),"",Toast.LENGTH_SHORT).show();
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
        super.onCreate(SavedInstances);
        setRetainInstance(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setRetainInstance(true);

        if(savedInstanceState!=null)
        {
            Toast.makeText(getContext(),"Previous State Exisits",Toast.LENGTH_SHORT).show();

        }


View view = inflater.inflate(R.layout.top_segment_fragment,container,false);
lv = (ListView) view.findViewById(R.id.itemsMenu);
        adap = new itemAdapter(getContext(),R.layout.row);
        v = view;
        String strtext = getArguments().getString("jsontext");
        lv.setAdapter(adap);

        try {
            int position = 0;
            int size = 0;
            JSONArray rj = new JSONArray(strtext);
            while(position<rj.length()) {
                foodItem tempItem = new foodItem(1, 75, rj.getString(position), "Some stuff about the dish.");
                adap.add(tempItem);
                position++;
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }


        return  view;

    }


    public void setJSON(String json,Context context)
    {

    }

}
