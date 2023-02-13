

package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ohdmmapviewerapp.ohdmmapviewerapp.R;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel.Map;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModel;

import java.util.ArrayList;

public class AllMapsAdapter extends RecyclerView.Adapter<AllMapsAdapter.AllMapsViewHolder> {

    private static final int VIEW_TYPE_ADD = 0 ;
    private static final int VIEW_TYPE_DOWNLOADED = 1;
    private static final int VIEW_TYPE_DOWNLOADING = 2;

    private ArrayList<Map> mapsList;
    private ArchiveItemClickListener clickListener;
    private MapViewerViewModel mapViewerViewModel;

    public AllMapsAdapter(ArrayList<Map> mapsList, MapViewerViewModel mapViewerViewModel) {
        this.mapsList = mapsList;
        this.mapViewerViewModel = mapViewerViewModel;
    }


    @Override
    public AllMapsViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_DOWNLOADED) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mapcard_layout, parent , false);
            return new AllMapsViewHolder(view);
        }
        else if (viewType == VIEW_TYPE_DOWNLOADING){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.downloadingmapcard_layout, parent , false);
            return new AllMapsViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newmapcard_layout, parent , false);
            return new AllMapsViewHolder(view);

        }

    }

    @Override
    public void onBindViewHolder(AllMapsAdapter.AllMapsViewHolder holder, int position) {
        if (position < mapsList.size() ) {
            holder.mapTitle.setText(mapsList.get(position).getTitle());
            holder.mapDate.setText(mapsList.get(position).getDate());
        }
    }

    @Override
    public int getItemCount() {
        return mapsList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mapsList.size()){
            return VIEW_TYPE_ADD;
        }
        else if (mapsList.get(position).getMapFilePath()!= null) {
            if (!mapsList.get(position).getMapFilePath().isEmpty()) {
                return VIEW_TYPE_DOWNLOADED;
            }
            else {return VIEW_TYPE_DOWNLOADING; }
        }
        else {
            return VIEW_TYPE_DOWNLOADING;
        }

    }

    public void removeMap(int position) {
        mapViewerViewModel.deleteMap(mapsList.get(position));
        mapsList = mapViewerViewModel.getAllMapsOrderedAlphabetically();
        notifyDataSetChanged();
    }

    public void updateData() {
        mapsList = mapViewerViewModel.getAllMapsOrderedAlphabetically();
        notifyDataSetChanged();
    }

    Map getItem(int id) {
        return mapsList.get(id);
    }

    void setClickListener(ArchiveItemClickListener archiveItemClickListener) {
        this.clickListener = archiveItemClickListener;
    }

    public class AllMapsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener{

        private TextView mapTitle;
        private TextView mapDate;

        public AllMapsViewHolder(View itemView) {
            super(itemView);

            mapTitle = itemView.findViewById(R.id.mapCardTitle);
            mapDate = itemView.findViewById(R.id.mapCardDate);

            if (itemView.getSourceLayoutResId() == R.layout.mapcard_layout || itemView.getSourceLayoutResId() == R.layout.newmapcard_layout) {
                itemView.setOnClickListener(this);
                itemView.setOnCreateContextMenuListener(this);
            }
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onItemClickAllMapsAdapter(view, getAdapterPosition());
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(getAdapterPosition(), 3, 1, "Edit");
            menu.add(getAdapterPosition(), 4, 2, "Delete");
        }

    }

}