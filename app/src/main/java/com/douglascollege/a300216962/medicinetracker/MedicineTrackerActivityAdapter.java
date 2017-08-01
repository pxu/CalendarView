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

import com.douglascollege.a300216962.medicinetracker.database.MedicineTrackerItem;

import java.util.List;

import static com.douglascollege.a300216962.medicinetracker.MainActivity.medicineTrackerManager;
import static com.douglascollege.a300216962.medicinetracker.R.mipmap.happy_face;
import static com.douglascollege.a300216962.medicinetracker.R.mipmap.sad_face;

/**
 * Created by 300216962 on 7/22/2017.
 */


public class MedicineTrackerActivityAdapter extends BaseAdapter {

    Context context;
    List<MedicineTrackerItem> data;
    private static LayoutInflater inflater = null;

    public MedicineTrackerActivityAdapter(Context context, List<MedicineTrackerItem> data) {
        // TODO Auto-generated constructor stub
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
        // TODO Auto-generated method stub
        View vi = convertView;

        if (vi == null)
            vi = inflater.inflate(R.layout.activity_medicine_tracker_row, null);

        final MedicineTrackerItem item = data.get(position);
        final CheckBox checkBoxMedicineTakingDate = (CheckBox) vi.findViewById(R.id.checkBoxMedicineTakingDate);
        final ImageView face = (ImageView)vi.findViewById(R.id.imageView_face);
        updateView(item.isTaken(),face, checkBoxMedicineTakingDate);

        checkBoxMedicineTakingDate.setText("" + CommonUtils.getDateInString(item.getDate()));


        checkBoxMedicineTakingDate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                updateView(isChecked, face, checkBoxMedicineTakingDate,item);
            }
        });

        final TextView textView_Day = (TextView) vi.findViewById(R.id.textView_Day);
        textView_Day.setText(item.getDay());


        return vi;
    }

    private void updateView(boolean isTaken, ImageView face, CheckBox checkBoxMedicineTakingDate){
        checkBoxMedicineTakingDate.setChecked(isTaken);
        if(isTaken){
            face.setBackgroundResource(happy_face);
            checkBoxMedicineTakingDate.setTextColor(Color.GREEN);
        }else{
            face.setBackgroundResource(sad_face);
            checkBoxMedicineTakingDate.setTextColor(Color.RED);
        }
    }

    private void updateView(boolean isTaken, ImageView face, CheckBox checkBoxMedicineTakingDate, MedicineTrackerItem item){
        item.setTaken(isTaken);
        medicineTrackerManager.updateMedicineTrackerItem(item);
        updateView(isTaken,face,checkBoxMedicineTakingDate);
    }
}