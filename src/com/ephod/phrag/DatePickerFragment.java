package com.ephod.phrag;

import java.sql.Date;
import java.util.Calendar;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}
	

	@SuppressWarnings("deprecation")
	public void onDateSet(DatePicker view, int year, int month, int day) {
		// Do something with the date chosen by the user
		NewTaskActivity parent = (NewTaskActivity)this.getActivity();
		String dayOfWeek = DateFormat.format("EE", new Date(year, month, (day - 1))).toString();
		String monthInShort = DateFormat.format("MMM", new Date(year, month, day)).toString();
		String DateAsString  = dayOfWeek + ", " + String.valueOf(day) + " " + monthInShort + " " + String.valueOf(year); 
		parent.setDateValue(DateAsString);
	}
}
