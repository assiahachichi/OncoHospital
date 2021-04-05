package fr.oncohospital.model.calendar.chemotherapyStrategies;

import android.content.res.Resources;

import fr.oncohospital.R;

/**
 * Created by Assia HACHICHI on 08/03/2021
 */
public enum ElementTaxotere {
    PHARMACIE(R.string.PHARMACIE),
    ANALYSES(R.string.ANALYSES),
    PATCH(R.string.PATCH),
    SOLUPRED_VEILLE_8(R.string.SOLUPRED_VEILLE_8),
    SOLUPRED_VEILLE_15(R.string.SOLUPRED_VEILLE_15),
    SOLUPRED_JOUR_8(R.string.SOLUPRED_JOUR_8),
    SOLUPRED_JOUR_15(R.string.SOLUPRED_JOUR_15),
    SOLUPRED_LELENDEMAIN_8(R.string.SOLUPRED_LELENDEMAIN_8),
    SOLUPRED_LELENDEMAIN_15(R.string.SOLUPRED_LELENDEMAIN_15),
    PELMEG(R.string.PELMEG);

    final private int value;
    private final int index;

    ElementTaxotere(int s) {
        value = s;
        index = this.ordinal() + 1;
    }

    public String getValue(Resources res){
        return res.getString(value);
    }
    public int getIndex() {
        return index;
    }
}