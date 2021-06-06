package it.uniba.di.sms2021.gruppodkl.wefit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.CheckBoxPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;


import it.uniba.di.sms2021.gruppodkl.wefit.utility.LocaleHelper;

public class SettingsActivity extends BaseActivity {

    public interface SettingKeys{
        String DARK = "my_dark_mode";
        String LANGUAGE = "my_language";
        String DARK_MODE_VALUE = "dm_value";
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ImageView mBackImage;
        mBackImage = findViewById(R.id.settings_back_arrow);
        mBackImage.setOnClickListener(v -> onBackPressed());

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    public static class SettingsFragment extends PreferenceFragmentCompat
        implements SharedPreferences.OnSharedPreferenceChangeListener{

        private SharedPreferences mSharedPref;


        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            mSharedPref = getActivity().getSharedPreferences("settings", MODE_PRIVATE);


            CheckBoxPreference mDarkModePref = findPreference(SettingKeys.DARK);

            mDarkModePref.setOnPreferenceChangeListener((preference, newValue) -> {
                setDarkMode(newValue);
                return true;
            });
        }

        private void setDarkMode(Object val){
            boolean res = (Boolean) val;
            int mode = res ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
            SharedPreferences.Editor mEditor = mSharedPref.edit();

            if(res) {
                AppCompatDelegate.setDefaultNightMode(mode);
                mEditor.putInt(SettingKeys.DARK_MODE_VALUE, AppCompatDelegate.MODE_NIGHT_YES);
            }else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                mEditor.putInt(SettingKeys.DARK_MODE_VALUE, AppCompatDelegate.MODE_NIGHT_NO);
            }

            mEditor.putBoolean(SettingKeys.DARK, res);
            mEditor.apply();
        }


        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (SettingKeys.LANGUAGE.equals(key)) {
                LocaleHelper.setLocale(getContext(), PreferenceManager.getDefaultSharedPreferences(getContext()).getString(key, ""));
                Intent intent = getActivity().getBaseContext().getPackageManager().getLaunchIntentForPackage(
                        getActivity().getBaseContext().getPackageName() );
                intent .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }
    }

}