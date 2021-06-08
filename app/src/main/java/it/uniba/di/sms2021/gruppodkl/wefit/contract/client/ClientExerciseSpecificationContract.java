package it.uniba.di.sms2021.gruppodkl.wefit.contract.client;

import android.content.Context;

import it.uniba.di.sms2021.gruppodkl.wefit.model.Exercise;

public interface ClientExerciseSpecificationContract {

    interface View{
        void onInfoLoaded(Exercise exercise);
        void onFailure();
    }

    interface Presenter{
        void loadVideoInformation(String exerciseName);
        String getExerciseDescription(String exerciseName, Context context);
    }

}
