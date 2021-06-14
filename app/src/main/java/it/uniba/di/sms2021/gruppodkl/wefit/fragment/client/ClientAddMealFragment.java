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
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;

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
    private TextView mNoMoreMealLabel;
    private MaterialButton mBackButton;
    private CircularProgressIndicator mProgressIndicator;

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
        mSendButton.setVisibility(View.GONE);
        mSpinner.setVisibility(View.GONE);

        return layout;
    }

    /**
     * Il metodo permette di associare gli elementi della view ad oggetti
     *
     */
    private void bind(View layout){
        mSpinner = layout.findViewById(R.id.spinner_meal);
        mSendButton = layout.findViewById(R.id.send_button);
        mProgressIndicator = layout.findViewById(R.id.progress_indicator);
        mSendButton.setOnClickListener(v -> registerMeal());
        mNoMealLabel = layout.findViewById(R.id.no_meal_label);
        mNoMoreMealLabel = layout.findViewById(R.id.no_more_meal_label);
        mAddMealPanel = layout.findViewById(R.id.add_meal_panel);
        mAddMealSuccess = layout.findViewById(R.id.add_meal_success);
        ImageView mImageView = layout.findViewById(R.id.success_anim);
        mImageView.setBackgroundResource(R.drawable.success_anim);
        mSuccessAnimation = (AnimatedVectorDrawable) mImageView.getBackground();
        mBackButton = layout.findViewById(R.id.back_home);

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
        mProgressIndicator.setVisibility(View.VISIBLE);

        mPresenter.checkMealsAvailable();
    }

    /**
     * Il metodo permette di registrare un pasto
     */
    private void registerMeal(){
        if(mSpinner.getSelectedItem() != null)
            mPresenter.registerMeal((String) mSpinner.getSelectedItem());
    }

    @Override
    public void mealRegistered() {
        mBackButton.setVisibility(View.VISIBLE);
        mAddMealPanel.setVisibility(View.GONE);
        mAddMealSuccess.setVisibility(View.VISIBLE);
        mSuccessAnimation.start();
    }

    @Override
    public void onFailure() {
        Toast.makeText(getActivity(), getString(R.string.error_general), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setAvailableMealsToRegister(List<String> mealsList, boolean dietNotEmpty) {
        mProgressIndicator.setVisibility(View.GONE);

        if(mealsList.size() > 0) {
            mSendButton.setVisibility(View.VISIBLE);
            mSpinner.setVisibility(View.VISIBLE);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_layout);
            adapter.addAll(mealsList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(adapter);
        } else {
            mBackButton.setVisibility(View.VISIBLE);
            if(dietNotEmpty)
                mNoMoreMealLabel.setVisibility(View.VISIBLE);
            else
                mNoMealLabel.setVisibility(View.VISIBLE);
        }
    }
}