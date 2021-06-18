package it.uniba.di.sms2021.gruppokdl.wefit.coach.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.uniba.di.sms2021.gruppokdl.wefit.R;
import it.uniba.di.sms2021.gruppokdl.wefit.contract.coach.CoachAddExerciseContract;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Exercise;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Training;
import it.uniba.di.sms2021.gruppokdl.wefit.coach.presenter.CoachAddExercisePresenter;


public class CoachAddExerciseFragment extends BottomSheetDialogFragment implements CoachAddExerciseContract.View {

    public final static String TAG = CoachAddExerciseFragment.class.getSimpleName();

    private final static String CLIENT_MAIL = "clientMail";
    private final static String TRAINING = "training";

    private String mClientMail;
    private Training mTraining;

    private Spinner mSpinnerExercise;
    private RadioGroup mButtonRadioGroup;
    private TextView mTextViewNumberOf;
    private TextView mTextViewSeconds;
    private EditText mEditTextNumber;

    private CoachAddExerciseContract.Presenter mPresenter;



    public CoachAddExerciseFragment() {
        // Required empty public constructor
    }

    public static CoachAddExerciseFragment newInstance(String clientMail, Training training) {
        CoachAddExerciseFragment fragment = new CoachAddExerciseFragment();
        Bundle args = new Bundle();
        args.putString(CLIENT_MAIL, clientMail);
        args.putParcelable(TRAINING, training);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            mClientMail = getArguments().getString(CLIENT_MAIL);
            mTraining = getArguments().getParcelable(TRAINING);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout =  inflater.inflate(R.layout.coach_add_exercise_fragment, container, false);

        mPresenter = new CoachAddExercisePresenter(this);

        mButtonRadioGroup = layout.findViewById(R.id.exercise_type_radio_group);
        mTextViewNumberOf = layout.findViewById(R.id.number_of_text_view);
        mTextViewSeconds = layout.findViewById(R.id.seconds_label);
        mEditTextNumber = layout.findViewById(R.id.edit_text_number);

        Button mButtonCancel = layout.findViewById(R.id.btn_cancel);
        Button mButtonAdd = layout.findViewById(R.id.btn_add);

        mSpinnerExercise = layout.findViewById(R.id.spinner_exercise);


        mButtonRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            boolean isTime = checkedId == R.id.time_radio;
            if(isTime){
                mTextViewNumberOf.setText(getResources().getString(R.string.seconds_number));
                mTextViewSeconds.setVisibility(View.VISIBLE);
            }else {
                mTextViewNumberOf.setText(getResources().getString(R.string.reps_number));
                mTextViewSeconds.setVisibility(View.GONE);
            }
            mEditTextNumber.setText("");
        });

        getDialog().setOnShowListener(dialog -> {
            BottomSheetDialog d = (BottomSheetDialog) dialog;
            FrameLayout bottomSheet = d.findViewById(R.id.design_bottom_sheet);
            assert bottomSheet != null;
            CoordinatorLayout coordinatorLayout = (CoordinatorLayout) bottomSheet.getParent();
            BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
            bottomSheetBehavior.setPeekHeight(bottomSheet.getHeight());
            coordinatorLayout.getParent().requestLayout();
        });

        mButtonCancel.setOnClickListener(v -> dismiss());
        mButtonAdd.setOnClickListener(v -> addExercise());

        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.fetchAllExercises();
    }


    @Override
    public void onExercisesNamesLoaded(List<String> list) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_layout);
        adapter.addAll(list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerExercise.setAdapter(adapter);
    }


    /**
     * Il metodo permette di aggiungere un esercizio
     */
    private void addExercise() {
        boolean isCorrect = true;
        int exerciseReps = 0;
        String exerciseName = (String) mSpinnerExercise.getSelectedItem();
        boolean hasTime = mButtonRadioGroup.getCheckedRadioButtonId() == R.id.time_radio;

        if (!TextUtils.isEmpty(mEditTextNumber.getText().toString())) {
            exerciseReps = Integer.parseInt(mEditTextNumber.getText().toString());

            if(exerciseReps <= 0)
                isCorrect = false;

        }else
            isCorrect = false;

        if (isCorrect && mButtonRadioGroup.getCheckedRadioButtonId() != -1) {

            Map<String, Object> map = new HashMap<>();
            map.put(Exercise.ExerciseKeys.NAME, exerciseName);
            map.put(Exercise.ExerciseKeys.REPS, exerciseReps);
            map.put(Exercise.ExerciseKeys.TIME, hasTime);

            mPresenter.addExercise(mClientMail, mTraining.getId(), map);
            mEditTextNumber.setText("");
            Toast.makeText(getActivity(), R.string.exercise_added, Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(getActivity(),getResources().getString(R.string.error_data_missing), Toast.LENGTH_SHORT).show();
    }
}