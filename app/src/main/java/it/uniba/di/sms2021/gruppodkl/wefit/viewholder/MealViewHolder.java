package it.uniba.di.sms2021.gruppodkl.wefit.viewholder;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Meal;


public class MealViewHolder extends RecyclerView.ViewHolder {

    public interface CoachDietCallbackInterface{
        void deleteMeal(int position);
    }

    private TextView mMealQuantity;
    private TextView mMealName;
    private CoachDietCallbackInterface mCallback;
    private ImageButton mDeleteMealButton;


    public MealViewHolder(@NonNull View itemView) {
        super(itemView);

        mMealName = itemView.findViewById(R.id.meal_name);
        mMealQuantity = itemView.findViewById(R.id.meal_quantity);
    }

    public MealViewHolder(View itemView, CoachDietCallbackInterface callback){
        this(itemView);
        mCallback = callback;
        mDeleteMealButton = itemView.findViewById(R.id.delete_meal_button);
        mDeleteMealButton.setOnClickListener(v -> mCallback.deleteMeal(getAdapterPosition()));
    }

    public void setValues(Meal meal){
        mMealName.setText(Meal.convertName(meal.name, itemView.getContext()));
        mMealQuantity.setText(meal.convertQuantity());
    }








}
