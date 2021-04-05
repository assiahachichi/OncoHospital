package fr.oncohospital.ui.fragmentSettings;

import android.content.res.Resources;

import fr.oncohospital.R;

/**
 * Created by Assia HACHICHI on 18/03/2021
 */
public enum SettingsElement {


    NOTIFICATIONS(R.string.recevoir_notification),
    RDV(R.string.utiliser_agenda),
    TREATMENT(R.string.ajouter_traitement_agenda),
    RAPPELS(R.string.ajouter_rappels_agenda),
    COLOR_RDV(R.string.choisir_couleur_rdv),
    COLOR_TREATMENT(R.string.choisir_couleur_traitement),
    COLOR_ACTIVITY(R.string.choisir_couleur_activity);

    final private int value;
    private final int index;

    SettingsElement(int value) {
        index = this.ordinal() ;
        this.value = value;
    }


    public String getValue(Resources res){
        return res.getString(value);
    }
    public int getIndex() {
        return index;
    }


}
