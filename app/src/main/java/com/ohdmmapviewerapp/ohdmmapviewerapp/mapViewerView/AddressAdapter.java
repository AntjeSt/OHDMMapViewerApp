package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerView;

import android.location.Address;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ohdmmapviewerapp.ohdmmapviewerapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

    private ArrayList<Address> addressList;
    private AddressAdapter.ItemClickListener clickListener;

    public AddressAdapter(ArrayList<Address> addressList) {
        this.addressList = addressList;
    }


    @Override
    public AddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_layout, parent, false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AddressAdapter.AddressViewHolder holder, int position) {
        holder.addressLine.setText(addressLine(addressList.get(position)));
    }

    public String addressLine (Address address){
        if (address.getExtras() != null){
            Bundle bundle = address.getExtras();
             return bundle.getString("display_name");
        }
        else {
            String addressLine = "";
            int size = address.getMaxAddressLineIndex();
            for (int i = 0; i <= size; i++) {
                if (i == 0) {
                    addressLine = address.getAddressLine(i);
                } else {
                    addressLine = addressLine.concat(", " + address.getAddressLine(i));
                }
            }
            return addressLine;
        }
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }


    public class AddressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView addressLine;

        public AddressViewHolder(View itemView) {
            super(itemView);
            addressLine = itemView.findViewById(R.id.addressLine);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onItemClickAddressAdapter(view, getAdapterPosition());
        }

    }

    // convenience method for getting data at click position
    Address getItem(int id) {
        return addressList.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClickAddressAdapter(View view, int position);
    }

}