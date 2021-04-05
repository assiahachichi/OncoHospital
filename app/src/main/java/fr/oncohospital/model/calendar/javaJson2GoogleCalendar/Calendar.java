package fr.oncohospital.model.calendar.javaJson2GoogleCalendar;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.CalendarContract;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Assia HACHICHI
 */
public class Calendar {
	private ArrayList<Event> listEvents = new ArrayList<Event>();
	private Reminder reminder ;
	private Date lastDate;
	private ContentResolver cr;
	private Context context;
	String timeZone;

	/**
	 *
	 * @param cr
	 * @param context
	 * @param timeZone
	 */
	public Calendar(ContentResolver cr, Context context, String timeZone) {
		this.lastDate = null;
		reminder = null;
		this.cr = cr;
		this.context = context;
		this.timeZone = timeZone;
		listEvents = new ArrayList<Event>();
	}

	/**
	 *
	 * @param event
	 * @return
	 */
	public Uri queryEvent(Event event){
		long myEventId = event.getId();
		Date dtCreated = event.getDtCreated();
		Uri eventUri = null;

		if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED)
		{
			String dtNew;
			Cursor cursor = cr.query(CalendarContract.Events.CONTENT_URI, null, null, null, null);
			while (cursor.moveToNext()) {
				if (cursor != null) {
					int id1 = cursor.getColumnIndex(CalendarContract.Events._ID);
					long idEvent = cursor.getLong(id1);

					int id2 = cursor.getColumnIndex(CalendarContract.Events.UID_2445);
					String dtCreatedEvent = cursor.getString(id2);

					if ((idEvent == myEventId)&&(dtCreatedEvent!=null)) {
						dtNew =  "" + dtCreated;
						int i = dtNew.compareTo(dtCreatedEvent);
						if (i>0){
							eventUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, idEvent);
							break;
						}

					}
				}
			}
		}
		return eventUri;
	}

	/**
	 *
	 * @param event
	 */
	public void deleteEvent( Event event){
		Uri eventUri = queryEvent(event);
		if (eventUri != null){
			cr.delete(eventUri, null, null);
		}
	}

	/**
	 *
	 * @param event
	 */
	public void insertEvent(Event event){
		Uri eventUri ;
		ContentValues cvNew = event.convertToGoogleCalendar();
		eventUri= queryEvent(event);

		if (eventUri != null){
			cr.delete(eventUri, null, null);
		}
		eventUri = cr.insert(CalendarContract.Events.CONTENT_URI, cvNew);
	}

	/**
	 *
	 * @param event
	 */
	public void updateEvent(Event event){
		insertEvent(event);
	}

	/**
	 *
	 */
	public void convertToGoogleCalendar( ) {
		for (Event event : listEvents){
			if (event.getFunction().equals("INSERT")){
				insertEvent(event);
			}else {
				if (event.getFunction().equals("UPDATE")) {
					updateEvent(event);
				} else {
					//DELETE
					deleteEvent( event);

				}
			}
		}
	}

	/**
	 *
	 */
	public void generateAllNewReminders() {
		Uri eventUri;
		for (Event event : listEvents){
			if (!event.getFunction().equals("DELETE")){
				eventUri = queryEvent(event);
				if (eventUri!=null) generateEventReminders(eventUri, event);
			}
		}
	}

	/**
	 *
	 * @param eventUri
	 * @param event
	 */
	private void generateEventReminders(Uri eventUri, Event event) {
		int method;
		int minutes;
		Reminder r;
		r = event.getReminder();
		if (event.getReminder()!=null){
			minutes = r.getMinutes();
			method = r.getMethod();
		}else{
			if(this.reminder !=null){
				minutes = reminder.getMinutes();
				method = reminder.getMethod();
			}else{
				return;
			}

		}

		long eventID = Long.parseLong(eventUri.getLastPathSegment());
		ContentValues reminders = new ContentValues();
		reminders.put(CalendarContract.Reminders.EVENT_ID, eventID);
		reminders.put(CalendarContract.Reminders.METHOD, method);
		reminders.put(CalendarContract.Reminders.MINUTES, minutes);
		Uri uri2 = cr.insert(CalendarContract.Reminders.CONTENT_URI, reminders);
	}

	/**
	 *
	 * @param d
	 */
	public void setLastDate(Date d) {
		if (lastDate == null){
			lastDate = d;
		}else{
			if (d.compareTo(lastDate)>0){lastDate = d;}
		}
	}

	/**
	 *
	 * @return
	 */
	public Date getLastDate(){
		return lastDate;
	}

	/**
	 *
	 * @param e
	 */
	public void addEvent(Event e) {
		listEvents.add(e);
	}

	/**
	 *
	 * @return
	 */
	public ArrayList<Event> getAllEvents(){return listEvents;}

	/**
	 *
	 * @return
	 */
	public String toJson() {
		String s = "{\n"
				+ "\"VCALENDAR\":[\n";
		for (Event event : this.listEvents) {
			s = s + event.toJson();
		}
		if (reminder == null){
			s = s + Reminder.toJsonNull();
		}else{
			s = s + reminder.toJson();
		}
		s = s +  "]}";
		return s;
	}

	/**
	 *
	 * @return
	 */
	@Override
	public String toString() {
		return "Calendar{" +
				"listEvents=" + listEvents +
				", reminder=" + reminder +
				", lastDate=" + lastDate +
				'}';
	}

	/**
	 *
	 * @return
	 */
	public Reminder getReminder() {
		return reminder;
	}

	/**
	 *
	 * @param reminder
	 */
	public void setReminder(Reminder reminder) {
		this.reminder = reminder;
	}

	/**
	 *
	 * @param filename
	 */
	public void saveCalendarIntoFile(String filename)  {
		FileOutputStream fos = null;
		try {
				File path = Environment.getExternalStoragePublicDirectory(
					Environment.DIRECTORY_PICTURES);
				fos = new FileOutputStream(new File(path, filename),false);
				fos.write(this.toJson().getBytes());
				Toast.makeText(context, "Saved to " + filename, Toast.LENGTH_SHORT).show();

		} catch (FileNotFoundException e) {
		e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null){
				try {
					fos.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}
}


