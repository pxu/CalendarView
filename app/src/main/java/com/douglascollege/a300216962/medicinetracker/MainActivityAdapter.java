package com.douglascollege.a300216962.medicinetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 300216962 on 7/22/2017.
 */


public class MainActivityAdapter extends BaseAdapter {

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
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;

        if (vi == null)
            vi = inflater.inflate(R.layout.activity_main_row, null);

        MainActivityAdapterItem item = data.get(position);
        TextView text = (TextView) vi.findViewById(R.id.textViewMedicineName);
        text.setText(item.getMedicineName());

        TextView textViewQuantity = (TextView) vi.findViewById(R.id.textViewMedicineQuantity);
        textViewQuantity.setText(item.getMedicineQuantity() + " Dose/Day");

        TextView textViewStartDate = (TextView) vi.findViewById(R.id.textViewStartDate);
        textViewStartDate.setText("start from: " + item.getStartDate());


        return vi;
    }
}