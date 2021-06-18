package it.uniba.di.sms2021.gruppokdl.wefit.coach.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import it.uniba.di.sms2021.gruppokdl.wefit.R;
import it.uniba.di.sms2021.gruppokdl.wefit.coach.contract.CoachAddMealContract;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Meal;
import it.uniba.di.sms2021.gruppokdl.wefit.coach.presenter.CoachAddMealPresenter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CoachAddMealFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CoachAddMealFragment extends BottomSheetDialogFragment implements CoachAddMealContract.View {

    public static final String TAG = CoachAddMealFragment.class.getSimpleName();

    private static final String CLIENT_MAIL = "clientMail";
    private static final String DAY_OF_THE_WEEK = "dayOfTheWeek";

    private String mClientMail;
    private String mDayOfTheWeek;

    private Spinner mMealSpinner;
    private Spinner mMomentOfTheDaySpinner;
    private EditText mQuantityEditText;

    private CoachAddMealContract.Presenter mPresenter;

    public CoachAddMealFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param clientMail email del cliente
     * @param dayOfTheWeek Giorno della settimana
     * @return A new instance of fragment CoachAddMealFragment.
     */
    public static CoachAddMealFragment newInstance(String clientMail, String dayOfTheWeek) {
        CoachAddMealFragment fragment = new CoachAddMealFragment();
        Bundle args = new Bundle();
        args.putString(CLIENT_MAIL, clientMail);
        args.putString(DAY_OF_THE_WEEK, dayOfTheWeek);
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
        View view =  inflater.inflate(R.layout.coach_add_meal_fragment, container, false);

        mPresenter = new CoachAddMealPresenter(this, getActivity());

        mMealSpinner = view.findViewById(R.id.meal_spinner);
        mMomentOfTheDaySpinner = view.findViewById(R.id.moment_of_the_day_spinner);
        mQuantityEditText = view.findViewById(R.id.meal_quantity);

        MaterialButton mAddButton = view.findViewById(R.id.btn_add);
        mAddButton.setOnClickListener(v -> addMeal());

        ArrayAdapter<CharSequence> momentOfTheDayAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.moment_of_the_day, R.layout.spinner_layout);
        momentOfTheDayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMomentOfTheDaySpinner.setAdapter(momentOfTheDayAdapter);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        mPresenter.loadAllMeals();

    }

    /**
     * Il metodo permette di aggiungere un pasto
     */
    private void addMeal(){
        if(mMealSpinner.getSelectedItem() != null && mMomentOfTheDaySpinner.getSelectedItem() != null) {
            String mealSelected = mMealSpinner.getSelectedItem().toString();
            int momentOfTheDay;
            int quantity = -1;

            if (!TextUtils.isEmpty(mQuantityEditText.getText().toString()) && TextUtils.isDigitsOnly(mQuantityEditText.getText().toString())
                    && Integer.parseInt(mQuantityEditText.getText().toString()) > 0)

                quantity = Integer.parseInt(mQuantityEditText.getText().toString());
            else
                mQuantityEditText.setError(getString(R.string.error_quantity));

            if (mMomentOfTheDaySpinner.getSelectedItem().toString().equals(getString(R.string.breakfast)))
                momentOfTheDay = Meal.MealType.BREAKFAST;
            else if (mMomentOfTheDaySpinner.getSelectedItem().toString().equals(getString(R.string.lunch)))
                momentOfTheDay = Meal.MealType.LUNCH;
            else
                momentOfTheDay = Meal.MealType.DINNER;

            if (quantity != -1)
                mPresenter.addMeal(mClientMail, mDayOfTheWeek, mealSelected, momentOfTheDay, quantity);
        }else{
            Toast.makeText(getActivity(), getString(R.string.error_general), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void mealsLoaded(List<String> translatedList) {
        ArrayAdapter<String> mealsAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_layout);
        mealsAdapter.addAll(translatedList);
        mealsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMealSpinner.setAdapter(mealsAdapter);
    }

    @Override
    public void onFailure() {
        Toast.makeText(getActivity(), getString(R.string.error_general),Toast.LENGTH_SHORT).show();
    }
}