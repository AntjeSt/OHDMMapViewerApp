package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.ohdmmapviewerapp.ohdmmapviewerapp.R;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel.Map;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModel;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModelFactory;


public class ArchiveActivity extends AppCompatActivity implements ArchiveItemClickListener, EditDialog.OnInputListener, View.OnCreateContextMenuListener{

    private RecyclerView recentsRecyclerView;
    private RecyclerView folderRecyclerView;
    private RecyclerView allMapsRecyclerView;
    private RecentsAdapter recentsAdapter;
    private FolderAdapter folderAdapter;
    private AllMapsAdapter allMapsAdapter;
    private MapViewerViewModel mapViewerViewModel;
    private ImageView menuButton;

    private Context ctx;

    private final int COLUM_NUMBER = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = getApplicationContext();
        mapViewerViewModel = MapViewerViewModelFactory.produceMapViewerViewModel(ctx);
        Map openendMap1 = (Map) getIntent().getSerializableExtra("map1");
        Map openendMap2 = (Map) getIntent().getSerializableExtra("map2");

        if (openendMap1 != null){
            mapViewerViewModel.addRecentsMap(openendMap1);
        }
        if (openendMap2 != null){
            mapViewerViewModel.addRecentsMap(openendMap2);
        }

        if (mapViewerViewModel.getAllMapsOrderedAlphabetically() != null) {
            setContentView(R.layout.activity_archive);
            menuButton = findViewById(R.id.back);

            menuButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ArchiveActivity.this, MenuActivity.class);
                    startActivity(intent);
                }
            });

            TextView recentsTitle = findViewById(R.id.recentsTitle);
            recentsRecyclerView = findViewById(R.id.recentsRecyclerView);

            if (mapViewerViewModel.getRecentMapsListCount() == 0) {
                recentsRecyclerView.setVisibility(GONE);
                recentsTitle.setVisibility(GONE);
            }else{
                recentsRecyclerView.setVisibility(VISIBLE);
                recentsTitle.setVisibility(VISIBLE);
                this.setUpRecentsRV();
            }

            if (mapViewerViewModel.getAllFoldersOrderedAlphabetically()!= null) {
                this.setUpFolderRV();
            }

            this.setUpAllMapsRV();
        }

        // no maps available
        else {
            setContentView(R.layout.notfound_default);
            TextView text = findViewById(R.id.defaultText);
            text.setText("No maps found.");
            Button actionButton = findViewById(R.id.actionButton);
            actionButton.setText("Download map");
            actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Environment.isExternalStorageManager()) {
                        Intent intent = new Intent(ArchiveActivity.this, DownloadActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(ArchiveActivity.this, MenuActivity.class);
                        intent.putExtra("storage_permission", "none");
                        startActivity(intent);
                    }
                }
            });
        }

    }

    @Override
    public void onItemClickRecentsAdapter(View view, int position) {
        Map map = recentsAdapter.getItem(position);
        Intent intent = new Intent(ArchiveActivity.this, MapViewActivity.class);
        intent.putExtra("map1", map);
        startActivity(intent);

    }

    @Override
    public void onItemClickFolderAdapter(View view, int position) {
        String folder = folderAdapter.getItem(position);
        Intent intent = new Intent(ArchiveActivity.this, FolderActivity.class);
        intent.putExtra("folderName", folder);
        startActivity(intent);
    }

    @Override
    public void onItemClickAllMapsAdapter(View view, int position) {
        if (position == allMapsAdapter.getItemCount() - 1) {
            Intent intent = new Intent(ArchiveActivity.this, DownloadActivity.class);
            startActivity(intent);
        } else {
            Map map = allMapsAdapter.getItem(position);
            Intent intent = new Intent(ArchiveActivity.this, MapViewActivity.class);
            intent.putExtra("map1", map);
            startActivity(intent);
        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);
        switch (item.getItemId()) {
            case 1:
                String folder = folderAdapter.getItem(item.getGroupId());
                EditDialog editFolderDialog = new EditDialog();
                Bundle bundle = new Bundle();
                bundle.putString("FOLDER",folder);
                editFolderDialog.setArguments(bundle);
                editFolderDialog.show(getSupportFragmentManager(),"editdialog");
                return true;
            case 2:
                folderAdapter.removeFolder(item.getGroupId());
                notifyRVDataChanged();
                Snackbar.make(folderRecyclerView, "Removed folder succesfully", Snackbar.LENGTH_SHORT).show();
                return true;
            case 3:
                Map map = allMapsAdapter.getItem(item.getGroupId());
                EditDialog editMapDialog = new EditDialog();
                Bundle mapBundle = new Bundle();
                mapBundle.putSerializable("MAP",map);
                editMapDialog.setArguments(mapBundle);
                editMapDialog.show(getSupportFragmentManager(),"editdialog");
                return true;
            case 4:
                allMapsAdapter.removeMap(item.getGroupId());
                notifyRVDataChanged();
                Snackbar.make(allMapsRecyclerView, "Removed Map succesfully", Snackbar.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }

    @Override
    public void notifyRVDataChanged() {
        // refresh all recyclerviews after editing maps/folders
        allMapsAdapter.updateData();
        folderAdapter.updateData();
        //recentsAdapter.updateData(maps);

    }

    @Override
    public void onPause() {
        super.onPause();
        mapViewerViewModel.persistData();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mapViewerViewModel.persistData();
    }

    private void setUpAllMapsRV(){
        allMapsRecyclerView = findViewById(R.id.allMapsRecyclerView);
        allMapsRecyclerView.setHasFixedSize(true);
        allMapsRecyclerView.setLayoutManager(new GridLayoutManager(this, COLUM_NUMBER));
        allMapsAdapter = new AllMapsAdapter(mapViewerViewModel.getAllMapsOrderedAlphabetically(), mapViewerViewModel);
        allMapsAdapter.setClickListener(this);
        allMapsRecyclerView.setAdapter(allMapsAdapter);
    }

    private void setUpFolderRV(){
        folderRecyclerView = findViewById(R.id.foldersRecyclerView);
        folderRecyclerView.setHasFixedSize(true);
        folderRecyclerView.setLayoutManager(new GridLayoutManager(this, COLUM_NUMBER));
        folderAdapter = new FolderAdapter(mapViewerViewModel.getAllFoldersOrderedAlphabetically(), mapViewerViewModel, ctx);
        folderAdapter.setClickListener(this);
        folderRecyclerView.setAdapter(folderAdapter);
    }

    private void setUpRecentsRV(){
        recentsRecyclerView.setHasFixedSize(true);
        recentsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recentsAdapter = new RecentsAdapter(mapViewerViewModel.getRecentMapsList());
        recentsAdapter.setClickListener(this);
        recentsRecyclerView.setAdapter(recentsAdapter);
    }

}