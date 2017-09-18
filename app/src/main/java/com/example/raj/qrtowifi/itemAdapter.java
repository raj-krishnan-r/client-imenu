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

class itemAdapter extends ArrayAdapter<foodItem>{

private Context context;
    private TextView foodname;
    private TextView foodprice;
    private TextView fooddescription,ordercount;
    private Button order,ordermore,orderless;
    View pview,row;


   /* private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.43.203:3000");
        } catch (URISyntaxException e) {}
    }*/
    @Override
    public void add(foodItem obj)
    {
        ((RealHome)context).foodit.add(obj);
        //fooditemslist.add(obj);
        super.add(obj);
    }
    public itemAdapter(Context context, int textViewResourceId,View parentView){
        super(context,textViewResourceId);
        this.context=context;
        this.pview = parentView;
        //mSocket.connect();



    }
    public itemAdapter(Context context, int textViewResourceId){
        super(context,textViewResourceId);
        this.context=context;
        //mSocket.connect();

    }

    public int getCount(){
        //return this.fooditemslist.size();\
        return ((RealHome)context).foodit.size();
    }

    public foodItem getItem(int index)
    {
        return ((RealHome)context).foodit.get(index);
    }

    public View getView(final int position, final View convertView, ViewGroup parent)
    {
        foodItem fdob = getItem(position);
        row = convertView;
        ((RealHome)context).orders.add(new itemOrder(1,1));
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
ordercount.setText(String.valueOf(((RealHome)context).foodit.get(position).getCount()));


        order.setTag(position);
        ordermore.setTag(position);
        orderless.setTag(position);
        if(((RealHome)context).foodit.get(position).getOrder())
        {
           order.setEnabled(true);
        }



        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view.findViewById(R.id.order);
                int pos = Integer.parseInt(b.getTag().toString());
                Toast.makeText(getContext(),((RealHome)context).foodit.get(pos).getName()+" Ordered.",Toast.LENGTH_SHORT).show();
                b.setEnabled(false);
                ((RealHome)context).foodit.get(pos).setOrder(true);
                //Set Unique Orderid
                ((RealHome)context).foodit.get(pos).orderid=String.valueOf(System.currentTimeMillis())+((RealHome)getContext()).tableid;

                JSONObject ob = new JSONObject();
                try {
                    ob.put("tableid",((RealHome)context).tableid);
                    ob.put("item",((RealHome)context).foodit.get(pos).getName());
                    ob.put("itemid",((RealHome)context).foodit.get(pos).getId());
                    ob.put("count",((RealHome)context).foodit.get(pos).getCount());
                    ob.put("orderid",((RealHome)context).foodit.get(pos).getOrderid());
                    ob.put("price",((RealHome)context).foodit.get(pos).getPrice());


                } catch (JSONException e) {
                    e.printStackTrace();
                }
               // mSocket.emit("order",ob.toString());

                ((RealHome)context).mSocket.emit("order",ob.toString());

                notifyDataSetChanged();
            }
        });
        ordermore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view.findViewById(R.id.ordermore);
                int pos = Integer.parseInt(b.getTag().toString());
                ((RealHome)context).foodit.get(pos).setOrder(true);
                ordercount.setText(String.valueOf(((RealHome)context).foodit.get(pos).getCount()+1));
                ((RealHome)context).foodit.get(pos).setCount(((RealHome)context).foodit.get(pos).getCount()+1);


                notifyDataSetChanged();

            }
        });

        orderless.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view.findViewById(R.id.orderless);
                int pos = Integer.parseInt(b.getTag().toString());
                if(((RealHome)context).foodit.get(pos).getCount()!=0) {
                    ordercount.setText(String.valueOf(((RealHome)context).foodit.get(pos).getCount() - 1));
                    ((RealHome)context).foodit.get(pos).setCount(((RealHome)context).foodit.get(pos).getCount() - 1);
                    ((RealHome)context).presentCost-=((RealHome)context).foodit.get(pos).getPrice()*((RealHome)context).foodit.get(pos).getCount();
                    ((RealHome)context).pCost.setText(String.valueOf(((RealHome)context).presentCost));


                    if(((RealHome)context).foodit.get(pos).getCount()==0)
                    {
                        ((RealHome)context).foodit.get(pos).setOrder(false);
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