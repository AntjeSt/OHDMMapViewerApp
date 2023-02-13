package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.ohdmmapviewerapp.ohdmmapviewerapp.R;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel.Map;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModel;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModelFactory;

public class MenuActivity extends AppCompatActivity {

    CardView mapCard;
    CardView archiveCard;
    CardView downloadCard;
    CardView settingsCard;

    ActivityResultLauncher<Intent> activityResultLauncher;
    MapViewerViewModel mapViewerViewModel;

    private final String TAG = "FCM-Menu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mapViewerViewModel = MapViewerViewModelFactory.produceMapViewerViewModel(getApplicationContext());

        mapCard = findViewById(R.id.mapCard);
        archiveCard = findViewById(R.id.archiveCard);
        downloadCard = findViewById(R.id.downloadCard);
        settingsCard = findViewById(R.id.settingsCard);

        Intent intent = getIntent();
        if (intent.hasExtra("storage_permission")){
            Snackbar.make(downloadCard, "You need to grant the storage permissions to use the app.", Snackbar.LENGTH_SHORT).show();
        }

        // check for notification payload
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String mapID = extras.getString("mapID");
            Log.d(TAG, mapID);
            Snackbar.make(downloadCard, "Your map is getting downloaded.", Snackbar.LENGTH_SHORT).show();
            mapViewerViewModel.downloadFile(mapID);
        }


        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK){
                    if(Environment.isExternalStorageManager()){
                    }
                    else{
                        Snackbar.make(downloadCard, "You need to grant the storage permissions to use the app.", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });

        if (!Environment.isExternalStorageManager()){
            Intent intent2 = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
            activityResultLauncher.launch(intent2);
        }

        mapCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, MapViewActivity.class);
                Map map = mapViewerViewModel.getMostRecentMap();
                if (map != null){
                    intent.putExtra("map1", map);
                }
                startActivity(intent);
            }
        });

        archiveCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Environment.isExternalStorageManager()){
                Intent intent = new Intent(MenuActivity.this, ArchiveActivity.class);
                startActivity(intent);
                }
                else{
                    Snackbar.make(downloadCard, "You need to grant the storage permissions to use the app.", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        downloadCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Environment.isExternalStorageManager()){
                    if (mapViewerViewModel.isConnected(getApplicationContext())) {
                        Intent intent = new Intent(MenuActivity.this, DownloadActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Snackbar.make(downloadCard, "You need a working internet connection to download a map.", Snackbar.LENGTH_SHORT).show();
                    }
                }
                else{
                    Snackbar.make(downloadCard, "You need to grant the storage permissions to use the app.", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        settingsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }


}