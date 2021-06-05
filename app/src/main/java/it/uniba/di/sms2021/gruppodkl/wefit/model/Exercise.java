package it.uniba.di.sms2021.gruppodkl.wefit.model;

public class Exercise {

    private String exerciseId;
    public String name;
    public int reps;
    public boolean hasTime;
    public String videoUrl;
    public String description;

    public Exercise(){

    }


    public String getRepsString(){
        String res = "";

        if(hasTime){
            if(reps > 60)
                res += reps/60 + " min ";

            res += reps%60 + " sec";
        }else
            res = (String.valueOf(reps));

        return res;
    }


    public String getId(){
        return exerciseId;
    }

    public void setId(String id){
        this.exerciseId = id;
    }

}
