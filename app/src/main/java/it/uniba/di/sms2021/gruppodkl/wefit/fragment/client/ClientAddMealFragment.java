package it.uniba.di.sms2021.gruppodkl.wefit.fragment.client;

import android.app.Activity;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.client.ClientAddMealContract;
import it.uniba.di.sms2021.gruppodkl.wefit.presenter.client.ClientAddMealPresenter;


public class ClientAddMealFragment extends BottomSheetDialogFragment implements ClientAddMealContract.View {

    public static final String TAG = ClientAddMealFragment.class.getSimpleName();

    private Spinner mSpinner;
    private ClientAddMealContract.Presenter mPresenter;
    private MaterialButton mSendButton;
    private View mView;
    private AnimatedVectorDrawable mSuccessAnimation;
    private LinearLayout mAddMealPanel;
    private LinearLayout mAddMealSuccess;
    private TextView mNoMealLabel;

    public ClientAddMealFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.client_add_meal_fragment, container, false);


        assert getActivity() != null;
        String mClientMail = ((WeFitApplication) getActivity().getApplicationContext()).getUser().email;
        assert getActivity() != null;
        Activity activity = getActivity();
        assert activity.getWindow() != null;
        mView = activity.getWindow().getDecorView();
        mPresenter = new ClientAddMealPresenter(this, mClientMail);
        bind(layout);

        return layout;
    }

    private void bind(View layout){
        mSpinner = layout.findViewById(R.id.spinner_meal);
        mSendButton = layout.findViewById(R.id.send_button);

        mSendButton.setOnClickListener(v -> registerMeal());
        mNoMealLabel = layout.findViewById(R.id.no_meal_label);
        mAddMealPanel = layout.findViewById(R.id.add_meal_panel);
        mAddMealSuccess = layout.findViewById(R.id.add_meal_success);
        ImageView mImageView = layout.findViewById(R.id.success_anim);
        mImageView.setBackgroundResource(R.drawable.success_anim);
        mSuccessAnimation = (AnimatedVectorDrawable) mImageView.getBackground();
        MaterialButton mBackButton = layout.findViewById(R.id.back_home);

        mBackButton.setOnClickListener(v -> {
            dismiss();
            assert getActivity() != null;
            ClientAddFragment parent = (ClientAddFragment) getActivity().getSupportFragmentManager().findFragmentByTag(ClientAddFragment.TAG);
            if(parent!=null){
                parent.dismiss();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        assert getActivity() != null;
        ((WeFitApplication)getActivity().getApplicationContext()).startProgress(mView);
        mPresenter.checkMealsAvailable();
    }



    private void registerMeal(){
        if(mSpinner.getSelectedItem() != null)
            mPresenter.registerMeal((String) mSpinner.getSelectedItem());
    }

    @Override
    public void mealRegistered() {

        mAddMealPanel.setVisibility(View.GONE);
        mAddMealSuccess.setVisibility(View.VISIBLE);
        mSuccessAnimation.start();
    }

    @Override
    public void onFailure() {
        //TODO NOTIFICA PROBLEMI NELLA REGISTRAZIONE DEL PASTO
    }

    @Override
    public void setAvailableMealsToRegister(List<String> mealsList, boolean dietNotEmpty) {
        assert getActivity() != null;
        ((WeFitApplication) getActivity().getApplicationContext()).stopProgress(mView);

        if(mealsList.size() > 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_layout);
            adapter.addAll(mealsList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(adapter);
        } else {
            mSpinner.setVisibility(View.GONE);
            mSendButton.setVisibility(View.GONE);
            mNoMealLabel.setVisibility(View.VISIBLE);

//            if(dietNotEmpty){
//                //TODO Notifica che tutti i pasti sono stati aggiunti
//            } else{
//                //TODO Notifica che non è stata fornita la dieta dal coach
//            }

        }
    }
}