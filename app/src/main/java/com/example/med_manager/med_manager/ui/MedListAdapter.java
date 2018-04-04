package com.example.med_manager.med_manager.ui;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.med_manager.R;

/**
 * Created by salabs on 03/04/2018.
 */

public class MedListAdapter extends RecyclerView.Adapter<MedListAdapter.MedViewHolder> {

    @Override
    public MedListAdapter.MedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MedListAdapter.MedViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /**
     * PlantViewHolder class for the recycler view item
     */
    class MedViewHolder extends RecyclerView.ViewHolder {

        TextView medName;
        TextView medDesc;

        public MedViewHolder(View itemView) {
            super(itemView);
            medName = (TextView) itemView.findViewById(R.id.med_name);
            medDesc = (TextView) itemView.findViewById(R.id.med_decs);
        }
    }
}
