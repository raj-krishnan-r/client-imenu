package com.example.raj.qrtowifi;

public class orderRecieved
{
    int itemid;
    String status;
    String name;
    int count;
    String orderid;
    public orderRecieved(int itemid,String status, String name,int count,String tableid)
    {
        this.itemid=itemid;
        this.status=status;
        this.name=name;
        this.count=count;
        this.orderid=tableid;
    }
    public int getItemid()
    {
        return this.itemid;
    }
    public String getStatus()
    {
        return this.status;
    }
    public String getName()
{
    return this.name;
}
public int getCount()
{
    return this.count;
}
}
