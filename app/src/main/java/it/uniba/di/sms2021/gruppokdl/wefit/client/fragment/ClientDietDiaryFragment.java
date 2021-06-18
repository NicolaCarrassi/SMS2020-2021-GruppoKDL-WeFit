package it.uniba.di.sms2021.gruppokdl.wefit.fragment.client;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import it.uniba.di.sms2021.gruppokdl.wefit.R;
import it.uniba.di.sms2021.gruppokdl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppokdl.wefit.client.adapter.ClientDietDiaryAdapter;
import it.uniba.di.sms2021.gruppokdl.wefit.contract.client.ClientDietDiaryContract;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Meal;
import it.uniba.di.sms2021.gruppokdl.wefit.presenter.client.ClientDietDiaryPresenter;
import it.uniba.di.sms2021.gruppokdl.wefit.recyclerview.CustomRecyclerView;


public class ClientDietDiaryFragment extends Fragment implements ClientDietDiaryContract.View {

    public static final String TAG = ClientDietDiaryFragment.class.getSimpleName();

    private static final String DATE_SELECTED = "selectedDate";

    private String mSelectedDate;
    private String mClientMail;
    private SimpleDateFormat mSimpleDateFormatDateToQuery = new SimpleDateFormat("yyy-MM-dd");
    private SimpleDateFormat mSdfDateLabel = new SimpleDateFormat("dd/MM/yyyy");
    private ClientDietDiaryContract.Presenter mPresenter;

    private LinearProgressIndicator mProgressIndicator;
    private TextView mDateTextView;
    private ImageButton mBackBtn;
    private ImageButton mNextBtn;


    private CustomRecyclerView mBreakfastRecycler;
    private CustomRecyclerView mLunchRecycler;
    private CustomRecyclerView mDinnerRecycler;
    private ClientDietDiaryAdapter mBreakfastAdapter;
    private ClientDietDiaryAdapter mLunchAdapter;
    private ClientDietDiaryAdapter mDinnerAdapter;


    public ClientDietDiaryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null &&
                savedInstanceState.containsKey(DATE_SELECTED) && savedInstanceState.getString(DATE_SELECTED) != null) {
            try{
                mSelectedDate = mSimpleDateFormatDateToQuery.format(Objects.requireNonNull(mSimpleDateFormatDateToQuery.parse(savedInstanceState.getString(DATE_SELECTED))));
            } catch (Exception e) {
                mSelectedDate = mSimpleDateFormatDateToQuery.format(new Date());
            }
        }
        //in caso in cui non contenga la chiave oppure il valore sia uguale a null
        if(mSelectedDate == null)
            mSelectedDate = mSimpleDateFormatDateToQuery.format(new Date());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.client_diet_diary_fragment, container, false);

        assert getActivity() != null;
        mClientMail = ((WeFitApplication)getActivity().getApplicationContext()).getUser().email;

        mPresenter = new ClientDietDiaryPresenter(this);
        bind(view);

        mBackBtn.setOnClickListener(v -> changeDay(false));
        mNextBtn.setOnClickListener(v -> changeDay(true));
        return view;
    }


    /**
     * Il metodo permette di associare gli elementi della view ad oggetti
     *
     */
    private void bind(View view){
        mProgressIndicator = view.findViewById(R.id.day_loader);
        mBackBtn = view.findViewById(R.id.previous_day_btn);
        mDateTextView = view.findViewById(R.id.date_text_view);

        setDateLabel();

        mNextBtn = view.findViewById(R.id.next_day_btn);

        mBreakfastRecycler = view.findViewById(R.id.breakfast_recycler);
        mLunchRecycler = view.findViewById(R.id.lunch_recycler);
        mDinnerRecycler = view.findViewById(R.id.dinner_recycler);

        mBreakfastRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mLunchRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDinnerRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        handleAdapters();

        mBreakfastRecycler.setEmptyView(view.findViewById(R.id.text_empty_breakfast));
        mLunchRecycler.setEmptyView(view.findViewById(R.id.text_empty_lunch));
        mDinnerRecycler.setEmptyView(view.findViewById(R.id.text_empty_dinner));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(DATE_SELECTED, mSelectedDate);
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

    /**
     * Il metodo permette di modificare la data di cui si visualizza il diario
     * alimentare
     *
     * @param forward booleano, indica che si vuole visualizzare la data successiva
     *                 a quella che si sta visualizzando
     */
    private void changeDay(boolean forward){
        mBreakfastRecycler.setVisibility(View.GONE);
        mLunchRecycler.setVisibility(View.GONE);
        mDinnerRecycler.setVisibility(View.GONE);
        mProgressIndicator.setVisibility(View.VISIBLE);

        mBreakfastAdapter.stopListening();
        mLunchAdapter.stopListening();
        mDinnerAdapter.stopListening();

        Date temp = null;

        try {
            temp = mSimpleDateFormatDateToQuery.parse(mSelectedDate);
        } catch (ParseException e){
            e.printStackTrace();
        }

        if(temp == null)
            temp = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(temp);

        if(forward){
            calendar.add(Calendar.DATE, 1);
            mSelectedDate = mSimpleDateFormatDateToQuery.format(calendar.getTime());
            if(mSelectedDate.equals(mSimpleDateFormatDateToQuery.format(new Date())))
                mNextBtn.setClickable(false);
        } else {
            calendar.add(Calendar.DATE, -1);
            mSelectedDate = mSimpleDateFormatDateToQuery.format(calendar.getTime());
            mNextBtn.setClickable(true);
        }

        setDateLabel();
        handleAdapters();

        mBreakfastAdapter.startListening();
        mLunchAdapter.startListening();
        mDinnerAdapter.startListening();

        mBreakfastRecycler.setVisibility(View.VISIBLE);
        mLunchRecycler.setVisibility(View.VISIBLE);
        mDinnerRecycler.setVisibility(View.VISIBLE);
        mProgressIndicator.setVisibility(View.GONE);
    }

    /**
     * Il metodo permette di impostare la label relativa alla data
     *
     */
    private void setDateLabel(){
        try {
            mDateTextView.setText(mSdfDateLabel.format(Objects.requireNonNull(mSimpleDateFormatDateToQuery.parse(mSelectedDate))));
        } catch (ParseException e){
            mDateTextView.setText(mSelectedDate);
        }
    }

    /**
     * Il metodo permette di ottenere e impostare gli adapters
     */
    private void handleAdapters(){
        mBreakfastAdapter = mPresenter.getAdapter(mClientMail, mSelectedDate, Meal.MealType.BREAKFAST);
        mLunchAdapter = mPresenter.getAdapter(mClientMail, mSelectedDate, Meal.MealType.LUNCH);
        mDinnerAdapter = mPresenter.getAdapter(mClientMail, mSelectedDate, Meal.MealType.DINNER);

        mBreakfastRecycler.setAdapter(mBreakfastAdapter);
        mLunchRecycler.setAdapter(mLunchAdapter);
        mDinnerRecycler.setAdapter(mDinnerAdapter);
    }

}