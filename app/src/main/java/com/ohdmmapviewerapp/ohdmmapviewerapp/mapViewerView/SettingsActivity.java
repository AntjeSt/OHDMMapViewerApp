package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;

import com.ohdmmapviewerapp.ohdmmapviewerapp.R;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModel;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModelFactory;

public class SettingsActivity extends AppCompatActivity {

    ImageView back;
    MapViewerViewModel mapViewerViewModel;
    CardView themeCard1;
    CardView themeCard2;
    CardView themeCard3;

    String currentTheme;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        themeCard1 = findViewById(R.id.theme1);
        themeCard2 = findViewById(R.id.theme2);
        themeCard3 = findViewById(R.id.theme3);

        mapViewerViewModel = MapViewerViewModelFactory.produceMapViewerViewModel(getApplicationContext());
        // set Themecard backgrounds according to theme in sharedPreferences
        loadBackgroundColors();


        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });


        themeCard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBackgroundColors(currentTheme);
                mapViewerViewModel.setTheme(themeCard1.getTag().toString());
                currentTheme= "theme1";
                themeCard1.setCardBackgroundColor(getResources().getColor(R.color.lightturquoise, null));

            }
        }
        );

        themeCard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBackgroundColors(currentTheme);
                mapViewerViewModel.setTheme(themeCard2.getTag().toString());
                currentTheme = "theme2";
                themeCard2.setCardBackgroundColor(getResources().getColor(R.color.lightturquoise, null));
            }
        }
        );

        themeCard3.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              updateBackgroundColors(currentTheme);
                                              mapViewerViewModel.setTheme(themeCard3.getTag().toString());
                                              currentTheme = "theme3";
                                              themeCard3.setCardBackgroundColor(getResources().getColor(R.color.lightturquoise, null));
                                          }
                                      }
        );
    }

    // show which theme is currently selected by its backgroundcolor
    private void loadBackgroundColors() {
        currentTheme = mapViewerViewModel.getTheme();
             switch (currentTheme){
                 case "theme1":
                     themeCard1.setCardBackgroundColor(getResources().getColor(R.color.lightturquoise, null));
                     break;
                 case "theme2":
                     themeCard2.setCardBackgroundColor(getResources().getColor(R.color.lightturquoise, null));
                     break;
                 case "theme3":
                     themeCard3.setCardBackgroundColor(getResources().getColor(R.color.lightturquoise, null));
                     break;

             }
    }

    // update backgroundColor of last theme when user switches theme
    public void updateBackgroundColors(String theme){
        switch (theme){
            case "theme1":
                themeCard1.setCardBackgroundColor(getResources().getColor(R.color.cream, null));
                break;
            case "theme2":
                themeCard2.setCardBackgroundColor(getResources().getColor(R.color.cream, null));
                break;
            case "theme3":
                themeCard3.setCardBackgroundColor(getResources().getColor(R.color.cream, null));
                break;

        }
    }
}