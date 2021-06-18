package it.uniba.di.sms2021.gruppokdl.wefit;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import it.uniba.di.sms2021.gruppokdl.wefit.utility.LocaleHelper;


public class BaseActivity extends AppCompatActivity {
    private String initialLocale;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialLocale = LocaleHelper.getPersistedLocale(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (initialLocale != null && !initialLocale.equals(LocaleHelper.getPersistedLocale(this))) {
            recreate();
        }
    }

}