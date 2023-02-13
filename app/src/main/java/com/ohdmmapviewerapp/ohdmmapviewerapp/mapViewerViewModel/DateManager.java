package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel;

import android.app.DatePickerDialog;
import android.content.Context;

public interface DateManager {


    /**
     * converts returned date from calender to string
     * @return date as string
     */
    String convertDateString(int day, int month, int year);

    /**
     * initalizes datePickerDialog
     * @return DatePickerDialog for popup calender
     */
    DatePickerDialog getDatePickerDialog(DatePickerDialog.OnDateSetListener dateSetListener, Context ctx);

    /**
     * gets current Date as a string
     * @return date as String
     */
    String getTodaysDate();

    /**
     * mirrors date string to "year-month-day"
     * @return mirrored date as String
     */
    String mirrorDateString(String date);

}
