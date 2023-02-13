package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ohdmmapviewerapp.ohdmmapviewerapp.R;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModel;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModelFactory;


public class FolderPickerDialog extends DialogFragment implements FolderPickerAdapter.ItemClickListener {

    private static final String TAG = "folderpicker";
    RecyclerView folderPickerRecyclerView;
    FolderPickerAdapter folderPickerAdapter;
    View view;
    Context ctx;
    MapViewerViewModel mapViewerViewModel;
    Button cancelButton;
    OnInputListener onInputListener;
    int invokingActivity;

    public FolderPickerDialog (int invokingActivity){
        this.invokingActivity = invokingActivity;
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_folder_picker_dialog, container, false);
    folderPickerRecyclerView = view.findViewById(R.id.folderPicker_rv);
    ctx = getContext();
    mapViewerViewModel = MapViewerViewModelFactory.produceMapViewerViewModel(ctx);

    cancelButton = view.findViewById(R.id.cancel_button);

        cancelButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dismiss();
        }
    });

        folderPickerRecyclerView.setHasFixedSize(true);
        folderPickerRecyclerView.setLayoutManager(new LinearLayoutManager(ctx));


        folderPickerAdapter = new FolderPickerAdapter(mapViewerViewModel.getAllFoldersOrderedAlphabetically());
        folderPickerAdapter.setClickListener(this);
        folderPickerRecyclerView.setAdapter(folderPickerAdapter);
        return view;
}

    @Override
    public void onItemClickFolderPickerAdapter(View view, int position) {
        String folder = folderPickerAdapter.getItem(position);

        // check originating Activity 1 - EditDialogFragment in ArchiveActivity, 0 - AddActivity
        if (invokingActivity == 1) {
            Bundle result = new Bundle();
            result.putString("folder", folder);
            getParentFragmentManager().setFragmentResult("folderRequest", result);
            dismiss();
        }
        else {
            onInputListener.sendInput(folder);
            dismiss();
        }
    }

    public interface OnInputListener{
        void sendInput(String folder);
    }

    @Override
    public void onAttach( Context context) {
        super.onAttach(context);
        try{
            onInputListener = (FolderPickerDialog.OnInputListener) getActivity();
        }catch (ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage());
        }
    }


}
