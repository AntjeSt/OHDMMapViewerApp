package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerView;

import android.view.View;

public interface ArchiveItemClickListener {
    void onItemClickAllMapsAdapter(View view, int position);

    void onItemClickFolderAdapter(View view, int position);

    void onItemClickRecentsAdapter(View view, int position);

}
