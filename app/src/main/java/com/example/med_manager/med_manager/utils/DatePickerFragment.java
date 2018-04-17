package com.example.med_manager.med_manager.utils;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by salabs on 16/04/2018.
 */

public  class DatePickerFragment extends DialogFragment{
    private static String DATE_FORMAT ="yyyy-MM-dd";
    public Calendar mCalender = Calendar.getInstance();



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        int year = mCalender.get(Calendar.YEAR);
        int month = mCalender.get(Calendar.MONTH);
        int day = mCalender.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(),
                (DatePickerDialog.OnDateSetListener)
                        getActivity(), year, month, day);
    }



}
