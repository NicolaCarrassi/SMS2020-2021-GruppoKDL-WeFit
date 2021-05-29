package it.uniba.di.sms2021.gruppodkl.wefit;


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

import it.uniba.di.sms2021.gruppodkl.wefit.fragment.CoachProfileFragment;
import it.uniba.di.sms2021.gruppodkl.wefit.fragment.DietFragment;
import it.uniba.di.sms2021.gruppodkl.wefit.fragment.HomeFragmentUser;
import it.uniba.di.sms2021.gruppodkl.wefit.fragment.NotificationsFragment;
import it.uniba.di.sms2021.gruppodkl.wefit.fragment.ProfileFragment;
import it.uniba.di.sms2021.gruppodkl.wefit.fragment.ProgressFragment;
import it.uniba.di.sms2021.gruppodkl.wefit.fragment.SettingsFragment;
import it.uniba.di.sms2021.gruppodkl.wefit.fragment.TermsFragment;
import it.uniba.di.sms2021.gruppodkl.wefit.fragment.TrainingFragment;

public class MainActivityUser extends AppCompatActivity implements HomeFragmentUser.OpenDrawer, ProfileFragment.ProfileFragmentActivity {



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
        mBottomNavigation.setOnNavigationItemSelectedListener(item -> {
            boolean res;
            switch(item.getItemId()) {
                case R.id.home:
                    final HomeFragmentUser homeFragmentUser = new HomeFragmentUser();
                    getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point, homeFragmentUser, HomeFragmentUser.TAG).commit();
                    res =  true;
                    break;
                case R.id.training:
                    final TrainingFragment trainingFragment = new TrainingFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point,trainingFragment, TrainingFragment.TAG).commit();
                    res =  true;
                    break;
                case R.id.add:
                    res =  true;
                    break;
                case R.id.diet:
                    final DietFragment dietFragment = new DietFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point,dietFragment, DietFragment.TAG).commit();
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
        mNavigationView.inflateMenu(R.menu.menu_drawer_cliente);
        mNavigationView.setNavigationItemSelectedListener(item -> {
            showFragment(item);
            Log.d("DRAWER","Selezionato nel drawer: " + item);
            mDrawer.closeDrawer(mNavigationView);
            return false;
        });
    }

    private void showFragment(final MenuItem item){
        final int itemId = item.getItemId();
        Fragment nextFragment = new Fragment();
        String tag = "SETTINGS";


        switch(itemId){
            case R.id.profile_item:
                nextFragment = new ProfileFragment();
                tag = ProfileFragment.TAG;
                break;
            case R.id.coach_item:
                nextFragment = new CoachProfileFragment();
                tag = CoachProfileFragment.TAG;
                break;
            case R.id.notifications_item:
                nextFragment = new NotificationsFragment();
                tag = NotificationsFragment.TAG;
                break;
            case R.id.progress_item:
                nextFragment = new ProgressFragment();
                tag = ProgressFragment.TAG;
                break;
            case R.id.settings:
                //TODO Sostituisci con activity
                nextFragment = new SettingsFragment();
                break;
            case R.id.terms_item:
                nextFragment = new TermsFragment();
                tag = TermsFragment.TAG;
                break;

            case R.id.logout_item:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;

            default:
                throw new IllegalArgumentException("Nessun Fragment per item selezionato");
        }
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

            ProfileFragment profileFragment = (ProfileFragment) getSupportFragmentManager().findFragmentByTag(ProfileFragment.TAG);
            if(profileFragment != null && profileFragment.isVisible()){
                profileFragment.onImageReveived(data.getData());
            }
        }
    }



    @Override
    public void changeImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, ProfileFragment.ProfileFragmentActivity.IMAGE_RECEIVED_CODE);
    }


}