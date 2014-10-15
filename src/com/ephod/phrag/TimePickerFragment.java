package com.ephod.phrag;

import java.util.Calendar;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

	public TimePickerFragment(){
		
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current time as the default values for the picker
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		
		// Create a new instance of TimePickerDialog and return it
		return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
	}
	
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		// Do something with the time chosen by the user
		String value = updateTime(hourOfDay, minute);
		NewTaskActivity parent = (NewTaskActivity)this.getActivity();
		parent.setTimeValue(value);
	}
	
	
	  private String updateTime(int hours, int mins) {
	         
	        String timeSet = "";
	        if (hours > 12) {
	            hours -= 12;
	            timeSet = "PM";
	        } else if (hours == 0) {
	            hours += 12;
	            timeSet = "AM";
	        } else if (hours == 12)
	            timeSet = "PM";
	        else
	            timeSet = "AM";
	 
	         
	        String minutes = "";
	        if (mins < 10)
	            minutes = "0" + mins;
	        else
	            minutes = String.valueOf(mins);
	 
	        // Append in a StringBuilder
	         String aTime = new StringBuilder().append(hours).append(':').append(minutes).append(" ").append(timeSet).toString();
	 
	         return aTime;
	    }
}
