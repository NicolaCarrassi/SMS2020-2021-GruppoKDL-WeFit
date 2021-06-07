package it.uniba.di.sms2021.gruppodkl.wefit.utility;

import android.content.Context;
import android.util.Log;
import android.view.View;

import java.util.List;
import java.util.function.LongFunction;

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

    public static int getStringValue(String dayOfTheWeek, Context context){
        Log.d("AOO", dayOfTheWeek);
        int res = -1;
        String sunday = context.getResources().getString(R.string.sunday);
        String monday = context.getResources().getString(R.string.monday);
        String tuesday = context.getResources().getString(R.string.tuesday);
        String wednesday = context.getResources().getString(R.string.wednesday);
        String thursday = context.getResources().getString(R.string.thursday);
        String friday = context.getResources().getString(R.string.friday);
        String saturday = context.getResources().getString(R.string.saturday);


        if(dayOfTheWeek.equals(sunday))
            res = WeekDay.SUNDAY;

        if(res == -1 && dayOfTheWeek.equals(monday))
            res = WeekDay.MONDAY;

        if(res == -1 && dayOfTheWeek.equals(tuesday))
            res = WeekDay.TUESDAY;

        if(res == -1 && dayOfTheWeek.equals(wednesday))
            res = WeekDay.WEDNESDAY;

        if(res == -1 && dayOfTheWeek.equals(thursday))
            res = WeekDay.THURSDAY;

        if(res == -1 && dayOfTheWeek.equals(friday))
            res = WeekDay.FRIDAY;

        if(res == -1){
            if(dayOfTheWeek.equals(saturday))
                res = WeekDay.SATURDAY;
            else
                res = WeekDay.NOT_SET;
        }

        return res;
    }




}
