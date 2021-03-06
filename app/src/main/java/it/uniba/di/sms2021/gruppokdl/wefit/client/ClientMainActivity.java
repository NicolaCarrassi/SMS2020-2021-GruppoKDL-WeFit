package it.uniba.di.sms2021.gruppokdl.wefit.client;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import it.uniba.di.sms2021.gruppokdl.wefit.BaseActivity;
import it.uniba.di.sms2021.gruppokdl.wefit.LoginActivity;
import it.uniba.di.sms2021.gruppokdl.wefit.R;
import it.uniba.di.sms2021.gruppokdl.wefit.SettingsActivity;
import it.uniba.di.sms2021.gruppokdl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppokdl.wefit.client.contract.ClientMainActivityContract;
import it.uniba.di.sms2021.gruppokdl.wefit.client.fragment.ClientAddFragment;
import it.uniba.di.sms2021.gruppokdl.wefit.client.fragment.ClientDietFragment;
import it.uniba.di.sms2021.gruppokdl.wefit.client.fragment.ClientHomeFragment;
import it.uniba.di.sms2021.gruppokdl.wefit.client.fragment.ClientMyCoachFragment;
import it.uniba.di.sms2021.gruppokdl.wefit.client.fragment.ClientMyProfileFragment;
import it.uniba.di.sms2021.gruppokdl.wefit.client.fragment.ClientMyProgressFragment;
import it.uniba.di.sms2021.gruppokdl.wefit.client.fragment.ClientMyTrainingFragment;
import it.uniba.di.sms2021.gruppokdl.wefit.client.fragment.ClientRunFragment;
import it.uniba.di.sms2021.gruppokdl.wefit.client.presenter.ClientMainActivityPresenter;
import it.uniba.di.sms2021.gruppokdl.wefit.fragment.TermsFragment;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Client;
import it.uniba.di.sms2021.gruppokdl.wefit.model.User;
import it.uniba.di.sms2021.gruppokdl.wefit.utility.Keys;

public class ClientMainActivity extends BaseActivity implements WeFitApplication.CallbackOperations,
        ClientMyProfileFragment.ProfileFragmentActivity, ClientMyCoachFragment.CoachProfileCallbacks,
        ClientAddFragment.BottomNavigationSelector, ClientMainActivityContract.View {

    private static final String NFC_COACH_REQUEST = "nfc_coach";

    private BottomNavigationView mBottomNavigation;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawer;

    private ClientMainActivityContract.Presenter mPresenter;
    private String mNfcCoachMail = null;
    private String mClientMail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null){
            final ClientHomeFragment clientHomeFragment = new ClientHomeFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.anchor_point, clientHomeFragment, ClientHomeFragment.TAG)
                    .addToBackStack(ClientHomeFragment.TAG).commit();
        }

        mPresenter = new ClientMainActivityPresenter(this);
        bind();
        setListener();
        setNavigationView();
    }


    @Override
    protected void onStart() {
        super.onStart();

        if(mNfcCoachMail != null){
           makeAlert();
        }
    }

    /**
     * Il metodo permette di associare gli elementi della view
     * ad oggetti
     */
    private void bind() {
        mBottomNavigation = findViewById(R.id.bottom_navigation);
        mBottomNavigation.inflateMenu(R.menu.client_bottom_navigation_menu);

        mClientMail = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();

        if(getIntent().getStringExtra(NFC_COACH_REQUEST) != null)
            mNfcCoachMail = getIntent().getStringExtra(NFC_COACH_REQUEST);
    }


    /**
     * Il metodo permette di settare i listeners collegati
     * ai vari eventi
     */
    private void setListener(){
        mBottomNavigation.setOnNavigationItemSelectedListener(item -> {
            boolean res;
            switch(item.getItemId()) {
                case R.id.home:
                    final ClientHomeFragment clientHomeFragment = new ClientHomeFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point, clientHomeFragment, ClientHomeFragment.TAG).addToBackStack(ClientHomeFragment.TAG).commit();
                    res =  true;
                    break;
                case R.id.training:
                    final ClientMyTrainingFragment clientMyTrainingFragment = new ClientMyTrainingFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point, clientMyTrainingFragment, ClientMyTrainingFragment.TAG).addToBackStack(ClientMyTrainingFragment.TAG).commit();
                    res =  true;
                    break;
                case R.id.add:
                    final ClientAddFragment clientAddFragment = new ClientAddFragment();
                    clientAddFragment.show(getSupportFragmentManager(), ClientAddFragment.TAG);
                    res =  true;
                    break;
                case R.id.diet:
                    final ClientDietFragment clientDietFragment = new ClientDietFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point, clientDietFragment, ClientDietFragment.TAG).addToBackStack(ClientDietFragment.TAG).commit();
                    res =  true;
                    break;
                case R.id.run:
                    final ClientRunFragment clientRunFragment = new ClientRunFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point, clientRunFragment, ClientRunFragment.TAG).addToBackStack(ClientRunFragment.TAG).commit();
                    res =  true;
                    break;
                default:
                    res=false;
            }
            return res;
        });

    }

    /**
     * Il seguente metodo permette di effettuare il binding della navigation view
     */
    private void setNavigationView(){
        mDrawer = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.navigation);
        mNavigationView.inflateMenu(R.menu.client_menu_drawer);
        mNavigationView.setNavigationItemSelectedListener(item -> {
            showFragment(item);
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
                Client client = (Client) ((WeFitApplication)getApplicationContext()).getUser();
                nextFragment = ClientMyCoachFragment.newInstance(true, client.coach);
                tag = ClientMyCoachFragment.TAG;
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
                finish();
                break;

            default:
                throw new IllegalArgumentException("Nessun Fragment per item selezionato");
        }

        if(transactionNeeded)
            getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point, nextFragment, tag).addToBackStack(tag).commit();

    }


    @Override
    public void openDrw() {
        mDrawer.openDrawer(GravityCompat.END);
    }



    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        for (Fragment frag : fm.getFragments()) {
            if (frag.isVisible()) {
                FragmentManager childFm = frag.getChildFragmentManager();
                if (childFm.getBackStackEntryCount() > 1){
                    String tag = childFm.getBackStackEntryAt(childFm.getBackStackEntryCount()-2).getName();
                    if(frag instanceof ClientDietFragment && tag != null)
                        ((ClientDietFragment) frag).changeTabItem(tag);

                    childFm.popBackStack(childFm.getBackStackEntryAt(childFm.getBackStackEntryCount()-1).getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    return;
                }
            }
        }


        // se il numero di figli ?? uguale a 2 allora posso chiudere l'activity in quanto sono all'ultima schermata del backstack
        if(fm.getBackStackEntryCount() > 2) {
            fm.popBackStackImmediate(fm.getBackStackEntryAt(fm.getBackStackEntryCount() - 1).getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            selectBottomNavigationItem();
        } else if((fm.getBackStackEntryCount() == 2 && Objects.equals(fm.getBackStackEntryAt(0).getName(), ClientHomeFragment.TAG) &&
                Objects.equals(fm.getBackStackEntryAt(1).getName(), fm.getBackStackEntryAt(0).getName()))
                || fm.getBackStackEntryCount() <= 1){
            finish();
        } else {
            super.onBackPressed();
            selectBottomNavigationItem();
        }
        Log.d(this.getClass().getSimpleName(), "On back pressed ok");

    }


    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void goHome() {
        ClientHomeFragment clientHomeFragment = new ClientHomeFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point, clientHomeFragment,ClientHomeFragment.TAG)
                .addToBackStack(ClientHomeFragment.TAG).commit();

        mBottomNavigation.setSelectedItemId(R.id.home);
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

    @Override
    public void makeRating(Map<String, Object> map){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        User user = ((WeFitApplication) getApplicationContext()).getUser();
        map.put(Keys.RatingInfo.CLIENT, user.email);
        map.put(Keys.RatingInfo.CLIENT_NAME, user.fullName);
        map.put(Keys.RatingInfo.DATE,sdf.format(date));

        ClientMyCoachFragment coachFragment = (ClientMyCoachFragment) getSupportFragmentManager().findFragmentByTag(ClientMyCoachFragment.TAG);
        if(coachFragment!= null)
            coachFragment.handleFeedback(map);
    }


    @Override
    public void selectBottomNavigationItem() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.anchor_point);

        if(f instanceof ClientHomeFragment){
            mBottomNavigation.setSelectedItemId(R.id.home);
        } else if (f instanceof ClientDietFragment){
            mBottomNavigation.setSelectedItemId(R.id.diet);
        } else if (f instanceof ClientMyTrainingFragment){
            mBottomNavigation.setSelectedItemId(R.id.training);
        }
    }


    /**
     * Il seguente metodo permette di creare l'alert necessario
     * per la conferma dell'associazione con il coach tramite NFC
     */
    private void makeAlert(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.trained_by_question) + " " + mNfcCoachMail + " ?")
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), (dialog, id) -> mPresenter.addCoachFromNFC(mClientMail,mNfcCoachMail))
                .setNegativeButton(getString(R.string.no), (dialog, id) -> {
                    mNfcCoachMail = null;
                    dialog.cancel();
                    Toast.makeText(getApplicationContext(), getString(R.string.pairing_cancelled),Toast.LENGTH_SHORT).show();
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onSuccess() {
        ((Client)((WeFitApplication)getApplicationContext()).getUser()).coach = mNfcCoachMail;
        Toast.makeText(this, getString(R.string.pairing_successful), Toast.LENGTH_SHORT).show();
        mNfcCoachMail = null;
    }
}