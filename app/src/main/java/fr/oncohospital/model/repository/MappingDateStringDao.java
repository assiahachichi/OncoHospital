package fr.oncohospital.model.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Assia HACHICHI on 25/03/2021
 */
public class MappingDateStringDao {

    /**
     *
     * @param d
     * @return
     */
    public static String convertDateToString(Date d){
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (d == null) return "";
        return simpleDateFormat.format(d);
    }

    /**
     *
     * @param s
     * @return
     */
    public static Date convertStringToDate(String s){
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if ((s == null) || (s.equals("")) )return null;
        try {
            return simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param d
     * @return
     */
    public static String getSDayName(Date d) {
        if (d == null) return "";
        SimpleDateFormat jourFormat = new SimpleDateFormat("EE");
        return jourFormat.format(d);
    }

    public static String getSDayFullName(Date d) {
        if (d == null) return "";
        SimpleDateFormat jourFormat = new SimpleDateFormat("EEEE");
        return jourFormat.format(d);
    }

    /**
     *
     * @param d
     * @return
     */
    public static String getDayNumber(Date d){
        if (d == null) return "";
        SimpleDateFormat nbJourFormat =new SimpleDateFormat("dd");
        return nbJourFormat.format(d);
    }

    /**
     *
     * @param d
     * @return
     */
    public static  String getMonth(Date d) {
        if (d == null) return "";
        SimpleDateFormat moisFormat = new SimpleDateFormat("MMM");
        return moisFormat.format(d);
    }

    public static  String getFullMonth(Date d) {
        if (d == null) return "";
        SimpleDateFormat moisFormat = new SimpleDateFormat("MMMM");
        return moisFormat.format(d);
    }

    public static  String getYear(Date d) {
        if (d == null) return "";
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        return yearFormat.format(d);
    }
    /**
     *
     * @param d
     * @return
     */
    public static String getHour(Date d){
        if (d == null) return "";
        SimpleDateFormat heureFormat =new SimpleDateFormat("HH:mm");
        return heureFormat.format(d);
    }

    /**
     *
     * @param d
     * @return
     */
    public static String getDay(Date d){
        SimpleDateFormat dayFormat =new SimpleDateFormat("yyyy-MM-dd");
        return dayFormat.format(d);
    }

}
