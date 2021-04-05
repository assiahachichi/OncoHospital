package fr.oncohospital.ui.fragmentContacts;

/**
 * Created by Assia HACHICHI on 06/03/2021
 */
public class ContactsItem {
    private int mImageResource;
    private String mText1;

    public ContactsItem(int imageResource, String text1){
        mImageResource = imageResource;
        mText1 = text1;
    }

    public void changeText1(String text){
        mText1 = text;
    }

    public int getImageResource(){
        return mImageResource;
    }

    public String getText1(){
        return mText1;
    }


}
