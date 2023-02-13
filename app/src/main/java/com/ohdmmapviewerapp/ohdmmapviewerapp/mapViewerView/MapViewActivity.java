package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerView;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ohdmmapviewerapp.ohdmmapviewerapp.R;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel.Map;


public class MapViewActivity extends AppCompatActivity implements MapPickerDialog.OnInputListener{

    private FloatingActionButton menuButton;
    private FloatingActionButton multipleButton;
    private Map map1;
    private Map map2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        map1 = (Map) getIntent().getSerializableExtra("map1");

        if (map1 != null) {

        setContentView(R.layout.activity_map_view);
        menuButton = findViewById(R.id.menuButton);
        multipleButton = findViewById(R.id.add_remove_Button);

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapViewActivity.this, ArchiveActivity.class);
                intent.putExtra("map1", map1);
                if (map2 != null) {
                    intent.putExtra("map2", map2);
                }
                startActivity(intent);
            }
        });

        multipleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (map2 == null) {
                    MapPickerDialog mapPickerDialog = new MapPickerDialog(map1);
                    mapPickerDialog.show(getSupportFragmentManager(), "mappicker");
                }
                else {
                    View fragmentView1 = findViewById(R.id.fragment_map_view1);
                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            0,
                            2
                    );
                    fragmentView1.setLayoutParams(param);
                    View fragmentView2 = findViewById(R.id.fragment_map_view2);
                    LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            0,
                            0
                    );
                    fragmentView2.setLayoutParams(param2);
                    getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.fragment_map_view2))
                            .commit();
                    map2 = null;
                }
            }
        });

        if (savedInstanceState == null) {
            this.startFirstFragment(map1);
        }

    }
        else {
            // check for recentMap
            setContentView(R.layout.notfound_default);
            TextView text = findViewById(R.id.defaultText);
            text.setText("No recently opened map available.");
            Button actionButton = findViewById(R.id.actionButton);
            actionButton.setText("Open map from archive");

            actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MapViewActivity.this, ArchiveActivity.class);
                    startActivity(intent);
                }
            });
        }

    }


    @Override
    public void sendInput(Map map) {
        // add second Mapfragment
            map2 = map;
            // set splitscreen
            View fragmentView1 = findViewById(R.id.fragment_map_view1);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0,
                    1
            );
            fragmentView1.setLayoutParams(param);

            View fragmentView2 = findViewById(R.id.fragment_map_view2);
            LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0,
                    1
            );
            fragmentView2.setLayoutParams(param2);
            this.startSecondFragment(map2);
}


    private void startFirstFragment(Map map1){
        Bundle bundle = new Bundle();
        bundle.putSerializable("map", map1);
        MapViewFragment mapFragment = new MapViewFragment();
        mapFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_map_view1, mapFragment)
                .commit();
    }

    private void startSecondFragment(Map map2){
        Bundle bundle = new Bundle();
        bundle.putSerializable("map", map2);
        MapViewFragment mapFragment = new MapViewFragment();
        mapFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_map_view2, mapFragment)
                .commit();

    }
}