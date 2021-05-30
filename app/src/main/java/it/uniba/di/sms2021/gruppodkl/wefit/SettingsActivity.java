package it.uniba.di.sms2021.gruppodkl.wefit;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.CheckBoxPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    interface Keys{
        String DARK = "dark_mode";
        String LANGUAGE = "language";
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


    public static class SettingsFragment extends PreferenceFragmentCompat {

        private CheckBoxPreference mDarkModePref;
        private ListPreference mLanguagePref;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            mDarkModePref = findPreference(Keys.DARK);
            mLanguagePref = findPreference(Keys.LANGUAGE);
            Log.d("AOO", "" + getResources().getConfiguration().locale);
            mLanguagePref.setDefaultValue(getResources().getConfiguration().locale.toLanguageTag());

            mDarkModePref.setChecked(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES);

            mDarkModePref.setOnPreferenceChangeListener((preference, newValue) -> {
                setDarkMode(newValue);
                return true;
            });

            mLanguagePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    changeLanguage(newValue);
                    return true;
                }
            });

        }

        private void setDarkMode(Object setting){
            boolean darkMode = (Boolean) setting;
            int mode = darkMode? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO ;

            AppCompatDelegate.setDefaultNightMode(mode);
        }


        //TODO correggi!
        private void changeLanguage(Object value){
            String lang = (String) value;
            Locale locale;

            if(lang.equals("it_it"))
                locale = new Locale("it");
            else
                locale = new Locale("en");

            Locale.setDefault(locale);

            Configuration config = getActivity().getResources().getConfiguration();
            config.setLocale(locale);
            getActivity().getResources().updateConfiguration(config, getActivity().getResources().getDisplayMetrics());

        }
    }



}