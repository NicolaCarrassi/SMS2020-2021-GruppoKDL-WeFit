package it.uniba.di.sms2021.gruppodkl.wefit;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import it.uniba.di.sms2021.gruppodkl.wefit.contract.RegistrationActivityContract;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.fragment.RegistrationFragmentContract;
import it.uniba.di.sms2021.gruppodkl.wefit.fragment.ClientRegistrationFragment;
import it.uniba.di.sms2021.gruppodkl.wefit.fragment.CoachRegistrationFragment;
import it.uniba.di.sms2021.gruppodkl.wefit.model.User;
import it.uniba.di.sms2021.gruppodkl.wefit.presenter.RegistrationActivityPresenter;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.Keys;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.UtilityStrings;

public class RegistrationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        RegistrationActivityContract.View, CoachRegistrationFragment.CoachCallBackActivity {

    private static final String TAG = RegistrationActivity.class.getSimpleName();
    private static final int IMAGE_INTENT_CODE = 1;

    public interface ViewActivated{
        String PERSONAL_INFO = "PERSONAL_INFO";
        String COACH_CLIENT = "COACH_CLIENT";
    }

    private LinearLayout mPersonalDataLayout;
    private RegistrationActivityContract.Presenter mPresenter;
    private EditText mFirstNameEdit;
    private EditText mLastNameEdit;
    private RadioGroup mRadioGender;
    private EditText mBirthDateEdit;
    private EditText mEmailEdit;
    private EditText mPasswordEdit;
    private EditText mConfirmPasswordEdit;
    private Button mFirstForwardButton;

    private LinearLayout mCoachClientLayout;
    private RadioGroup mRadioRole;
    private Button mBackButton;
    private Button mRegisterButton;

    private boolean mHasBeenOpened = false;
    private RegistrationFragmentContract.View mFragmentView;


    private Uri mUri;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mPresenter = new RegistrationActivityPresenter(this);
        this.bind();

        if(savedInstanceState != null){
            if(savedInstanceState.containsKey(ViewActivated.COACH_CLIENT)){
                changeActiveLayout(mCoachClientLayout, mPersonalDataLayout);
            }
        }

        this.setListeners();
    }


    /**
     * Il metodo permette di collegare gli elementi del layout ad oggetti
     */
    private void bind(){
        mPersonalDataLayout = findViewById(R.id.layout_personal_data);
        mCoachClientLayout = findViewById(R.id.layout_coach_client);


        mFirstNameEdit = findViewById(R.id.first_name_edit_text);
        mLastNameEdit = findViewById(R.id.last_name_text);
        mRadioGender = findViewById(R.id.radio_gender);
        mBirthDateEdit = findViewById(R.id.birth_date_edit_text);
        mEmailEdit = findViewById(R.id.registration_email_edit_text);
        mPasswordEdit = findViewById(R.id.registration_password_edit_text);
        mConfirmPasswordEdit = findViewById(R.id.confirm_password_edit_text);

        mFirstForwardButton = findViewById(R.id.forward_first_button);

        mRadioRole = findViewById(R.id.radio_role);
        mBackButton = findViewById(R.id.back_button);
        mRegisterButton = findViewById(R.id.register_button);



    }

    /**
     * Il metodo permette di impostare tutti i listener necessari alla view
     */
    private void setListeners(){

        mFirstForwardButton.setOnClickListener(v -> changeActiveLayout(mCoachClientLayout, mPersonalDataLayout));


        //date picker
        mBirthDateEdit.setKeyListener(null);
        mBirthDateEdit.setOnTouchListener((v, event) -> {
            if(event.getAction() == MotionEvent.ACTION_UP){
                showDatePicker();
            }
            return false;
        });

        mBackButton.setOnClickListener(v -> changeActiveLayout(mPersonalDataLayout, mCoachClientLayout));

        mRadioRole.setOnCheckedChangeListener((group, checkedId) -> {
            boolean isClient = checkedId == R.id.radio_client;
            ClientRegistrationFragment clientRegistrationFragment;
            CoachRegistrationFragment coachRegistrationFragment;

                if(isClient) {
                    clientRegistrationFragment = new ClientRegistrationFragment();
                    mFragmentView = clientRegistrationFragment;

                    if(mHasBeenOpened)
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.register_anchor_point, clientRegistrationFragment).commit();
                    else {
                        mHasBeenOpened = true;
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.register_anchor_point, clientRegistrationFragment).commit();
                    }

                }else {
                    coachRegistrationFragment = new CoachRegistrationFragment();
                    mFragmentView = coachRegistrationFragment;

                    if(mHasBeenOpened)
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.register_anchor_point, coachRegistrationFragment).commit();
                    else {
                        mHasBeenOpened = true;
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.register_anchor_point, coachRegistrationFragment).commit();
                    }
                }
            });

        mPasswordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkifMatching();
            }
        });

        mConfirmPasswordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkifMatching();
            }
        });

        mRegisterButton.setOnClickListener(v -> fetchUserData());
    }

    /**
     * Il metodo permette di mostrare un date picker
     */
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
            Log.d(TAG, "Error in parsing");
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

    /**
     * Il metodo permette di impostare il layout che deve essere visibile all'utente
     *
     * @param activeLayout layout da impostare come attivo
     * @param disappearingLayout layout da impostare come invisibile
     */
    private void changeActiveLayout(ViewGroup activeLayout, ViewGroup disappearingLayout){
        disappearingLayout.setVisibility(View.GONE);
        activeLayout.setVisibility(View.VISIBLE);
    }

    /**
     * Il metodo permette di ottenere i dati inseriti nel form da parte dell'utente, controllando
     * che essi siano corretti, in caso di errore mostra all'utente che vi è un errore in quel campo
     */
    private void fetchUserData(){
        Map<String,String> userData = new HashMap<>();
        boolean isCorrect = true; //serve per capire che i dati inseriti siano corretti


        if(checkName(mFirstNameEdit))
            userData.put(Keys.RegistrationKeys.FIRST_NAME,mFirstNameEdit.getText().toString());
        else
            isCorrect = false;



        if(checkName(mLastNameEdit)){
            userData.put(Keys.RegistrationKeys.LAST_NAME, mLastNameEdit.getText().toString());
        } else {
            if(isCorrect)
                isCorrect = false;
        }

        if(!TextUtils.isEmpty(mBirthDateEdit.getText().toString())) {
           userData.put(Keys.RegistrationKeys.BIRTH_DATE, mBirthDateEdit.getText().toString());
        } else{
            if(isCorrect)
                isCorrect = false;

            mBirthDateEdit.setError(getResources().getString(R.string.date_error));
        }

        //GENDER
        if(checkRadioGroup(mRadioGender)){
            if(R.id.radio_male == mRadioGender.getCheckedRadioButtonId())
                userData.put(Keys.RegistrationKeys.GENDER,Keys.Gender.MALE);
            else
                userData.put(Keys.RegistrationKeys.GENDER,Keys.Gender.FEMALE);
        } else {
            if(isCorrect)
                isCorrect = false;
        }

        if(checkEmail())
            userData.put(Keys.RegistrationKeys.EMAIL, mEmailEdit.getText().toString());
        else {
            if(isCorrect)
                isCorrect = false;
        }
        if(passwordCheck())
            userData.put(Keys.RegistrationKeys.PASSWORD, mPasswordEdit.getText().toString());
        else {
            if(isCorrect)
                isCorrect = false;
        }

        if(isCorrect)
            fetchAddictionalData(userData);
         else {
            changeActiveLayout(mPersonalDataLayout, mCoachClientLayout);
            Toast.makeText(this, getResources().getString(R.string.error_general), Toast.LENGTH_LONG).show();
        }
    }


    /**
     * Il metodo permette di ottenere i dati presenti nel secondo relative layout
     * della View, quindi ruolo e dati specifici rispetto al ruolo.
     *
     * @param userData dati precedentemente ottenuti
     */
    private void fetchAddictionalData(Map<String,String> userData){
        boolean isCorrect = true;

        if(checkRadioGroup(mRadioRole)){
            if(R.id.radio_client == mRadioRole.getCheckedRadioButtonId())
                userData.put(Keys.RegistrationKeys.ROLE, Keys.Role.CLIENT);
            else
                userData.put(Keys.RegistrationKeys.ROLE,Keys.Role.COACH);
        } else
            isCorrect = false;


        if(isCorrect){
            isCorrect = mFragmentView.areCorrect();
            if(isCorrect){
                Map<String, String> addictionalData = new HashMap<>(mFragmentView.getAddictionalData());
                mPresenter.registerUser(userData,addictionalData);
            }
        }
    }



    /**
     * Il metodo permette di controllare che la password inserita sia corretta
     *
     * @return true in caso è corretta, false altrienti
     */
    private boolean passwordCheck(){
        boolean insertPassword = false;

        String password= mPasswordEdit.getText().toString();
        String confirmPassword = mConfirmPasswordEdit.getText().toString();

        if (!TextUtils.isEmpty(password) && (Pattern.compile(UtilityStrings.PASSOWRD_REGEX).matcher(password).matches())) {
            if(password.equals(confirmPassword))
                insertPassword = true;
            else{
                mPasswordEdit.setError(getResources().getString(R.string.error_password_not_matching));
                mConfirmPasswordEdit.setError(getResources().getString(R.string.error_password_not_matching));
            }

        } else
            mPasswordEdit.setError(getResources().getString(R.string.error_password));

        return insertPassword;
    }

    /**
     * Il metodo permette di controllare che l'utente abbia inserito una mail in modo corretto
     *
     * @return true se l'email è corretta, false altrimenti
     */
    private boolean checkEmail(){
        boolean insertData = false;
        String temp = mEmailEdit.getText().toString();

        if (!TextUtils.isEmpty(temp) && Patterns.EMAIL_ADDRESS.matcher(temp).matches())
            insertData = true;
         else
            mEmailEdit.setError(getResources().getString(R.string.error_email));


        return insertData;
    }

    /**
     * Il metodo permette di controllare che sia stato inserito un valore all'interno della checkbox e permette di controllare
     * che esso sia conforme alla espressione regolare
     *
     * @param editText EditText di cui si interessa conoscere se il valore è conforme all'espressione regolare
     * @return True se il valore inserito è corretto, False altrimenti
     */
    private boolean checkName(final EditText editText){
        boolean insertData = false;
        String str = editText.getText().toString();

        if(!TextUtils.isEmpty(str) && str.trim().matches(UtilityStrings.NAME_REGEX))
            insertData = true;
        else
            editText.setError(getResources().getString(R.string.name_error));

        return insertData;
    }

    /**
     * Il metodo permette di controllare che sia stata selezionata almeno una opzione dal RadioGroup
     *
     * @param radioGroup RadioGroup di cui si vuole sapere se è stato selezionato un elemento
     * @return True se è stato selezionato almeno un elemento, false altirmenti
     */
    private boolean checkRadioGroup(RadioGroup radioGroup){
        int radioButtonChecked = radioGroup.getCheckedRadioButtonId();
        boolean insertData = false;


        if(radioButtonChecked <= 0){
            int lastChild = radioGroup.getChildCount() -1;
            ((RadioButton)radioGroup.getChildAt(lastChild)).setError(getResources().getString(R.string.error_radio));
        } else
            insertData = true;

        return insertData;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_INTENT_CODE && resultCode == RESULT_OK && data != null && data.getData() != null){
            Log.d("AOO", data.getData().toString());
            mUri = data.getData();
        }
    }

    @Override
    public void openFindFile() {


        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf, image/*");
        startActivityForResult(intent, IMAGE_INTENT_CODE);
    }


    @Override
    public Uri getFileURI() {
        return mUri;
    }

    @Override
    public void onSuccess(User user) {
        ((WeFitApplication) getApplication()).setUser(user);
        if(user.role.equals(Keys.Role.CLIENT)){
            Intent intent = new Intent(this, MainActivityUser.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onFailure() {
        //TODO aggiungi stringa
        Toast.makeText(this, "Failed to register a user", Toast.LENGTH_SHORT).show();
    }

    public String getFileExtension(){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return  mime.getExtensionFromMimeType(contentResolver.getType(mUri));
    }

    /**
     * Il metodo permette di verificare che le due password inserite siano uguali
     */
    public void checkifMatching(){
        String passwordText = mPasswordEdit.getText().toString();
        String confirmPasswordText = mConfirmPasswordEdit.getText().toString();

        if(!TextUtils.isEmpty(passwordText) && !TextUtils.isEmpty(confirmPasswordText)){
            if(!passwordText.equals(confirmPasswordText)){
                mPasswordEdit.setError(getResources().getString(R.string.error_password_not_matching));
                mConfirmPasswordEdit.setError(getResources().getString(R.string.error_password_not_matching));
            } else {
                mPasswordEdit.setError(null);
                mConfirmPasswordEdit.setError(null);
            }
        }
    }



}