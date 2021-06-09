package it.uniba.di.sms2021.gruppodkl.wefit.coach;

import android.content.Intent;
import android.os.Bundle;
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
import it.uniba.di.sms2021.gruppodkl.wefit.fragment.NotificationsFragment;
import it.uniba.di.sms2021.gruppodkl.wefit.fragment.TermsFragment;
import it.uniba.di.sms2021.gruppodkl.wefit.fragment.client.ClientMyProfileFragment;
import it.uniba.di.sms2021.gruppodkl.wefit.fragment.coach.CoachClientsFragment;
import it.uniba.di.sms2021.gruppodkl.wefit.fragment.coach.CoachFeedbacksFragment;
import it.uniba.di.sms2021.gruppodkl.wefit.fragment.coach.CoachHomeFragment;
import it.uniba.di.sms2021.gruppodkl.wefit.fragment.coach.CoachProfileFragment;

public class CoachMainActivity extends AppCompatActivity implements WeFitApplication.CallbackOperations,
        CoachProfileFragment.CoachProfileActivity {

    private BottomNavigationView mBottomNavigationView;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bind();

        if(savedInstanceState == null){
            final CoachHomeFragment coachHomeFragment = new CoachHomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point, coachHomeFragment).commit();
            mBottomNavigationView.setSelectedItemId(R.id.home_item);
        }


        setListeners();
    }

    /**
     * Il metodo permette di associare gli elementi della view
     * ad oggetti
     */
    private void bind(){
        mBottomNavigationView = findViewById(R.id.bottom_navigation);
        mBottomNavigationView.inflateMenu(R.menu.coach_bottom_navigation_menu);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.navigation);
        mNavigationView.inflateMenu(R.menu.coach_menu_drawer);
    }

    /**
     * Il metodo permette di settare i listeners collegati
     * ai vari eventi
     */
    private void setListeners(){
        mBottomNavigationView.setOnNavigationItemSelectedListener(this::handleBottomFragments);

        mNavigationView.setNavigationItemSelectedListener(item ->{
            handleBottomFragments(item);
            mDrawerLayout.closeDrawer(mNavigationView);
            return false;
        });
    }


    /**
     * Il metodo permette, dato un MenuItem di passare alla schermata corrispondente
     *
     * @param item MenuItem
     * @return true se il MenuItem rappresenta un elemento di una azione consentita, false altrimenti
     */
    private boolean handleBottomFragments(MenuItem item){
        Fragment fragment = null;
        String tag = null;
        boolean res = true;
        Intent intent;
        int itemSelected = item.getItemId();

        switch (itemSelected){
            case R.id.clients:
                fragment = new CoachClientsFragment();
                tag = CoachClientsFragment.TAG;
                break;
            case R.id.home_item:
                fragment = new CoachHomeFragment();
                tag = CoachHomeFragment.TAG;
                break;
            case R.id.feedbacks:
                fragment = new CoachFeedbacksFragment();
                tag = CoachFeedbacksFragment.TAG;
                break;
            case R.id.profile_item:
                fragment = new CoachProfileFragment();
                tag = CoachProfileFragment.TAG;
                break;
            case R.id.notifications_item:
                fragment = new NotificationsFragment();
                tag = NotificationsFragment.TAG;
                break;
            case R.id.settings_item:
                res = false;
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.terms_item:
                fragment = new TermsFragment();
                tag = TermsFragment.TAG;
                break;
            case R.id.logout_item:
                res = false;
                FirebaseAuth.getInstance().signOut();
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                res = false;
        }

        if(res)
            getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point, fragment, tag).addToBackStack(tag).commit();

        return res;
    }


    @Override
    public void openDrw() {
        mDrawerLayout.openDrawer(GravityCompat.END);
    }


    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void goHome() {
        CoachHomeFragment coachHomeFragment = new CoachHomeFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point, coachHomeFragment, CoachHomeFragment.TAG)
                .addToBackStack(CoachHomeFragment.TAG).commit();

        mBottomNavigationView.setSelectedItemId(R.id.home_item);
    }


    @Override
    public void changeImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, ClientMyProfileFragment.ProfileFragmentActivity.IMAGE_RECEIVED_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case IMAGE_RECEIVED_CODE:
                if(resultCode == RESULT_OK
                        && data != null && data.getData() != null){

                    CoachProfileFragment coachProfileFragment = (CoachProfileFragment) getSupportFragmentManager().findFragmentByTag(CoachProfileFragment.TAG);
                    if(coachProfileFragment != null && coachProfileFragment.isVisible()){
                        coachProfileFragment.onImageReceived(data.getData());
                    }
               }
            break;
            case FILE_RECEIVED_CODE:
                if(resultCode == RESULT_OK && data != null && data.getData() != null) {
                    CoachProfileFragment coachProfileFragment = ((CoachProfileFragment) getSupportFragmentManager().findFragmentByTag(CoachProfileFragment.TAG));

                    if(coachProfileFragment != null && coachProfileFragment.isVisible())
                        coachProfileFragment.onFileReceived(data.getData().toString());
                }
        }
    }

}