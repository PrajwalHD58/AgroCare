package com.example.agrocare.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agrocare.R;
import com.example.agrocare.adapter.GroupAdapter;
import com.example.agrocare.model.Group;
import com.example.agrocare.model.Plant;
import com.example.agrocare.splashscreen.screen1;


import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private Context mContext;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private GroupAdapter groupAdapter;
    private ArrayList<Group> groups;
    private ArrayList<Plant> featured_plants;
    private ArrayList<Plant> recommended;
    //todo 0. If you want to add more types of objects, you can easily do so. Check "todo list" (I added it step by step)

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView username=view.findViewById(R.id.your_name);
        username.setText("Hello "+screen1.getUsername()+"!");
        setAdapterType(view);
        setAdapter();
    }

    private void initGroupData() {
        groups = new ArrayList<>();
        groups.add(new Group("Your Crops", "Add"));
        groups.add(new Group("Recommended Crops", "More"));
    }

    private void initPlantData() {
        featured_plants = new ArrayList<>();
        recommended = new ArrayList<>();

        featured_plants.add(new Plant("Rice", "Oryza sativa L", "120 days", R.drawable.bottom_img_1));
        featured_plants.add(new Plant("Maize", "Zea mays Linn", "200 days", R.drawable.bottom_img_1));
        featured_plants.add(new Plant("Wheat", "Triticum aestivum", "100 days", R.drawable.bottom_img_1));

        recommended.add(new Plant("Soyabean", "Glycine max (L.) Merr", "60 days", R.drawable.image_1));
        recommended.add(new Plant("Urd", "Phaseolus mungo Linn", "50 days", R.drawable.image_2));
        recommended.add(new Plant("Mustard", " Brassica spp", "40 days", R.drawable.image_3));
        recommended.add(new Plant("Potato", "Solanum tuberosum L", "30 days", R.drawable.image_1));
        recommended.add(new Plant("Banana", "Musa spp", "45 days", R.drawable.image_2));
        recommended.add(new Plant("Papaya", "Carica papaya", "10 days", R.drawable.image_3));
    }

    private void setAdapterType(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setAdapter() {
        initGroupData();
        initPlantData();
        //todo 1. Add the new object to the parameter list.
        groupAdapter = new GroupAdapter(mContext, groups, featured_plants, recommended);
        recyclerView.setAdapter(groupAdapter);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }
}
