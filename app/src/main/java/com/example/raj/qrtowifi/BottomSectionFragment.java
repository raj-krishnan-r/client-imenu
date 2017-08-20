package com.example.raj.qrtowifi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BottomSectionFragment extends Fragment {

    private static TextView tv,tv1;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_picture_fragment, container, false);

        tv = (TextView) view.findViewById(R.id.topmemetext);
        tv1 = (TextView) view.findViewById(R.id.bottomemetext);
        return view;

    }
    public void setMemeText(String top,String bottom)
    {
        tv.setText(top);
        tv1.setText(bottom);
    }
}
