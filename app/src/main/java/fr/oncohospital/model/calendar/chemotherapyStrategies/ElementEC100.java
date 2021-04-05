package fr.oncohospital.model.calendar.chemotherapyStrategies;

import android.content.res.Resources;

import fr.oncohospital.R;

/**
 * Created by Assia HACHICHI on 08/03/2021
 */
public enum ElementEC100 {
    PHARMACIE(R.string.PHARMACIE),
    ANALYSES(R.string.ANALYSES),
    PATCH(R.string.PATCH),
    EMEND125(R.string.EMEND125),
    EMEND801(R.string.EMEND801),
    EMEND802(R.string.EMEND802),
    PELMEG(R.string.PELMEG);

    final private int value;
    private final int index;

    ElementEC100(int value) {

        this.value = value;
        index = this.ordinal() + 1;


    }

    public String getValue(Resources res){
        return res.getString(value);
    }
    public int getIndex() {
        return index;
    }

}