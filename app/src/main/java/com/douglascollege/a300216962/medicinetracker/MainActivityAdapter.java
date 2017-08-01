package com.douglascollege.a300216962.medicinetracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.douglascollege.a300216962.medicinetracker.database.MedicineTracker;
import com.douglascollege.a300216962.medicinetracker.database.MedicineTrackerItem;

import java.util.Collection;
import java.util.List;

import static com.douglascollege.a300216962.medicinetracker.MainActivity.medicineTrackerManager;

/**
 * Created by 300216962 on 7/22/2017.
 */


public class MainActivityAdapter extends RecyclerView.Adapter {

    Context context;
    List<MedicineTracker> data;
    private static LayoutInflater inflater = null;

    public MainActivityAdapter(Context context, Collection<MedicineTracker> data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = (List)data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainActivityViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MainActivityViewHolder viewHolder = (MainActivityViewHolder)holder;
        final MedicineTracker item = data.get(position);

        viewHolder.textViewMedicineName.setText(item.getName());
        viewHolder.textViewQuantity.setText(item.getDailyDosage() + " Dose/Day");
        viewHolder.textViewStartDate.setText( item.getPeriod());

        if(medicineTrackerManager.isMedicineTrackerCompletedSuccessfully(item.getId())){
            viewHolder.linearLayoutMainActivityRow.setBackgroundColor(Color.GREEN);
        }else{
            viewHolder.linearLayoutMainActivityRow.setBackgroundColor(Color.LTGRAY);
        }
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * ViewHolder capable of presenting two states: "normal" and "undo" state.
     */
    class MainActivityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        LinearLayout linearLayoutMainActivityRow;
        TextView textViewMedicineName;
        TextView textViewQuantity;
        TextView textViewStartDate;

        public MainActivityViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_row, parent, false));
            linearLayoutMainActivityRow = (LinearLayout)itemView.findViewById(R.id.linearLayoutMainActivityRow);
            textViewMedicineName = (TextView) itemView.findViewById(R.id.textViewMedicineName);
            textViewQuantity = (TextView) itemView.findViewById(R.id.textViewMedicineQuantity);
            textViewStartDate = (TextView) itemView.findViewById(R.id.textViewStartDate);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            Intent intent = new Intent(itemView.getContext(), MedicineTrackerActivity.class);
            MedicineTracker mt = data.get(getAdapterPosition());
            intent.putExtra("medicineTrackerId", mt.getId());
            itemView.getContext().startActivity(intent);
        }
    }

}