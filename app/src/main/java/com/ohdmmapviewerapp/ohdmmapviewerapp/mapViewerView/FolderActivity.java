package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.ohdmmapviewerapp.ohdmmapviewerapp.R;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel.Map;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModel;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModelFactory;

public class FolderActivity extends AppCompatActivity implements FolderContentAdapter.ItemClickListener, EditDialog.OnInputListener{


    private RecyclerView folderContentRecyclerView;
    private FolderContentAdapter folderContentAdapter;

    TextView caption;
    ImageView backButton;

    MapViewerViewModel mapViewerViewModel;
    String folder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder);
        folder = getIntent().getStringExtra("folderName");
        mapViewerViewModel = MapViewerViewModelFactory.produceMapViewerViewModel(getApplicationContext());

        caption = findViewById(R.id.folderCaption);
        caption.setText(folder);

        backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FolderActivity.this,ArchiveActivity.class);
                startActivity(intent);
            }
        });

        folderContentRecyclerView = findViewById(R.id.folderContentRecyclerView);
        folderContentRecyclerView.setHasFixedSize(true);
        folderContentRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        folderContentAdapter = new FolderContentAdapter(folder, mapViewerViewModel);
        folderContentAdapter.setClickListener(this);
        folderContentRecyclerView.setAdapter(folderContentAdapter);
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

     @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);
        switch (item.getItemId()) {
            case 1:
                Map map = folderContentAdapter.getItem(item.getGroupId());
                EditDialog editMapDialog = new EditDialog();
                Bundle mapBundle = new Bundle();
                mapBundle.putSerializable("MAP_FOLDERCONTENT", map);
                editMapDialog.setArguments(mapBundle);
                editMapDialog.show(getSupportFragmentManager(), "editdialog");
                return true;
            case 2:
                folderContentAdapter.removeMap(item.getGroupId());
                Snackbar.make(caption, "Removed Map succesfully", Snackbar.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }


    @Override
    public void onItemClickFolderContentAdapter(View view, int position) {
        Map map = folderContentAdapter.getItem(position);
        Intent intent = new Intent(FolderActivity.this, MapViewActivity.class);
        intent.putExtra("map1", map);
        startActivity(intent);
    }

    @Override
    public void notifyRVDataChanged() {
        folderContentAdapter.updateData();
    }

}

