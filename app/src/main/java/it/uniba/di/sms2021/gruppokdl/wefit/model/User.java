package it.uniba.di.sms2021.gruppokdl.wefit.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import it.uniba.di.sms2021.gruppokdl.wefit.presenter.LoginActivityPresenter;


public class User {

    public interface UserKeys{
        String FIRST_NAME = "firstName";
        String LAST_NAME = "lastName";
        String EMAIL = "email";
        String PASSWORD = "password";
        String FULL_NAME = "fullName";
        String BIRTH_DATE ="birthDate";
        String GENDER = "gender";
        String IMAGE = "image";
        String ROLE = "role";
    }

    public interface MyImageBitmapCallback{
        void handleCallback();
    }

    public String fullName;
    public String email;
    public String birthDate;
    public String gender;
    public String image;
    public String role;

    private Bitmap imageBitmap = null;


    public User(){

    }

    public User(String fullName, String email, String birthDate, String gender, String role){
        this.fullName = fullName;
        this.email = email;
        this.birthDate = birthDate;
        this.gender = gender;
        this.role = role;
    }


    public void setImage(String imageUri) {
        this.image = imageUri;
    }

    public void setImageBitmap(Bitmap bitmap){
        this.imageBitmap = bitmap;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public boolean isBitmapImageAvailable(){
        return imageBitmap !=  null;
    }


    public void createImageBitmap(MyImageBitmapCallback callback){
        if(image != null) {

            StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(image);
            try {
                File localFile = File.createTempFile("images", "jpg");
                storageRef.getFile(localFile).addOnSuccessListener(taskSnapshot -> {
                    this.imageBitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    callback.handleCallback();
                });
            } catch (IOException e) {
                Log.d(LoginActivityPresenter.class.getSimpleName(), e.getLocalizedMessage());
            }

        }
    }

}


