package fr.oncohospital.model.calendar.javaJson2GoogleCalendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Assia HACHICHI
 */
public class MappingDateString {
    public static String convertDateToString4Json(Date d) {
        if (d == null) return null;
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);

    }
    public static String convertDateToString4Calendar(Date d) {
        if (d == null) return null;
        return new SimpleDateFormat("yyyyMMddHHmmss").format(d);
    }


    public static Date convertStringToDate(String sDate) {
        SimpleDateFormat simpleformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            if(sDate.equals("")||(sDate==null)){
                return d;
            }else{
                d = simpleformat.parse(sDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }


}
