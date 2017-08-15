package com.example.raj.qrtowifi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

class itemAdapter extends ArrayAdapter<foodItem>{

    private List<foodItem> fooditemslist = new ArrayList<foodItem>();
private Context context;
    private TextView foodname;
    private TextView foodprice;
    private TextView fooddescription;

    @Override
    public void add(foodItem obj)
    {
        fooditemslist.add(obj);
        super.add(obj);
    }
    public itemAdapter(Context context, int textViewResourceId){
        super(context,textViewResourceId);
        this.context=context;

    }
    public int getCount(){
        return this.fooditemslist.size();
    }

    public foodItem getItem(int index)
    {
        return this.fooditemslist.get(index);
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        foodItem fdob = getItem(position);
        View row = convertView;

        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.row,parent,false);
        foodname = (TextView) row.findViewById(R.id.food_title);
        //Toast.makeText(getContext(),fdob.getDescriptions(),Toast.LENGTH_SHORT).show();
        fooddescription = (TextView) row.findViewById(R.id.food_desc);
        foodprice = (TextView) row.findViewById(R.id.food_price);

       foodname.setText(fdob.getName());
       fooddescription.setText(fdob.getDescriptions());
        foodprice.setText("$"+String.valueOf(fdob.getPrice()) );
        return row;

    }



}