package com.example.raj.qrtowifi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity implements TopSectionFragment.TopSectionListener {

    private FragmentTabHost mTabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();

        String jsonresult = intent.getStringExtra("jsonresult");
        TopSectionFragment tfrag = (TopSectionFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        tfrag.setJSON(jsonresult,getApplicationContext());

    }
    @Override
    public void createMeme(String top, String bottom) {

    }


}
