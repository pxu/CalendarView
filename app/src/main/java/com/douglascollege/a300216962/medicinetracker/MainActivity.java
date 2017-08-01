package com.douglascollege.a300216962.medicinetracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.*;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.douglascollege.a300216962.medicinetracker.database.MedicineTracker;
import com.douglascollege.a300216962.medicinetracker.database.MedicineTrackerDao;
import com.douglascollege.a300216962.medicinetracker.database.MedicineTrackerItem;
import com.douglascollege.a300216962.medicinetracker.database.MedicineTrackerItemDao;
import com.henry.calendarview.SimpleMonthAdapter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

public class MainActivity extends Activity{


    public static Context context;
    // the business manager to handle all the backend related stuff(i.e. with database)
    // since trying to maintain singleton mode to access database, this object will be
    //shared by the whole application
    public static MedicineTrackerManager medicineTrackerManager;

    MainActivityAdapter mainActivityAdapter;
    RecyclerView medicineTrackerView;
    List <MedicineTracker> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        medicineTrackerManager = new MedicineTrackerManager();

        Button buttonAdd = (Button)findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddMedicineTrackerActivity.class);
                startActivityForResult(intent,1);
            }
        });

        Button buttonReport = (Button)findViewById(R.id.buttonReport);
        buttonReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReportActivity.class);
                startActivity(intent);
            }
        });


        medicineTrackerView = (RecyclerView) findViewById(R.id.recyclerViewMedicineTrack);
        medicineTrackerView.setLayoutManager(new LinearLayoutManager(this));
        medicineTrackerView.setMinimumHeight(40);
        mainActivityAdapter = new MainActivityAdapter(context, data);
        medicineTrackerView.setAdapter(mainActivityAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Remove item from backing list here

                MedicineTracker mt = data.get(viewHolder.getAdapterPosition());
                if(medicineTrackerManager.deleteMedicineTracker(mt.getId())){
                    data.remove(viewHolder.getAdapterPosition());
                    mainActivityAdapter.notifyDataSetChanged();
                }

            }
        });

        itemTouchHelper.attachToRecyclerView(medicineTrackerView);

    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        data.clear();
        data.addAll(medicineTrackerManager.getAllMedicineTrackers());

        mainActivityAdapter.notifyDataSetChanged();

    }

}
