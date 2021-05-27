package it.uniba.di.sms2021.gruppodkl.wefit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import it.uniba.di.sms2021.gruppodkl.wefit.contract.RegistrationActivityContract;
import it.uniba.di.sms2021.gruppodkl.wefit.presenter.RegistrationActivityPresenter;

public class RegistrationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, RegistrationActivityContract.View {

    public interface ViewActivated{
        String PERSONAL_INFO = "PERSONAL_INFO";
        String COACH_CLIENT = "COACH_CLIENT";
    }

    private RelativeLayout mPersonalDataLayout;
    private RegistrationActivityContract.Presenter mPresenter;
    private EditText mBirthDateEdit;
    private EditText mFirstNameEdit;
    private EditText mLastNameEdit;
    private RadioGroup mRadioGender;
    private Button mFirstForwardButton;

    private RelativeLayout mCoachClientLayout;
    private Button mTest;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mPresenter = new RegistrationActivityPresenter(this);
        this.bind();

        if(savedInstanceState != null){
            if(savedInstanceState.containsKey(ViewActivated.COACH_CLIENT)){
                setActivatedView(mCoachClientLayout, mPersonalDataLayout);
            }
        }

        this.setListeners();
    }


    private void bind(){
        mPersonalDataLayout = findViewById(R.id.layout_personal_data);
        mFirstNameEdit = findViewById(R.id.first_name_edit_text);
        mLastNameEdit = findViewById(R.id.last_name_text);
        mRadioGender = findViewById(R.id.radio_gender);
        mBirthDateEdit = findViewById(R.id.birth_date_edit_text);
        mFirstForwardButton = findViewById(R.id.forward_first_button);

        mCoachClientLayout = findViewById(R.id.layout_coach_client);
        mTest = findViewById(R.id.click);
    }

    private void setListeners(){

        mFirstForwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActivatedView(mCoachClientLayout, mPersonalDataLayout);
            }
        });


        //date picker
        mBirthDateEdit.setKeyListener(null);
        mBirthDateEdit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    showDatePicker();
                }
                return false;
            }
        });

        mTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActivatedView(mPersonalDataLayout, mCoachClientLayout);
            }
        });
    }

    private void showDatePicker(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month = (month % 12) + 1;
        String temp = dayOfMonth + "/" + month + "/" + year;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try{
            date = simpleDateFormat.parse(temp);
        } catch (ParseException e){
            Log.d("AOO", "Sta un problema");
        }
        DateFormat dateFormatter = android.text.format.DateFormat.getDateFormat(this);
        assert date != null;
        mBirthDateEdit.setText(dateFormatter.format(date));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if(mCoachClientLayout.getVisibility() == View.VISIBLE){
            outState.putString(ViewActivated.COACH_CLIENT, ViewActivated.COACH_CLIENT);
        } else {
            outState.putString(ViewActivated.PERSONAL_INFO, ViewActivated.PERSONAL_INFO);
        }
    }

    private void setActivatedView(RelativeLayout activeLayout, RelativeLayout disappearingLayout){
        disappearingLayout.setVisibility(View.GONE);
        activeLayout.setVisibility(View.VISIBLE);
    }

}