package com.example.med_manager.med_manager.ui;

import android.app.DatePickerDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.android.med_manager.R;
import com.example.med_manager.med_manager.model.Medication;
import com.example.med_manager.med_manager.provider.MedContract;
import com.example.med_manager.med_manager.utils.DatePickerFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static com.example.med_manager.med_manager.provider.MedContract.BASE_CONTENT_URI;
import static com.example.med_manager.med_manager.provider.MedContract.MEDS_PATH;
import static com.example.med_manager.med_manager.ui.MedListActivity.CREATE_MED;
import static com.example.med_manager.med_manager.ui.MedListActivity.EDIT_MED;
import static com.example.med_manager.med_manager.ui.MedListActivity.EXTRA_MED_ID;
import static com.example.med_manager.med_manager.ui.MedListActivity.MED_CREATE_CODE;
import static com.example.med_manager.med_manager.ui.MedListActivity.MED_EDIT_CODE;


public class AddMedActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener{
   // holder of startdate EditText or enddate EditText
    Object dateTag;

    private static String DATE_FORMAT ="dd/MM/yyyy";
    private Calendar mCalender;
    int ACTION_CODE;
    Uri SINGLE_MED_URI;
    long mMedId;

    Button saveMedButton;
    EditText etName;
    EditText etDecs;
    EditText etFreq;
    EditText etStartDate;
    EditText etEndtDateP;
    Button setDate;
    Medication medication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_med);

        // reference to ui elements
        etName = (EditText) findViewById(R.id.etNname);
        etDecs = (EditText)findViewById(R.id.et_Decs);
        etFreq = (EditText)findViewById(R.id.etFreq);
        etStartDate = (EditText)findViewById(R.id.dpStartDate);
        etEndtDateP = (EditText)findViewById(R.id.dpEndDate);




        saveMedButton = (Button)findViewById(R.id.save_med);
        saveMedButton.setOnClickListener(this);

        // retrieve the intent
        Intent intent = getIntent();
        // check to make sure intent is not null
        if (intent != null) {
            Bundle extras = intent.getExtras();
            // retrieve medicine id from the intent
            mMedId = extras.getInt("ID");

            // check the intent for create or edit code
            ACTION_CODE = extras.getInt(EDIT_MED);

            if (ACTION_CODE == MED_EDIT_CODE) {
                 SINGLE_MED_URI = ContentUris.withAppendedId(
                        BASE_CONTENT_URI.buildUpon().appendPath(MEDS_PATH).build(), mMedId);

                // retrieve med info from the Db by id
                Cursor cursor = getContentResolver().query(SINGLE_MED_URI,null,null,null,null);
                cursor.moveToFirst();
                int nameIndex = cursor.getColumnIndex(MedContract.MedEntry.COLUMN_NAME);
                int descIndex = cursor.getColumnIndex(MedContract.MedEntry.COLUMN_DESCRIPTION);
                int freqIndex = cursor.getColumnIndex(MedContract.MedEntry.COLUMN_FREQUENCY);
                int startDateIndex = cursor.getColumnIndex(MedContract.MedEntry.COLUMN_START_DATE);
                int endDateIndex = cursor.getColumnIndex(MedContract.MedEntry.COLUMN_END_DATE);

                //create variables to hold the med info from the cursor
                String name = cursor.getString(nameIndex);
                String desc = cursor.getString(descIndex);
                int freq = cursor.getInt(freqIndex);
                String startDate = cursor.getString(startDateIndex);
                String endDate = cursor.getString(endDateIndex);

                // create medication object
                 medication = new Medication(name,desc,freq,startDate,endDate);
                // close cursor to free up resource
                cursor.close();

                // set the ui element
                etName.setText(medication.getName());
                etDecs.setText(medication.getDescription());
                etFreq.setText(String.valueOf(medication.getFrequency()));
                etStartDate.setText(medication.getStartDate());
                etEndtDateP.setText(medication.getEndDate());





            }

        }


    }


    @Override
    public void onClick(View view) {

        String name = etName.getText().toString();
        String Description = etDecs.getText().toString();
        String frequency = etFreq.getText().toString();
        int frequency_to_int = Integer.valueOf(frequency);
        String startDate = etStartDate.getText().toString();
        String endDate = etEndtDateP.getText().toString();

        Medication medication = new Medication(name,Description,frequency_to_int,startDate,endDate);
        ContentValues cv = new ContentValues();
        cv.put(MedContract.MedEntry.COLUMN_NAME, medication.getName());
        cv.put(MedContract.MedEntry.COLUMN_DESCRIPTION, medication.getDescription());
        cv.put(MedContract.MedEntry.COLUMN_FREQUENCY, frequency_to_int);
        cv.put(MedContract.MedEntry.COLUMN_START_DATE, medication.getStartDate());
        cv.put(MedContract.MedEntry.COLUMN_END_DATE, medication.getEndDate());
        // check if in create or edit mode
        if (ACTION_CODE == MED_CREATE_CODE) {
            getContentResolver().insert(MedContract.MedEntry.CONTENT_URI, cv);

        } else if (ACTION_CODE == MED_EDIT_CODE) {
            getContentResolver().update(SINGLE_MED_URI,cv,null,null);

        }

        finish();



    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
        dateTag = v.getTag();

    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
         mCalender = new GregorianCalendar(year, month, day);
        setDate(mCalender);

    }

    private void setDate(final Calendar calendar) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String dateForEditText = dateFormat.format(calendar.getTime());
        // display date on appropriate EditText from the date picker
        if (dateTag == etStartDate.getTag()) {
            etStartDate.setText(dateForEditText);
        } else if (dateTag == etEndtDateP.getTag()) {
            etEndtDateP.setText(dateForEditText);
        }

    }

}
