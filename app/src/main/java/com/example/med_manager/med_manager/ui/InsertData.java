package com.example.med_manager.med_manager.ui;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


import com.example.med_manager.med_manager.provider.MedContract.MedEntry;
import com.example.med_manager.med_manager.provider.MedDbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by salabs on 04/04/2018.
 */

public class InsertData {

    public static void insertMedData(SQLiteDatabase db) {

        if(db == null) return;

       /* //initialize arraylist of words
        words.add( new Word("Bai", "Dog", "Kare",R.drawable.animals, false));
        words.add( new Word("Babba", "Donkey", "Jaki", R.drawable.donkey, true));
        words.add( new Word("Ankilin", "Lizard", "Kadangare", R.drawable.lizard,false));
        words.add( new Word("Tuje", "Horse", "Doki", R.drawable.horse,true));
        words.add( new Word("Tanga", "Cow", "Shanu", R.drawable.cow, false));
        words.add( new Word("Andokko", "Grasshopper", "Fara", R.drawable.grassphopper, true));*/

        // list of data to be insert to db
        List<ContentValues> list = new ArrayList<ContentValues>();

        ContentValues cv = new ContentValues();
        cv.put(MedEntry.COLUMN_NAME,"Panadol");
        cv.put(MedEntry.COLUMN_DESCRIPTION,"This is medicine for headache take two tabs twice a day");
        cv.put(MedEntry.COLUMN_FREQUENCY,2);
        cv.put(MedEntry.COLUMN_START_DATE,"22/04/2018");
        cv.put(MedEntry.COLUMN_END_DATE,"28/04/2018");
        list.add(cv);

        cv = new ContentValues();
        cv.put(MedEntry.COLUMN_NAME,"Pectol");
        cv.put(MedEntry.COLUMN_DESCRIPTION,"This is medicine for Cough a thrice a day");
        cv.put(MedEntry.COLUMN_FREQUENCY,4);
        cv.put(MedEntry.COLUMN_START_DATE,"26/05/2018");
        cv.put(MedEntry.COLUMN_END_DATE,"28/05/2018");
        list.add(cv);

        cv = new ContentValues();
        cv.put(MedEntry.COLUMN_NAME,"Plagin");
        cv.put(MedEntry.COLUMN_DESCRIPTION,"This is medicine for stomatchace a thrice a day");
        cv.put(MedEntry.COLUMN_FREQUENCY,8);
        cv.put(MedEntry.COLUMN_START_DATE,"1/05/2018");
        cv.put(MedEntry.COLUMN_END_DATE,"20/05/2018");
        list.add(cv);

        cv = new ContentValues();
        cv.put(MedEntry.COLUMN_NAME,"methylphenidate");
        cv.put(MedEntry.COLUMN_DESCRIPTION,"treats attention deficit disorder and excessive sleepiness");
        cv.put(MedEntry.COLUMN_FREQUENCY,4);
        cv.put(MedEntry.COLUMN_START_DATE,"1/06/2018");
        cv.put(MedEntry.COLUMN_END_DATE,"25/07/2018");
        list.add(cv);
        cv = new ContentValues();
        cv.put(MedEntry.COLUMN_NAME,"pindolol");
        cv.put(MedEntry.COLUMN_DESCRIPTION,"treats high blood pressure, take one tablet");
        cv.put(MedEntry.COLUMN_FREQUENCY,4);
        cv.put(MedEntry.COLUMN_START_DATE,"1/06/2018");
        cv.put(MedEntry.COLUMN_END_DATE,"25/07/2018");
        list.add(cv);

        cv = new ContentValues();
        cv.put(MedEntry.COLUMN_NAME,"pindolol");
        cv.put(MedEntry.COLUMN_DESCRIPTION,"suppresses immune system and give energy");
        cv.put(MedEntry.COLUMN_FREQUENCY,15);
        cv.put(MedEntry.COLUMN_START_DATE,"1/06/2018");
        cv.put(MedEntry.COLUMN_END_DATE,"25/07/2018");
        list.add(cv);

        cv = new ContentValues();
        cv.put(MedEntry.COLUMN_NAME,"Levothyroxine");
        cv.put(MedEntry.COLUMN_DESCRIPTION,"Levothyroxine treats hypothyroidism (low thyroid hormone). It is also used to treat or prevent goiter (enlarged thyroid gland), which can be caused by hormone imbalances, radiation treatment, surgery, or cancer");
        cv.put(MedEntry.COLUMN_FREQUENCY,10);
        cv.put(MedEntry.COLUMN_START_DATE,"1/06/2018");
        cv.put(MedEntry.COLUMN_END_DATE,"25/07/2018");
        list.add(cv);

        cv = new ContentValues();
        cv.put(MedEntry.COLUMN_NAME,"Lisinopri");
        cv.put(MedEntry.COLUMN_DESCRIPTION,"Lisinopril is used to treat high blood pressure (hypertension) or congestive heart failure. It is also used to improve survival after a heart attack");
        cv.put(MedEntry.COLUMN_FREQUENCY,4);
        cv.put(MedEntry.COLUMN_START_DATE,"1/06/2018");
        cv.put(MedEntry.COLUMN_END_DATE,"25/07/2018");
        list.add(cv);

        cv = new ContentValues();
        cv.put(MedEntry.COLUMN_NAME,"Simvastatin");
        cv.put(MedEntry.COLUMN_DESCRIPTION,"Simvastatin is used to lower cholesterol and triglycerides (types of fat) in the blood");
        cv.put(MedEntry.COLUMN_FREQUENCY,4);
        cv.put(MedEntry.COLUMN_START_DATE,"1/06/2018");
        cv.put(MedEntry.COLUMN_END_DATE,"25/07/2018");
        list.add(cv);

        cv = new ContentValues();
        cv.put(MedEntry.COLUMN_NAME,"Hydrocodone");
        cv.put(MedEntry.COLUMN_DESCRIPTION,"Hydrocodone and acetaminophen combination is used to relieve moderate to moderately severe pain");
        cv.put(MedEntry.COLUMN_FREQUENCY,4);
        cv.put(MedEntry.COLUMN_START_DATE,"1/06/2018");
        cv.put(MedEntry.COLUMN_END_DATE,"25/07/2018");
        list.add(cv);


        cv = new ContentValues();
        cv.put(MedEntry.COLUMN_NAME,"Losartan");
        cv.put(MedEntry.COLUMN_DESCRIPTION,"Losartan is used to treat high blood pressure (hypertension). It is also used to lower the risk of stroke in certain people with heart disease");
        cv.put(MedEntry.COLUMN_FREQUENCY,4);
        cv.put(MedEntry.COLUMN_START_DATE,"1/06/2018");
        cv.put(MedEntry.COLUMN_END_DATE,"25/07/2018");
        list.add(cv);



        try {

            db.beginTransaction();
            // clear db
           // db.delete(MedEntry.TABLE_NAME,null,null);
            // loop to insert data in the list
            for (ContentValues c : list) {
                db.insert(MedEntry.TABLE_NAME,null,c);
            }
            db.setTransactionSuccessful();
            db.endTransaction();

        } catch (SQLException e) {
        }finally {
        }



    }

}
