package com.example.med_manager.med_manager.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by salabs on 03/04/2018.
 */

public class MedContract {
    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.example.android.med_manager";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    public static final String MEDS_PATH = "meds";


    public static final class MedEntry implements BaseColumns {

        // MedEntry content URI = base content URI + path
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(MEDS_PATH).build();

        public static final String TABLE_NAME = "meds";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_FREQUENCY = "frequency";
        public static final String COLUMN_START_DATE = "start_date";
        public static final String COLUMN_END_DATE = "end_date";
    }
}
