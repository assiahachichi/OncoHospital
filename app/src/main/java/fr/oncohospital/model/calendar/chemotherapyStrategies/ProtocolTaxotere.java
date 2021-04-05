package fr.oncohospital.model.calendar.chemotherapyStrategies;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Date;

import fr.oncohospital.model.calendar.javaJson2GoogleCalendar.Event;
import fr.oncohospital.ui.myCalendarNotification.TypeEvent;
import fr.oncohospital.model.calendar.javaJson2GoogleCalendar.MappingDateString;
import fr.oncohospital.model.calendar.javaJson2GoogleCalendar.Reminder;

/**
 * Created by Assia HACHICHI on 24/02/2021
 */
public class ProtocolTaxotere extends Protocol{
    public static final String TAXOTERE = "TAXOTERE";
    public static final String DOCETAXEL = "DOCETAXEL";
    private static ProtocolTaxotere myProtocol = null;


    public static Protocol getInstance() {
        if (myProtocol == null){
            myProtocol = new ProtocolTaxotere();
        }
        return myProtocol;
    }

    private ProtocolTaxotere(){}


    @Override
    public ArrayList<Event> generateTreatment(Resources res, Event e) {
        String timeZone = e.getTimeZone();

        ArrayList<Event> list = new ArrayList<>();

        Date dtStartE = e.getDtStart();
        long idE, idRdv, l, longStart = dtStartE.getTime();
        long id = e.getId();
        int minutes;
        String  uid, description, title, location, function, type, sId ;//= String.valueOf(id);

        Event newEvent;
        int index, color;
        Date dtStart, dtEnd, dtCreated;

        for (ElementTaxotere elt : ElementTaxotere.values()){
            index = elt.getIndex();
            title = elt.getValue(res);
            sId = String.valueOf(id);
            sId = index + sId;
            idE = Long.parseLong(sId);
            idRdv = e.getIdRDV();
            dtCreated = e.getDtCreated();

            uid = idE + "" + MappingDateString.convertDateToString4Calendar(dtCreated);
            description = "";
            location = "";
            function = e.getFunction();

            if ((index == 1)||(index == 2)) {
                type = TypeEvent.ACTIVITY.toString();
                color = colorActivity;
                minutes = ACTIVITY_TIME;
            }else{
                type = TypeEvent.TRAITEMENT.toString();
                color = colorTreatment;
                minutes = TREATMENT_TIME;
            }

            l = getDtStart(elt, longStart);
            dtStart = new Date(l);
            dtEnd = new Date(l + uneHeurMilli);

            newEvent = new Event(idE, idRdv, uid, dtStart, dtEnd,  dtCreated, type,
                    title, description, function, location, new Reminder(minutes), timeZone);
            newEvent.setColor(color);
            list.add(newEvent);

        }
        return list;
    }

    public long getDtStart(ElementTaxotere elt, long longStart){
        long l, laVeille, leLendemain, leSurLendemain;
        Date d = new Date();
        l = longStart;
        laVeille = l - unJourMilli;
        leLendemain = l + unJourMilli;
        leSurLendemain = leLendemain + unJourMilli;
        switch (elt){
            case PHARMACIE:
                l = longStart - uneSemaineMilli;
                break;
            case ANALYSES:

                d = new Date(laVeille);
                d.setHours(07);
                l = d.getTime();
                if(d.getDay() == 0){
                    //si dimanche, alors effectuer les analyses le vendredi

                    l = l - (2 * unJourMilli);
                    d = new Date(l);
                    l = d.getTime();
                }

                break;
            case PATCH:
                l = longStart - uneHeurMilli;
                break;
            case SOLUPRED_VEILLE_8:
                d = new Date(laVeille);
                d.setHours(8);
                l = d.getTime();
                break;
            case SOLUPRED_VEILLE_15:
                d = new Date(laVeille);
                d.setHours(15);
                l = d.getTime();
                break;
            case SOLUPRED_JOUR_8:
                d = new Date(longStart);
                d.setHours(8);
                l = d.getTime();
                break;
            case SOLUPRED_JOUR_15:
                d = new Date(longStart);
                d.setHours(15);
                l = d.getTime();
                break;
            case SOLUPRED_LELENDEMAIN_8:
                d = new Date(leLendemain);
                d.setHours(8);
                l = d.getTime();
                break;
            case SOLUPRED_LELENDEMAIN_15:
                d = new Date(leLendemain);
                d.setHours(15);
                l = d.getTime();
                break;

            default://Pelmeg
                l = leSurLendemain;
                d = new Date(l);
                d.setHours(10);
                l = d.getTime();
                break;
        }


        return l;

    }



}
