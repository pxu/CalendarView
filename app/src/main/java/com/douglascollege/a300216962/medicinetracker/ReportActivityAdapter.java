package com.douglascollege.a300216962.medicinetracker;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.douglascollege.a300216962.medicinetracker.database.MedicineTracker;
import com.douglascollege.a300216962.medicinetracker.database.MedicineTrackerItem;

import java.util.List;

import static com.douglascollege.a300216962.medicinetracker.MainActivity.medicineTrackerManager;
import static com.douglascollege.a300216962.medicinetracker.R.mipmap.happy_face;
import static com.douglascollege.a300216962.medicinetracker.R.mipmap.sad_face;

/**
 * Created by 300216962 on 7/22/2017.
 */


public class ReportActivityAdapter extends BaseAdapter {

    Context context;
    List<MedicineTracker> data;
    private static LayoutInflater inflater = null;

    public ReportActivityAdapter(Context context, List<MedicineTracker> data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;

        if (vi == null)
            vi = inflater.inflate(R.layout.activity_report_row, null);

        final MedicineTracker item = data.get(position);

        renderView(item, vi);

        return vi;
    }

    private void renderView(MedicineTracker tracker, View vi ){
        TextView textViewMedicineName = (TextView) vi.findViewById(R.id.textViewMedicineName);
        TextView textViewProgress = (TextView) vi.findViewById(R.id.textViewProgress);
        TextView textViewDates = (TextView) vi.findViewById(R.id.textViewDates);
        textViewMedicineName.setText(tracker.getName());
        textViewDates.setText(tracker.getPeriod());
        textViewProgress.setText(medicineTrackerManager.getMedicineTrackerProgress(tracker));
    }

}