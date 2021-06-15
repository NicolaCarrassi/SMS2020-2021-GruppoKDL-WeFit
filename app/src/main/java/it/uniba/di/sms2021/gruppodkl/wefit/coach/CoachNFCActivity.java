package it.uniba.di.sms2021.gruppodkl.wefit.coach;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import it.uniba.di.sms2021.gruppodkl.wefit.BaseActivity;
import it.uniba.di.sms2021.gruppodkl.wefit.BuildConfig;
import it.uniba.di.sms2021.gruppodkl.wefit.R;

public class CoachNFCActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_nfcactivity);

        NfcAdapter mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if(mNfcAdapter != null){
            if(!mNfcAdapter.isEnabled()){
                AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
                alertbox.setTitle(getString(R.string.nfc_required));
                alertbox.setMessage(R.string.nfc_required_message);
                alertbox.setPositiveButton(getString(R.string.yes), (dialog, which) -> {
                    {
                        Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
                        startActivity(intent);
                    }
                });
                    alertbox.setNegativeButton(getString(R.string.no), (dialog, which) -> {

                    });
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
            }
        }
}
