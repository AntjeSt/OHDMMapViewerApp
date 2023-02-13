

package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerView;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ohdmmapviewerapp.ohdmmapviewerapp.R;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModel;

import java.util.ArrayList;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.FolderViewHolder> {

    private ArrayList<String> folderList;
    private ArchiveItemClickListener clickListener;
    MapViewerViewModel mapViewerViewModel;
    Context ctx;


        public FolderAdapter(ArrayList<String> FolderList, MapViewerViewModel mapViewerViewModel, Context  ctx) {
            this.folderList = FolderList;
            this.mapViewerViewModel = mapViewerViewModel;
            this.ctx = ctx;
        }



        @Override
        public FolderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.folder_layout, parent , false);
                return new FolderViewHolder(view);
        }

        @Override
        public void onBindViewHolder(FolderViewHolder holder, int position) {
                holder.folderTitle.setText(folderList.get(position));
        }

        @Override
        public int getItemCount() {
            return folderList.size();
        }

        public void removeFolder(int position) {
            mapViewerViewModel.deleteFolder(folderList.get(position));
            folderList = mapViewerViewModel.getAllFoldersOrderedAlphabetically();
            notifyDataSetChanged();
        }

    public void updateData() {
            folderList = mapViewerViewModel.getAllFoldersOrderedAlphabetically();
    }

    public class FolderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener{

            private TextView folderTitle;

            public FolderViewHolder(View itemView) {
                super(itemView);
                folderTitle = itemView.findViewById(R.id.folderTitle);
                itemView.setOnClickListener(this);
                itemView.setOnCreateContextMenuListener(this);
            }

            @Override
            public void onClick(View view) {
                if (clickListener != null) clickListener.onItemClickFolderAdapter(view, getAdapterPosition());
            }

            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(getAdapterPosition(), 1, 1, "Edit");
                menu.add(getAdapterPosition(), 2, 2, "Delete");
            }


        }
        // convenience method for getting data at click position
        String getItem(int position) {
            return folderList.get(position);
        }

        // allows click events to be caught
        void setClickListener(ArchiveItemClickListener archiveItemClickListener) {
            this.clickListener = archiveItemClickListener;
        }

    }


