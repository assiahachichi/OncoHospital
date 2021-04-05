package fr.oncohospital.ui.myCalendarNotification;

import android.graphics.Color;

import androidx.annotation.NonNull;

/**
 * Created by Assia HACHICHI on 01/03/2021
 */
public enum TypeEvent {
    RDV(Color.YELLOW),
    TRAITEMENT(Color.BLUE),
    ACTIVITY(Color.GREEN);

    int color;
    TypeEvent(int color){
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
