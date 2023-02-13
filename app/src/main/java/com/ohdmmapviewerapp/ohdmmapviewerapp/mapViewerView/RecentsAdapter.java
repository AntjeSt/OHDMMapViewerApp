package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ohdmmapviewerapp.ohdmmapviewerapp.R;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel.Map;

import java.util.ArrayList;

public class RecentsAdapter extends RecyclerView.Adapter<RecentsAdapter.RecentsViewHolder> {

    private ArrayList<Map> recentMapsList;
    private ArchiveItemClickListener clickListener;

    public RecentsAdapter(ArrayList<Map> recentMapsList) {
        this.recentMapsList = recentMapsList;
    }

    @Override
    public RecentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recentsitem_layout, parent , false);
        return new RecentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecentsViewHolder holder, int position) {
        if (recentMapsList.get(position) != null){
        holder.mapTitle.setText(recentMapsList.get(position).getTitle());
        holder.mapDate.setText(recentMapsList.get(position).getDate());}
    }

    @Override
    public int getItemCount() {
        return recentMapsList.size();
    }

    public void updateData(ArrayList<Map> recentsMapList) {
        this.recentMapsList = recentsMapList;
        notifyDataSetChanged();
    }

    public class RecentsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mapTitle;
        private TextView mapDate;

        public RecentsViewHolder(View itemView) {
            super(itemView);

           mapTitle = itemView.findViewById(R.id.mapTitle);
           mapDate = itemView.findViewById(R.id.mapDate);
           itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onItemClickRecentsAdapter(view, getAdapterPosition());
        }
    }

    Map getItem(int id) {
        return recentMapsList.get(id);
    }

    void setClickListener(ArchiveItemClickListener archiveItemClickListener) {
        this.clickListener = archiveItemClickListener;
    }


}
