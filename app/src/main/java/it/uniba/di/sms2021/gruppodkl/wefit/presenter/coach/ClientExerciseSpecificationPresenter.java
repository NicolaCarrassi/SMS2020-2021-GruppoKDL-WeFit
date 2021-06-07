package it.uniba.di.sms2021.gruppodkl.wefit.presenter.coach;

import java.util.List;

import it.uniba.di.sms2021.gruppodkl.wefit.contract.client.ClientExerciseSpecificationContract;
import it.uniba.di.sms2021.gruppodkl.wefit.db.TrainingDAO;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Exercise;

public class ClientExerciseSpecificationPresenter implements ClientExerciseSpecificationContract.Presenter,
    TrainingDAO.ExercisesCallbacks{

    private final ClientExerciseSpecificationContract.View mView;

    public ClientExerciseSpecificationPresenter(ClientExerciseSpecificationContract.View view){
        this.mView = view;
    }


    @Override
    public void loadVideoInformation(String exerciseName) {
        TrainingDAO.loadExerciseInformation(exerciseName, this);
    }

    @Override
    public void onExercisesLoaded(List<String> exerciseNames) {
        //NON NECESSARIO
    }

    @Override
    public void onInformationLoaded(Exercise exercise) {
        if(exercise != null)
            mView.onInfoLoaded(exercise);
        else
            mView.onFailure();
    }
}
