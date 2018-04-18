package com.example.med_manager.med_manager.ui;

import android.content.ContentUris;
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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
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
    Uri MED_URI;

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

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // delete from database
                int id = viewHolder.getAdapterPosition() + 1;
               Uri SINGLE_MED_URI = ContentUris.withAppendedId(
                        BASE_CONTENT_URI.buildUpon().appendPath(MEDS_PATH).build(), id);
                String queryString = MedContract.MedEntry.COLUMN_NAME + " = " +  id ;

                MED_URI = BASE_CONTENT_URI.buildUpon().appendPath(MEDS_PATH).build();
                getContentResolver().delete(SINGLE_MED_URI,queryString,null);
                Cursor cursor = getContentResolver().query(MED_URI,null,null,null,null);
                cursor.moveToFirst();
                mAdapter.swapCursor(cursor);

            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mMedRecyclerView);

        // start service
        Intent reminderIntent = new Intent(this, MedReminderIntentService.class);
        reminderIntent.setAction(ReminderTasks.ACTION_MEDICATION_REMINDER);
        startService(reminderIntent);

        // issue reminder
        ReminderScheduler.scheduleMedicationReminder(this);

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    private void requery(Uri MED_URI, String queryString) {
        getContentResolver().delete(MED_URI,queryString,null);
        Cursor cursor = getContentResolver().query(MED_URI,null,null,null,null);
        cursor.moveToFirst();
        mAdapter.swapCursor(cursor);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
         MED_URI = BASE_CONTENT_URI.buildUpon().appendPath(MEDS_PATH).build();
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
        // start add medicine activity and pass the id of the clicked item on the listview
        Intent intent = new Intent(this, AddMedActivity.class);
        intent.putExtra(EDIT_MED,MED_EDIT_CODE);
        intent.putExtra("ID", id);
        startActivity(intent);
        //Toast.makeText(this,String.valueOf(id),Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            Intent searchIntent = new Intent(this,SearchActivity.class);
            startActivity(searchIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }





}
