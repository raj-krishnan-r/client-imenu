package com.example.raj.qrtowifi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raj on 28/8/17.
 */

public class category {
    private String title;
    private int categoryid;
    public List<foodItem> items = new ArrayList<>();
    public category(String tit,int cid)
    {
        this.title = tit;
        this.categoryid = cid;
    }
    public String getTitle()
    {
        return this.title;
    }
    public int getCategoryid()
    {
        return this.categoryid;
    }
}
