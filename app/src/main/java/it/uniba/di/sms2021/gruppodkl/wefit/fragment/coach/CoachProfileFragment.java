package it.uniba.di.sms2021.gruppodkl.wefit.fragment.coach;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.Map;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.coach.CoachProfileContract;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppodkl.wefit.model.User;
import it.uniba.di.sms2021.gruppodkl.wefit.presenter.coach.CoachProfilePresenter;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.Keys;


public class CoachProfileFragment extends Fragment implements CoachProfileContract.View, User.MyImageBitmapCallback {
    public static final String TAG = CoachProfileFragment.class.getSimpleName();

    private boolean mHasImageChanged = false;
    private int mDialogElementChosen;
    private CoachProfileActivity mActivity;
    private Coach mCoach;
    private CoachProfileContract.Presenter mPresenter;

    private ImageView mProfileImage;
    private ImageView mEditImage;
    private ImageView mEditFullName;
    private ImageView mEditGender;

    private CheckBox mIsPersonalTrainer;
    private CheckBox mIsDietician;

    private EditText mFullName;
    private EditText mGender;

    private ImageView mNoCertificationAttachedImage;
    private ImageView mCertificationAlreadyAttachedImage;
    private TextView mEmail;

    private Button mUpdateButton;



    /**
     * Interfaccia che contiene tutti i metodi che l'activity in cui si desidera
     * utilizzare il fragment deve contenere
     */
    public interface CoachProfileActivity extends WeFitApplication.CallbackOperations {
        void changeImage();
        int FILE_RECEIVED_CODE = 126;
        int IMAGE_RECEIVED_CODE = 777;
    }

    public CoachProfileFragment(){}


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof CoachProfileActivity)
            mActivity = (CoachProfileActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout =  inflater.inflate(R.layout.coach_profile_fragment, container, false);

        mPresenter = new CoachProfilePresenter(this);
        mCoach = (Coach) ((WeFitApplication) getActivity().getApplicationContext()).getUser();
        bind(layout);
        setListeners();
        setValues();

        return layout;
    }

    /**
     * Il metodo permette di effettuare il binding degli elementi della view
     * @param view view
     */
    private void bind(View view){
        ((WeFitApplication) getActivity().getApplicationContext()).setToolbar(view, mActivity);
        mProfileImage =  view.findViewById(R.id.profile_image);
        mEditImage = view.findViewById(R.id.edit_pfp);

        mFullName = view.findViewById(R.id.profile_full_name);
        mEditFullName = view.findViewById(R.id.edit_full_name);


        mFullName.setClickable(false);


        mGender = view.findViewById(R.id.profile_gender_text);
        mEditGender = view.findViewById(R.id.edit_gender);

        mIsPersonalTrainer = view.findViewById(R.id.check_box_pt);
        mIsDietician = view.findViewById(R.id.check_box_dietician);

        mNoCertificationAttachedImage = view.findViewById(R.id.send_certification);
        mCertificationAlreadyAttachedImage = view.findViewById(R.id.check_icon);

        mUpdateButton = view.findViewById(R.id.btn_save_profile_update);

        mEmail = view.findViewById(R.id.profile_email);
    }

    /**
     * Il metodo permette di impostare tutti i listener necessari
     */
    private void setListeners(){
        mEditImage.setOnClickListener(v -> {
            mActivity.changeImage();
            checkIfButtonIsActivated();
        });

        mEditFullName.setOnClickListener(v -> {
            mFullName.setEnabled(true);
            mFullName.setClickable(true);
            mFullName.setFocusable(true);
            mFullName.setFocusableInTouchMode(true);
            checkIfButtonIsActivated();
        });

        mEditGender.setOnClickListener(v -> {
            showGenderDialog();
            checkIfButtonIsActivated();
        });

        mIsDietician.setOnClickListener(v -> checkIfButtonIsActivated());
        mIsPersonalTrainer.setOnClickListener(v -> checkIfButtonIsActivated());
    }

    private void setValues(){
        if(mCoach.image != null)
            if(!mCoach.isBitmapImageAvailable())
                mCoach.createImageBitmap(this);
            else
                mProfileImage.setImageBitmap(mCoach.getImageBitmap());

        String gender = mCoach.gender.equals(Keys.Gender.MALE) ? getResources().getString(R.string.male) : getResources().getString(R.string.female);

        mFullName.setText(mCoach.fullName);
        mGender.setText(gender);
        mEmail.setText(mCoach.email);

        mIsPersonalTrainer.setChecked(mCoach.isPersonalTrainer);
        mIsDietician.setChecked(mCoach.isDietist);

        if(mCoach.certificationUri != null){
            mNoCertificationAttachedImage.setVisibility(View.GONE);
            mCertificationAlreadyAttachedImage.setVisibility(View.VISIBLE);
        } else{
            mNoCertificationAttachedImage.setOnClickListener(v -> openFindFile());
        }
    }



    /**
     * Il metodo permette di controllare se il
     * tasto per effettare l'update del profilo è attivo
     */
    private void checkIfButtonIsActivated(){
        if(mUpdateButton.getVisibility() == View.INVISIBLE){
            mUpdateButton.setVisibility(View.VISIBLE);
            mUpdateButton.setFocusable(true);
            mUpdateButton.setClickable(true);

            mUpdateButton.setOnClickListener(v -> updateInfo());
        }
    }

    /**
     * Il metodo permette di effettuare l'update delle informazioni contenute sul profilo
     */
    private void updateInfo() {
        mUpdateButton.setClickable(false);
        boolean anyChange = false;
        Map<String, Object> map = new HashMap<>();
        //fetch dati
        String fullName = mFullName.getText().toString().trim();
        String gender = mGender.getText().toString().equals(getResources().getString(R.string.male)) ? Keys.Gender.MALE : Keys.Gender.FEMALE;
        boolean isPersonalTrainer = mIsPersonalTrainer.isChecked();
        boolean isDietist = mIsDietician.isChecked();

        if(!TextUtils.isEmpty(fullName) && !fullName.equalsIgnoreCase(mCoach.fullName)){
            map.put(Coach.CoachKeys.FULL_NAME, fullName);
            mCoach.fullName = fullName;
            anyChange = true;
        }

        if(! gender.equals(mCoach.gender)) {
            map.put(Coach.CoachKeys.GENDER, gender);
            mCoach.gender = gender;
            anyChange = true;
        }

        if(isPersonalTrainer != mCoach.isPersonalTrainer){
            mCoach.isPersonalTrainer = isPersonalTrainer;
            anyChange = true;
            map.put(Coach.CoachKeys.IS_PERSONAL_TRAINER, isPersonalTrainer);
        }

        if(isDietist != mCoach.isDietist){
            mCoach.isDietist = isDietist;
            anyChange = true;
            map.put(Coach.CoachKeys.IS_DIETIST, isDietist);
        }
        String msg;

        if(anyChange){
            mPresenter.updateCoach(map, mCoach);
            msg = getResources().getString(R.string.update_successful);
        } else {
            if(mHasImageChanged)
                msg = getResources().getString(R.string.image_changed_string);
            else
                msg = getResources().getString(R.string.nothing_to_update);
        }
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        mHasImageChanged = false;
        mUpdateButton.setClickable(true);
    }


    /**
     * Il metodo permette di gestire il salvataggio di una immagine
     * e l'aggiornamento della corrispondente image view
     *
     * @param imageUri uri dell'immagine da visualizzare
     */
    public void onImageReceived(Uri imageUri) {
        mProfileImage.setImageURI(imageUri);
        mHasImageChanged = true;
        mPresenter.saveImage(imageUri, mCoach);
    }

    /**
     * Il metodo permette di creare il dialog per la scelta del genere
     */
    private void showGenderDialog(){
        mDialogElementChosen = mGender.getText().toString().equals(getResources().getString(R.string.male)) ? 0 : 1;
        String[] genders = {getResources().getString(R.string.male), getResources().getString(R.string.female)};

        new AlertDialog.Builder(getActivity()).setTitle(getResources().getString(R.string.gender))
                .setSingleChoiceItems(genders, mDialogElementChosen, ((dialog, which) -> mDialogElementChosen = which))
                .setPositiveButton(getResources().getString(R.string.confirm), (dialog, which) -> {

                     if(mDialogElementChosen == 0)
                        mGender.setText(getResources().getString(R.string.male));
                    else
                        mGender.setText(getResources().getString(R.string.female));
                })
                .setNegativeButton(getResources().getString(R.string.back_button), null)
                .create().show();
    }

    /**
     * Il metodo permette di ottenere l'estensione di un file, fornito in input il proprio URI
     *
     * @param uri uri del file di cui si vuole conoscere l'estensione
     * @return Stringa contenente l'estensione del file
     */
    @Override
    public String getFileExtension(Uri uri){
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return  mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    @Override
    public void handleCallback() {
        mProfileImage.setImageBitmap(mCoach.getImageBitmap());
    }

    private void openFindFile(){
        mNoCertificationAttachedImage.setClickable(false);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf, image/*");
        startActivityForResult(intent, CoachProfileActivity.FILE_RECEIVED_CODE);
    }

    public void onFileReceived(String file){
        mCoach.certificationUri = file;

        mPresenter.uploadFile(mCoach);

        if(mCoach.certificationUri != null){
            mNoCertificationAttachedImage.setVisibility(View.GONE);
            mCertificationAlreadyAttachedImage.setVisibility(View.VISIBLE);
            mNoCertificationAttachedImage.setOnClickListener(null);
        }else
            mNoCertificationAttachedImage.setClickable(true);
    }

}