package com.douglascollege.a300216962.medicinetracker;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.douglascollege.a300216962.medicinetracker.database.MedicineTracker;

import java.util.List;

import static com.douglascollege.a300216962.medicinetracker.MainActivity.context;
import static com.douglascollege.a300216962.medicinetracker.MainActivity.medicineTrackerManager;

public class ReportActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        init();
    }

    private void init(){
        List<MedicineTracker> trackerList = medicineTrackerManager.getAllMedicineTrackers();

        final ListView listview = (ListView) findViewById(R.id.listviewReport);
        listview.setAdapter(new ReportActivityAdapter(context, trackerList));
    }
}
