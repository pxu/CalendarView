package com.douglascollege.a300216962.medicinetracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 300216962 on 7/22/2017.
 */


public class MainActivityAdapter extends RecyclerView.Adapter {

    Context context;
    List<MainActivityAdapterItem> data;
    private static LayoutInflater inflater = null;

    public MainActivityAdapter(Context context, List<MainActivityAdapterItem> data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
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

        MainActivityAdapterItem item = data.get(position);


        viewHolder.textViewMedicineName.setText(item.getMedicineName());


        viewHolder.textViewQuantity.setText(item.getMedicineQuantity() + " Dose/Day");


        viewHolder.textViewStartDate.setText("start from: " + item.getStartDate());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(context, MedicineTrackerActivity.class);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getMedicineId();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * ViewHolder capable of presenting two states: "normal" and "undo" state.
     */
    static class MainActivityViewHolder extends RecyclerView.ViewHolder{

        TextView textViewMedicineName;
        TextView textViewQuantity;
        TextView textViewStartDate;

        public MainActivityViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_row, parent, false));
            textViewMedicineName = (TextView) itemView.findViewById(R.id.textViewMedicineName);
            textViewQuantity = (TextView) itemView.findViewById(R.id.textViewMedicineQuantity);
            textViewStartDate = (TextView) itemView.findViewById(R.id.textViewStartDate);
        }


    }

}