package fr.oncohospital.ui.fragmentSettings;

/**
 * Created by Assia HACHICHI on 16/03/2021
 */
public class SettingsItem {
        private String mText1;
        private Boolean bSwitch;

    public SettingsItem(String text1, Boolean bSwitch) {
        mText1 = text1;
        this.bSwitch = bSwitch;
    }

    public void setText1(String text){
            mText1 = text;
        }

    public Boolean isCheckedSwitch() {
        return bSwitch;
    }

    public String getText1(){
            return mText1;
        }

}
