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
import it.uniba.di.sms2021.gruppodkl.wefit.adapter.DietListAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CoachMyClientDietListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CoachMyClientDietListFragment extends Fragment {


    public static final String TAG = CoachMyClientDietListFragment.class.getSimpleName();

    private static final String CLIENT_MAIL  = "clientMail";
    private static final String CLIENT_NAME = "clientName";

    private TextView mTextClientName;


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

        mTextClientName = view.findViewById(R.id.diet_client_name);
        mTextClientName.setText(mClienName + " " +  getResources().getString(R.string.diet_caps));

        RecyclerView recyclerView = view.findViewById(R.id.recycler_diet_days);
        recyclerView.setAdapter(new DietListAdapter(getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }
}