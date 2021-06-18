package it.uniba.di.sms2021.gruppokdl.wefit.model;

public class Exercise {

    private String exerciseId;
    public String name;
    public int reps;
    public boolean hasTime;
    public String videoUrl;



    public interface ExerciseKeys{
        String NAME = "name";
        String REPS = "reps";
        String TIME = "hasTime";
    }

    public Exercise(){

    }


    public Exercise(String name, int reps, boolean time){
        this.name = name;
        this.reps = reps;
        this.hasTime = time;
    }


    public String convertRepsNumberToString(){
        String res = "";

        if(hasTime){
            if(reps > 60)
                res += reps/60 + " min ";

            res += reps%60 + " sec";
        }else
            res = reps + " reps ";


        return res;
    }


    public String getId(){
        return exerciseId;
    }

    public void setId(String id){
        this.exerciseId = id;
    }

}
