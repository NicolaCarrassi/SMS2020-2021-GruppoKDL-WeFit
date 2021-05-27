package it.uniba.di.sms2021.gruppodkl.wefit;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import it.uniba.di.sms2021.gruppodkl.wefit.fragment.DietFragment;
import it.uniba.di.sms2021.gruppodkl.wefit.fragment.HomeFragment;
import it.uniba.di.sms2021.gruppodkl.wefit.fragment.TrainingFragment;

public class MainActivityUser extends AppCompatActivity {

    private BottomNavigationView mBottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null){
            final HomeFragment homeFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point,homeFragment).commit();
        }

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
                        final HomeFragment homeFragment = new HomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point,homeFragment).commit();
                        res =  true;
                        break;
                    case R.id.training:
                        final TrainingFragment trainingFragment = new TrainingFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point,trainingFragment).commit();
                        res =  true;
                        break;
                    case R.id.add:
                        res =  true;
                        break;
                    case R.id.diet:
                        final DietFragment dietFragment = new DietFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point,dietFragment).commit();
                        res =  true;
                        break;
                    default:
                        res=false;
                }
                return res;
            }
        });

        mBottomNavigation.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.home:
                        break;
                    case R.id.training:
                        break;
                    case R.id.add:
                        break;
                    case R.id.diet:
                        break;
                    default:
                        break;
                }
            }
        });
    }


}