package it.uniba.di.sms2021.gruppodkl.wefit.model;

public class Training {
    private String id;
    public String title;
    public int dayOfWeek;
    public int time;

    public Training(){
        //empty constructor needed by firebase
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId(){
        return id;
    }


    public String getDurationTime(){
        if(time > 60)
            return time/60 + " h " + time%60 + " min";
        else
            return time + " min";
    }

}
