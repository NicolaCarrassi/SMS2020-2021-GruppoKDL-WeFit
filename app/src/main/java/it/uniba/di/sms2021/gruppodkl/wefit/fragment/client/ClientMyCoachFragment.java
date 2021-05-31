package it.uniba.di.sms2021.gruppodkl.wefit.fragment.client;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Map;

import it.uniba.di.sms2021.gruppodkl.wefit.AddFeedbackDialog;
import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.fragment.client.ClientMyCoachContract;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppodkl.wefit.model.User;
import it.uniba.di.sms2021.gruppodkl.wefit.presenter.client.ClientMyCoachPresenter;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.Keys;

public class ClientMyCoachFragment extends Fragment implements ClientMyCoachContract.View, User.MyImageBitmapCallback {

    public interface CoachProfileCallbacks{
        void makeRating(Map<String, Object> map);
    }

    public static final String TAG = ClientMyCoachFragment.class.getSimpleName();

    private WeFitApplication.CallbackOperations mActivity;
    private ClientMyCoachContract.Presenter mPresenter;

    private ImageView mCoachProfileImage;
    private TextView mCoachNameTitle;
    private TextView mCoachName;
    private TextView mCoachGender;
    private TextView mCoachMail;
    private TextView mCoachSkills;
    private Button mFeedbackButton;
    private Button mLeaveCoachButton;
    private RatingBar mRatingBar;

    private Client mClient;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof WeFitApplication.CallbackOperations){
            mActivity = (WeFitApplication.CallbackOperations) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    public ClientMyCoachFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View layout =  inflater.inflate(R.layout.client_my_coach_profile_fragment, container, false);
        mPresenter = new ClientMyCoachPresenter(this);
        bind(layout);
        setListeners();
        return layout;
    }

    /**
     * Il metodo permette di effettuare il binding di una view di cui si è fatto l'inflate
     * @param view view di cui si vuole effettuare l'inflate
     */
    private void bind(View view) {
        ((WeFitApplication) getActivity().getApplicationContext()).setToolbar(view, mActivity);
        mCoachProfileImage = view.findViewById(R.id.profile_image_coach);

        mCoachNameTitle = view.findViewById(R.id.coach_name_title);
        mCoachName = view.findViewById(R.id.coach_full_name);
        mCoachGender = view.findViewById(R.id.coach_gender);
        mCoachMail = view.findViewById(R.id.coach_mail);
        mCoachSkills = view.findViewById(R.id.coach_skills);

        mFeedbackButton = view.findViewById(R.id.leave_a_feedback_button);
        mLeaveCoachButton = view.findViewById(R.id.change_coach_button);

        mRatingBar = view.findViewById(R.id.rating_coach);
    }

    /**
     * Il metodo permette di impostare listeners per la view
     */
    private void setListeners(){
        mFeedbackButton.setOnClickListener(v -> openFeedbackDialog());
        mLeaveCoachButton.setOnClickListener(v -> leaveCoachDialog());
    }


    @Override
    public void onStart() {
        super.onStart();

        mClient= (Client) ((WeFitApplication) getActivity().getApplicationContext()).getUser();
        if(mClient.coach != null)
            mPresenter.getCoachData(mClient.coach);
        //else
        //TODO passa alla schermata di richiesta nuovo coach
    }


    @Override
    public void onCoachDataReceived(Coach coach) {
        if (coach.image != null)
            if (!coach.isBitmapImageAvailable())
                coach.createImageBitmap(this);
            else
                mCoachProfileImage.setImageBitmap(coach.getImageBitmap());

        mCoachName.setText(coach.fullName);
        mCoachNameTitle.setText(coach.fullName);

        if(coach.gender.equals(Keys.Gender.MALE))
            mCoachGender.setText(getResources().getString(R.string.male));
        else
            mCoachGender.setText(getResources().getString(R.string.female));

        mCoachMail.setText(coach.email);

        String skills = "";


        if(coach.isPersonalTrainer) {
            skills = getResources().getString(R.string.personal_trainer);

            if(coach.isDietist)
                skills = skills + " | " + getResources().getString(R.string.dietician);
        }else {
            if (coach.isDietist)
                skills = getResources().getString(R.string.dietician);
        }
        mCoachSkills.setText(skills);

        mPresenter.getCoachRatingStars(coach);
    }

    @Override
    public void onCoachNotFound() {
        Log.d("AOO", "Non ha coach");
        //TODO Passa a fragment di richiesta a nuovo coach
    }

    @Override
    public void onCoachRatingStarsObtained(float numStars) {
        mRatingBar.setRating(numStars);
    }

    @Override
    public void handleCallback() {
        mCoachProfileImage.setImageBitmap(mClient.getImageBitmap());
    }

    public void openFeedbackDialog(){
        AddFeedbackDialog addDialog = new AddFeedbackDialog(getActivity());
        addDialog.show();
    }


    public void handleFeedback(Map<String, Object> map) {
        mPresenter.addFeedback(map,mClient.coach);
    }

    public void leaveCoachDialog(){
        new MaterialAlertDialogBuilder(getContext())
                .setTitle(getResources().getString(R.string.leave_coach_title))
                .setMessage(getResources().getString(R.string.leave_coach_text))
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                .setPositiveButton(R.string.confirm, (dialog, which) -> mPresenter.leaveCoach(mClient)).show();
    }
}