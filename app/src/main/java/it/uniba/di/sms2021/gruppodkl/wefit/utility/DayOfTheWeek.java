package it.uniba.di.sms2021.gruppodkl.wefit.utility;

import android.view.View;

import it.uniba.di.sms2021.gruppodkl.wefit.R;

public class DayOfTheWeek {

    public interface WeekDay{
        int SUNDAY = 0;
        int MONDAY = 1;
        int TUESDAY = 2;
        int WEDNESDAY = 3;
        int THURSDAY = 4;
        int FRIDAY = 5;
        int SATURDAY = 6;
        int NOT_SET = 7;
    }


    public static String getDayOfTheWeek(int day, View view){
        String dayOfTheWeek;
        switch (day){
            case WeekDay.SUNDAY:
                dayOfTheWeek =  view.getResources().getString(R.string.sunday);
                break;
            case WeekDay.MONDAY:
                dayOfTheWeek =  view.getResources().getString(R.string.monday);
                break;
            case WeekDay.TUESDAY:
                dayOfTheWeek =  view.getResources().getString(R.string.tuesday);
                break;
            case WeekDay.WEDNESDAY:
                dayOfTheWeek =  view.getResources().getString(R.string.wednesday);
                break;
            case WeekDay.THURSDAY:
                dayOfTheWeek =  view.getResources().getString(R.string.thursday);
                break;
            case WeekDay.FRIDAY:
                dayOfTheWeek =  view.getResources().getString(R.string.friday);
                break;
            case WeekDay.SATURDAY:
                dayOfTheWeek =  view.getResources().getString(R.string.saturday);
                break;
            default:
                dayOfTheWeek ="";
        }
        return dayOfTheWeek;
    }



}
