package fr.oncohospital.model.calendar.javaJson2GoogleCalendar;

import android.content.ContentValues;
import android.graphics.Color;
import android.provider.CalendarContract;

import java.util.Date;

/**
 * @author Assia HACHICHI
 */
public class Event {
	private Date dtStart;
	private Date dtEnd;
	private long id;
	private long idRDV;
	private String uid;
	private String description;
	private String location;
	private String title;
	private String function;
	private String type ;
	private Date dtCreated;
	private int color;
	private Reminder reminder;
	private String timeZone;

    public Event(long id, long idRdv, String uid, Date dtStart, Date dtEnd, Date dtCreated,
                 String type, String title, String description, String function,
                 String location, Reminder reminder, String timeZone) {

		color = Color.GREEN;this.id = id;
        this.idRDV = idRdv;
        this.uid = uid;
        this.dtStart = dtStart;
        this.dtEnd = dtEnd;
        this.type = type;
        this.title = title;
        this.description = description;
        this.function = function;
        this.location = location;
        this.dtCreated = dtCreated;
        this.reminder = reminder;
        this.timeZone = timeZone;
    }

    public ContentValues convertToGoogleCalendar(){
		ContentValues cv = new ContentValues();
		cv.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone);
		cv.put(CalendarContract.Events.EVENT_COLOR, color);
		cv.put(CalendarContract.Events.TITLE, this.title);
		cv.put(CalendarContract.Events.DESCRIPTION,this.description);
		cv.put(CalendarContract.Events.EVENT_LOCATION,this.location);
		cv.put(CalendarContract.Events.DTSTART,this.dtStart.getTime());
		cv.put(CalendarContract.Events.DTEND,this.dtEnd.getTime());
		String s = MappingDateString.convertDateToString4Calendar(dtCreated);

		cv.put(CalendarContract.Events.UID_2445,s);
		cv.put(CalendarContract.Events.CALENDAR_ID,1);
		cv.put(CalendarContract.Events._ID, this.id);
		cv.put(CalendarContract.Events.HAS_ALARM, 1);
		return cv;
	}

	public Date getDtCreated() {
		return dtCreated;
	}
	public void setDtCreated(Date dtCreated) {
		this.dtCreated = dtCreated;
	}
	public Date getDtStart() {
		return dtStart;
	}
	public String getStringDtStart() {
		return MappingDateString.convertDateToString4Json(dtStart);
	}
	public void setDtStart(Date dtStart) {
		this.dtStart = dtStart;
	}
	public String getType() { return type; }
	public void setType(String type) { this.type = type; }
	public Date getdEnd() {
		return dtEnd;
	}
	public void setdEnd(Date dEnd) {
		this.dtEnd = dEnd;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public long getIdRDV() {
		return idRDV;
	}
	public void setIdRDV(long idRDV) {
		this.idRDV = idRDV;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public void setFunction(String function) { this.function = function; }
	public void setColor(int color){this.color = color;}
	public String getFunction() {
		return this.function;
	}
	public Reminder getReminder() {
		return reminder;
	}
	public void setReminder( Reminder reminder) {
		this.reminder = reminder;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}


	public String toJson() {

		String s = "{\"VEVENT\":{\n"
				+ "    	\"DTSTART\": \""+ MappingDateString.convertDateToString4Json(this.dtStart) +"\",\n"
				+ "    	\"DTEND\": \""+ MappingDateString.convertDateToString4Json(this.dtEnd) +"\",\n"
				+ "    	\"DTCREATED\": \""+MappingDateString.convertDateToString4Json(this.dtCreated)+"\",\n"
				+ "    	\"IDRDV\": \""+this.idRDV +"\",\n"
				+ "    	\"ID\": \""+this.id +"\",\n"
				+ "    	\"TYPE\": \""+this.type +"\",\n"
				+ "    	\"TITLE\": \""+this.title +"\",\n"
				+ "    	\"DESCRIPTION\": \""+this.description+"\",\n"
				+ "    	\"FUNCTION\": \""+this.function+"\",\n"
				+ "    	\"LOCATION\": \""+this.location+"\",\n";
		if (this.reminder != null) {
			s = s + "    	\"MINUTES\":\"" + this.reminder.getMinutes();
		}else{
			s = s + "    	\"MINUTES\":\"-1\"";
		}
		s = s + "      }},\n";
		return s;
	}

	/**
	 *
	 * @return
	 */
	@Override
	public String toString() {
		return "Event{" +
				"dtStart=" + dtStart +
				", dtEnd=" + dtEnd +
				", id=" + id +
				", idRDV=" + idRDV +
				", uid='" + uid + '\'' +
				", description='" + description + '\'' +
				", location='" + location + '\'' +
				", title='" + title + '\'' +
				", function='" + function + '\'' +
				", type='" + type + '\'' +
				", dtCreated=" + dtCreated +
				", color=" + color +
				", reminder=" + reminder +
				'}';
	}
}
