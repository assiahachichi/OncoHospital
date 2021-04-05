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
public class ProtocolEC100 extends Protocol{
    public final static String EC100 = "EC100";

    private static ProtocolEC100 myProtocol = null;

    public static Protocol getInstance() {
        if (myProtocol == null){
            myProtocol = new ProtocolEC100();
        }
        return myProtocol;
    }

    private ProtocolEC100(){}

    @Override
    public ArrayList<Event> generateTreatment(Resources res, Event e) {
        String timeZone = e.getTimeZone();
        ArrayList<Event> list = new ArrayList<>();

        Date dtStartE = e.getDtStart();
        long l, longStart = dtStartE.getTime();
        long idE, idRdv, id = e.getId();
        int minutes;
        String type, uid, description, location, function, title, sId ;

        Event newEvent;
        int index, color;

        Date dtCreated, dtStart, dtEnd;

        for (ElementEC100 elt : ElementEC100.values()){
            index = elt.getIndex();
            title = elt.getValue(res);

            sId = String.valueOf(id);
            sId = String.valueOf(index)+sId;
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
            dtEnd = new Date(l+uneHeurMilli);

            newEvent = new Event(idE, idRdv, uid, dtStart, dtEnd,  dtCreated, type,
                    title, description, function, location, new Reminder(minutes), timeZone);
            newEvent.setColor(color);
            list.add(newEvent);

        }
        return list;
    }

    public long getDtStart(ElementEC100 elt, long longStart){
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
                case EMEND125:
                    l = longStart - uneHeurMilli;
                    break;
                case EMEND801:
                    l = leLendemain - uneHeurMilli;
                    break;
                case EMEND802:
                    l = leSurLendemain - uneHeurMilli;
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
