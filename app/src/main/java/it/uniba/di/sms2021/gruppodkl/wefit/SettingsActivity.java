package it.uniba.di.sms2021.gruppodkl.wefit;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
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

    public interface Keys{
        String DARK = "my_dark_mode";
        String LANGUAGE = "my_language";
        String DARK_MODE_VALUE = "dm_value";
        String LANGUAGE_VALUE = "lang_value";
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
        private SharedPreferences mSharedPref;
        private SharedPreferences.Editor editor;



        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            mSharedPref = getActivity().getSharedPreferences("settings", MODE_PRIVATE);
            editor = mSharedPref.edit();

            mDarkModePref = findPreference(Keys.DARK);
            mLanguagePref = findPreference(Keys.LANGUAGE);

            mDarkModePref.setOnPreferenceChangeListener((preference, newValue) -> {
                setDarkMode(newValue);
                return true;
            });

            if(Locale.getDefault().getDisplayLanguage().equals("en")){
                mLanguagePref.setDefaultValue("en");
            }

            mLanguagePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    changeLanguage(newValue);
                    return true;
                }
            });

        }

        private void setDarkMode(Object val){
            boolean res = (Boolean) val;
            int mode = res ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;


            if(res) {
                AppCompatDelegate.setDefaultNightMode(mode);
                editor.putInt(Keys.DARK_MODE_VALUE, AppCompatDelegate.MODE_NIGHT_YES);
            }else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor.putInt(Keys.DARK_MODE_VALUE, AppCompatDelegate.MODE_NIGHT_NO);
            }

            editor.putBoolean(Keys.DARK, res);
            editor.apply();
        }


        //TODO correggi!
        private void changeLanguage(Object value){
            String lang = (String) value;
            Locale locale;

            if(lang.equals("en")) {
                locale = new Locale("en");
                editor.putString(Keys.LANGUAGE_VALUE, "en");
            }else {
                locale = new Locale("it_IT");
                editor.putString(Keys.LANGUAGE_VALUE, "it_IT");
            }

            Locale.setDefault(locale);

            Configuration config = getActivity().getResources().getConfiguration();
            config.setLocale(locale);
            getActivity().getResources().updateConfiguration(config, getActivity().getResources().getDisplayMetrics());

            editor.apply();
        }
    }



}