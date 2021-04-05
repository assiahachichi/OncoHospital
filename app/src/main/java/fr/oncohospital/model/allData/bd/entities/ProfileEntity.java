package fr.oncohospital.model.allData.bd.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

import fr.oncohospital.model.repository.MappingDateStringDao;

/**
 * Created by Assia HACHICHI on 24/03/2021
 * @author Assia HACHICHI
 */

/**
 * This class represents a patient profile entity.
 * This class will be mapped into a SQLite table in the database.
 * Its primary key id idPatient, which represents a patient identifier
 */
@Entity(tableName = "table_profile")
public class ProfileEntity {
    @PrimaryKey() @NonNull
    private String idPatient;
    private String language ;
    private boolean calendarSetting ;
    private boolean reminderSetting ;
    private boolean treatmentSetting ;
    private boolean notificationSetting ;
    //color
    private int rdvColor ;
    private int treatmentColor;
    private int activityColor ;
    private int textSize;
    private String lastDateRefresh;

    /**
     *
     * @param idPatient represents a patient identifier, It is also the primary key of this entity.
     * @param language
     * @param calendarSetting
     * @param reminderSetting
     * @param treatmentSetting
     * @param notificationSetting
     * @param rdvColor
     * @param treatmentColor
     * @param activityColor
     * @param textSize
     * @param lastDateRefresh
     */
    public ProfileEntity(@NonNull String idPatient, String language, boolean calendarSetting, boolean reminderSetting, boolean treatmentSetting, boolean notificationSetting, int rdvColor, int treatmentColor, int activityColor, int textSize, String lastDateRefresh) {
        this.idPatient = idPatient;
        this.language = language;
        this.calendarSetting = calendarSetting;
        this.reminderSetting = reminderSetting;
        this.treatmentSetting = treatmentSetting;
        this.notificationSetting = notificationSetting;
        this.rdvColor = rdvColor;
        this.treatmentColor = treatmentColor;
        this.activityColor = activityColor;
        this.textSize = textSize;
        this.lastDateRefresh = lastDateRefresh;
    }

    /**
     * This method is a getter of the patient identifier attribute, which is also the primary key.
     * @return patient identifier
     */
    @NonNull
    public String getIdPatient() {
        return idPatient;
    }

    /**
     *
     * @param idPatient represents a value of the patient identifier
     */
    public void setIdPatient(@NonNull String idPatient) {
        this.idPatient = idPatient;
    }

    /**
     * This method is a getter of the language chosen by the patient
     * @return the language chosen by the patient
     */
    public String getLanguage() {
        return language;
    }

    /**
     * This method is a setter of the language chosen by the patient
     * @param language represents the new value of language chosen
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     *
     * @return
     */
    public boolean isCalendarSetting() {
        return calendarSetting;
    }

    /**
     *
     * @param calendarSetting
     */
    public void setCalendarSetting(boolean calendarSetting) {
        this.calendarSetting = calendarSetting;
    }

    /**
     *
     * @return
     */
    public boolean isReminderSetting() {
        return reminderSetting;
    }

    /**
     *
     * @param reminderSetting
     */
    public void setReminderSetting(boolean reminderSetting) {
        this.reminderSetting = reminderSetting;
    }

    /**
     *
     * @return
     */
    public boolean isTreatmentSetting() {
        return treatmentSetting;
    }

    /**
     *
     * @param treatmentSetting
     */
    public void setTreatmentSetting(boolean treatmentSetting) {
        this.treatmentSetting = treatmentSetting;
    }

    /**
     *
     * @return
     */
    public boolean isNotificationSetting() {
        return notificationSetting;
    }

    /**
     *
     * @param notificationSetting
     */
    public void setNotificationSetting(boolean notificationSetting) {
        this.notificationSetting = notificationSetting;
    }

    /**
     *
     * @return
     */
    public int getRdvColor() {
        return rdvColor;
    }

    /**
     *
     * @param rdvColor
     */
    public void setRdvColor(int rdvColor) {
        this.rdvColor = rdvColor;
    }

    /**
     *
     * @return
     */
    public int getTreatmentColor() {
        return treatmentColor;
    }

    /**
     *
     * @param treatmentColor
     */
    public void setTreatmentColor(int treatmentColor) {
        this.treatmentColor = treatmentColor;
    }

    /**
     *
     * @return
     */
    public int getActivityColor() {
        return activityColor;
    }

    /**
     *
     * @param activityColor
     */
    public void setActivityColor(int activityColor) {
        this.activityColor = activityColor;
    }

    /**
     *
     * @return
     */
    public int getTextSize() {
        return textSize;
    }

    /**
     *
     * @param textSize
     */
    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    /**
     *
     * @return
     */
    public String getLastDateRefresh() {
        return lastDateRefresh;
    }

    /**
     *
     * @param lastDateRefresh
     */
    public void setLastDateRefresh(String lastDateRefresh) {

        Date lastDate = MappingDateStringDao.convertStringToDate(this.lastDateRefresh);
        Date newLastDate = MappingDateStringDao.convertStringToDate(lastDateRefresh);
        if ((lastDate==null)||(newLastDate.compareTo(lastDate)>0)){
            if ((lastDateRefresh!=null)&&(!lastDateRefresh.equals(""))){ this.lastDateRefresh = lastDateRefresh;}
        }
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "ProfileEntity{" +
                "idPatient='" + idPatient + '\'' +
                ", language='" + language + '\'' +
                ", calendarSetting=" + calendarSetting +
                ", reminderSetting=" + reminderSetting +
                ", treatmentSetting=" + treatmentSetting +
                ", notificationSetting=" + notificationSetting +
                ", rdvColor=" + rdvColor +
                ", treatmentColor=" + treatmentColor +
                ", activityColor=" + activityColor +
                ", textSize=" + textSize +
                ", lastDateRefresh='" + lastDateRefresh + '\'' +
                '}';
    }
}
