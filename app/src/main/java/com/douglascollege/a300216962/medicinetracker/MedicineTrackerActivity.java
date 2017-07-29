package com.douglascollege.a300216962.medicinetracker;

import android.app.Activity;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static com.douglascollege.a300216962.medicinetracker.MainActivity.context;

public class MedicineTrackerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_tracker);
        init();
    }

    private void init(){
        final List<MedicineTrackerActivityAdapterItem> data = new ArrayList<>();

        data.add(new MedicineTrackerActivityAdapterItem(false, "2017-07-18"));
        data.add(new MedicineTrackerActivityAdapterItem(true, "2017-07-19"));
        data.add(new MedicineTrackerActivityAdapterItem(false, "2017-07-12"));
        data.add(new MedicineTrackerActivityAdapterItem(true, "2017-07-18"));
        data.add(new MedicineTrackerActivityAdapterItem(false, "2017-07-18"));
        data.add(new MedicineTrackerActivityAdapterItem(true, "2017-07-18"));
        data.add(new MedicineTrackerActivityAdapterItem(false, "2017-07-18"));
        data.add(new MedicineTrackerActivityAdapterItem(true, "2017-07-18"));
        data.add(new MedicineTrackerActivityAdapterItem(false, "2017-07-18"));
        data.add(new MedicineTrackerActivityAdapterItem(true, "2017-07-18"));



        final ListView listview = (ListView) findViewById(R.id.listviewMedicineTakingItem);
        listview.setAdapter(new MedicineTrackerActivityAdapter(context, data));
    }


}
