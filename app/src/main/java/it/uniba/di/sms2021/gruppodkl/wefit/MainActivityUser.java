package it.uniba.di.sms2021.gruppodkl.wefit;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import it.uniba.di.sms2021.gruppodkl.wefit.fragment.CoachProfileFragment;
import it.uniba.di.sms2021.gruppodkl.wefit.fragment.DietFragment;
import it.uniba.di.sms2021.gruppodkl.wefit.fragment.HomeFragmentUser;

import it.uniba.di.sms2021.gruppodkl.wefit.fragment.NotificationsFragment;
import it.uniba.di.sms2021.gruppodkl.wefit.fragment.ProfileFragment;
import it.uniba.di.sms2021.gruppodkl.wefit.fragment.ProgressFragment;
import it.uniba.di.sms2021.gruppodkl.wefit.fragment.SettingsFragment;
import it.uniba.di.sms2021.gruppodkl.wefit.fragment.TermsFragment;
import it.uniba.di.sms2021.gruppodkl.wefit.fragment.TrainingFragment;

public class MainActivityUser extends AppCompatActivity {

    private BottomNavigationView mBottomNavigation;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null){
            final HomeFragmentUser homeFragmentUser = new HomeFragmentUser();
            getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point, homeFragmentUser).commit();
        }

        bind();
        setListener();
        setNavigationView();

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
                        final HomeFragmentUser homeFragmentUser = new HomeFragmentUser();
                        getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point, homeFragmentUser).commit();
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

    private void setNavigationView(){
        mDrawer = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.navigation);
        mNavigationView.inflateMenu(R.menu.menu_drawer_cliente);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                showFragment(item);
                Log.d("DRAWER","Selezionato nel drawer: " + item);
                mDrawer.closeDrawer(mNavigationView);
                return false;
            }
        });
    }

    private void showFragment(final MenuItem item){
        final int itemId = item.getItemId();
        Fragment nextFragment = new Fragment();

        switch(itemId){
            case R.id.profile_item:
                nextFragment = new ProfileFragment();
                break;
            case R.id.coach_item:
                nextFragment = new CoachProfileFragment();
                break;
            case R.id.notifications_item:
                nextFragment = new NotificationsFragment();
                break;
            case R.id.progress_item:
                nextFragment = new ProgressFragment();
                break;
            case R.id.settings:
                nextFragment = new SettingsFragment();
                break;
            case R.id.terms_item:
                nextFragment = new TermsFragment();
                break;
            case R.id.logout_item:
                //TODO
                break;
            default:
                throw new IllegalArgumentException("Nessun Fragment per item selezionato");
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point, nextFragment).commit();

    }

}