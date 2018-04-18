package com.example.med_manager.med_manager.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.android.med_manager.R;
import com.example.med_manager.med_manager.provider.MedContract;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.med_manager.med_manager.provider.MedContract.BASE_CONTENT_URI;
import static com.example.med_manager.med_manager.provider.MedContract.MEDS_PATH;
import static com.example.med_manager.med_manager.ui.MedListActivity.CREATE_MED;
import static com.example.med_manager.med_manager.ui.MedListActivity.EDIT_MED;
import static com.example.med_manager.med_manager.ui.MedListActivity.MED_CREATE_CODE;
import static com.example.med_manager.med_manager.ui.MedListActivity.MED_EDIT_CODE;

public class SearchActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, MedListAdapter.ListItemClickListener{
    RecyclerView mSearchRecylerView;
     MedListAdapter mAdapter;
    static final int SEARCH_LOADER_ID = 600;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // The list activity displays the meds in a  recycler view
        mSearchRecylerView = (RecyclerView) findViewById(R.id.search_recycler_view);
        mSearchRecylerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new MedListAdapter(this, this, null);
        mSearchRecylerView.setAdapter(mAdapter);

        final Intent intent = new Intent(this, AddMedActivity.class);
        intent.putExtra(CREATE_MED,MED_CREATE_CODE);



        getSupportLoaderManager().initLoader(SEARCH_LOADER_ID, null, this);
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
    public void onListItemClick(int id) {
        Intent intent = new Intent(this, AddMedActivity.class);
        intent.putExtra(EDIT_MED,MED_EDIT_CODE);
        intent.putExtra("ID", id);
        startActivity(intent);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconified(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.setMaxWidth(Integer.MAX_VALUE);
        MenuItemCompat.expandActionView(searchViewItem);



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                // load all data from the Db if searchview is empty
                Uri MED_URI = BASE_CONTENT_URI.buildUpon().appendPath(MEDS_PATH).build();
                if (TextUtils.isEmpty(newText) || newText.length() == 0) {
                    Cursor cursor = getContentResolver().query(MED_URI,null,null,null,null);
                    mAdapter.swapCursor(cursor);

                } else {
                    String queryString = MedContract.MedEntry.COLUMN_NAME + " LIKE " + "'%" + newText + "%'" ;
                    // load all data from the Db based on searchview string
                    Cursor cursor = getContentResolver().query(MED_URI,null,queryString,null,null);
                   mAdapter.swapCursor(cursor);
                }

                return true;
            }
        });

        MenuItemCompat.setOnActionExpandListener(searchViewItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            // this expand the search view as it return true
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            //this close the search activity as search view collapse
            public boolean onMenuItemActionCollapse(MenuItem item) {
                finish();
                return true;
            }
        });

        return true;
    }
}
