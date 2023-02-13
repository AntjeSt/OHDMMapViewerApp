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


public class FolderContentAdapter extends RecyclerView.Adapter<FolderContentAdapter.FolderContentViewHolder> {

    private static final int VIEW_TYPE_DOWNLOADED = 1;
    private static final int VIEW_TYPE_DOWNLOADING = 2;

    private ArrayList<Map> mapsList;
    private String folder;
    private FolderContentAdapter.ItemClickListener clickListener;
    private MapViewerViewModel mapViewerViewModel;

    public FolderContentAdapter(String folder, MapViewerViewModel mapViewerViewModel) {
        this.folder = folder;
        this.mapsList = mapViewerViewModel.getAllMapsOfFolder(folder);
        this.mapViewerViewModel = mapViewerViewModel;
    }


    @Override
    public FolderContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_DOWNLOADED) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mapcard_layout, parent , false);
            return new FolderContentAdapter.FolderContentViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.downloadingmapcard_layout, parent , false);
            return new FolderContentAdapter.FolderContentViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(FolderContentAdapter.FolderContentViewHolder holder, int position) {
        if (position < mapsList.size() ) {
            holder.mapTitle.setText(mapsList.get(position).getTitle());
            holder.mapDate.setText(mapsList.get(position).getDate());
        }

    }


    @Override
    public int getItemCount() {
        return mapsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mapsList.get(position).getMapFilePath()!= null) {
            if (!mapsList.get(position).getMapFilePath().isEmpty()) {
                return VIEW_TYPE_DOWNLOADED;
            } else {
                return VIEW_TYPE_DOWNLOADING;
            }
         }else {
            return VIEW_TYPE_DOWNLOADING;
        }
    }

    public void removeMap(int position) {
        mapViewerViewModel.deleteMap(mapsList.get(position));
        mapsList = mapViewerViewModel.getAllMapsOrderedAlphabetically();
        notifyDataSetChanged();
    }

    // called when maps was edited in editdialog
    public void updateData() {
        mapsList = mapViewerViewModel.getAllMapsOfFolder(folder);
        notifyDataSetChanged();
    }

    Map getItem(int id) {
        return mapsList.get(id);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }


    public class FolderContentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener{

        private TextView mapTitle;
        private TextView mapDate;

        public FolderContentViewHolder(View itemView) {
            super(itemView);

            mapTitle = itemView.findViewById(R.id.mapCardTitle);
            mapDate = itemView.findViewById(R.id.mapCardDate);

            if (itemView.getSourceLayoutResId() == R.layout.mapcard_layout) {
                itemView.setOnClickListener(this);
                itemView.setOnCreateContextMenuListener(this);
            }
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onItemClickFolderContentAdapter(view, getAdapterPosition());
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(getAdapterPosition(), 1, 1, "Edit");
            menu.add(getAdapterPosition(), 2, 2, "Delete");
        }
    }



    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClickFolderContentAdapter(View view, int position);
    }

}