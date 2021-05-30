package it.uniba.di.sms2021.gruppodkl.wefit.client;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import it.uniba.di.sms2021.gruppodkl.wefit.LoginActivity;
import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.SettingsActivity;
import it.uniba.di.sms2021.gruppodkl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppodkl.wefit.fragment.client.ClientAddFragment;
import it.uniba.di.sms2021.gruppodkl.wefit.fragment.client.ClientMyCoachFragment;
import it.uniba.di.sms2021.gruppodkl.wefit.fragment.client.ClientDietFragment;
import it.uniba.di.sms2021.gruppodkl.wefit.fragment.client.ClientHomeFragment;
import it.uniba.di.sms2021.gruppodkl.wefit.fragment.NotificationsFragment;
import it.uniba.di.sms2021.gruppodkl.wefit.fragment.client.ClientMyProfileFragment;
import it.uniba.di.sms2021.gruppodkl.wefit.fragment.client.ClientMyProgressFragment;
import it.uniba.di.sms2021.gruppodkl.wefit.fragment.TermsFragment;
import it.uniba.di.sms2021.gruppodkl.wefit.fragment.client.ClientMyTrainingFragment;

public class ClientMainActivity extends AppCompatActivity implements WeFitApplication.OpenDrawer, ClientMyProfileFragment.ProfileFragmentActivity {



    private BottomNavigationView mBottomNavigation;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null){
            final ClientHomeFragment clientHomeFragment = new ClientHomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point, clientHomeFragment).commit();
        }

        bind();
        setListener();
        setNavigationView();

    }

    private void bind() {
        mBottomNavigation = findViewById(R.id.bottom_navigation);
        mBottomNavigation.inflateMenu(R.menu.client_bottom_navigation_menu);
    }

    private void setListener(){
        mBottomNavigation.setOnNavigationItemSelectedListener(item -> {
            boolean res;
            switch(item.getItemId()) {
                case R.id.home:
                    final ClientHomeFragment clientHomeFragment = new ClientHomeFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point, clientHomeFragment, ClientHomeFragment.TAG).commit();
                    res =  true;
                    break;
                case R.id.training:
                    final ClientMyTrainingFragment clientMyTrainingFragment = new ClientMyTrainingFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point, clientMyTrainingFragment, ClientMyTrainingFragment.TAG).commit();
                    res =  true;
                    break;
                case R.id.add:
                    final ClientAddFragment clientAddFragment = new ClientAddFragment();
                    clientAddFragment.show(getSupportFragmentManager(), ClientAddFragment.TAG);
                    res =  true;
                    break;
                case R.id.diet:
                    final ClientDietFragment clientDietFragment = new ClientDietFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point, clientDietFragment, ClientDietFragment.TAG).commit();
                    res =  true;
                    break;
                default:
                    res=false;
            }
            return res;
        });

    }

    private void setNavigationView(){
        mDrawer = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.navigation);
        mNavigationView.inflateMenu(R.menu.client_menu_drawer);
        mNavigationView.setNavigationItemSelectedListener(item -> {
            showFragment(item);
            Log.d("DRAWER","Selezionato nel drawer: " + item);
            mDrawer.closeDrawer(mNavigationView);
            return false;
        });
    }

    private void showFragment(final MenuItem item){
        Fragment nextFragment = new Fragment();
        Intent intent;
        String tag = "SETTINGS";
        final int itemId = item.getItemId();
        boolean transactionNeeded = true;


        switch(itemId){
            case R.id.profile_item:
                nextFragment = new ClientMyProfileFragment();
                tag = ClientMyProfileFragment.TAG;
                break;
            case R.id.coach_item:
                nextFragment = new ClientMyCoachFragment();
                tag = ClientMyCoachFragment.TAG;
                break;
            case R.id.notifications_item:
                nextFragment = new NotificationsFragment();
                tag = NotificationsFragment.TAG;
                break;
            case R.id.progress_item:
                nextFragment = new ClientMyProgressFragment();
                tag = ClientMyProgressFragment.TAG;
                break;
            case R.id.settings_item:
                transactionNeeded = false;
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;

            case R.id.terms_item:
                nextFragment = new TermsFragment();
                tag = TermsFragment.TAG;
                break;

            case R.id.logout_item:
                transactionNeeded = false;
                FirebaseAuth.getInstance().signOut();
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;

            default:
                throw new IllegalArgumentException("Nessun Fragment per item selezionato");
        }

        if(transactionNeeded)
            getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point, nextFragment, tag).commit();

    }


    @Override
    public void openDrw() {
        mDrawer.openDrawer(GravityCompat.END);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_RECEIVED_CODE && resultCode == RESULT_OK
        && data != null && data.getData() != null){

            ClientMyProfileFragment clientMyProfileFragment = (ClientMyProfileFragment) getSupportFragmentManager().findFragmentByTag(ClientMyProfileFragment.TAG);
            if(clientMyProfileFragment != null && clientMyProfileFragment.isVisible()){
                clientMyProfileFragment.onImageReveived(data.getData());
            }
        }
    }



    @Override
    public void changeImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, ClientMyProfileFragment.ProfileFragmentActivity.IMAGE_RECEIVED_CODE);
    }


}