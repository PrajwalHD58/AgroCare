package com.example.agrocare.ui.home;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agrocare.R;
import com.example.agrocare.adapter.GenericRecordAdapter;
import com.example.agrocare.interf.CropDao;
import com.example.agrocare.model.Crop;
import com.example.agrocare.model.CropWithGenericRecord;
import com.example.agrocare.model.GenericRecord;
import com.example.agrocare.model.Utility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class IndividualPlant extends AppCompatActivity {

    private Crop crop;
    private TextView daysSinceGrowStartTextView, plantName;
    private TextView weeksSinceGrowStartTextView;
    private TextView weeksSinceStateStartTextView;
    private TextView daysSinceStateStartTextView;
    private TextView stateNameTextView;
    private TextView fromSeedTextView;
    private RecyclerView recordableEventListView;
    private Spinner addEventSpinner;
    private GenericRecordAdapter groupAdapter;
    private String CurrPhase="None";
    private String CurrPhaseTime;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_individual_plant);
        Intent intent = getIntent();
        crop = (Crop) intent.getSerializableExtra("Curr_Crop");
        Log.v("IndivisualPlant---->",String.valueOf(crop.getPlantId()));
        bindIndividualPlantView();

        plantName.setText(crop.getPlantName());
        daysSinceGrowStartTextView.setText(String.valueOf(crop.getDaysFromStart()));
        weeksSinceGrowStartTextView.setText(String.valueOf(crop.getWeeksFromStart()));
        if (crop.isFromSeed()) {
            fromSeedTextView.setText("SEED\n");
        } else {
            fromSeedTextView.setText("CLONE\n");
        }

        ArrayList<String> eventOptions = new ArrayList<>();
        eventOptions.add("--ADD NEW OBSERVATION/RECORD--");
        eventOptions.add("Water");
        eventOptions.add("Phase Change");
        eventOptions.add("Feeding/Fertilizers");
        eventOptions.add("Disease Detected");
        eventOptions.add("Pesticides/Insecticides");
        eventOptions.add("Weedicides/Herbicides");
        eventOptions.add("Pictures");
        eventOptions.add("Other Observation");



        new MyAsyncTask().execute(new Pair<>(getApplication(),crop.getPlantId()));



        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.item_spinner_lightfg, eventOptions);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        addEventSpinner.setAdapter(adapter);
        addEventSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedItem = adapter.getItem(position);


                if (position == 0) {

                } else {

                    Intent intent1 = new Intent(getApplicationContext(), CollectPlantData.class);
                    intent1.putExtra("obs_no", position);
                    intent1.putExtra("Curr_Crop", crop);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                    finish();

                }


                addEventSpinner.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setAdapter(List<GenericRecord> records) {
        groupAdapter = new GenericRecordAdapter(getApplicationContext(),records );
        recordableEventListView.setHasFixedSize(true);
        recordableEventListView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recordableEventListView.setAdapter(groupAdapter);
        recordableEventListView.setNestedScrollingEnabled(true);

    }


    private void bindIndividualPlantView() {
        daysSinceGrowStartTextView = findViewById(R.id.daysSinceGrowStartTextView);
        weeksSinceGrowStartTextView = findViewById(R.id.weeksSinceGrowStartTextView);
        fromSeedTextView = findViewById(R.id.fromSeedTextView);
        recordableEventListView = findViewById(R.id.recordableEventListView);
        addEventSpinner = findViewById(R.id.addEventSpinner);
        stateNameTextView = findViewById(R.id.stateNameTextView);
        weeksSinceStateStartTextView = findViewById(R.id.weeksSinceStateStartTextView);
        daysSinceStateStartTextView = findViewById(R.id.daysSinceStateStartTextView);
        plantName=findViewById(R.id.space2);
    }

    private class MyAsyncTask extends AsyncTask<Pair<Application, Long>, Void, Void> {

        private List<GenericRecord> records;
        public MyAsyncTask() {

        }

        @Override
        protected Void doInBackground(Pair<Application, Long>... pairs) {
            CropDatabase cropDatabase=CropDatabase.getInstance(pairs[0].first);
            CropDao cropDao= cropDatabase.cropDao();
            records=cropDao.getAllCropWithGenericRecord(pairs[0].second).records;
            return null;
        }




        @Override
        protected void onPostExecute(Void result) {
            Collections.sort(records, new Sortbytime());
            for(GenericRecord r:records)
            {
                if(r.getObsNo()==2)
                {
                    stateNameTextView.setText(r.getRemark());
                    Long Time=r.getTime();
                    int pd= Utility.calcDaysFromTime(Time, Calendar.getInstance());
                    int pw= Utility.calcWeeksFromTime(Time, Calendar.getInstance());
                    weeksSinceStateStartTextView.setText(String.valueOf(pw));
                    daysSinceStateStartTextView.setText(String.valueOf(pd));
                    break;
                }
            }
            setAdapter(records);
        }

        private class Sortbytime implements Comparator<GenericRecord> {

            @Override
            public int compare(GenericRecord o1, GenericRecord o2) {
                if(o1.getTime()<o2.getTime())
                    return 1;
                else if(o1.getTime()>o2.getTime())
                    return -1;
                else
                    return 0;
            }
        }
    }


}
