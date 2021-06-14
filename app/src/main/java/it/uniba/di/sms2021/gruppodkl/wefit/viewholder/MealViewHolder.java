package it.uniba.di.sms2021.gruppodkl.wefit.viewholder;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Meal;


public class MealViewHolder extends RecyclerView.ViewHolder {

    /**
     * Interfaccia di callback per le operazioni del coach
     */
    public interface CoachDietCallbackInterface{
        /**
         * Il metodo permette di cancellare un pasto, data la sua posizione
         * @param position posizione del pasto da eliminare
         */
        void deleteMeal(int position);
    }

    private final TextView mMealQuantity;
    private final TextView mMealName;
    private CoachDietCallbackInterface mCallback;


    public MealViewHolder(@NonNull View itemView) {
        super(itemView);

        mMealName = itemView.findViewById(R.id.meal_name);
        mMealQuantity = itemView.findViewById(R.id.meal_quantity);
    }

    public MealViewHolder(View itemView, CoachDietCallbackInterface callback){
        this(itemView);
        mCallback = callback;
        ImageButton mDeleteMealButton = itemView.findViewById(R.id.delete_meal_button);
        mDeleteMealButton.setOnClickListener(v -> mCallback.deleteMeal(getAdapterPosition()));
    }

    /**
     * Il metodo permette di associare i valori del model al viewholder
     * @param meal model
     */
    public void setValues(Meal meal){
        mMealName.setText(Meal.convertName(meal.name, itemView.getContext()));
        mMealQuantity.setText(meal.convertQuantity());
    }








}
