package fr.oncohospital.ui.dialogLanguage;

/**
 * Created by Assia HACHICHI on 18/03/2021
 */
public class DialogLanguageSingleRow {
    private  int flag;
    private  LanguageType languageType;
    private  String language;
    private String nameLanguage;
    private boolean selected;

    public DialogLanguageSingleRow(int flag, LanguageType languageType, String language, String nameLanguage, boolean selected) {
        this.flag = flag;
        this.languageType = languageType;
        this.language = language;
        this.nameLanguage = nameLanguage;
        this.selected = selected;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setLanguageType(LanguageType languageType) {
        this.languageType = languageType;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setNameLanguage(String nameLanguage) {
        this.nameLanguage = nameLanguage;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getFlag() {
        return flag;
    }

    public LanguageType getLanguageType() {
        return languageType;
    }

    public String getLanguage() {
        return language;
    }

    public String getNameLanguage() {
        return nameLanguage;
    }

    public boolean isSelected() {
        return selected;
    }
}
