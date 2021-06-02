package it.uniba.di.sms2021.gruppodkl.wefit;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;

import it.uniba.di.sms2021.gruppodkl.wefit.client.ClientMainActivity;

public class ClientRequestCoachSentDialog extends Dialog {

    private AnimatedVectorDrawable mSuccessAnimation;
    private MaterialButton mBack;


    public ClientRequestCoachSentDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_request_coach_sent_dialog);
        setCanceledOnTouchOutside(true);
        ImageView mImageView = findViewById(R.id.success_anim);
        mImageView.setBackgroundResource(R.drawable.success_anim);
        mSuccessAnimation = (AnimatedVectorDrawable) mImageView.getBackground();
        mSuccessAnimation.start();

        mBack = findViewById(R.id.back_button);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}
