package it.uniba.di.sms2021.gruppodkl.wefit.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Meal;

public class ShoppingListItemViewHolder extends RecyclerView.ViewHolder {

    private final TextView mMealQuantity;
    private final TextView mMealName;


    public ShoppingListItemViewHolder(@NonNull View itemView) {
        super(itemView);

        mMealQuantity = itemView.findViewById(R.id.meal_quantity);
        mMealName = itemView.findViewById(R.id.meal_name);
    }

    /**
     * Il metodo permette di associare i valori della classe model al viewholder
     * @param meal oggetto della classe model
     */
    public void setValues(Meal meal){
        mMealName.setText(meal.name);
        mMealQuantity.setText(meal.convertQuantity());
    }




}
