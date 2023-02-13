package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ohdmmapviewerapp.ohdmmapviewerapp.R;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModel;

import java.util.ArrayList;

public class FolderPickerAdapter extends RecyclerView.Adapter<FolderPickerAdapter.FolderPickerViewHolder> {

    public static final String TAG  = "folderpicker";
    private ArrayList<String> folderList;
    private FolderPickerAdapter.ItemClickListener clickListener;
    private MapViewerViewModel mapViewerViewModel;

    public FolderPickerAdapter(ArrayList<String> folderList) {
        this.folderList = folderList;
    }


    @Override
    public FolderPickerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.folder_dialog_layout, parent, false);
        return new FolderPickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FolderPickerAdapter.FolderPickerViewHolder holder, int position) {
        holder.folderTitle.setText(folderList.get(position));
    }

    @Override
    public int getItemCount() {
        return folderList.size();
    }


    public class FolderPickerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView folderTitle;


        public FolderPickerViewHolder(View itemView) {
            super(itemView);
            folderTitle = itemView.findViewById(R.id.folderTitle);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (clickListener != null)
                clickListener.onItemClickFolderPickerAdapter(view, getAdapterPosition());
        }

    }

    String getItem(int position) {
        return folderList.get(position);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClickFolderPickerAdapter(View view, int position);
    }
}