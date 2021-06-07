package it.uniba.di.sms2021.gruppodkl.wefit.fragment.client;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.uniba.di.sms2021.gruppodkl.wefit.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClientExerciseSpecificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientExerciseSpecificationFragment extends Fragment {

    private static final String EXERCISE_NAME = "exercise_name";

    private String mExerciseName;

    public ClientExerciseSpecificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param exerciseName nome dell'esercizio di cui si vuole vedere la specifica
     * @return A new instance of fragment ClientExerciseSpecificationFragment.
     */
    public static ClientExerciseSpecificationFragment newInstance(String exerciseName) {
        ClientExerciseSpecificationFragment fragment = new ClientExerciseSpecificationFragment();
        Bundle args = new Bundle();
        args.putString(EXERCISE_NAME, exerciseName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            mExerciseName = getArguments().getString(EXERCISE_NAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.client_exercise_specification_fragment, container, false);

        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        //TODO query valori
    }
}