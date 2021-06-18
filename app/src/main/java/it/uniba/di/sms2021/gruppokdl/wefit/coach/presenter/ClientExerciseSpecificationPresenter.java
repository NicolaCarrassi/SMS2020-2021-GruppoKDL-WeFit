package it.uniba.di.sms2021.gruppokdl.wefit.coach.presenter;

import android.content.Context;

import java.util.List;

import it.uniba.di.sms2021.gruppokdl.wefit.R;
import it.uniba.di.sms2021.gruppokdl.wefit.contract.client.ClientExerciseSpecificationContract;
import it.uniba.di.sms2021.gruppokdl.wefit.db.TrainingDAO;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Exercise;

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
    public String getExerciseDescription(String exerciseName, Context context) {
        String res = null;
        boolean found = false;

        if(exerciseName.equals(context.getResources().getString(R.string.bicycle_crunches))){
            found = true;
            res = context.getResources().getString(R.string.bicycle_crunches_description);
        }

        if(!found && exerciseName.equals(context.getResources().getString(R.string.heel_touch))){
            found = true;
            res = context.getResources().getString(R.string.heel_touch_description);
        }
        if(!found && exerciseName.equals(context.getResources().getString(R.string.jumping_jacks))){
            found = true;
            res = context.getResources().getString(R.string.jumping_jacks_description);
        }

        if(!found && exerciseName.equals(context.getResources().getString(R.string.military_push_ups))){
            found = true;
            res = context.getResources().getString(R.string.military_push_ups_description);
        }

        if(!found && exerciseName.equals(context.getResources().getString(R.string.mountain_climber))){
            found = true;
            res = context.getResources().getString(R.string.mountain_climber_description);
        }

        if(!found && exerciseName.equals(context.getResources().getString(R.string.plank))){
            found = true;
            res = context.getResources().getString(R.string.plank_description);
        }


        if(!found && exerciseName.equals(context.getResources().getString(R.string.push_ups))){
            found = true;
            res = context.getResources().getString(R.string.push_ups_description);
        }

        if(!found && exerciseName.equals(context.getResources().getString(R.string.reverse_crunches))){
            found = true;
            res = context.getResources().getString(R.string.reverse_crunches_description);
        }

        if(!found && exerciseName.equals(context.getResources().getString(R.string.russian_twist))){
            found = true;
            res = context.getResources().getString(R.string.russian_twist_description);
        }

        if(!found && exerciseName.equals(context.getResources().getString(R.string.side_crunches))) {
            found = true;
            res = context.getResources().getString(R.string.side_crunches_description);
        }

        if(!found && exerciseName.equals(context.getResources().getString(R.string.sit_ups))) {
            found = true;
            res = context.getResources().getString(R.string.sit_ups_description);
        }

        return res;
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
