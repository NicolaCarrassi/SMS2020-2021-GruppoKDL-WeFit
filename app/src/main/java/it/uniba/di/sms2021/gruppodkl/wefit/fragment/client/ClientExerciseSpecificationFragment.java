package it.uniba.di.sms2021.gruppodkl.wefit.fragment.client;

import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.client.ClientExerciseSpecificationContract;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Exercise;
import it.uniba.di.sms2021.gruppodkl.wefit.presenter.coach.ClientExerciseSpecificationPresenter;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.YoutubeConfig;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClientExerciseSpecificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientExerciseSpecificationFragment extends Fragment implements ClientExerciseSpecificationContract.View{

    public static final String TAG = ClientExerciseSpecificationFragment.class.getSimpleName();

    private static final String EXERCISE_NAME = "exercise_name";
    private static final int RECOVERY_DIALOG_REQUEST = 1;

    private String mExerciseName;


    private Exercise mExercise;
    private ClientExerciseSpecificationContract.Presenter mPresenter;
    private WebView mWebView;


    private TextView mTextViewExerciseTitle;
    private TextView mTextViewExerciseDescription;


    private YouTubePlayer mYoutubePlayer;
    private YouTubePlayerSupportFragmentX mYoutubePlayerFragment;



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

        mPresenter = new ClientExerciseSpecificationPresenter(this);

        if(getActivity() instanceof WeFitApplication.CallbackOperations){
            WeFitApplication.CallbackOperations activity = (WeFitApplication.CallbackOperations) getActivity();
            ((WeFitApplication)getActivity().getApplicationContext()).setToolbar(layout,activity);
        }

        mWebView = layout.findViewById(R.id.web_video);

        mTextViewExerciseDescription = layout.findViewById(R.id.exercise_description);
        mTextViewExerciseTitle = layout.findViewById(R.id.exercise_name);

        initFragment(layout);
        
        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.loadVideoInformation(mExerciseName);
    }

    @Override
    public void onInfoLoaded(Exercise exercise){
        mExercise = exercise;
        mTextViewExerciseTitle.setText(mExercise.name);

        if(mYoutubePlayer != null) {
            mYoutubePlayer.cueVideo(mExercise.videoUrl);
            showEmbeddedPlayer();
        }
    }

    @Override
    public void onFailure() {
        Toast.makeText(getActivity(), getResources().getString(R.string.error_general), Toast.LENGTH_SHORT).show();
    }

    private void initFragment(View view){
        if(mYoutubePlayerFragment == null){
            mYoutubePlayerFragment = (YouTubePlayerSupportFragmentX) getChildFragmentManager().findFragmentById(R.id.youtube_frag);
            mYoutubePlayerFragment.initialize(YoutubeConfig.getApiKey(), new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                    mYoutubePlayer = youTubePlayer;
                    youTubePlayer.setFullscreen(false);
                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                    showEmbeddedPlayer();
                }
            });
        }
    }


    private void showEmbeddedPlayer(){
        getChildFragmentManager().beginTransaction().remove(mYoutubePlayerFragment);
        mWebView.setVisibility(View.VISIBLE);

        String url = String.format("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/%s\" frameborder=\"0\" allowfullscreen></iframe>","mExercise.videoUrl");

        Log.d("AOO", url);

        //TODO Sistema format della stringa, genera un player embedded e aprilo
        // in modo da riprodurre correttamente il video in qualsiasi situazione
        // (probabilmente ci sarà un errore nel manifest)
        // Trova una soluzione anche al problema delle descrizioni degli esercizi
    }





}