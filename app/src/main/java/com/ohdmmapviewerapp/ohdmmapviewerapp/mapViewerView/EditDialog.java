package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentResultListener;

import com.ohdmmapviewerapp.ohdmmapviewerapp.R;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel.Map;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModel;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModelFactory;


public class EditDialog extends DialogFragment {

    private static final String TAG = "editdialog";

    OnInputListener onInputListener;
    View view;
    Context ctx;

    Button cancelButton;
    Button confirmButton;
    View editMapFolderLayout;
    EditText editTitle;
    EditText editMapFolder;
    ImageView folderPickerIcon;

    MapViewerViewModel mapViewerViewModel;
    FolderPickerDialog folderPickerDialog;

    String editCause;


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_dialog, container, false);
        ctx = getContext();
        mapViewerViewModel = MapViewerViewModelFactory.produceMapViewerViewModel(ctx);

        // get map or folder arguments from ArchiveActivity or FolderActivity
        Bundle bundle = getArguments();
        String folder = bundle.getString("FOLDER");
        Map map = (Map)bundle.getSerializable("MAP");
        Map map_foldercontent = (Map) bundle.getSerializable("MAP_FOLDERCONTENT");

        editTitle = view.findViewById(R.id.editTitleDialog);
        folderPickerIcon = view.findViewById(R.id.folderPickerIcon);
        editMapFolderLayout = view.findViewById(R.id.editMapFolderLayout);


        if (map_foldercontent != null){
            // remove edit folder name layout when only folder being edited
            editCause = "map_foldercontent";
            editTitle.setText(map_foldercontent.getTitle());
            editMapFolderLayout.setVisibility(View.GONE);
        }
        else if (folder != null){
            editCause = "folder";
            editTitle.setText(folder);
            editMapFolderLayout.setVisibility(View.GONE);
        }
        else {
            // remove edit folder name layout when only folder being edited
            editCause = "map";
            editTitle.setText(map.getTitle());
            editMapFolder = view.findViewById(R.id.editMapFolderDialog);
            editMapFolder.setText(map.getFolder());
        }

        getParentFragmentManager().setFragmentResultListener("FolderRequest", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(String requestKey, Bundle bundle) {
                String folder = bundle.getString("folder");
                editMapFolder.setText(folder);
            }
        });

        cancelButton = view.findViewById(R.id.cancel_button);
        confirmButton = view.findViewById(R.id.confirm_button);

        folderPickerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             folderPickerDialog = new FolderPickerDialog(1);
             folderPickerDialog.show(getActivity().getSupportFragmentManager(),"folderpicker");
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editCause.equals("folder")){
                    mapViewerViewModel.updateFolder(folder, editTitle.getText().toString());
                    onInputListener.notifyRVDataChanged();
                    dismiss();
                }
                else if (editCause.equals("map_foldercontent")){
                    mapViewerViewModel.updateMap(map_foldercontent.getMapID(), editTitle.getText().toString());
                    onInputListener.notifyRVDataChanged();
                    dismiss();
                }
                else {
                    mapViewerViewModel.updateMap(map.getMapID(),editTitle.getText().toString(),editMapFolder.getText().toString());
                    onInputListener.notifyRVDataChanged();
                    dismiss();
                }

            }
        });

        return view;
    }


    public interface OnInputListener {
        void notifyRVDataChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onInputListener = (OnInputListener) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage());
        }
    }
}
