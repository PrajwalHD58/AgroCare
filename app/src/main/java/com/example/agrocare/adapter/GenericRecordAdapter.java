package com.example.agrocare.adapter;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agrocare.R;
import com.example.agrocare.model.GenericRecord;


import java.util.ArrayList;
import java.util.List;

public class GenericRecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final List<GenericRecord> records;
    private final ArrayList<Pair<Integer, Pair<String, String>>> obs_data;

    public GenericRecordAdapter(Context context, List<GenericRecord> plants) {
        this.context = context;
        this.records = plants;
        obs_data = new ArrayList<>();
        obs_data.add(new Pair<>(1, new Pair<>("Water", "Water pH: ")));
        obs_data.add(new Pair<>(2, new Pair<>("Phase Change", "Phase Name: ")));
        obs_data.add(new Pair<>(3, new Pair<>("Feeding/Fertilizer", "Food/Fertilizer Name: ")));
        obs_data.add(new Pair<>(4, new Pair<>("Disease Detected", "Disease Name: ")));
        obs_data.add(new Pair<>(5, new Pair<>("Pesticides/Insecticides", "Pesticides/Insecticides Name: ")));
        obs_data.add(new Pair<>(6, new Pair<>("Weedicides/Herbicides", "Weedicides/Herbicides Name: ")));
        obs_data.add(new Pair<>(7, new Pair<>("Picture", "Picture Name: ")));
        obs_data.add(new Pair<>(8, new Pair<>("Other Observation", "Remark: ")));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.generic_record_element, parent, false);
        return new GenericRecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((GenericRecordViewHolder) holder).obs_name.setText(records.get(position).getDisplayName());
        ((GenericRecordViewHolder) holder).obs_time.setText(records.get(position).getDisplayTime());
        ((GenericRecordViewHolder) holder).obs_remark.setText(obs_data.get(this.records.get(position).getObsNo() - 1).second.second + " " + records.get(position).getDisplayRemark());
        ((GenericRecordViewHolder) holder).obs_notes.setText(records.get(position).getDisplayNotes());
        int obsNo = records.get(position).getObsNo();
        if (obsNo == 1) {
            ((GenericRecordViewHolder) holder).obs_name.setBackgroundColor(context.getResources().getColor(R.color.col1, null));
        } else if (obsNo == 2) {
            ((GenericRecordViewHolder) holder).obs_name.setBackgroundColor(context.getResources().getColor(R.color.col2, null));
        } else if (obsNo == 3) {
            ((GenericRecordViewHolder) holder).obs_name.setBackgroundColor(context.getResources().getColor(R.color.col3, null));
        } else if (obsNo == 4) {
            ((GenericRecordViewHolder) holder).obs_name.setBackgroundColor(context.getResources().getColor(R.color.col4, null));
        } else if (obsNo == 5) {
            ((GenericRecordViewHolder) holder).obs_name.setBackgroundColor(context.getResources().getColor(R.color.col5, null));
        } else if (obsNo == 6) {
            ((GenericRecordViewHolder) holder).obs_name.setBackgroundColor(context.getResources().getColor(R.color.col6, null));
        } else if (obsNo == 7) {
            ((GenericRecordViewHolder) holder).obs_name.setBackgroundColor(context.getResources().getColor(R.color.col7, null));
        } else if (obsNo == 8) {
            ((GenericRecordViewHolder) holder).obs_name.setBackgroundColor(context.getResources().getColor(R.color.col8, null));
        }
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

}

class GenericRecordViewHolder extends RecyclerView.ViewHolder {

    TextView obs_name, obs_time, obs_remark, obs_notes;

    public GenericRecordViewHolder(@NonNull View itemView) {
        super(itemView);
        obs_name = itemView.findViewById(R.id.obs_name);
        obs_time = itemView.findViewById(R.id.obs_time);
        obs_remark = itemView.findViewById(R.id.obs_remark);
        obs_notes = itemView.findViewById(R.id.obs_notes);
    }

}

