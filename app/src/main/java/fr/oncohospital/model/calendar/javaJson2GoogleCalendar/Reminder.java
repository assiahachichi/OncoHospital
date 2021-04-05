package fr.oncohospital.model.calendar.javaJson2GoogleCalendar;


import android.provider.CalendarContract;

/**
 * @author Assia HACHICHI
 */
public class Reminder  {
	private int method;
	private int minutes;


	public Reminder(int minutes) {
		this.minutes = minutes;
		method = CalendarContract.Reminders.METHOD_ALERT;
	}

	public int getMinutes() {
		return minutes;
	}
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	public int getMethod() {
		return method;
	}

	@Override
	public String toString() {
		return "Reminder{" +
				"minutes='" + minutes + '\'' +
				'}';
	}


	public String toJson() {
		String s = "{\"VREMINDER\":{\n"
				+ "        			\"MINUTES\": \""+this.minutes+"\"\n"
				+ "      			}}";
		return s;
	}
	public static String toJsonNull() {
		String s = "{\"VREMINDER\":{\n"
				+ "        			\"MINUTES\": \"-1\"\n"
				+ "      			}}";
		return s;
	}


}
