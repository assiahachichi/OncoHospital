package fr.oncohospital.model.allData.bd.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

import fr.oncohospital.model.calendar.javaJson2GoogleCalendar.Event;
import fr.oncohospital.model.repository.MappingDateStringDao;

/**
 * Created by Assia HACHICHI on 24/03/2021
 * @author Assia HACHICHI
 */

/**
 * This class represents an appointment entity.
 * This class will be mapped into a SQLite table in the database.
 * Its primary key id idRDV, which represents an appointment identifier
 */
@Entity(tableName = "table_rendez_vous")
public class RendezVousEntity implements Comparable<RendezVousEntity> {
    @PrimaryKey()
    private long idRDV;
    private String idPatient;
    private String sDtStart;
    private String sDtEnd;
    private String sDtCreated;
    private String title;
    private String description;
    private String location;

    /**
     *
     * @param idRDV represents an appointment identifier, It is also the primary key of this entity.
     * @param idPatient
     * @param sDtStart
     * @param sDtEnd
     * @param sDtCreated
     * @param title
     * @param description
     * @param location
     */
    public RendezVousEntity(long idRDV, String idPatient, String sDtStart, String sDtEnd, String sDtCreated, String title, String description, String location) {
        this.idRDV = idRDV;
        this.idPatient = idPatient;
        this.sDtStart = sDtStart;
        this.sDtEnd = sDtEnd;
        this.sDtCreated = sDtCreated;
        this.title = title;
        this.description = description;
        this.location = location;
    }

    /**
     *
     * @param idPatient
     * @param e
     * @return
     */
    public static RendezVousEntity convertEventToRendezVous(String idPatient, Event e) {
        return new RendezVousEntity(e.getIdRDV(), idPatient, MappingDateStringDao.convertDateToString(e.getDtStart()),
                MappingDateStringDao.convertDateToString(e.getdEnd()),
                MappingDateStringDao.convertDateToString(e.getDtCreated()),
                e.getTitle(), e.getDescription(), e.getLocation());
    }

    /**
     *
     * @return
     */
    public long getIdRDV() {
        return idRDV;
    }

    /**
     *
     * @return
     */
    public String getIdPatient() {
        return idPatient;
    }

    /**
     *
     * @return
     */
    public String getSDtStart() {
        return sDtStart;
    }

    /**
     *
     * @return
     */
    public String getSDtEnd() {
        return sDtEnd;
    }

    /**
     *
     * @return
     */
    public String getSDtCreated() {
        return sDtCreated;
    }

    /**
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @return
     */
    public String getDayName(){
        Date d;
        d = MappingDateStringDao.convertStringToDate(sDtStart);
        String s = MappingDateStringDao.getSDayName(d);
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
    public String getDayFullName(){
        Date d;
        d = MappingDateStringDao.convertStringToDate(sDtStart);
        String s = MappingDateStringDao.getSDayFullName(d);
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    /**
     *
     * @return
     */
    public String getNbDay(){
        Date d;
        d = MappingDateStringDao.convertStringToDate(sDtStart);
        return MappingDateStringDao.getDayNumber(d);
    }

    /**
     *
     * @return
     */
    public String getMonth(){
        Date d;
        d = MappingDateStringDao.convertStringToDate(sDtStart);
        String s = MappingDateStringDao.getMonth(d);

        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    public String getFullMonth(){
        Date d;
        d = MappingDateStringDao.convertStringToDate(sDtStart);
        String s =  MappingDateStringDao.getFullMonth(d);

        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    public String getYear(){
        Date d;
        d = MappingDateStringDao.convertStringToDate(sDtStart);
        return  MappingDateStringDao.getYear(d);

      }
    /**
     *
     * @return
     */
    public String getStartHour(){
        Date d;
        d = MappingDateStringDao.convertStringToDate(sDtStart);
        return MappingDateStringDao.getHour(d);
    }

    /**
     *
     * @return
     */
    public String getEndHour(){
        Date d;
        d = MappingDateStringDao.convertStringToDate(sDtEnd);
        return MappingDateStringDao.getHour(d);
    }

    /**
     *
     * @param rve
     * @return
     */
    public boolean isSameDay(RendezVousEntity rve){
        Date dThisStart = MappingDateStringDao.convertStringToDate(this.sDtStart);
        Date dRveStart = MappingDateStringDao.convertStringToDate(rve.getSDtStart());
        String s1 = MappingDateStringDao.getDay(dThisStart);
        String s2 = MappingDateStringDao.getDay(dRveStart);
        return s1.equals(s2);
    }

    /**
     *
     * @return
     */
    public boolean isInOneDay(){//renomer isSameDay
        Date dThisStart = MappingDateStringDao.convertStringToDate(this.sDtStart);
        Date dThisEnd = MappingDateStringDao.convertStringToDate(this.sDtEnd);
        String s1 = MappingDateStringDao.getDay(dThisStart);
        String s2 = MappingDateStringDao.getDay(dThisEnd);
        return s1.equals(s2);
    }

    /**
     *
     * @param rdv
     * @return
     */
    public boolean isNewVersion(RendezVousEntity rdv){
        Date dThis = MappingDateStringDao.convertStringToDate(sDtCreated);
        Date dRdv = MappingDateStringDao.convertStringToDate(rdv.getSDtCreated());
        return  (this.idRDV == rdv.getIdRDV()) && (dThis.after(dRdv));
    }

    /**
     *
     * @param rve
     * @return
     */
    public int compareTo(RendezVousEntity rve) {
        Date dThisStart = MappingDateStringDao.convertStringToDate(this.sDtStart);
        Date dRveStart = MappingDateStringDao.convertStringToDate(rve.getSDtStart());
        return dThisStart.compareTo(dRveStart);
    }

}
