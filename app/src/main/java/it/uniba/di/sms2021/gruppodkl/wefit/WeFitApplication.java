package it.uniba.di.sms2021.gruppodkl.wefit;

import android.app.Application;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;


import it.uniba.di.sms2021.gruppodkl.wefit.model.User;

public class WeFitApplication extends Application {

    public interface CallbackOperations{
        /**
         * Il metodo permette di aprire il drawer
         */
        void openDrw();

        /**
         * Il metodo permette di tornare indietro
         */
        void goBack();

        /**
         * Il metodo permette di tornare alla home
         */
        void goHome();
    }

    /**
     * Utente autenticato
     */
    private User user;

    /**
     * Il metodo permette di restituire l'istanza dell'utente autenticato
     *
     * @return User utente autenticato
     */
    public User getUser() {
        return user;
    }

    /**
     * Il metodo permette di impostare l'utente autenticato
     *
     * @param user utente autenticato
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Il metodo permette di impostare la toolbar associata alla view
     * La toolbar deve avere necessariamente id toolbar
     *
     * @param view view che include la toolbar
     * @param activity activity utilizzata per l'impostazione dei metodi di Callback
     */
    public void setToolbar(View view, CallbackOperations activity){
        Toolbar mToolbar = view.findViewById(R.id.toolbar);
        mToolbar.inflateMenu(R.menu.toolbar_menu);
        mToolbar.setNavigationIcon(R.drawable.back_24);
        MenuItem mDrawable = mToolbar.getMenu().findItem(R.id.action_menu);

        mDrawable.setOnMenuItemClickListener(item -> {
            activity.openDrw();
            return false;
        });

        mToolbar.setNavigationOnClickListener(v -> activity.goBack());
    }


    /**
     * Il metodo permette di far partire la progress bar
     *
     * @param view view contenente la progress bar
     */
    public void startProgress(View view){
        view.findViewById(R.id.circular_progress_indicator).setVisibility(View.VISIBLE);
    }


    /**
     * Il metodo permette di stoppare la progress bar
     *
     * @param view view contenente la progress bar
     */
    public void stopProgress(View view){
        view.findViewById(R.id.circular_progress_indicator).setVisibility(View.GONE);
    }



}
