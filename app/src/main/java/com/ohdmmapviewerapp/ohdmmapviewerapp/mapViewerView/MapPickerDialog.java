package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ohdmmapviewerapp.ohdmmapviewerapp.R;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel.Map;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModel;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModelFactory;

import java.util.ArrayList;

public class MapPickerDialog extends DialogFragment implements MapPickerAdapter.ItemClickListener  {

    private static final String TAG = "mappicker";
    RecyclerView mapPickerRecyclerView;
    MapPickerAdapter mapPickerAdapter;
    OnInputListener onInputListener;
    View view;
    Context ctx;
    MapViewerViewModel mapViewerViewModel;
    Button cancelButton;
    Map currentMap;


    public MapPickerDialog(Map currentMap){
        this.currentMap = currentMap;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ctx = getContext();
        mapViewerViewModel = MapViewerViewModelFactory.produceMapViewerViewModel(ctx);
        ArrayList<Map> remainingMaps = mapViewerViewModel.getRemainingMapsOrderedAlphabetically(currentMap);

        if (remainingMaps.size() > 0) {
            view = inflater.inflate(R.layout.fragment_map_picker_dialog, container, false);

            mapPickerRecyclerView = view.findViewById(R.id.mapPicker_default);
            cancelButton = view.findViewById(R.id.cancel_button);
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

            mapPickerRecyclerView.setHasFixedSize(true);
            mapPickerRecyclerView.setLayoutManager(new GridLayoutManager(ctx, 3));

            mapPickerAdapter = new MapPickerAdapter(mapViewerViewModel.getRemainingMapsOrderedAlphabetically(currentMap));
            mapPickerAdapter.setClickListener(this);
            mapPickerRecyclerView.setAdapter(mapPickerAdapter);
            return view;
        } else {
            view = inflater.inflate(R.layout.fragment_map_picker_dialog_default, container, false);
            cancelButton = view.findViewById(R.id.cancel_button);
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
            return view;
        }

}

    @Override
    public void onItemClickMapPickerAdapter(View view, int position) {
        Map map = mapPickerAdapter.getItem(position);
        onInputListener.sendInput(map);
        dismiss();
    }

    public interface OnInputListener{
        void sendInput(Map map);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            onInputListener = (OnInputListener) getActivity();
        }catch (ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage() );
        }
    }
}