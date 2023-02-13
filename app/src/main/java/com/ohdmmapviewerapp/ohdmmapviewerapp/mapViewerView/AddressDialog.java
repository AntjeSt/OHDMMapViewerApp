package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerView;

import android.content.Context;
import android.location.Address;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ohdmmapviewerapp.ohdmmapviewerapp.R;

import java.util.ArrayList;

public class AddressDialog extends DialogFragment implements AddressAdapter.ItemClickListener{

    private static final String TAG = "addresspopup";
    RecyclerView addressRecyclerView;
    AddressAdapter addressAdapter;
    View view;
    Context ctx;
    ArrayList<Address> addresses;
    DownloadActivity downloadActivity;

    public AddressDialog(ArrayList<Address> addresses, DownloadActivity downloadActivity){
        this.addresses = addresses;
        this.downloadActivity = downloadActivity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_addresspopup, container, false);
        addressRecyclerView = view.findViewById(R.id.addressRecyclerview);
        ctx = getContext();

        addressRecyclerView.setHasFixedSize(true);
        addressRecyclerView.setLayoutManager(new LinearLayoutManager(ctx));

        addressAdapter = new AddressAdapter(addresses);
        addressAdapter.setClickListener(this);
        addressRecyclerView.setAdapter(addressAdapter);

        return view;
    }

    @Override
    public void onItemClickAddressAdapter(View view, int position) {
        Address address = addressAdapter.getItem(position);
            downloadActivity.setAddressMarker(address);
            dismiss();
    }

}
