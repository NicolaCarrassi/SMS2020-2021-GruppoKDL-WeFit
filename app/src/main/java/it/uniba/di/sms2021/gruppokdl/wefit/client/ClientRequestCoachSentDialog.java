package it.uniba.di.sms2021.gruppodkl.wefit.client;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.WeFitApplication.CallbackOperations;

import com.google.android.material.button.MaterialButton;


public class ClientRequestCoachSentDialog extends Dialog {

    private CallbackOperations mActivity;



    public ClientRequestCoachSentDialog(Context context) {
        super(context);

        if(context instanceof CallbackOperations)
            mActivity = (CallbackOperations) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_request_coach_sent_dialog);
        setCanceledOnTouchOutside(true);
        ImageView mImageView = findViewById(R.id.success_anim);
        mImageView.setBackgroundResource(R.drawable.success_anim);
        AnimatedVectorDrawable mSuccessAnimation = (AnimatedVectorDrawable) mImageView.getBackground();
        mSuccessAnimation.start();

        MaterialButton mBack = findViewById(R.id.back_button);
        mBack.setOnClickListener(v -> {
            mActivity.goHome();
            dismiss();
        });
    }

}
