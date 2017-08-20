package com.example.raj.qrtowifi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class RealHome extends FragmentActivity {
    String jsonresult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_home);
        Intent intent = getIntent();
        jsonresult = intent.getStringExtra("jsonresult");
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        TopSectionFragment tf = new TopSectionFragment();
        //tf.setJSON(jsonresult.toString(),getApplicationContext());
       // getSupportFragmentManager().beginTransaction().add(R.id.content,tf);



    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            TopSectionFragment tfg;
            tfg = new TopSectionFragment();




           // transaction.replace(R.id.content,new TopSectionFragment()).commit();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    Bundle bundle = new Bundle();
                    bundle.putString("jsontext", jsonresult);
                    tfg.setArguments(bundle);
                    transaction.replace(R.id.content,tfg).commit();
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    Intent intent = new Intent(getApplicationContext(), bill.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }

    };


    public void createMeme(String top, String bottom) {

    }

}
