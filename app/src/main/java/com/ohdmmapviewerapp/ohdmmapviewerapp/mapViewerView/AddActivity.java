package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ohdmmapviewerapp.ohdmmapviewerapp.R;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel.Map;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModel;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModelFactory;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity implements FolderPickerDialog.OnInputListener {

    MapViewerViewModel mapViewerViewModel;
    Context ctx;
    DatePickerDialog datePickerDialog;
    FloatingActionButton confirmButton;

    EditText titleInput;
    EditText folderInput;
    TextView dateInput;
    TextView boundingboxInput;
    ImageView folderPickerIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ctx = getApplicationContext();
        mapViewerViewModel = MapViewerViewModelFactory.produceMapViewerViewModel(ctx);

        Intent i= getIntent();
        ArrayList<GeoPoint> geoPoints = i.getParcelableArrayListExtra("geoPoints");

        initDatePicker();

        titleInput = findViewById(R.id.titleInput);
        folderInput = findViewById(R.id.folderInput);
        dateInput = findViewById(R.id.dateInput);
        boundingboxInput = findViewById(R.id.bbInput);
        boundingboxInput.setText("Longitude: " + geoPoints.get(0) + "," + geoPoints.get(1) );
        dateInput.setText(mapViewerViewModel.getTodaysDate());
        confirmButton = findViewById(R.id.confirmDetailsButton);
        folderPickerIcon = findViewById(R.id.folderPickerIcon);
        folderPickerIcon.setVisibility(View.GONE);

        if (mapViewerViewModel.getAllFoldersOrderedAlphabetically() != null){
            folderPickerIcon.setVisibility(View.VISIBLE);
            folderPickerIcon.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                FolderPickerDialog folderPickerDialog = new FolderPickerDialog(0);
                folderPickerDialog.show(getSupportFragmentManager(),"folderpicker");
                }
            });


        }

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (titleInput.getText().toString().length() != 0 && folderInput.getText().toString().length() != 0){
                    Map map = mapViewerViewModel.saveMap(titleInput.getText().toString(), dateInput.getText().toString(), folderInput.getText().toString());
                    mapViewerViewModel.requestData(map, geoPoints);
                    Intent intent = new Intent(AddActivity.this,ArchiveActivity.class);
                    startActivity(intent);
                }
                else if (titleInput.getText().toString().length() != 0){
                    Map map = mapViewerViewModel.saveMap(titleInput.getText().toString(), dateInput.getText().toString());
                    mapViewerViewModel.requestData(map, geoPoints);
                    Intent intent = new Intent(AddActivity.this, ArchiveActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(ctx, "Please provide a title for your map.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = mapViewerViewModel.convertDateString(day, month, year);
                dateInput.setText(date);
            }
        };

        datePickerDialog = mapViewerViewModel.getDatePickerDialog(dateSetListener, AddActivity.this);
    }

    public void openDatePicker(View view) {
        this.datePickerDialog.show();
    }

    public void navigateBack(View view) {
        Intent intent = new Intent(AddActivity.this, DownloadActivity.class);
        startActivity(intent);
    }

    @Override
    public void sendInput(String folder) {
        folderInput.setText(folder);
    }


    @Override
    protected void onPause() {
        super.onPause();
        mapViewerViewModel.persistData();
    }
}