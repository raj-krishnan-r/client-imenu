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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

class itemAdapter extends ArrayAdapter<foodItem>{

    private List<foodItem> fooditemslist = new ArrayList<foodItem>();
private Context context;
    private TextView foodname;
    private TextView foodprice;
    private TextView fooddescription,ordercount;
    private Button order,ordermore,orderless;
    View pview,row;


    @Override
    public void add(foodItem obj)
    {
        fooditemslist.add(obj);
        super.add(obj);
    }
    public itemAdapter(Context context, int textViewResourceId,View parentView){
        super(context,textViewResourceId);
        this.context=context;
        this.pview = parentView;

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

    public View getView(final int position, final View convertView, ViewGroup parent)
    {
        foodItem fdob = getItem(position);
        row = convertView;

        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.row,parent,false);
        foodname = (TextView) row.findViewById(R.id.food_title);
        //Toast.makeText(getContext(),fdob.getDescriptions(),Toast.LENGTH_SHORT).show();
        fooddescription = (TextView) row.findViewById(R.id.food_desc);
        foodprice = (TextView) row.findViewById(R.id.food_price);
        order=(Button) row.findViewById(R.id.order);
        ordermore=(Button) row.findViewById(R.id.ordermore);
        orderless=(Button) row.findViewById(R.id.orderless);
        ordercount=(EditText) row.findViewById(R.id.ordercount);
ordercount.setText(String.valueOf(fooditemslist.get(position).getCount()));


        order.setTag(position);
        ordermore.setTag(position);
        orderless.setTag(position);
        if(fooditemslist.get(position).getOrder())
        {
           order.setEnabled(true);
        }



        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view.findViewById(R.id.order);
                int pos = Integer.parseInt(b.getTag().toString());
                Toast.makeText(getContext(),fooditemslist.get(pos).getName()+" Ordered.",Toast.LENGTH_SHORT).show();

                b.setEnabled(false);
                fooditemslist.get(pos).setOrder(true);

                notifyDataSetChanged();
            }
        });
        ordermore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view.findViewById(R.id.ordermore);
                int pos = Integer.parseInt(b.getTag().toString());
                fooditemslist.get(pos).setOrder(true);
                ordercount.setText(String.valueOf(fooditemslist.get(pos).getCount()+1));
                fooditemslist.get(pos).setCount(fooditemslist.get(pos).getCount()+1);


                notifyDataSetChanged();

            }
        });

        orderless.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view.findViewById(R.id.orderless);
                int pos = Integer.parseInt(b.getTag().toString());
                if(fooditemslist.get(pos).getCount()!=0) {
                    ordercount.setText(String.valueOf(fooditemslist.get(pos).getCount() - 1));
                    fooditemslist.get(pos).setCount(fooditemslist.get(pos).getCount() - 1);


                    if(fooditemslist.get(pos).getCount()==0)
                    {
                        fooditemslist.get(pos).setOrder(false);
                    }
                    notifyDataSetChanged();
                }

            }
        });

        foodname.setText(fdob.getName());
       fooddescription.setText(fdob.getDescriptions());
        foodprice.setText("$"+String.valueOf(fdob.getPrice()) );



        return row;

    }



}