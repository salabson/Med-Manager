package com.example.med_manager.med_manager.ui;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.android.med_manager.R;
import com.example.med_manager.med_manager.provider.MedContract;
import com.example.med_manager.med_manager.provider.MedDbHelper;

import static com.example.med_manager.med_manager.provider.MedContract.BASE_CONTENT_URI;
import static com.example.med_manager.med_manager.provider.MedContract.MEDS_PATH;

/**
 * Created by salabs on 04/04/2018.
 */

public class MedListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, MedListAdapter.ListItemClickListener {
    public static  final int  MED_CREATE_CODE = 0;
    public static  final int MED_EDIT_CODE = 1;
    public static  String  CREATE_MED;
    public static  String EDIT_MED;
    public static String EXTRA_MED_ID;

    static final int LOADER_ID = 500;
    RecyclerView mMedRecyclerView;
    MedListAdapter mAdapter;
    MedDbHelper mDbHelper;
    SQLiteDatabase mDb;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medlist);

        mDbHelper = new MedDbHelper(this);
        mDb = mDbHelper.getWritableDatabase();
        InsertData.insertMedData(mDb);

        // The list activity displays the meds in a  recycler view
        mMedRecyclerView = (RecyclerView) findViewById(R.id.med_recycler_view);
        mMedRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new MedListAdapter(this, this, null);
        mMedRecyclerView.setAdapter(mAdapter);

        final Intent intent = new Intent(this, AddMedActivity.class);
        intent.putExtra(CREATE_MED,MED_CREATE_CODE);

        fab = (FloatingActionButton)findViewById(R.id.add_button);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(intent);
            }
        });

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri MED_URI = BASE_CONTENT_URI.buildUpon().appendPath(MEDS_PATH).build();
        return new CursorLoader(this,MED_URI , null,
                null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cursor.moveToFirst();
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

   @Override
    protected void onResume() {
        super.onResume();
        Uri MED_URI = BASE_CONTENT_URI.buildUpon().appendPath(MEDS_PATH).build();
        Cursor cursor = this.getContentResolver().query(MED_URI,null,null,null,null,null);
        cursor.moveToFirst();
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onListItemClick(int id) {
        Intent intent = new Intent(this, AddMedActivity.class);
        intent.putExtra(EDIT_MED,MED_EDIT_CODE);
        intent.putExtra("ID", id);
        startActivity(intent);
        //Toast.makeText(this,String.valueOf(id),Toast.LENGTH_LONG).show();
    }
}
