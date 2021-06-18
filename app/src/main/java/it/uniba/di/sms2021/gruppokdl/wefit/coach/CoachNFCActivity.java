package it.uniba.di.sms2021.gruppokdl.wefit.coach;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import it.uniba.di.sms2021.gruppokdl.wefit.BaseActivity;
import it.uniba.di.sms2021.gruppokdl.wefit.BuildConfig;
import it.uniba.di.sms2021.gruppokdl.wefit.R;
import it.uniba.di.sms2021.gruppokdl.wefit.WeFitApplication;

public class CoachNFCActivity extends BaseActivity {

    private WebView mWebView;
    private ImageView mBackButton;
    private TextView mNfcMessage;
    private TextView mNoNfcMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_nfcactivity);

        mNfcMessage = findViewById(R.id.nfc_message);
        mNoNfcMessage = findViewById(R.id.no_nfc_message);

        mWebView = findViewById(R.id.gif_nfc);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String file = "file:android_res/drawable/nfc_tutorial.gif";
        mWebView.loadUrl(file);

        mBackButton = findViewById(R.id.back_butt);
        mBackButton.setOnClickListener(v -> onBackPressed());

        NfcAdapter mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if(mNfcAdapter != null){
            if(!mNfcAdapter.isEnabled()){
                AlertDialog.Builder alertbox = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.nfc_required))
                .setMessage(R.string.nfc_required_message)
                .setPositiveButton(getString(R.string.yes), (dialog, which) -> {
                        Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
                        startActivity(intent);
                })
                .setNegativeButton(getString(R.string.no), (dialog, which) -> {});

                alertbox.show();
            }
            //CREATE MESSAGE NDF
            String coachMail = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();

            assert coachMail != null;
            NdefMessage mNdfMessage = new NdefMessage(new NdefRecord[]{
                    NdefRecord.createMime("text/plain", coachMail.getBytes()),
                    NdefRecord.createApplicationRecord(BuildConfig.APPLICATION_ID)
            });

            mNfcAdapter.setNdefPushMessage(mNdfMessage, this);
            }else{

            mWebView.setVisibility(View.GONE);
            mNfcMessage.setVisibility(View.GONE);
            mNoNfcMessage.setVisibility(View.VISIBLE);
        }
    }
}
