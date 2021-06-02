package it.uniba.di.sms2021.gruppodkl.wefit.fragment.client;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Map;

import it.uniba.di.sms2021.gruppodkl.wefit.AddFeedbackDialog;
import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.client.ClientMyCoachContract;
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

    private static final String HAS_SENT_REQUEST = "hasSentRequest";
    private static final String COACH_MAIL = "coachMail";

    private WeFitApplication.CallbackOperations mActivity;
    private ClientMyCoachContract.Presenter mPresenter;

    private ImageView mCoachProfileImage;
    private TextView mCoachNameTitle;
    private TextView mCoachName;
    private TextView mCoachGender;
    private TextView mCoachMail;
    private TextView mCoachSkills;
    private MaterialButton mFeedbackButton;
    private MaterialButton mLeaveCoachButton;
    private MaterialButton mRequestSubButton;
    private MaterialButton mRemoveRequestButton;
    private RatingBar mRatingBar;

    private Coach mCoach;
    private Client mClient;
    private boolean mHasSentRequest = false;
    private String mCoachToFetch;



    public ClientMyCoachFragment() {
    }

    /**
     * Metodo di factory per creare una istanza del fragment con tutti i parametri necessari
     *
     *
     * @param hasSentRequest Il parametro è true se il cliente ha inviato una richiesta al coach in precedenza
     *                       o è un suo cliente (per essere suo cliente deve aver inviato una richiesta in passato
     *                       la quale è stata accettata dal coach).
     *
     * @param coachMail La mail del profilo del coach che si intende visualizzare.
     *
     * @return A new instance of fragment ClientMyCoachFragment.
     */
    public static ClientMyCoachFragment newInstance(boolean hasSentRequest, String coachMail) {
        ClientMyCoachFragment fragment = new ClientMyCoachFragment();
        Bundle args = new Bundle();
        args.putBoolean(HAS_SENT_REQUEST, hasSentRequest);
        args.putString(COACH_MAIL, coachMail);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof WeFitApplication.CallbackOperations){
            mActivity = (WeFitApplication.CallbackOperations) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            mHasSentRequest = getArguments().getBoolean(HAS_SENT_REQUEST);
            mCoachToFetch = getArguments().getString(COACH_MAIL);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View layout =  inflater.inflate(R.layout.client_my_coach_profile_fragment, container, false);
        mClient = (Client) ((WeFitApplication) getActivity().getApplicationContext()).getUser();
        mPresenter = new ClientMyCoachPresenter(this);
        bind(layout);
        return layout;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
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
        mRequestSubButton = view.findViewById(R.id.btn_coach_subscribe);
        mRemoveRequestButton = view.findViewById(R.id.btn_coach_remove_request);


        if(mHasSentRequest){
            if(mClient.pendingRequests){
                mFeedbackButton.setVisibility(View.GONE);
                mLeaveCoachButton.setVisibility(View.GONE);
                mRemoveRequestButton.setVisibility(View.VISIBLE);
            }
        }else{
            mFeedbackButton.setVisibility(View.GONE);
            mLeaveCoachButton.setVisibility(View.GONE);
            mRequestSubButton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Il metodo permette di impostare listeners per la view
     */
    private void setListeners(){
            mFeedbackButton.setOnClickListener(v -> openFeedbackDialog());
            mLeaveCoachButton.setOnClickListener(v -> leaveCoachDialog());
            mRequestSubButton.setOnClickListener(v -> requestSubToCoach());
            mRemoveRequestButton.setOnClickListener(v -> removeRequestToCoach());
    }


    @Override
    public void onStart() {
        super.onStart();

        if(mHasSentRequest) {
            if (mClient.coach != null)
                mPresenter.getCoachData(mClient.coach);
            else
                onCoachNotFound();
        } else
            mPresenter.getCoachData(mCoachToFetch);
    }


    @Override
    public void onCoachDataReceived(Coach coach) {
        mCoach = coach;
        setListeners();

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
        ClientCoachListFragment fragment = new ClientCoachListFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point,fragment,ClientCoachListFragment.TAG)
                .addToBackStack(null).commit();
    }

    @Override
    public void onCoachRatingStarsObtained(float numStars) {
        mRatingBar.setRating(numStars);
    }

    @Override
    public void requestSentSuccessfully() {
        Toast.makeText(getActivity(), getResources().getString(R.string.request_sent_successuflly), Toast.LENGTH_SHORT).show();
        mRequestSubButton.setVisibility(View.GONE);
        mRemoveRequestButton.setVisibility(View.VISIBLE);
        mRemoveRequestButton.setClickable(true);
    }

    @Override
    public void requestFailed() {
        Toast.makeText(getActivity(), getResources().getString(R.string.error_request), Toast.LENGTH_SHORT).show();
        mRequestSubButton.setClickable(true);
    }

    @Override
    public void handleCallback() {
        mCoachProfileImage.setImageBitmap(mCoach.getImageBitmap());
    }

    public void openFeedbackDialog(){
        AddFeedbackDialog addDialog = new AddFeedbackDialog(getActivity());
        addDialog.show();
    }


    public void handleFeedback(Map<String, Object> map) {
        mPresenter.addFeedback(map, mCoach.email);
    }

    public void leaveCoachDialog(){
        new MaterialAlertDialogBuilder(getContext())
                .setTitle(getResources().getString(R.string.leave_coach_title))
                .setMessage(getResources().getString(R.string.leave_coach_text))
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                .setPositiveButton(R.string.confirm, (dialog, which) ->{
                    mClient.coach = null;
                    mPresenter.leaveCoach(mClient);
                }).show();
    }



    private void requestSubToCoach() {
        mRequestSubButton.setClickable(false);
        mPresenter.sendRequestToCoach(mClient, mCoach);
    }

    private void removeRequestToCoach() {
        mRemoveRequestButton.setClickable(false);

        new AlertDialog.Builder(getActivity()).setTitle(getResources().
                getString(R.string.are_you_sure))
                .setMessage(getResources().getString(R.string.remove_request_message))
                .setNegativeButton(getResources().getString(R.string.no), (dialog, which) -> {
                    mRemoveRequestButton.setClickable(true);
                    dialog.dismiss();
                })
                .setPositiveButton(getResources().getString(R.string.yes), (dialog, which) ->{
                    mPresenter.deleteRequestToCoach(mClient, mCoach);
                    mClient.coach = null;
                    mClient.pendingRequests = false;
                    mRemoveRequestButton.setVisibility(View.GONE);
                    mRequestSubButton.setVisibility(View.VISIBLE);
                    mRequestSubButton.setClickable(true);
                    Toast.makeText(getActivity(),getResources().getString(R.string.request_deleted), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();})
                .show();
    }
}