package it.uniba.di.sms2021.gruppodkl.wefit.presenter.coach;


import java.util.List;
import java.util.Map;

import it.uniba.di.sms2021.gruppodkl.wefit.contract.coach.CoachAddExerciseContract;
import it.uniba.di.sms2021.gruppodkl.wefit.db.TrainingDAO;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Exercise;

public class CoachAddExercisePresenter implements CoachAddExerciseContract.Presenter, TrainingDAO.ExercisesCallbacks {

    private final CoachAddExerciseContract.View mView;


    public CoachAddExercisePresenter(CoachAddExerciseContract.View view){
        this.mView = view;
    }

    @Override
    public void fetchAllExercises() {
        TrainingDAO.fetchAllExercises(this);
    }

    @Override
    public void onExercisesLoaded(List<String> exerciseNames) {
        mView.onExercisesNamesLoaded(exerciseNames);
    }

    @Override
    public void addExercise(String clientMail, String trainingId, Map<String, Object> map) {
        if(map.containsKey(Exercise.ExerciseKeys.REPS) && map.containsKey(Exercise.ExerciseKeys.TIME) && map.containsKey(Exercise.ExerciseKeys.NAME)
            && map.get(Exercise.ExerciseKeys.NAME) != null && map.get(Exercise.ExerciseKeys.REPS) != null && map.get(Exercise.ExerciseKeys.TIME) != null ) {

            boolean hasTime = (boolean) map.get(Exercise.ExerciseKeys.TIME);
            int reps = (int) map.get(Exercise.ExerciseKeys.REPS);

            Exercise exercise = new Exercise((String) map.get(Exercise.ExerciseKeys.NAME),reps,hasTime);

            TrainingDAO.addExerciseInTraining(clientMail, trainingId, exercise);
        }
    }
}
