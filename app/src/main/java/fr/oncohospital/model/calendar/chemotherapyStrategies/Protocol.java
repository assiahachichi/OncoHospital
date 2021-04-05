package fr.oncohospital.model.calendar.chemotherapyStrategies;

import android.content.res.Resources;
import android.graphics.Color;

import java.util.ArrayList;

import fr.oncohospital.model.calendar.javaJson2GoogleCalendar.Event;

/**
 * Created by Assia HACHICHI on 24/02/2021
 */
public  abstract class Protocol {

    protected static int colorTreatment = Color.BLUE;
    protected static int colorActivity = Color.GREEN;
    protected static long uneHeurMilli = 60*60*1000;
    protected static long unJourMilli = 24*uneHeurMilli;
    protected static long uneSemaineMilli = unJourMilli*7;
    protected static int TREATMENT_TIME = 5;
    protected static int RDV_TIME = 60*24;
    protected static int ACTIVITY_TIME = RDV_TIME;

    public abstract ArrayList<Event> generateTreatment(Resources res, Event e);

    public static void setColorActivity(int colorActivity){
        Protocol.colorActivity = colorActivity;
    }


    public static void setColorTreatment(int colorTreatment){
        Protocol.colorTreatment = colorTreatment;
    }
}
