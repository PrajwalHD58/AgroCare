package com.example.agrocare.ui.home;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.agrocare.R;
import com.example.agrocare.interf.CropDao;
import com.example.agrocare.model.Crop;
import com.example.agrocare.model.GenericRecord;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class CollectPlantData extends AppCompatActivity {

    private SimpleDateFormat simpleDateFormat =
            new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());

    private int selectedTab = 0;
    ArrayList<Pair<Integer, Pair<String, String>>> obs_data;
    private GenericRecord record;
    private boolean applyToGroup;
    private long selectedGroup;
    private int position;
    private boolean showNotes;
    private TreeMap<String, Long> availableGroups;

    private Uri photoURI;
    private ArrayList<String> images = new ArrayList<>();
    private Crop crop;
    private RecyclerView attachedImageRecyclerView;
    private TextView attachedImageCountTextView;


    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_plant_data);

        Intent intent = getIntent();

        crop = (Crop) intent.getSerializableExtra("Curr_Crop");
        position = intent.getIntExtra("obs_no", 8);
        obs_data = new ArrayList<>();
        obs_data.add(new Pair<>(1, new Pair<>("Water", "Water pH: ")));
        obs_data.add(new Pair<>(2, new Pair<>("Phase Change", "Phase Name: ")));
        obs_data.add(new Pair<>(3, new Pair<>("Feeding/Fertilizer", "Food/Fertilizer Name: ")));
        obs_data.add(new Pair<>(4, new Pair<>("Disease Detected", "Disease Name: ")));
        obs_data.add(new Pair<>(5, new Pair<>("Pesticides/Insecticides", "Pesticides/Insecticides Name: ")));
        obs_data.add(new Pair<>(6, new Pair<>("Weedicides/Herbicides", "Weedicides/Herbicides Name: ")));
        obs_data.add(new Pair<>(7, new Pair<>("Picture", "Picture Name: ")));
        obs_data.add(new Pair<>(8, new Pair<>("Other Observation", "Remark: ")));
        bindUi();
    }


    private void bindUi() {

        bindTabs();
        TextView t=findViewById(R.id.remark);
        t.setText(obs_data.get(position-1).second.second);
        TextView t2=findViewById(R.id.page_name);
        t2.setText(obs_data.get(position-1).second.first);

        bindSharedControls();

    }

    private void bindTabs() {
        TabHost tabs = findViewById(R.id.tabHost);
        tabs.setup();

        LinearLayout l1, l2, l3;
        RelativeLayout l4;
        l1 = findViewById(R.id.tab1);
        l2 = findViewById(R.id.tab2);
        l3 = findViewById(R.id.tab3);
        l4 = findViewById(R.id.tab4);


        l1.setVisibility(View.VISIBLE);
        l2.setVisibility(View.VISIBLE);
        l3.setVisibility(View.VISIBLE);
        l4.setVisibility(View.GONE);

        TabHost.TabSpec dialogTab = tabs.newTabSpec("Tab1");
        dialogTab.setIndicator("Info");
        dialogTab.setContent(R.id.tab1);
        tabs.addTab(dialogTab);

        TabHost.TabSpec changeDateTab = tabs.newTabSpec("Tab2");
        changeDateTab.setIndicator("Set Date");
        changeDateTab.setContent(R.id.tab2);
        tabs.addTab(changeDateTab);

        TabHost.TabSpec changeTimeTab = tabs.newTabSpec("Tab3");
        changeTimeTab.setIndicator("Set Time");
        changeTimeTab.setContent(R.id.tab3);
        tabs.addTab(changeTimeTab);

        if (position == 7) {

            l4.setVisibility(View.VISIBLE);
            TabHost.TabSpec attachImagesTab = tabs.newTabSpec("Tab4");
            attachImagesTab.setIndicator("Attach Images");
            attachImagesTab.setContent(R.id.tab4);
            tabs.addTab(attachImagesTab);

        }

        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                selectedTab = tabs.getCurrentTab();

                // Hide the keyboard if we're on any of the static tabs
                if (!tabId.equals("Tab1")) {
                    View view = getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm =
                                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
            }
        });


        tabs.setCurrentTab(selectedTab);
    }






    private void bindSharedControls() {
        Calendar c = Calendar.getInstance();

        final DatePicker datePicker = findViewById(R.id.eventDatePicker);
        datePicker.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                    c.get(Calendar.DAY_OF_MONTH));

        final TimePicker timePicker = findViewById(R.id.eventTimePicker);
        timePicker.setHour(c.get(Calendar.HOUR_OF_DAY));
        timePicker.setMinute(c.get(Calendar.MINUTE));


        final Button okButton = findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                endActivity();
            }
        });

        final Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelActivity();
            }
        });

    }




    private void cancelActivity() {
        Intent retInt = new Intent(getApplicationContext(),IndividualPlant.class);
        retInt.putExtra("Curr_Crop", crop);
        retInt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(retInt);
        finish();

    }

    private void endActivity() {
        DatePicker datePicker = findViewById(R.id.eventDatePicker);
        TimePicker timePicker = findViewById(R.id.eventTimePicker);
        EditText e1=findViewById(R.id.remark_value);
        EditText e2=findViewById(R.id.notes);

        String s1=e1.getText().toString().trim();
        String s2=e2.getText().toString().trim();

        if(s1.isEmpty())
        {
            s1="None";
        }
        if(s2.isEmpty())
        {
            s2="None";
        }

        Calendar cal = Calendar.getInstance();
        cal.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                timePicker.getHour(), timePicker.getMinute());
        long l=System.currentTimeMillis();
        GenericRecord record=new GenericRecord(l,crop.getPlantId(),position,cal.getTimeInMillis(),s1,s2);
        new MyAsyncTask().execute(new Pair<>(getApplication(),record));
    }
    public void doOperation()
    {
        Intent retInt = new Intent(getApplicationContext(),IndividualPlant.class);
        retInt.putExtra("Curr_Crop", crop);
        retInt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(retInt);
        finish();
    }

    private class MyAsyncTask extends AsyncTask<Pair<Application, GenericRecord>, Void, Void> {


        public MyAsyncTask() {

        }

        @Override
        protected Void doInBackground(Pair<Application, GenericRecord>... pairs) {
            CropDatabase cropDatabase=CropDatabase.getInstance(pairs[0].first);
            CropDao cropDao= cropDatabase.cropDao();
            cropDao.insertRecord(pairs[0].second);
            return null;
        }




        @Override
        protected void onPostExecute(Void result) {
            doOperation();
        }
    }

}




