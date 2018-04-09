package com.example.med_manager.med_manager.ui;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.med_manager.R;
import com.example.med_manager.med_manager.model.Medication;
import com.example.med_manager.med_manager.provider.MedContract;

public class AddMedActivity extends AppCompatActivity implements View.OnClickListener {

    Button saveMedButton;
    EditText etName;
    EditText etDecs;
    EditText etFreq;
    DatePicker dpStartDatePicker;
    DatePicker dpEndtDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_med);

        etName = (EditText) findViewById(R.id.etNname);
        etDecs = (EditText)findViewById(R.id.et_Decs);
        etFreq = (EditText)findViewById(R.id.etFreq);
        dpStartDatePicker = (DatePicker)findViewById(R.id.dpStartDate);
        dpEndtDatePicker = (DatePicker)findViewById(R.id.dpEndDate);


        saveMedButton = (Button)findViewById(R.id.save_med);
        saveMedButton.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {

        String name = etName.getText().toString();
        String Description = etDecs.getText().toString();
        String frequency = etFreq.getText().toString();
        int frequency_to_int = Integer.valueOf(frequency);
        String startDate = dpStartDatePicker.toString();
        String endDate = dpEndtDatePicker.toString();

        Medication medication = new Medication(name,Description,frequency_to_int,startDate,endDate);

        ContentValues cv = new ContentValues();
        cv.put(MedContract.MedEntry.COLUMN_NAME, medication.getName());
        cv.put(MedContract.MedEntry.COLUMN_DESCRIPTION,medication.getDescription());
        cv.put(MedContract.MedEntry.COLUMN_FREQUENCY,frequency_to_int);
        cv.put(MedContract.MedEntry.COLUMN_START_DATE,medication.getStartDate());
        cv.put(MedContract.MedEntry.COLUMN_END_DATE,medication.getEndDate());
        getContentResolver().insert(MedContract.MedEntry.CONTENT_URI,cv);
        finish();


    }
}
