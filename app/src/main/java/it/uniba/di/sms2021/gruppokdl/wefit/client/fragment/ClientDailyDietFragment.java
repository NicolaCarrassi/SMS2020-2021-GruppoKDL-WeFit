package it.uniba.di.sms2021.gruppokdl.wefit.fragment.client;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.uniba.di.sms2021.gruppokdl.wefit.R;
import it.uniba.di.sms2021.gruppokdl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppokdl.wefit.client.adapter.ClientDietDayAdapter;
import it.uniba.di.sms2021.gruppokdl.wefit.contract.client.ClientDailyDietContract;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Meal;
import it.uniba.di.sms2021.gruppokdl.wefit.presenter.client.ClientDailyDietPresenter;
import it.uniba.di.sms2021.gruppokdl.wefit.recyclerview.CustomRecyclerView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClientDailyDietFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientDailyDietFragment extends Fragment implements ClientDailyDietContract.View {


    public static final String TAG = ClientDailyDietFragment.class.getSimpleName();
    private static final String DAY_OF_THE_WEEK = "dayOfTheWeek";
    private String mDayOfTheWeek;
    private ClientDailyDietContract.Presenter mPresenter;
    private ClientDietDayAdapter mBreakfastAdapter;
    private ClientDietDayAdapter mLunchAdapter;
    private ClientDietDayAdapter mDinnerAdapter;




    public ClientDailyDietFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param dayOfTheWeek il giorno della settimana di cui si vuole visualizzare la dieta
     * @return A new instance of fragment ClientDailyDiet.
     */
    public static ClientDailyDietFragment newInstance(String dayOfTheWeek) {
        ClientDailyDietFragment fragment = new ClientDailyDietFragment();
        Bundle args = new Bundle();
        args.putString(DAY_OF_THE_WEEK, dayOfTheWeek);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDayOfTheWeek = getArguments().getString(DAY_OF_THE_WEEK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout =  inflater.inflate(R.layout.client_daily_diet_fragment, container, false);

        assert getActivity() != null;
        String clientMail = ((WeFitApplication)getActivity().getApplicationContext()).getUser().email;

        mPresenter = new ClientDailyDietPresenter(this);

        CustomRecyclerView breakfastRecycler = layout.findViewById(R.id.breakfast_recycler);
        mBreakfastAdapter = mPresenter.getAdapter(clientMail, mDayOfTheWeek, Meal.MealType.BREAKFAST);
        breakfastRecycler.setAdapter(mBreakfastAdapter);
        breakfastRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        breakfastRecycler.setEmptyView(layout.findViewById(R.id.text_empty_breakfast));

        CustomRecyclerView mLunchRecycler = layout.findViewById(R.id.lunch_recycler);
        mLunchAdapter = mPresenter.getAdapter(clientMail, mDayOfTheWeek, Meal.MealType.LUNCH);
        mLunchRecycler.setAdapter(mLunchAdapter);
        mLunchRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mLunchRecycler.setEmptyView(layout.findViewById(R.id.text_empty_lunch));


        CustomRecyclerView mDinnerRecycler = layout.findViewById(R.id.dinner_recycler);
        mDinnerAdapter = mPresenter.getAdapter(clientMail, mDayOfTheWeek, Meal.MealType.DINNER);
        mDinnerRecycler.setAdapter(mDinnerAdapter);
        mDinnerRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDinnerRecycler.setEmptyView(layout.findViewById(R.id.text_empty_dinner));

        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        mBreakfastAdapter.startListening();
        mLunchAdapter.startListening();
        mDinnerAdapter.startListening();
    }


    @Override
    public void onStop() {
        super.onStop();
        mBreakfastAdapter.stopListening();
        mLunchAdapter.stopListening();
        mDinnerAdapter.stopListening();
    }
}