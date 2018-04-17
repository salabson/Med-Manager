package com.example.med_manager.med_manager.ui;


import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.med_manager.R;
import com.example.med_manager.med_manager.provider.MedContract.MedEntry;

/**
 * Created by salabs on 03/04/2018.
 */

public class MedListAdapter extends RecyclerView.Adapter<MedListAdapter.MedViewHolder> {

    private Context mContext;
    private Cursor mCursor;
    private ListItemClickListener itemClickListener;



    /**
     * Constructor using the context and the db cursor
     *
     * @param listener the calling context/activity
     */
    public MedListAdapter(Context context, ListItemClickListener listener, Cursor cursor) {
        this.itemClickListener = listener;
        this.mCursor = cursor;
        this.mContext = context;
    }

    public interface ListItemClickListener {
        void onListItemClick(int id);
    }

    @Override
    public MedListAdapter.MedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Get the RecyclerView item layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.medlist_item, parent, false);
        return new MedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MedListAdapter.MedViewHolder holder, int position) {
        // Retrieve data from the cursor
        mCursor.moveToPosition(position);
        int idIndex = mCursor.getColumnIndex(MedEntry._ID);
        int nameIndex = mCursor.getColumnIndex(MedEntry.COLUMN_NAME);
        int descIndex = mCursor.getColumnIndex(MedEntry.COLUMN_DESCRIPTION);

       /* int frequencyIndex = mCursor.getColumnIndex(MedEntry.COLUMN_FREQUENCY);
        int startDateIndex = mCursor.getColumnIndex(MedEntry.COLUMN_START_DATE);
        int endDateIndex = mCursor.getColumnIndex(MedEntry.COLUMN_END_DATE);*/

        long id = mCursor.getLong(idIndex);
        String name = mCursor.getString(nameIndex);
        String desc = mCursor.getString(descIndex);

        // bind the data from the cursor to the ui

        holder.medId.setText(String.valueOf(id));
        holder.medName.setText(name);
        holder.medDesc.setText(desc);


    }

    @Override
    public int getItemCount() {
        if (mCursor == null) return 0;
        return mCursor.getCount();
    }

    /**
     * PlantViewHolder class for the recycler view item
     */
    class MedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView medId;
        TextView medName;
        TextView medDesc;

        public MedViewHolder(View itemView) {
            super(itemView);
            medName = (TextView) itemView.findViewById(R.id.med_name);
            medDesc = (TextView) itemView.findViewById(R.id.med_decs);
            medId = (TextView)itemView.findViewById(R.id.med_id);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int  itemClickedPositon = getAdapterPosition();
            //itemClickListener.onListItemClick(mCursor.getInt(mCursor.getColumnIndex(MedEntry._ID)));
            itemClickListener.onListItemClick(Integer.valueOf(medId.getText().toString()));
        }
    }



    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = newCursor;
        if (mCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }


}
