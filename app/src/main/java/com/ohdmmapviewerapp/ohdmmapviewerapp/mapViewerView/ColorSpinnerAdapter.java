package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.graphics.drawable.DrawableCompat;

import com.ohdmmapviewerapp.ohdmmapviewerapp.R;

import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.infowindow.InfoWindow;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class ColorSpinnerAdapter extends ArrayAdapter<ColorObject> {

    Context context;
    ArrayList<ColorObject> colorObjectList;
    Marker currentMarker;
    InfoWindow infoWindow;

public ColorSpinnerAdapter(Context context, ArrayList<ColorObject> colorObjectObjectList, Marker marker){
    super(context,0, colorObjectObjectList);
    this.context = context;
    this.colorObjectList = colorObjectObjectList;
    this.currentMarker = marker;
}

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, @Nullable View convertView,ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public int getColorPosition(String color) {
        int i;
        for (i = 0; i < colorObjectList.size(); i++) {
            if (colorObjectList.get(i).getColorName().equals(color)) {
                return i;
            }
        }
        return -1;
    }

    @Nullable
    @Override
    public ColorObject getItem(int position) {
        return super.getItem(position);
    }

    public View getCustomView (int position, View convertView, ViewGroup parent){

    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View row = inflater.inflate(R.layout.color_spinner_item, parent, false);


    TextView colorName = row.findViewById(R.id.colorName);
    View colorBlob = row.findViewById(R.id.colorBlob);

    ColorObject colorObject = getItem(position);
    colorName.setText(colorObject.getColorName());
    colorBlob.setBackgroundColor(colorObject.getColor());

    DrawableCompat.setTint(currentMarker.getIcon(), colorObject.getColor());
    currentMarker.setSubDescription(colorObject.getColorName());

    return row;
}


}
