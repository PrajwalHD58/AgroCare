package com.example.agrocare.ui.area;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.agrocare.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter  {

    private View view;

    //to display distance from other markers
    private String distance_no;

    //to display snippet from original adpater
    private String custom_snippet;


    public CustomInfoWindowAdapter(Context context) {

        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = li.inflate(R.layout.custom_info_window, null);
    }

    @Override
    public View getInfoContents(Marker marker) {return null;}

    @Override
    public View getInfoWindow(final Marker marker) {

        distance_no = marker.getTitle();
        custom_snippet = marker.getSnippet();

        //set titles and snippet from original infowindow
        TextView subtitle = (TextView) view.findViewById(R.id.subtitle);
        subtitle.setText(distance_no);


        TextView snippet = (TextView) view.findViewById(R.id.snippet);
        snippet.setText(custom_snippet);


        return view;
    }

}
