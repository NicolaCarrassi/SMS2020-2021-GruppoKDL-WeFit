package it.uniba.di.sms2021.gruppodkl.wefit.fragment.coach;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppodkl.wefit.adapter.DietListAdapter;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.coach.CoachMyClientDietListContract;
import it.uniba.di.sms2021.gruppodkl.wefit.presenter.coach.CoachMyClientDietListPresenter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CoachMyClientDietListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CoachMyClientDietListFragment extends Fragment implements CoachMyClientDietListContract.View {


    public static final String TAG = CoachMyClientDietListFragment.class.getSimpleName();

    private static final String CLIENT_MAIL  = "clientMail";
    private static final String CLIENT_NAME = "clientName";

    private CoachMyClientDietListContract.Presenter mPresenter;

    private boolean mTwoPane = false;
    private boolean isSomethingVisibile = false;

    private String mClientMail;
    private String mClienName;

    public CoachMyClientDietListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param clientMail mail del cliente di cui si vuole visualizzare la dieta
     * @param clientName nome del cliente di cui si vuole visualizzare la dieta
     * @return A new instance of fragment CoachMyFragmentDietListFragment.
     */
    public static CoachMyClientDietListFragment newInstance(String clientMail, String clientName) {
        CoachMyClientDietListFragment fragment = new CoachMyClientDietListFragment();
        Bundle args = new Bundle();
        args.putString(CLIENT_MAIL, clientMail);
        args.putString(CLIENT_NAME, clientName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mClientMail = getArguments().getString(CLIENT_MAIL);
            mClienName = getArguments().getString(CLIENT_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.coach_my_client_diet_list_fragment, container, false);
        String title = mClienName.split(" ")[0] + " " + getResources().getString(R.string.diet_caps);
        mPresenter = new CoachMyClientDietListPresenter(this);

        if(view.findViewById(R.id.diet_detail_container) != null)
            mTwoPane = true;
        else if(getActivity() != null && getActivity() instanceof WeFitApplication.CallbackOperations){
            WeFitApplication.CallbackOperations act = (WeFitApplication.CallbackOperations) getActivity();
            ((WeFitApplication) getActivity().getApplicationContext()).setToolbar(view,act);
        }


        TextView mTextClientName = view.findViewById(R.id.diet_client_name);
        mTextClientName.setText(title);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_diet_days);
        assert getActivity() != null;
        recyclerView.setAdapter(new DietListAdapter(getActivity(), mPresenter));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void showDietOfTheDay(String weekDay) {
        CoachMyClientDietSpecificationFragment fragment = CoachMyClientDietSpecificationFragment.newInstance(mClientMail, weekDay);
        int containerID = mTwoPane ? R.id.diet_detail_container : R.id.anchor_point;
        assert getActivity() != null;

        if(mTwoPane){
            if(!isSomethingVisibile) {
                isSomethingVisibile = true;
                getChildFragmentManager().beginTransaction().add(containerID, fragment, CoachMyClientDietSpecificationFragment.TAG)
                        .addToBackStack(CoachMyClientDietSpecificationFragment.TAG).commit();
            } else{
                getChildFragmentManager().beginTransaction().replace(containerID, fragment, CoachMyClientDietSpecificationFragment.TAG)
                        .addToBackStack(CoachMyClientDietSpecificationFragment.TAG).commit();
            }
        } else
            getActivity().getSupportFragmentManager().beginTransaction().replace(containerID, fragment, CoachMyClientDietSpecificationFragment.TAG)
                .addToBackStack(CoachMyClientDietSpecificationFragment.TAG).commit();
    }
}