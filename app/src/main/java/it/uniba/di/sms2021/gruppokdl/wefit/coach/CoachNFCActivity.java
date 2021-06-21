package it.uniba.di.sms2021.gruppokdl.wefit.coach;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.Objects;

import it.uniba.di.sms2021.gruppokdl.wefit.BaseActivity;
import it.uniba.di.sms2021.gruppokdl.wefit.BuildConfig;
import it.uniba.di.sms2021.gruppokdl.wefit.R;

public class CoachNFCActivity extends BaseActivity {

    boolean mWriteMode = false;
    private NfcAdapter mNfcAdapter;
    private PendingIntent mNfcPendingIntent;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_nfcactivity);

        TextView mNfcMessage = findViewById(R.id.nfc_message);
        TextView mNoNfcMessage = findViewById(R.id.no_nfc_message);

        WebView mWebView = findViewById(R.id.gif_nfc);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);

        String file = "file:android_res/drawable/nfc_tutorial.gif";
        mWebView.loadUrl(file);

        MaterialButton mNFCButton = findViewById(R.id.nfc_button);
        ImageView mBackButton = findViewById(R.id.back_butt);
        mBackButton.setOnClickListener(v -> onBackPressed());

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if(mNfcAdapter != null)
            mNFCButton.setOnClickListener(v -> startWriting());
        else{
            mNfcMessage.setVisibility(View.GONE);
            mNFCButton.setVisibility(View.GONE);
            mNoNfcMessage.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Il metodo permette di avviare la procedura necessaria per la scrittura di un tag
     *  nfc
     */
    private void startWriting(){
        if(mNfcAdapter.isEnabled()) {
            mNfcPendingIntent = PendingIntent.getActivity(this, 0,
                    new Intent(this, CoachNFCActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

            enableTagWriteMode();

            alertDialog = new AlertDialog.Builder(this).setTitle(R.string.nfc_registration_title)
                    .setMessage(R.string.nfc_registration_message)
                    .setOnCancelListener(dialog -> disableTagWriteMode())
                    .create();
            alertDialog.show();

        }else{
            AlertDialog.Builder alertBox = new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.nfc_required))
                    .setMessage(R.string.nfc_required_message)
                    .setPositiveButton(getString(R.string.yes), (dialog, which)->{
                        Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
                        startActivity(intent);
                    })
                    .setNegativeButton(getString(R.string.no), (dialog, which)->{});
            alertBox.show();
        }
    }

    /**
     * Il seguente modo permette di attivare la modalità di scrittura del tag
     */
    private void enableTagWriteMode() {
        mWriteMode = true;
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter[] mWriteTagFilters = new IntentFilter[]{tagDetected};
        mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent, mWriteTagFilters, null);
    }

    /**
     * Il metodo permette di disabilitare la modalità di scrittura del tag NFC
     */
    private void disableTagWriteMode() {
        mWriteMode = false;
        mNfcAdapter.disableForegroundDispatch(this);
    }


    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // Modalità di scrittura del tag
        if (mWriteMode && NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            Tag detectedTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String coachMail = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();

            //scrittura del messaggio NDEF
            assert coachMail != null;
            NdefMessage mNdfMessage = new NdefMessage(new NdefRecord[]{
                    NdefRecord.createMime("text/plain", coachMail.getBytes()),
                    NdefRecord.createApplicationRecord(BuildConfig.APPLICATION_ID)});
            if (writeTag(mNdfMessage, detectedTag)) {
                Toast.makeText(this, R.string.nfc_success, Toast.LENGTH_LONG)
                        .show();
                alertDialog.dismiss();
            }
        }
    }

    /**
     * Il seguente metodo permette di effettuare la scrittura scrittura di un messaggio NDEF
     * su un dato tag NFC
     *
     * @param message messaggio NDEF da scrivere
     * @param tag tag su cui scrivere il messaggio
     * @return true in caso di successo, false altrimenti
     */
    public boolean writeTag(NdefMessage message, Tag tag) {
        int size = message.toByteArray().length;
        try {
            Ndef ndef = Ndef.get(tag);
            if (ndef != null) {
                ndef.connect();
                if (!ndef.isWritable()) {
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.tag_not_writable),
                            Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (ndef.getMaxSize() < size) {
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.tag_too_small),
                            Toast.LENGTH_SHORT).show();
                    return false;
                }
                ndef.writeNdefMessage(message);
                return true;
            } else {
                NdefFormatable format = NdefFormatable.get(tag);
                if (format != null) {
                    try {
                        format.connect();
                        format.format(message);
                        return true;
                    } catch (IOException e) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }
    }
}
