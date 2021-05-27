package it.uniba.di.sms2021.gruppodkl.wefit;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivityUser extends AppCompatActivity {

    private BottomNavigationView mBottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bind();
        setListener();

    }

    private void bind() {
        mBottomNavigation = findViewById(R.id.bottom_navigation);
        mBottomNavigation.inflateMenu(R.menu.bottom_navigation_menu_user);
    }

    private void setListener(){
        mBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                boolean res;
                switch(item.getItemId()) {
                    case R.id.home:
                        res =  true;
                        break;
                    case R.id.training:
                        res =  true;
                        break;
                    case R.id.add:
                        res =  true;
                        break;
                    case R.id.diet:
                        res =  true;
                        break;
                    default:
                        res=false;
                }
                return res;
            }
        });
    }


}