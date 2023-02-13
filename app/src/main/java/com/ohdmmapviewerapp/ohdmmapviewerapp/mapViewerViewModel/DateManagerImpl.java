package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel;

import android.app.DatePickerDialog;
import android.content.Context;

import java.util.Calendar;

public class DateManagerImpl implements DateManager {

    @Override
    public String convertDateString(int day, int month, int year) {
        String month1 = "0";
        if (month < 10){
            month1 = month1 + month;
        }
        else {month1 = String.valueOf(month);}
        String day1 = "0";
        if (day <10){
            day1 = day1 + day;
        }
        else {day1 = String.valueOf(day);}
        String date = day1 + "-" +  month1 + "-" + year;
        return date;
    }

    @Override
    public DatePickerDialog getDatePickerDialog(DatePickerDialog.OnDateSetListener dateSetListener, Context ctx) {

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(ctx,dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        return datePickerDialog;
    }

    @Override
    public String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH) +1;
        int year = cal.get(Calendar.YEAR);

        return convertDateString(day, month, year);

    }

    @Override
    public String mirrorDateString(String date) {
        String[] string = date.split("-");
        return string[2] + "-" + string[1] + "-" + string[0];
    }
}
