package it.uniba.di.sms2021.gruppodkl.wefit.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Training implements Parcelable {

    public interface TrainingKeys{
        String ID ="id";
        String TITLE = "title";
        String DAY_OF_WEEK = "dayOfWeek";
        String TIME = "time";
    }


    private String id;
    public String title;
    public int dayOfWeek;
    public int time;

    private static final byte PRESENT = 1;
    private static final byte ABSENT = 0;


    public Training(){
        //empty constructor needed by firebase
    }

    public Training(String id,String title,int dayOfWeek, int time){
        this.id = id;
        this.title = title;
        this.dayOfWeek = dayOfWeek;
        this.time = time;
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


    //IMPLEMENTAZIONE INTERFACCIA PARCELABLE

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id != null){
            dest.writeByte(PRESENT);
            dest.writeString(id);
        }else
            dest.writeByte(ABSENT);

        dest.writeString(title);
        dest.writeInt(dayOfWeek);
        dest.writeInt(time);
    }

    protected Training(Parcel in) {
        if(in.readByte() == PRESENT)
            id = in.readString();

        title = in.readString();
        dayOfWeek = in.readInt();
        time = in.readInt();
    }

    public static final Creator<Training> CREATOR = new Creator<Training>() {
        @Override
        public Training createFromParcel(Parcel in) {
            return new Training(in);
        }

        @Override
        public Training[] newArray(int size) {
            return new Training[size];
        }
    };


}
