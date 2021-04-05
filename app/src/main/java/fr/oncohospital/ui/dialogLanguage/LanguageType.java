package fr.oncohospital.ui.dialogLanguage;

import fr.oncohospital.R;

/**
 * Created by Assia HACHICHI on 02/04/2021
 */
public enum LanguageType {

    ENGLISH("en","English", R.mipmap.ic_anglais),
    FRENCH("fr","Français", R.mipmap.ic_francais);



    private String nameLanguage;
    final private String language;
    private final int index;
    private int flag;

    LanguageType(String language,String nameLanguage,  int flag) {
        this.flag = flag;
        this.nameLanguage = nameLanguage;
        this.language = language;
        index = this.ordinal() ;

    }

    public static int getFlag(String language){
        LanguageType[] tab = LanguageType.values();
        int i = 0, taille = tab.length;
        while (i<taille){
            if (tab[i].getLanguage().equals(language)) {return tab[i].getFlag();}
            i++;
        }
        return tab[0].getFlag();
    }
    public int getFlag() {
        return flag;
    }

    public String getNameLanguage() {
        return nameLanguage;
    }

    public String getLanguage(){
        return language;
    }
    public int getIndex() {
        return index;
    }


}
