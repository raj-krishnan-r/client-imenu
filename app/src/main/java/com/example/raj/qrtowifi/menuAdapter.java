package com.example.raj.qrtowifi;

import android.content.Context;
import android.nfc.Tag;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.example.raj.qrtowifi.R.layout.row;

/**
 * Created by raj on 28/8/17.
 */

public class menuAdapter extends BaseExpandableListAdapter {
    private Context c;
    private ArrayList<category> category;
    private LayoutInflater inflater;
    private TextView foodname,fooddescription,foodprice,categorytitle;
    private Button ordermore,orderless,order;
    private EditText ordercount;


    public menuAdapter(Context c,ArrayList<category> cat)
    {
        this.c=c;
        this.category = cat;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getGroupCount() {
        return category.size();
    }

    @Override
    public int getChildrenCount(int groupPos) {

        return category.get(groupPos).items.size();
    }

    @Override
    public Object getGroup(int groupPos) {
        return category.get(groupPos);
    }

    @Override
    public Object getChild(int groupPos, int childPos) {


        return category.get(groupPos).items.get(childPos);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPos, boolean isExpanded, View convertView, ViewGroup parent) {

        if(convertView==null)
        {
            convertView = inflater.inflate(R.layout.category,null);
        }
        category ctg = (category) getGroup(groupPos);
        categorytitle = (TextView) convertView.findViewById(R.id.categoryTitle);
        categorytitle.setText(ctg.getTitle());
        return convertView;
    }

    @Override
    public View getChildView(int groupPos, final int childPos, boolean isLastChild, View row, ViewGroup parent) {

        if(row==null) {

            row = inflater.inflate(R.layout.row, null);
        }
            foodname = (TextView) row.findViewById(R.id.food_title);
            //Toast.makeText(getContext(),fdob.getDescriptions(),Toast.LENGTH_SHORT).show();
            fooddescription = (TextView) row.findViewById(R.id.food_desc);
            foodprice = (TextView) row.findViewById(R.id.food_price);
            order=(Button) row.findViewById(R.id.order);
            ordermore=(Button) row.findViewById(R.id.ordermore);
            orderless=(Button) row.findViewById(R.id.orderless);
            ordercount=(EditText) row.findViewById(R.id.ordercount);


        //Tags as String
        order.setTag(String.valueOf(childPos)+","+String.valueOf(groupPos));
        ordermore.setTag(String.valueOf(childPos)+","+String.valueOf(groupPos));
        orderless.setTag(String.valueOf(childPos)+","+String.valueOf(groupPos));
        foodname.setText(((RealHome)c).fullpack.get(groupPos).items.get(childPos).getName());
        ordercount.setText(String.valueOf(((RealHome)c).fullpack.get(groupPos).items.get(childPos).getCount()));
        fooddescription.setText(((RealHome)c).fullpack.get(groupPos).items.get(childPos).getDescriptions());
        foodprice.setText("Rs. "+String.valueOf(((RealHome)c).fullpack.get(groupPos).items.get(childPos).getPrice()) );

        if(((RealHome)c).fullpack.get(groupPos).items.get(childPos).getOrder())
        {
            order.setEnabled(true);
            orderless.setEnabled(true);
        }
        if(((RealHome)c).fullpack.get(groupPos).items.get(childPos).getCount()==0)
        {
            orderless.setEnabled(false);
            order.setEnabled(false);

        }
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view.findViewById(R.id.order);

                String buttonTag = b.getTag().toString();
                String[] btags = buttonTag.split(",");
                int groupP = Integer.parseInt(btags[1]);
                int childP = Integer.parseInt(btags[0]);
                ((RealHome)c).fullpack.get(groupP).items.get(childP).orderid=String.valueOf(System.currentTimeMillis())+((RealHome)c).tableid;


                JSONObject ob = new JSONObject();
                try {
                    ob.put("tableid",((RealHome)c).tableid);
                    ob.put("item",((RealHome)c).fullpack.get(groupP).items.get(childP).getName());
                    ob.put("itemid",((RealHome)c).fullpack.get(groupP).items.get(childP).getId());
                    ob.put("count",((RealHome)c).fullpack.get(groupP).items.get(childP).getCount());
                    ob.put("orderid",((RealHome)c).fullpack.get(groupP).items.get(childP).getOrderid());
                    ob.put("price",((RealHome)c).fullpack.get(groupP).items.get(childP).getPrice());



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ((RealHome)c).mSocket.emit("order",ob.toString());
                Toast.makeText(c, "Order for "+String.valueOf(((RealHome)c).fullpack.get(groupP).items.get(childP).getCount())+" "+((RealHome)c).fullpack.get(groupP).items.get(childP).getName()+" placed.", Toast.LENGTH_SHORT).show();
                //Code to deduct from the Present Cost and add to Confirmed
                float deductFromPresent = (((RealHome)c).fullpack.get(groupP).items.get(childP).getPrice())*(((RealHome)c).fullpack.get(groupP).items.get(childP).getCount());
                ((RealHome)c).presentCost-=deductFromPresent;
                ((RealHome)c).pCost.setText(String.valueOf(((RealHome)c).presentCost));


                ((RealHome)c).fullpack.get(groupP).items.get(childP).setCount(0);
                ((RealHome)c).fullpack.get(groupP).items.get(childP).setOrder(false);

                notifyDataSetChanged();

            }
        });

        ordermore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Button b = (Button) view.findViewById(R.id.ordermore);
                View tempview = (View) b.getParent();
                String buttonTag = b.getTag().toString();
                String[] btags = buttonTag.split(",");
                int groupP = Integer.parseInt(btags[1]);
                int childP = Integer.parseInt(btags[0]);
                ((RealHome)c).fullpack.get(groupP).items.get(childP).setOrder(true);
                Button bb = (Button) tempview.findViewById(R.id.order);
                bb.setEnabled(true);
                //Enable Orderless button
                Button orderle = (Button)tempview.findViewById(R.id.orderless);
                orderle.setEnabled(true);



                ((RealHome)c).fullpack.get(groupP).items.get(childP).setCount(((RealHome)c).fullpack.get(groupP).items.get(childP).getCount()+1);



                ((RealHome)c).presentCost+=((RealHome)c).fullpack.get(groupP).items.get(childP).getPrice();

                ((RealHome)c).pCost.setText(String.valueOf(((RealHome)c).presentCost));


                notifyDataSetChanged();

                ordercount = (EditText) tempview.findViewById(R.id.ordercount);
                ordercount.setText(String.valueOf(((RealHome)c).fullpack.get(groupP).items.get(childP).getCount()));

            }
        });

        orderless.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view.findViewById(R.id.orderless);
                View tempview = (View) b.getParent();
                Button orderButton = (Button) tempview.findViewById(R.id.order);
                String buttonTag = b.getTag().toString();
                String[] btags = buttonTag.split(",");
                int groupP = Integer.parseInt(btags[1]);
                int childP = Integer.parseInt(btags[0]);

                ((RealHome)c).fullpack.get(groupP).items.get(childP).setCount(((RealHome)c).fullpack.get(groupP).items.get(childP).getCount()-1);
                ordercount = (EditText) tempview.findViewById(R.id.ordercount);
                ordercount.setText(String.valueOf(((RealHome)c).fullpack.get(groupP).items.get(childP).getCount()));
                orderButton.setEnabled(false);
                ((RealHome)c).presentCost-=((RealHome)c).fullpack.get(groupP).items.get(childP).getPrice();

                ((RealHome)c).pCost.setText(String.valueOf(((RealHome)c).presentCost));


                    if(((RealHome)c).fullpack.get(groupP).items.get(childP).getCount()==0)
                    {
                        ((RealHome)c).fullpack.get(groupP).items.get(childP).setOrder(false);
                        orderButton.setEnabled(false);

                    }
                    notifyDataSetChanged();
                }


        });




        return row;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
