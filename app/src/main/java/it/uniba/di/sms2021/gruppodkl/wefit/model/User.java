package it.uniba.di.sms2021.gruppodkl.wefit.model;

import android.graphics.Bitmap;



public class User {

    public interface UserKeys{
        String FULL_NAME = "fullName";
        String BIRTH_DATE ="birthDate";
        String GENDER = "gender";
        String IMAGE = "image";
        String ROLE = "role";
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



}


