package it.uniba.di.sms2021.gruppodkl.wefit.contract.coach;

import java.util.List;
import java.util.Map;

public interface CoachAddExerciseContract {

    interface View{
        void onExercisesNamesLoaded(List<String> list);
    }

    interface Presenter{
        void fetchAllExercises();
        void addExercise(String clientMail, String trainingId, Map<String,Object> map);
    }

}
