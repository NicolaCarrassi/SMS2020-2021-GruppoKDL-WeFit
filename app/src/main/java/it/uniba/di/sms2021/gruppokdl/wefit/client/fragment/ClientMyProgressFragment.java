package it.uniba.di.sms2021.gruppodkl.wefit.fragment.client;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.jjoe64.graphview.GraphView;

import java.util.Date;
import java.util.List;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.client.ClientProgressContract;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;
import it.uniba.di.sms2021.gruppodkl.wefit.presenter.client.ClientProgressPresenter;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.GraphSettings;


public class ClientMyProgressFragment extends Fragment implements ClientProgressContract.View {

    public static final String TAG = ClientHomeFragment.class.getSimpleName();
    private static final String CLIENT_MAIL = "clientMail";

    private View mView;
    private String mClientMail;
    private TextView mClientInitialWeight;
    private TextView mClientCurrentWeight;
    private GraphView mWeightGraph;
    private ClientProgressContract.Presenter mPresenter;

    public ClientMyProgressFragment(){
        //
    }

    public static ClientMyProgressFragment newInstance(String clientMail){
        ClientMyProgressFragment fragment = new ClientMyProgressFragment();
        Bundle args = new Bundle();
        args.putString(CLIENT_MAIL, clientMail);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.findUserData(mClientMail);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.client_progress_frament, container, false);
        mView = layout;
        mPresenter = new ClientProgressPresenter(this);

        assert getActivity() != null;
        mClientMail = ((WeFitApplication)getActivity().getApplicationContext()).getUser().email;
        bind();
        return layout;
    }

    private void bind(){
        if(getActivity() instanceof WeFitApplication.CallbackOperations){
            WeFitApplication.CallbackOperations act = (WeFitApplication.CallbackOperations) getActivity();
            ((WeFitApplication) getActivity().getApplicationContext()).setToolbar(mView,act);
        }
        mClientInitialWeight = mView.findViewById(R.id.initial_weight);
        mClientCurrentWeight = mView.findViewById(R.id.current_weight);
        mWeightGraph = mView.findViewById(R.id.graph);

    }


    @Override
    public void onFailure() {
        Toast.makeText(getActivity(), R.string.error_data_missing, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClientDataReceived(List<Float> weightList, List<Date> dateList) {

        String clientInitialWeight = Float.toString(weightList.get(0)) ;
        assert getActivity() != null;
        String clientCurrentWeight = Float.toString(((Client) ((WeFitApplication) getActivity().getApplicationContext()).getUser()).weight);


        mClientInitialWeight.setText(clientInitialWeight);
        mClientCurrentWeight.setText(clientCurrentWeight);

        GraphSettings.graphSettings(mWeightGraph, dateList, weightList);
    }
}