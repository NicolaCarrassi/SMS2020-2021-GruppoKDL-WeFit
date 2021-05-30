package it.uniba.di.sms2021.gruppodkl.wefit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.lang.ref.WeakReference;

import it.uniba.di.sms2021.gruppodkl.wefit.client.ClientMainActivity;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.SplashActivityContract;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;
import it.uniba.di.sms2021.gruppodkl.wefit.model.User;
import it.uniba.di.sms2021.gruppodkl.wefit.presenter.SplashActivityPresenter;

public class SplashActivity extends AppCompatActivity  implements SplashActivityContract.View {

    // costanti
    private static final String TAG_LOG = SplashActivity.class.getName();
    private static final String IS_DONE_KEY = "it.kdl.sosa.fra.fraalbguidaibus.key.IS_DONE_KEY";
    private static final String START_TIME_KEY = "it.kdl.sosa.fra.fraalbguidaibus.key.START_TIME_KEY";

    private static final long INTERVALLO_MINIMO = 500L;
    private static final long INTERVALLO_MASSIMO = 1500L;

    private static final int GO_AHEAD_WHAT = 1;


    private long mStartTime = -1L;
    private boolean mIsDone;
    private UiHandler mHandler;
    private SplashActivityContract.Presenter mPresenter;




    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_DONE_KEY, mIsDone);
        outState.putLong(START_TIME_KEY, mStartTime);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.mIsDone = savedInstanceState.getBoolean(IS_DONE_KEY);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        this.mPresenter = new SplashActivityPresenter(this);

        if(savedInstanceState != null)
            this.mStartTime = savedInstanceState.getLong(START_TIME_KEY);

        // inizializzazione dell'mHandler
        mHandler = new UiHandler(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mStartTime == -1L)
            mStartTime = SystemClock.uptimeMillis();

        final Message goAheadMessage = mHandler.obtainMessage(GO_AHEAD_WHAT);
        mHandler.sendMessageAtTime(goAheadMessage, mStartTime + INTERVALLO_MASSIMO);
        Log.d(TAG_LOG, "Messaggio inviato");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    private void goAhead(){
        final Intent intent;
        if(FirebaseAuth.getInstance().getCurrentUser() != null)
            mPresenter.fetchUserData(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        else {
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onSuccess(User user) {
        Intent intent = null;

        ((WeFitApplication) getApplication()).setUser(user);

        if(user instanceof Client)
            intent = new Intent(this, ClientMainActivity.class);
        //TODO Aggiungi caso coach

        if(intent != null) {
            startActivity(intent);
            finish();
        }
    }

    private static class UiHandler extends Handler{
        private final WeakReference<SplashActivity> activityRef;

        public UiHandler(final SplashActivity activityRef){
            this.activityRef = new WeakReference<>(activityRef);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            final SplashActivity activity = this.activityRef.get();
            if(activity == null)
                Log.d(TAG_LOG, "Perso il riferimento");
            else
                switch (msg.what){
                    case GO_AHEAD_WHAT:
                        long tempoPassato = SystemClock.uptimeMillis() - activity.mStartTime;
                        if(tempoPassato >= INTERVALLO_MINIMO && !activity.mIsDone){
                            activity.mIsDone = true;
                            activity.goAhead();
                        }
                        break;
                }
        }
    }
}