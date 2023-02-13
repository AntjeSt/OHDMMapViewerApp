package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ohdmmapviewerapp.ohdmmapviewerapp.R;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel.Map;

import java.util.ArrayList;

public class MapPickerAdapter extends RecyclerView.Adapter<MapPickerAdapter.MapPickerViewHolder> {

    private ArrayList<Map> mapsList;
    private MapPickerAdapter.ItemClickListener clickListener;

    public MapPickerAdapter(ArrayList<Map> mapsList) {
        this.mapsList = mapsList;
    }



    @Override
    public MapPickerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mapcard_dialog_layout, parent, false);
            return new MapPickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MapPickerAdapter.MapPickerViewHolder holder, int position) {
            holder.mapTitle.setText(mapsList.get(position).getTitle());
            holder.mapDate.setText(mapsList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return mapsList.size();
    }


    public class MapPickerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mapTitle;
        private TextView mapDate;

        public MapPickerViewHolder(View itemView) {
            super(itemView);
            mapTitle = itemView.findViewById(R.id.mapCardTitle);
            mapDate = itemView.findViewById(R.id.mapCardDate);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onItemClickMapPickerAdapter(view, getAdapterPosition());
        }

    }

    // convenience method for getting data at click position
    Map getItem(int id) {
        return mapsList.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClickMapPickerAdapter(View view, int position);
    }

}
