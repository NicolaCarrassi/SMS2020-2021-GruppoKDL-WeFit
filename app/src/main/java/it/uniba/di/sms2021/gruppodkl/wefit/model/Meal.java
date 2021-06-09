package it.uniba.di.sms2021.gruppodkl.wefit.model;

import android.content.Context;


import it.uniba.di.sms2021.gruppodkl.wefit.R;

public class Meal {

    public interface MealKeys{
        String NAME = "name";
        String ID = "id";
        String QUANTITY = "quantity";
        String MEAL_TYPE = "mealType";
    }


    public interface MealType{
        //con i valori impostati in questo modo è possibile implementare in futuro la possibilità di aggiungere
        // spuntino mattutino e pomeridiano
        int BREAKFAST = 0;
        int LUNCH = 2;
        int DINNER = 4;
    }

   public interface MealList{
        String FETTE_BISCOTTATE = "Fette biscottate";
        String BISCOTTI = "Biscotti";
        String TACCHINO = "Tacchino";
        String PESCE = "Pesce";
        String UOVA = "Uova";
        String BANANA = "Banana";
        String LENTICCHIE = "Lenticchie";
        String PASTA = "Pasta";
        String PERA = "Pera";
        String CAROTE = "Carote";
        String CAFFE = "Caffe";
        String PATATE = "Patate";
        String MELA = "Mela";
        String POLLO = "Pollo";
        String LATTE = "Latte";
        String FORMAGGIO ="Formaggio";
        String MOZZARELLA = "Mozzarella";
        String TOFU = "Tofu";
        String RISO = "Riso";
        String CEREALI = "Cereali";
        String PIZZA = "Pizza";
        String INSALATA = "Insalata";
        String PANE = "Pane";
    }


    private String id;
    public String name;
    public int quantity;
    public int mealType;

    public Meal(){
        //costructor needed by the db
    }


    public Meal(String name, int quantity, int mealType){
        this.name = name;
        this.quantity = quantity;
        this.mealType = mealType;
    }

    public void setId(String id){this.id = id;}
    public String getId(){return  id;}

    public String convertQuantity(){
        int temp = quantity;
        String res = "";

        if((temp % 1000) != temp){
               res += (temp/1000) + " kg ";
               temp = temp % 1000;
        }

        return res + temp + " g";
    }


    public static String convertName(String foodName, Context context){
        String translatedRes = null;

            switch (foodName){
                case MealList.FETTE_BISCOTTATE:
                    context.getResources().getString(R.string.fette_biscottate);
                    break;
                case MealList.BISCOTTI:
                    translatedRes = context.getResources().getString(R.string.biscotti);
                    break;
                case MealList.TACCHINO:
                    translatedRes = context.getResources().getString(R.string.tacchino);
                    break;
                case MealList.PESCE:
                    translatedRes = context.getResources().getString(R.string.pesce);
                    break;
                case MealList.UOVA:
                    translatedRes = context.getResources().getString(R.string.uova);
                    break;
                case MealList.BANANA:
                    translatedRes = context.getResources().getString(R.string.banana);
                    break;
                case MealList.LENTICCHIE:
                    translatedRes = context.getResources().getString(R.string.lenticchie);
                    break;
                case MealList.PASTA:
                    translatedRes = context.getResources().getString(R.string.pasta);
                    break;
                case MealList.PERA:
                    translatedRes = context.getResources().getString(R.string.pera);
                    break;
                case MealList.CAROTE:
                    translatedRes = context.getResources().getString(R.string.carote);
                    break;
                case MealList.CAFFE:
                    translatedRes = context.getResources().getString(R.string.caffe);
                    break;
                case MealList.PATATE:
                    translatedRes = context.getResources().getString(R.string.patate);
                    break;
                case MealList.MOZZARELLA:
                    translatedRes = context.getResources().getString(R.string.mozzarella);
                    break;
                case MealList.TOFU:
                    translatedRes = context.getResources().getString(R.string.tofu);
                    break;
                case MealList.RISO:
                    translatedRes = context.getResources().getString(R.string.riso);
                    break;
                case MealList.CEREALI:
                    translatedRes = context.getResources().getString(R.string.cereali);
                    break;
                case MealList.PIZZA:
                    translatedRes = context.getResources().getString(R.string.pizza);
                    break;
                case MealList.INSALATA:
                    translatedRes = context.getResources().getString(R.string.insalata);
                    break;
                case MealList.PANE:
                    translatedRes = context.getResources().getString(R.string.pane);
                    break;
                case MealList.MELA:
                    translatedRes = context.getResources().getString(R.string.mela);
                    break;
                case MealList.POLLO:
                    translatedRes = context.getResources().getString(R.string.pollo);
                    break;
                case MealList.LATTE:
                    translatedRes = context.getResources().getString(R.string.latte);
                    break;
                case MealList.FORMAGGIO:
                    translatedRes = context.getResources().getString(R.string.formaggio);
                    break;
                default:
                    break;
            }
            return translatedRes;
        }




}
