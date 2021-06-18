package it.uniba.di.sms2021.gruppokdl.wefit.db;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.uniba.di.sms2021.gruppokdl.wefit.model.Meal;
import it.uniba.di.sms2021.gruppokdl.wefit.utility.Keys;

public class MealsDAO {

    /**
     * Interfaccia contenente i metodi relativi alla risposta
     * per il caricamento dei pasti
     */
    public interface MealsLoaded{
        /**
         * Il metodo permette di restituire una lista di pasti
         * @param meals lista di pasti
         */
        void onMealsLoaded(List<String> meals);
    }

    /**
     * Interfaccia che permette di effettuare il caricamento delle informazioni del
     * carrello della spesa
     */
    public interface ShoppingCartLoaded{
        /**
         * Il metodo permette di notificare il risultato dell'ottenimento della
         * lista della spesa
         * @param mealsMap mappa contenente nome del pasto e quantità in grammi
         */
        void onShoppingCartLoaded(Map<String, Integer> mealsMap);
    }

    /**
     * Interfaccia che permette di effettuare le operazioni di controllo e inserimento di un
     * pasto nel diario alimentare
     */
    public interface MealRegistered{
        /**
         * Il metodo permette di notificare quali pasti sono stati inseriti nella dieta per il giorno corrente
         * @param breakfastAvailable indica la presenza della dieta della colazione
         * @param lunchAvailable indica la presenza nella dieta del pranzo
         * @param dinnerAvailable indica la presenza nella dieta della cena
         */
        void giveMealsAvailable(boolean breakfastAvailable, boolean lunchAvailable, boolean dinnerAvailable);

        /**
         * Il metodo indica l'avvenuto succesos della registrazione di un pasto
         * @param success true se il pasto è stato registrato, false altrimenti
         *
         */
        void onMealRegistered(boolean success);

        /**
         * Il metodo permette di notificare l'avvenuta registrazione dei pasti nella giornata corrente
         * @param breakfastAvailable indica la registrazione o meno della colazione
         * @param lunchAvailable indica la registrazione o meno del pranzo
         * @param dinnerAvailable indica la registrazione o meno della cena
         */
        void mealsRegistered(boolean breakfastAvailable, boolean lunchAvailable, boolean dinnerAvailable);
    }


    private static boolean sBreakfastAvailable;
    private static boolean sLunchAvailable;
    private static boolean sDinnerAvailable;

    private static List<String> sMealsNameList;
    private static Map<String, Integer> sMealsMap;


    /**
     * Il metodo permette di ottenere tutti i pasti presenti nel database
     * @param callback implementazione dell'interfaccia di callback necessaria per la risposta
     */
    public static void loadAll(MealsLoaded callback){
        if(sMealsNameList != null)
            callback.onMealsLoaded(sMealsNameList);
        else
            sMealsNameList = new ArrayList<>();

        FirebaseFirestore.getInstance().collection(Keys.Collections.MEALS).get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        QuerySnapshot  snapshot = task.getResult();
                        for(DocumentSnapshot doc : snapshot.getDocuments())
                            sMealsNameList.add(doc.getString(Meal.MealKeys.NAME));

                        callback.onMealsLoaded(sMealsNameList);
                    }
                });
    }

    /**
     * Il metodo permette di realizzare il carrello della spesa per i giorni necessari
     * @param clientMail mail del cliente
     * @param daysToQuery giorni della settimana da aggiungere al carrello
     * @param callback implementazione dell'interfaccia di callback necessaria per la risposta
     */
    public static void fetchShoppingCart(String clientMail,List<String> daysToQuery, ShoppingCartLoaded callback) {
        if (sMealsMap != null)
            sMealsMap.clear();
        else
            sMealsMap = new HashMap<>();


        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection(Keys.Collections.USERS)
                .document(clientMail).collection(Keys.Collections.DIET);

        for(String day: daysToQuery){
            collectionReference.document(day).collection(Keys.Collections.MEALS).get()
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            QuerySnapshot qs = task.getResult();
                            if (qs != null && !qs.isEmpty()) {
                                List<DocumentSnapshot> documents = qs.getDocuments();
                                for (DocumentSnapshot document : documents) {
                                    if (!sMealsMap.containsKey(document.getString(Meal.MealKeys.NAME)))
                                        sMealsMap.put(document.getString(Meal.MealKeys.NAME), document.get(Meal.MealKeys.QUANTITY, Integer.class));
                                    else {
                                        Integer temp = sMealsMap.get(document.getString(Meal.MealKeys.NAME));
                                        temp += document.get(Meal.MealKeys.QUANTITY, Integer.class);
                                        sMealsMap.put(document.getString(Meal.MealKeys.NAME), temp);
                                    }
                                }
                                callback.onShoppingCartLoaded(sMealsMap);
                            }
                        }
                    });
        }
        callback.onShoppingCartLoaded(sMealsMap);
    }

    /**
     * Il metodo permette di registrare l'avvenuto consumo di un pasto
     * @param clientMail mail del cliente
     * @param day giorno della settimana
     * @param mealType tipo di pasto (COLAZIONE = 0, PRANZO = 2, CENA = 4)
     * @param date data del pasto
     * @param callback implementazione dell'interfaccia di callback necessaria per la risposta
     */
    public static void registerMeal(String clientMail, String day, int mealType, String date, MealRegistered callback){

        DocumentReference userRef = FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(clientMail);

        userRef.collection(Keys.Collections.DIET).document(day).collection(Keys.Collections.MEALS)
                .whereEqualTo(Meal.MealKeys.MEAL_TYPE, mealType).get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        QuerySnapshot qs = task.getResult();
                        if(!qs.isEmpty()){
                            for(Meal meal : qs.toObjects(Meal.class))
                                userRef.collection(Keys.Collections.REGISTERED_MEALS).document(date)
                                    .collection(Keys.Collections.MEALS).document().set(meal);
                            callback.onMealRegistered(true);
                        }
                    }else
                        callback.onMealRegistered(false);
        });
    }


    /**
     * Il metodo permette di controllare quali pasti sono presenti nella dieta di un dato cliente
     * @param clientMail mail del cliente
     * @param day giorno della settimana
     * @param callback implementazione dell'interfaccia di callback necessaria per la risposta
     */
    public static void checkAvailableMealsForClient(String clientMail, String day, MealRegistered callback){
        FirebaseFirestore instance = FirebaseFirestore.getInstance();
        CollectionReference ref = instance.collection(Keys.Collections.USERS).document(clientMail)
            .collection(Keys.Collections.DIET).document(day).collection(Keys.Collections.MEALS);

        ref.whereEqualTo(Meal.MealKeys.MEAL_TYPE, Meal.MealType.BREAKFAST).get()
        .addOnCompleteListener(breakfastTask -> {
            if(breakfastTask.isSuccessful()){
                sBreakfastAvailable = breakfastTask.getResult().getDocuments().size() > 0;

                ref.whereEqualTo(Meal.MealKeys.MEAL_TYPE, Meal.MealType.LUNCH).get()
                        .addOnCompleteListener(lunchTask -> {
                           if(lunchTask.isSuccessful()) {
                               sLunchAvailable = lunchTask.getResult().getDocuments().size() > 0;

                               ref.whereEqualTo(Meal.MealKeys.MEAL_TYPE, Meal.MealType.DINNER).get()
                                       .addOnCompleteListener(dinnerTask -> {
                                           if (dinnerTask.isSuccessful()) {
                                               sDinnerAvailable = dinnerTask.getResult().getDocuments().size() > 0;
                                           }

                                           callback.giveMealsAvailable(sBreakfastAvailable, sLunchAvailable, sDinnerAvailable);
                                       });
                           }
                        });
            }
        });
    }


    /**
     * Il metodo permette di registrare quali pasti sono stati consumati dal cliente nella giornata corrente
     * @param clientMail mail del cliente
     * @param today giorno della settimana, IN INGLESE
     * @param callback implementazione dell'interfaccia di callback necessaria per la risposta
     */
    public static void checkRegisteredMeals(String clientMail, String today, MealRegistered callback){
        FirebaseFirestore instance = FirebaseFirestore.getInstance();
        CollectionReference ref = instance.collection(Keys.Collections.USERS).document(clientMail)
                .collection(Keys.Collections.REGISTERED_MEALS).document(today).collection(Keys.Collections.MEALS);


        ref.whereEqualTo(Meal.MealKeys.MEAL_TYPE, Meal.MealType.BREAKFAST).get()
                .addOnCompleteListener(breakfastTask -> {
                    if(breakfastTask.isSuccessful()){
                        sBreakfastAvailable = breakfastTask.getResult().getDocuments().size() == 0;

                        ref.whereEqualTo(Meal.MealKeys.MEAL_TYPE, Meal.MealType.LUNCH).get()
                                .addOnCompleteListener(lunchTask -> {
                                    if(lunchTask.isSuccessful()) {
                                        sLunchAvailable = lunchTask.getResult().getDocuments().size() == 0;

                                        ref.whereEqualTo(Meal.MealKeys.MEAL_TYPE, Meal.MealType.DINNER).get()
                                                .addOnCompleteListener(dinnerTask -> {
                                                    if (dinnerTask.isSuccessful()) {
                                                        sDinnerAvailable = dinnerTask.getResult().getDocuments().size() == 0;
                                                    }

                                                    callback.mealsRegistered(sBreakfastAvailable, sLunchAvailable, sDinnerAvailable);
                                                });
                                    }
                                });
                    }
                });
    }

}
