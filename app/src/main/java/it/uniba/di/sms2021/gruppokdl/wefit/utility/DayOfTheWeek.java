package it.uniba.di.sms2021.gruppokdl.wefit.utility;

import android.content.Context;
import android.view.View;

import java.util.Calendar;
import java.util.Date;

import it.uniba.di.sms2021.gruppokdl.wefit.R;

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


    /**
     * Il metodo permette di ottenere una stringa corrispondente al giorno della settimana nella
     * lingua attiva dell'applicazione
     *
     * @param day intero rappresentante il giorno della settimana, deve essere un numero compreso tra 0
     *            e 6, dove 0 indica SUNDAY e 6 SATURDAY
     *
     * @param view view necessaria per l'ottenimento delle risorse
     * @return Stringa contenente il giorno della settimana se 0 <= day <= 6, null altrimenti
     */
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

    /**
     * Il metodo permette di ottenere il valore numerico corrispondente al giorno della settimana
     *
     * @param dayOfTheWeek Stringa contenente il giorno della settimana
     * @param context context
     * @return numero corrispondente al giorno della settimana se la stringa corrisponde
     *         ad uno di essi, NOT_SET altrimenti
     */
    public static int getStringValue(String dayOfTheWeek, Context context){
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


    /**
     * Il metodo permette di convertire un intero nel giorno della settimana
     * corrispondente
     *
     * @param day valore numerico del giorno
     * @return giorno della settimana
     */
    public static String convertPositionToDayOfTheWeek(int day){
        String res = null;

        if(day  <= 6){
            switch (day){
                case WeekDay.SUNDAY:
                    res = "sunday";
                    break;
                case WeekDay.MONDAY:
                    res = "monday";
                    break;
                case WeekDay.TUESDAY:
                    res = "tuesday";
                    break;
                case WeekDay.WEDNESDAY:
                    res = "wednesday";
                    break;
                case WeekDay.THURSDAY:
                    res = "thursday";
                    break;
                case WeekDay.FRIDAY:
                    res = "friday";
                    break;
                default:
                    res = "saturday";
                    break;
            }
        }

        return res;
    }

    /**
     * Il metodo permette di ottenere il giorno della settimana
     *
     * @param daysToAdd giorni da aggiungere alla data corrente,
     *                  se si vuole ottenere il giorno della settimana corrente, è sufficiente
     *                  passare 0 come parametro
     *
     * @return  intero corrispondente al giorno della settimana
     */
    public static int getDayOfTheWeekFromCalendar(int daysToAdd){
        Date dt = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dt);
        calendar.add(Calendar.DATE, daysToAdd);
        int today = calendar.get(Calendar.DAY_OF_WEEK);

        switch (today){
            case Calendar.SUNDAY:
                today = WeekDay.SUNDAY;
                break;
            case Calendar.MONDAY:
                today = WeekDay.MONDAY;
                break;
            case Calendar.TUESDAY:
                today = WeekDay.TUESDAY;
                break;
            case Calendar.WEDNESDAY:
                today = WeekDay.WEDNESDAY;
                break;
            case Calendar.THURSDAY:
                today = WeekDay.THURSDAY;
                break;
            case Calendar.FRIDAY:
                today = WeekDay.FRIDAY;
                break;
            default:
                today = WeekDay.SATURDAY;
        }

        return today;
    }




}
