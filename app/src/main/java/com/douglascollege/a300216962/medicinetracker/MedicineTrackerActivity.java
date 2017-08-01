package com.douglascollege.a300216962.medicinetracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;

import com.douglascollege.a300216962.medicinetracker.database.MedicineTracker;
import com.douglascollege.a300216962.medicinetracker.database.MedicineTrackerItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.douglascollege.a300216962.medicinetracker.MainActivity.context;
import static com.douglascollege.a300216962.medicinetracker.MainActivity.medicineTrackerManager;

public class MedicineTrackerActivity extends Activity {

    private int medicineTrackerId=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_tracker);
        medicineTrackerId = getIntent().getIntExtra("medicineTrackerId",-1);
        init();
    }

    private void init(){
        MedicineTracker mt = medicineTrackerManager.getMedicineTrackerById(medicineTrackerId);

        final ListView listview = (ListView) findViewById(R.id.listviewMedicineTakingItem);
        listview.setAdapter(new MedicineTrackerActivityAdapter(context, mt.getMedicineTrackerItems()));
    }


}
