package it.uniba.di.sms2021.gruppodkl.wefit.client;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import java.util.HashMap;
import java.util.Map;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.fragment.client.ClientMyCoachFragment;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.Keys;

public class AddFeedbackDialog extends Dialog implements View.OnClickListener {

    public Activity activity;
    public Dialog dialog;
    public Button annulla, conferma;
    public RatingBar ratingBar;
    public EditText feedbackText;

    public AddFeedbackDialog(Activity activity){
        super(activity);

        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.client_add_feedback_dialog);
        setCanceledOnTouchOutside(true);
        annulla = findViewById(R.id.annulla);
        conferma = findViewById(R.id.rating_button);

        annulla.setOnClickListener(this);
        conferma.setOnClickListener(this);

        ratingBar = findViewById(R.id.user_rating);
        feedbackText = findViewById(R.id.feedback_text);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.annulla:
                dismiss();
                break;
            case R.id.rating_button:
                if(ratingBar.getRating() > 0) {
                    if (activity instanceof ClientMyCoachFragment.CoachProfileCallbacks)
                        ((ClientMyCoachFragment.CoachProfileCallbacks) activity).makeRating(getRatingInfo());
                    dismiss();
                }
                break;
            default:
                break;
        }
    }

    /**
     * Prendo le informazioni presenti nel dialog per il rating
     *
     * @return Mappa con informazioni sul rating appena fornito
     */
    private Map<String, Object> getRatingInfo(){
        Map<String, Object> ratingInfo = new HashMap<>();
        String text = feedbackText.getText().toString().trim();

        ratingInfo.put(Keys.RatingInfo.RATE, ratingBar.getRating());
        ratingInfo.put(Keys.RatingInfo.MESSAGE, text);

        return ratingInfo;
    }

}
