package it.uniba.di.sms2021.gruppodkl.wefit.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Meal;

public class ShoppingListItemViewHolder extends RecyclerView.ViewHolder {

    private TextView mMealQuantity;
    private TextView mMealName;


    public ShoppingListItemViewHolder(@NonNull View itemView) {
        super(itemView);

        mMealQuantity = itemView.findViewById(R.id.meal_quantity);
        mMealName = itemView.findViewById(R.id.meal_name);
    }

    public void setValues(Meal meal){
        mMealName.setText(meal.name);
        mMealQuantity.setText(meal.convertQuantity());
    }




}
