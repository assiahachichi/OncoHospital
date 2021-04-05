package fr.oncohospital.model.calendar.javaJson2GoogleCalendar;


import android.content.ContentResolver;
import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;


/**
 * @author Assia HACHICHI
 */

public class ParserJsonToCalendar {
	private ContentResolver cr;
	private Context context;
	private String timeZone;

	public ParserJsonToCalendar(ContentResolver cr, Context context, String timeZone) {
		this.cr = cr;
		this.context = context;
		this.timeZone = timeZone;
	}

	public Calendar getCalendar(String sCalendar, int color) {
		Calendar calendar = new Calendar(cr, context, timeZone);
		JSONObject obj;
		if ((sCalendar==null)||(sCalendar.equals(""))) {return null;}
		try {

			Event e;
			obj = new JSONObject(sCalendar);

			JSONArray allEventsJSON = obj.getJSONArray("VCALENDAR");
			JSONObject vEvent;
			int taille = allEventsJSON.length();
			for (int i=0; i<taille-1; i++)
			{
				vEvent = allEventsJSON.getJSONObject(i);
				e = getEvent((JSONObject) vEvent.get("VEVENT"), color);
				calendar.addEvent(e);
				calendar.setLastDate(e.getDtCreated());
			}

			if (taille >0) {
				JSONObject vReminder = allEventsJSON.getJSONObject(taille - 1);
				Reminder r = getReminder((JSONObject) vReminder.get("VREMINDER"));
				calendar.setReminder(r);
			}

		} catch (JSONException ex2) {
			ex2.printStackTrace();
		}

		return calendar;
	}

	private Reminder getReminder(JSONObject vReminder) {
		Reminder reminder = null;
		int minutes;
		try {
			minutes = vReminder.getInt("MINUTES");
			if (minutes == -1) return null;
			reminder = new Reminder(minutes);
		}catch (JSONException ex){
			ex.printStackTrace();
		}
		return reminder;
	}

	private Event getEvent(JSONObject vEvent, int color) {
		Event event = null;
		String s1, uid, type, title, description, function, location;
		long id, id1, idRdv;
		Date dtStart, dtEnd, dtCreated;
		Reminder reminder;
		int minutes;
		try {
			s1 = vEvent.getString("DTSTART");
			dtStart = MappingDateString.convertStringToDate(s1);
			s1 = vEvent.getString("DTEND");
			dtEnd = MappingDateString.convertStringToDate(s1);
			s1 = vEvent.getString("DTCREATED");
			dtCreated = MappingDateString.convertStringToDate(s1);
			id1 =  vEvent.getLong("IDRDV");
			idRdv = id1;
			//ID est deja composé du IdPatient et IdRdv
			id =  vEvent.getLong("ID");
			uid = id + "" + s1;
			s1 = vEvent.getString("TYPE");
			type = s1;
			s1 = vEvent.getString("TITLE");
			title = s1;
			s1 = vEvent.getString("DESCRIPTION");
			description = s1;
			s1 = vEvent.getString("FUNCTION");
			function = s1;
			s1 = vEvent.getString("LOCATION");
			location = s1;
			minutes = vEvent.getInt("MINUTES");
			if (minutes == -1){
				reminder = null;
			}else{
				reminder = new Reminder(minutes);
			}
			event = new Event(id, idRdv, uid, dtStart, dtEnd, dtCreated, type, title,
					description, function, location, reminder, timeZone);
			event.setColor(color);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		JSONArray vAlarms = null;
		return event;
	}
	public  String parserFileToString(File file) {
		String sFile="";
		try {
			InputStream flux = new FileInputStream(file);
			InputStreamReader lecture = new InputStreamReader(flux);
			BufferedReader buff = new BufferedReader(lecture);
			String ligne;
			while ((ligne = buff.readLine()) != null) {
				sFile = sFile.concat(ligne);
				sFile = sFile.concat("\n");
			}
			buff.close();
			Toast.makeText(context, "The file is parsed", Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sFile;
	}

	public  String parserFileToString(String filename) {
		String sFile="";
		try {
			File path = Environment.getExternalStoragePublicDirectory(
					Environment.DIRECTORY_PICTURES);
			InputStream flux = new FileInputStream(new File(path, filename));
			InputStreamReader lecture = new InputStreamReader(flux);
			BufferedReader buff = new BufferedReader(lecture);
			String ligne;
			while ((ligne = buff.readLine()) != null) {
				sFile = sFile.concat(ligne);
				sFile = sFile.concat("\n");
			}
			buff.close();
			Toast.makeText(context, "The file is parsed", Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sFile;
	}


}