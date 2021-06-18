package it.uniba.di.sms2021.gruppodkl.wefit.fragment.coach;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppokdl.wefit.coach.adapter.CoachDietDayAdapter;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.coach.CoachMyClientDietSpecificationContract;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Meal;
import it.uniba.di.sms2021.gruppodkl.wefit.presenter.coach.CoachMyClientDietSpecificationPresenter;
import it.uniba.di.sms2021.gruppodkl.wefit.recyclerview.CustomRecyclerView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CoachMyClientDietSpecificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CoachMyClientDietSpecificationFragment extends Fragment
    implements CoachMyClientDietSpecificationContract.View{

    public static final String TAG = CoachMyClientDietSpecificationFragment.class.getSimpleName();

    private static final String CLIENT_MAIL = "clientMail";
    private static final String DAY_OF_THE_WEEK = "dayOfTheWeek";
    private String mDayOfTheWeek;
    private String mClientMail;
    private CoachMyClientDietSpecificationContract.Presenter mPresenter;

    private CoachDietDayAdapter mBreakfastAdapter;
    private CoachDietDayAdapter mLunchAdapter;
    private CoachDietDayAdapter mDinnerAdapter;




    public CoachMyClientDietSpecificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param clientMail cliente di cui si vuole visualizzare la dieta
     * @param dayOfTheWeek giorno della settimana di cui si vuole visualizzare la dieta.
     * @return A new instance of fragment CoachMyClientDietSpecificationFragment.
     */
    public static CoachMyClientDietSpecificationFragment newInstance(String clientMail, String dayOfTheWeek) {
        CoachMyClientDietSpecificationFragment fragment = new CoachMyClientDietSpecificationFragment();
        Bundle args = new Bundle();
        args.putString(DAY_OF_THE_WEEK, dayOfTheWeek);
        args.putString(CLIENT_MAIL, clientMail);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mClientMail = getArguments().getString(CLIENT_MAIL);
            mDayOfTheWeek = getArguments().getString(DAY_OF_THE_WEEK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.coach_my_client_diet_specification_fragment, container, false);


        mPresenter = new CoachMyClientDietSpecificationPresenter(this);

        bind(layout);


        return layout;
    }

    private void bind(View layout){

        if(getActivity() != null && getActivity() instanceof WeFitApplication.CallbackOperations){
            WeFitApplication.CallbackOperations act = (WeFitApplication.CallbackOperations) getActivity();
            ((WeFitApplication)getActivity().getApplicationContext()).setToolbar(layout,act);
        }


        CustomRecyclerView breakfastRecycler = layout.findViewById(R.id.breakfast_recycler);
        mBreakfastAdapter = mPresenter.getAdapter(mClientMail, mDayOfTheWeek, Meal.MealType.BREAKFAST);
        breakfastRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        breakfastRecycler.setAdapter(mBreakfastAdapter);
        breakfastRecycler.setEmptyView(layout.findViewById(R.id.text_empty_breakfast));

        CustomRecyclerView mLunchRecycler = layout.findViewById(R.id.lunch_recycler);
        mLunchAdapter = mPresenter.getAdapter(mClientMail, mDayOfTheWeek, Meal.MealType.LUNCH);
        mLunchRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mLunchRecycler.setAdapter(mLunchAdapter);
        mLunchRecycler.setEmptyView(layout.findViewById(R.id.text_empty_lunch));


        CustomRecyclerView mDinnerRecycler = layout.findViewById(R.id.dinner_recycler);
        mDinnerAdapter = mPresenter.getAdapter(mClientMail, mDayOfTheWeek, Meal.MealType.DINNER);
        mDinnerRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDinnerRecycler.setAdapter(mDinnerAdapter);
        mDinnerRecycler.setEmptyView(layout.findViewById(R.id.text_empty_dinner));

        MaterialButton addMealButton = layout.findViewById(R.id.btn_add_meal);
        MaterialButton updateButton = layout.findViewById(R.id.btn_update);

        addMealButton.setOnClickListener(v -> addMeal());
        updateButton.setOnClickListener(v -> updateMeals());
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


    private void addMeal(){
        if(getActivity() != null) {
            CoachAddMealFragment fragment = CoachAddMealFragment.newInstance(mClientMail, mDayOfTheWeek);
            fragment.show(getActivity().getSupportFragmentManager(), CoachAddMealFragment.TAG);
        }
    }

    private void updateMeals(){
        mBreakfastAdapter.showDeleteButtons();
        mLunchAdapter.showDeleteButtons();
        mDinnerAdapter.showDeleteButtons();
    }

    @Override
    public String getClientMail() {
        return mClientMail;
    }

    @Override
    public String getDayOfTheWeek() {
        return mDayOfTheWeek;
    }
}