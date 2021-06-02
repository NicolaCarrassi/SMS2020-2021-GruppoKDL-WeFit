package it.uniba.di.sms2021.gruppodkl.wefit.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import it.uniba.di.sms2021.gruppodkl.wefit.presenter.LoginActivityPresenter;

public class Request {

    public interface RequestImageBitmapCallback{
        void handleCallback();
    }

    public String email;
    public String fullName;
    public String image;

    private Bitmap imageBitmap = null;

    public Request(){
        //empty constructor for firebase
    }

    public Request(String email, String clientName, String clientImage){
        this.email = email;
        this.fullName = clientName;
        this.image = clientImage;
    }

    public boolean isBitmapImageAvailable(){
        return imageBitmap != null;
    }

    public Bitmap getImageBitmap(){
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap bitmap){this.imageBitmap = bitmap;}


    public void createImageBitmap(Request.RequestImageBitmapCallback callback){
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
