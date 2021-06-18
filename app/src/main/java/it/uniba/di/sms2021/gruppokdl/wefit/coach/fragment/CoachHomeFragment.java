package it.uniba.di.sms2021.gruppokdl.wefit.coach.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import it.uniba.di.sms2021.gruppokdl.wefit.R;
import it.uniba.di.sms2021.gruppokdl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppokdl.wefit.coach.CoachNFCActivity;
import it.uniba.di.sms2021.gruppokdl.wefit.coach.contract.CoachHomeContract;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppokdl.wefit.model.User;
import it.uniba.di.sms2021.gruppokdl.wefit.coach.presenter.CoachHomePresenter;


public class CoachHomeFragment extends Fragment implements User.MyImageBitmapCallback, CoachHomeContract.View {

    public static final String TAG = CoachHomeFragment.class.getSimpleName();

    private View mView;

    private WeFitApplication.CallbackOperations mActivity;
    private Coach mCoach;
    private ImageView mImageView;
    private CardView mRequestsTab;
    private TextView mFollowerRequestTextView;
    private CoachHomeContract.Presenter mPresenter;
    private CardView mNFCCard;

    public CoachHomeFragment(){

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof WeFitApplication.CallbackOperations)
            mActivity = (WeFitApplication.CallbackOperations) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View layout =  inflater.inflate(R.layout.coach_home_fragment, container, false);

        assert getActivity() != null;
        mCoach = (Coach) ((WeFitApplication) getActivity().getApplicationContext()).getUser();
        mView = layout;
        bind();
        setListeners();
        mPresenter = new CoachHomePresenter(this);

        return layout;
    }

    /**
     * Il metodo permette di associare gli elementi della view ad oggetti
     *
     */
    private void bind(){
        String temp;

        assert getActivity() != null;
        ((WeFitApplication) getActivity().getApplicationContext()).setToolbar(mView, mActivity);

        mImageView = mView.findViewById(R.id.user_image);
        mRequestsTab = mView.findViewById(R.id.requests_tab);

        TextView textView = mView.findViewById(R.id.hi_user);
        temp = getResources().getString(R.string.hi_user_string)+ " "+ mCoach.fullName.split(" ")[0]+ " !";
        textView.setText(temp);

        mNFCCard = mView.findViewById(R.id.nfc_tab);
        mNFCCard.setOnClickListener(v -> openNFCActivity());

        mFollowerRequestTextView = mView.findViewById(R.id.follower_request_textview);

        if(mCoach.image != null)
            if(!mCoach.isBitmapImageAvailable()) {
                mCoach.createImageBitmap(this);
                ((WeFitApplication) getActivity().getApplicationContext()).startProgress(mView);
            }else
                mImageView.setImageBitmap(mCoach.getImageBitmap());

    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.loadNumberRequest(mCoach);
    }

    /**
     * Il metodo permette di impostare i listeners per gli elementi del
     * layout
     *
     */
    private void setListeners(){
        mImageView.setOnClickListener(v -> {
            final CoachProfileFragment coachProfileFragment = new CoachProfileFragment();

            FragmentActivity activity = getActivity();
            if(activity != null)
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point, coachProfileFragment, CoachProfileFragment.TAG)
                        .addToBackStack(CoachProfileFragment.TAG).commit();
        });

       mRequestsTab.setOnClickListener(v -> {
           final CoachClientsRequestsFragment requestsFragment = new CoachClientsRequestsFragment();

           FragmentActivity activity = getActivity();
           if(activity != null)
               activity.getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point,requestsFragment, CoachClientsRequestsFragment.TAG)
               .addToBackStack(CoachClientsRequestsFragment.TAG).commit();
       });
    }

    @Override
    public void handleCallback() {
        assert getActivity() != null;
        ((WeFitApplication) getActivity().getApplicationContext()).stopProgress(mView);
        mImageView.setImageBitmap(mCoach.getImageBitmap());
    }


    //Dato che zero viene ignorato dai plurals, lo metto a mano
    @Override
    public void onRequestNumberLoaded(int num) {
        Resources res = getResources();
        if(num == 0)
            mFollowerRequestTextView.setText(R.string.zero_client_request);
        else
            mFollowerRequestTextView.setText(res.getQuantityString(R.plurals.numberClientRequest, num, num));
    }

    /**
     * Il metodo apre l'activity di aggiunta cliente tramite NFC
     */

    private void openNFCActivity(){
        Intent intent = new Intent(getActivity(), CoachNFCActivity.class);
        startActivity(intent);
    }
}