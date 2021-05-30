package it.uniba.di.sms2021.gruppodkl.wefit.fragment;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.fragment.ProfileFragmentContract;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;
import it.uniba.di.sms2021.gruppodkl.wefit.presenter.fragment.ProfileFragmentPresenter;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.Keys;


public class ProfileFragment extends Fragment implements ProfileFragmentContract.View {

    public static final String TAG = ProfileFragment.class.getSimpleName();

    private static final int GENDER_ALERT_DIALOG = 0;
    private static final int OBJECTIVE_ALERT_DIALOG = 1;
    private boolean mHasImageChanged = false;
    private int dialogElementChosen;


    /**
     * Interfaccia che contiene tutti i metodi che l'activity in cui si desidera
     * utilizzare il fragment deve contenere
     */
    public interface ProfileFragmentActivity{
       void changeImage();
       int IMAGE_RECEIVED_CODE = 777;
    }

    private Client mUser;
    private ProfileFragmentActivity mActivity;
    private ProfileFragmentContract.Presenter mPresenter;

    private ImageView mProfilePicture;
    private ImageView mEditProfilePicture;

    private EditText mFullNameEditText;
    private ImageView mEditFullName;

    private EditText mGenderEditText;
    private ImageView mEditGender;

    private EditText mHeightEditText;
    private ImageView mEditHeight;

    private EditText mObjectiveEditText;
    private ImageView mEditObjective;

    private Button mUpdateButton;




    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof ProfileFragmentActivity)
            mActivity = (ProfileFragmentActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_client_profile, container, false);

        FragmentActivity activity = getActivity();
        if(activity != null)
            mUser = (Client) ((WeFitApplication) activity.getApplicationContext()).getUser();

        mPresenter = new ProfileFragmentPresenter(this);

        bind(layout);
        setListeners();

        return layout;
    }

    /**
     * Il metodo permette di effettuare il binding degli elementi della view
     *
     * @param view La view da cui prendere i riferimenti
     */
    private void bind(View view){
        mProfilePicture = view.findViewById(R.id.profile_image);
        mEditProfilePicture = view.findViewById(R.id.edit_pfp);

        mFullNameEditText = view.findViewById(R.id.profile_name_edit_text);
        mEditFullName= view.findViewById(R.id.profile_edit_name);

        mGenderEditText= view.findViewById(R.id.profile_gender_text);
        mEditGender= view.findViewById(R.id.edit_gender);

        mHeightEditText= view.findViewById(R.id.profile_height_edit);
        mEditHeight= view.findViewById(R.id.profile_edit_height);

        mObjectiveEditText= view.findViewById(R.id.profile_objective_edit);
        mEditObjective= view.findViewById(R.id.edit_objective);

        if(mUser.image != null){
            if(!mUser.isBitmapImageAvailable())
                mUser.createImageBitmap();

            mProfilePicture.setImageBitmap(mUser.getImageBitmap());
        }

        String gender = mUser.gender.equals(Keys.Gender.MALE) ? getResources().getString(R.string.male) : getResources().getString(R.string.female);
        String objective;

        if(mUser.objective.equals(Keys.Objectives.LOSE_WEIGHT)){
            objective = getResources().getString(R.string.lose_weight_objective);
        } else {
            if(mUser.objective.equals(Keys.Objectives.FIT_OBJECTIVE))
                objective = getResources().getString(R.string.fit_objective);
            else
                objective = getResources().getString(R.string.gain_mass_objective);
        }


        mFullNameEditText.setText(mUser.fullName);
        mGenderEditText.setText(gender);
        mHeightEditText.setText(Integer.valueOf(mUser.height).toString());
        mObjectiveEditText.setText(objective);

        // necessaria solo per settare i testi degli attributi non modificabili
        TextView temp = view.findViewById(R.id.profile_email);
        temp.setText(mUser.email);

        temp = view.findViewById(R.id.profile_weight);
        temp.setText(String.format("%.2f",mUser.weight));

        mUpdateButton = view.findViewById(R.id.btn_save_profile_update);

        mFullNameEditText.setClickable(false);
        mHeightEditText.setClickable(false);
    }

    /**
     * Il metodo permette di impostare tutti i listeners necessari
     */
    private void setListeners(){
        mEditProfilePicture.setOnClickListener(v -> {
            mActivity.changeImage();
            checkIfButtonIsActivated();});

        mEditFullName.setOnClickListener(v -> {
            Log.d("AOO","cliccato");
            makeEditTextFocused(mFullNameEditText);
            checkIfButtonIsActivated();
        });

        mEditHeight.setOnClickListener(v -> {
            makeEditTextFocused(mHeightEditText);
            checkIfButtonIsActivated();
        });

        mEditGender.setOnClickListener(v ->{
            showUpdateDialog(GENDER_ALERT_DIALOG);
            checkIfButtonIsActivated();
        });

        mEditObjective.setOnClickListener(v ->{
            showUpdateDialog(OBJECTIVE_ALERT_DIALOG);
            checkIfButtonIsActivated();
        });

    }

    /**
     * Il metodo permette di rendere cliccabile una EditText
     *
     * @param editText EditText di cui si vuol editare il valore
     */
    private void makeEditTextFocused(EditText editText) {
        if(!editText.isClickable()) {
            editText.setClickable(true);
            editText.setFocusable(true);
            editText.requestFocus();
        }
    }

    /**
     * Il metodo permette di controllare se il
     * tasto per effettare l'update del profilo è attivo
     */
    private void checkIfButtonIsActivated(){
        Log.d("AOO", "Stiamo qua");
        if(mUpdateButton.getVisibility() == View.INVISIBLE){
            Log.d("AOO", "Cliccabile");
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
        boolean somethingChanged = false;
        String fullName = mFullNameEditText.getText().toString().trim();
        String gender = mGenderEditText.getText().toString();
        int height = Integer.parseInt(mHeightEditText.getText().toString());
        String objective = mObjectiveEditText.getText().toString();
        Map<String, Object> map = new HashMap<>();

        if(!TextUtils.isEmpty(fullName) && !fullName.equals(mUser.fullName)){
            map.put(Client.ClientKeys.FULL_NAME, fullName);
            mUser.fullName = fullName;
            somethingChanged = true;
        }

        if(gender.equals(getResources().getString(R.string.male)))
            gender = Keys.Gender.MALE;
        else
            gender = Keys.Gender.FEMALE;

        if(!gender.equals(mUser.gender)) {
            map.put(Client.ClientKeys.GENDER, gender);
            mUser.gender = gender;
            somethingChanged = true;
        }

        if(height != mUser.height){
            map.put(Client.ClientKeys.HEIGHT, height);
            mUser.height = height;
            somethingChanged = true;
        }

        if(objective.equals(getResources().getString(R.string.fit_objective)))
            objective = Keys.Objectives.FIT_OBJECTIVE;
        else
            if(objective.equals(getResources().getString(R.string.gain_mass_objective)))
                objective = Keys.Objectives.GAIN_MASS;
            else
                objective = Keys.Objectives.LOSE_WEIGHT;


        if(!objective.equals(mUser.objective)){
            map.put(Client.ClientKeys.OBJECTIVE, objective);
            mUser.objective = objective;
            somethingChanged = true;
        }

        if(somethingChanged) {
            Log.d("AOO", "QUALCOSA è cambiato");
            mPresenter.updateUser(map, mUser);
            Toast.makeText(getActivity(), getResources().getString(R.string.update_successful), Toast.LENGTH_SHORT).show();
        }else {
            if(mHasImageChanged) {
                Log.d("AOO", "Solo immagine");
                Toast.makeText(getActivity(), getResources().getString(R.string.image_changed_string), Toast.LENGTH_SHORT).show();
            }else {
                Log.d("AOO", "Tutto uguale");
                Toast.makeText(getActivity(), getResources().getString(R.string.nothing_to_update), Toast.LENGTH_SHORT).show();
            }
        }

        mHasImageChanged = false;
        mUpdateButton.setOnClickListener(null);
        mUpdateButton.setVisibility(View.INVISIBLE);
        mUpdateButton.setClickable(false);
        mUpdateButton.setFocusable(false);
    }

    /**
     * Il metodo permette di gestire il salvataggio di una immagine
     * e l'aggiornamento della corrispondente image view
     *
     * @param imageUri uri dell'immagine da visualizzare
     */
    public void onImageReveived(Uri imageUri) {
        mProfilePicture.setImageURI(imageUri);
        mHasImageChanged = true;
        mPresenter.saveImage(imageUri, mUser);
    }

    /**
     * Il metodo permette di mostrare gli alert dialog
     *
     *
     * @param type tipo di alert dialog da mostrare
     */
    private void showUpdateDialog(int type){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

         if(type == GENDER_ALERT_DIALOG){

             //Alert dialog per il genere

             builder.setTitle(getResources().getString(R.string.gender));
             String[] genders = {getResources().getString(R.string.male), getResources().getString(R.string.female)};

             dialogElementChosen = mGenderEditText.getText().toString().equals(getResources().getString(R.string.male)) ? 0 : 1;
             builder.setSingleChoiceItems(genders, dialogElementChosen, (dialog, which) -> dialogElementChosen = which);

             builder.setPositiveButton(getResources().getString(R.string.confirm), (dialog, which) -> {
                 if(dialogElementChosen == 0)
                     mGenderEditText.setText(getResources().getString(R.string.male));
                 else
                     mGenderEditText.setText(getResources().getString(R.string.female));
             });

         } else {

             //Alert dialog per l'obiettivo

             String[] objectives = {getResources().getString(R.string.fit_objective),
                     getResources().getString(R.string.gain_mass_objective), getResources().getString(R.string.lose_weight_objective)};

             // imposto il valore selezionato

             if(mObjectiveEditText.getText().toString().equals(getResources().getString(R.string.fit_objective))){
                 dialogElementChosen = 0;
             } else {
                 if(mObjectiveEditText.getText().toString().equals(getResources().getString(R.string.gain_mass_objective))){
                     dialogElementChosen = 1;
                 }else {
                     dialogElementChosen =2;
                 }
             }

             builder.setSingleChoiceItems(objectives, dialogElementChosen, (dialog, which) -> dialogElementChosen = which);

             builder.setPositiveButton(getResources().getString(R.string.confirm), (dialog, which) -> {
                 if(dialogElementChosen == 0)
                     mObjectiveEditText.setText(getResources().getString(R.string.fit_objective));
                 else
                     if(dialogElementChosen == 1)
                        mObjectiveEditText.setText(getResources().getString(R.string.gain_mass_objective));
                    else
                        mObjectiveEditText.setText(getResources().getString(R.string.lose_weight_objective));
             });
         }

        builder.setNegativeButton(getResources().getString(R.string.back_button), null);
        builder.create().show();
    }

    /**
     * Il metodo permette di ottenere l'estensione di un file, fornito in input il proprio URI
     *
     * @param uri uri del file di cui si vuole conoscere l'estensione
     * @return Stringa contenente l'estensione del file
     */
    public String getFileExtension(Uri uri){
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return  mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }


}