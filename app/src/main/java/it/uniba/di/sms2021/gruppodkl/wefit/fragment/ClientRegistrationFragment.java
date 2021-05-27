package it.uniba.di.sms2021.gruppodkl.wefit.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.fragment.ClientRegistrationFragmentContract;
import it.uniba.di.sms2021.gruppodkl.wefit.presenter.fragment.ClientRegistrationFragmentPresenter;


public class ClientRegistrationFragment extends Fragment implements ClientRegistrationFragmentContract.View {

    public interface ClientRegistrationActivityCallbacks{

    }

   private ClientRegistrationFragmentContract.Presenter mPresenter;
   private ClientRegistrationActivityCallbacks mActivity;

   private EditText mHeightEdit;
   private EditText mWeightEdit;
   private RadioGroup mObjectiveRadio;



    public ClientRegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof ClientRegistrationActivityCallbacks){
            mActivity = (ClientRegistrationActivityCallbacks) context;
        }else{
            throw new IllegalStateException("Context must be an instance of ClientRegistrationFragmentContract.Presenter");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout =  inflater.inflate(R.layout.fragment_client_registration, container, false);

        mPresenter = new ClientRegistrationFragmentPresenter(this);
        bind(layout);

        return layout;
    }


    private void bind(View layout){
        mHeightEdit = layout.findViewById(R.id.height_edit_text);
        mWeightEdit = layout.findViewById(R.id.weight_edit_text);
        mObjectiveRadio = layout.findViewById(R.id.radio_objective);
    }

    public ClientRegistrationFragmentContract.Presenter getPresenter(){
        return mPresenter;
    }

}