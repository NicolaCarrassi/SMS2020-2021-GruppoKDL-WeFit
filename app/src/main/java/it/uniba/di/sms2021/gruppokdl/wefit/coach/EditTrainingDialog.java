package it.uniba.di.sms2021.gruppokdl.wefit.coach;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;


import com.google.android.material.button.MaterialButton;

import it.uniba.di.sms2021.gruppokdl.wefit.R;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Training;
import it.uniba.di.sms2021.gruppokdl.wefit.utility.DayOfTheWeek;

public class EditTrainingDialog extends Dialog implements View.OnClickListener{


    /**
     * Interfaccia contenente le operazioni di callback per il
     * dialog
     */
    public interface EditTrainingDialogCallbacks{
        /**
         * Il seguente metodo permette di modificare un training
         * @param training training da modificare
         */
        void editTraining(Training training);
    }

    private final EditTrainingDialogCallbacks mTrainingCallbacks;
    private final Training mTraining;

    private EditText mTrainingNameEdit;
    private EditText mTrainingTimeEdit;
    private Spinner mDayOfTheWeekSpinner;
    private final Context mContext;


    public EditTrainingDialog(@NonNull Context context, final EditTrainingDialogCallbacks callback, Training training) {
        super(context);

        mTrainingCallbacks = callback;
        mTraining = training;
        mContext = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.coach_update_training_info_dialog);
        setCanceledOnTouchOutside(true);

        mTrainingNameEdit = findViewById(R.id.training_name_edit_text);
        mTrainingTimeEdit = findViewById(R.id.training_time_edit_text);
        mDayOfTheWeekSpinner = findViewById(R.id.spinner_training_day);

        MaterialButton mBtnCacnel = findViewById(R.id.btn_cancel);
        MaterialButton mBtnUpdate = findViewById(R.id.btn_update);

        mBtnCacnel.setOnClickListener(this);
        mBtnUpdate.setOnClickListener(this);

        //IMPOSTO VALORI DI DEFAULT
        mTrainingNameEdit.setText(mTraining.title);

        if(mTraining.time > 0) {
            String trainingTime = Integer.toString(mTraining.time);
            mTrainingTimeEdit.setText(trainingTime);
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.day_of_the_week, R.layout.spinner_layout);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDayOfTheWeekSpinner.setAdapter(adapter);
        mDayOfTheWeekSpinner.setSelection(mTraining.dayOfWeek);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_cancel)
            dismiss();
        else if (v.getId() == R.id.btn_update)
            updateTrainingInfo();
    }


    /**
     * Il seguente metodo permette di modificare le informazioni dell'allenamento
     */
    private void updateTrainingInfo(){
        boolean errors = false;
        int time = 0 ;

        if(TextUtils.isEmpty(mTrainingNameEdit.getText().toString())) {
            mTrainingNameEdit.setError(getContext().getResources().getString(R.string.error_training_name));
            errors = true;
        }

        if(TextUtils.isEmpty(mTrainingTimeEdit.getText().toString())){
            errors = true;
            mTrainingTimeEdit.setError(getContext().getResources().getString(R.string.error_training_time));
        } else{
            try {
                time = Integer.parseInt(mTrainingTimeEdit.getText().toString());
            } catch (NumberFormatException exc) {
                mTrainingTimeEdit.setError(getContext().getResources().getString(R.string.error_training_time));
                errors = true;
            }
        }

        if(!errors) {
            mTraining.title = mTrainingNameEdit.getText().toString();
            mTraining.time = time;
            mTraining.dayOfWeek = DayOfTheWeek.getStringValue((String) mDayOfTheWeekSpinner.getSelectedItem(), mContext);

            mTrainingCallbacks.editTraining(mTraining);
            dismiss();
        }
    }







}
