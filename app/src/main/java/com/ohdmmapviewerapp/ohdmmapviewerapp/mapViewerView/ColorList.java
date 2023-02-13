package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerView;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.ohdmmapviewerapp.ohdmmapviewerapp.R;

import java.util.ArrayList;

public class ColorList {

    Context ctx;
    ArrayList<ColorObject> colorObjectObjectList;

    public ColorList(Context ctx) {
        colorObjectObjectList = new ArrayList<>();
        ColorObject yellow = new ColorObject("yellow", ContextCompat.getColor(ctx, R.color.yellow));
        ColorObject orange = new ColorObject("orange", ContextCompat.getColor(ctx, R.color.orange));
        ColorObject red = new ColorObject("red", ContextCompat.getColor(ctx, R.color.red));
        ColorObject green = new ColorObject("green", ContextCompat.getColor(ctx, R.color.green));
        ColorObject blue = new ColorObject("blue", ContextCompat.getColor(ctx, R.color.blue));
        ColorObject navy = new ColorObject("navy", ContextCompat.getColor(ctx, R.color.navy));
        ColorObject indigo = new ColorObject("indigo", ContextCompat.getColor(ctx, R.color.indigo));

        colorObjectObjectList.add(yellow);
        colorObjectObjectList.add(orange);
        colorObjectObjectList.add(red);
        colorObjectObjectList.add(green);
        colorObjectObjectList.add(indigo);
        colorObjectObjectList.add(blue);
        colorObjectObjectList.add(navy);

    }

    ;

    public ArrayList<ColorObject> getColorList() {
        return colorObjectObjectList;
    }


}
