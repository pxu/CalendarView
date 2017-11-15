package com.douglascollege.a300216962.medicinetracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
import com.douglascollege.a300216962.medicinetracker.database.MedicineTrackerCloudItem;
import com.douglascollege.a300216962.medicinetracker.database.MedicineTrackerDao;
import com.douglascollege.a300216962.medicinetracker.database.MedicineTrackerItem;
import com.douglascollege.a300216962.medicinetracker.database.MedicineTrackerItemDao;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.henry.calendarview.SimpleMonthAdapter;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity{


    public static Context context;
    public static MainActivity mainActivity;
    // the business manager to handle all the backend related stuff(i.e. with database)
    // since trying to maintain singleton mode to access database, this object will be
    //shared by the whole application
    public static MedicineTrackerManager medicineTrackerManager;

    MainActivityAdapter mainActivityAdapter;
    RecyclerView medicineTrackerView;
    List <MedicineTracker> data = new ArrayList<>();
    String TAG = "MainActivity";
    public static FirebaseDatabase mCloudDatabase;
    public static SharedPreferences sharedPreferences ;

    Timer timer;
    TimerTask timerTask;
    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        mainActivity = this;
        sharedPreferences = getPreferences(MODE_PRIVATE);
        medicineTrackerManager = new MedicineTrackerManager();
        mCloudDatabase = FirebaseDatabase.getInstance();

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

        Button buttonReminder = (Button)findViewById(R.id.buttonReminder);
        buttonReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SettingActivity.class);
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
        startTimer();

    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first

        stoptimertask();

    }

    private void createCloudDbListener() {

        MainActivity.mCloudDatabase.getReference("records").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(MainActivity.sharedPreferences.getBoolean("notification",false) == false) return;
                Map<String,Map<String,MedicineTrackerCloudItem>> data = (Map<String,Map<String,MedicineTrackerCloudItem>>)dataSnapshot.getValue();
                if(data == null || data.size()==0) return;

                for(Map.Entry<String,Map<String,MedicineTrackerCloudItem>> pair : data.entrySet()){
                    String user = pair.getKey();
                    Map<String,MedicineTrackerCloudItem> items = pair.getValue();

                    for(Map.Entry<String, MedicineTrackerCloudItem> item: items.entrySet()){
                        final MedicineTrackerCloudItem medicineTrackerCloudItem = MedicineTrackerCloudItem.fromMap((Map)item.getValue());
                        if(medicineTrackerCloudItem.getDateInStr().equals(new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date())) == false  || medicineTrackerCloudItem.getPatientName().equals(sharedPreferences.getString("patientName",""))){
                            continue;
                        }

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(mainActivity);
                        builder1.setMessage("Do you want to remind " + medicineTrackerCloudItem.getPatientName() + " to take " + medicineTrackerCloudItem.getName() + "?");
                        builder1.setTitle("time for " + medicineTrackerCloudItem.getPatientName() + " to take medicine!");
                        builder1.setCancelable(true);

                        /*builder1.setPositiveButton(
                                "Text ",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        String phoneNumber = MainActivity.sharedPreferences.getString("phoneNumber","");
                                        String message = "Hi " + medicineTrackerCloudItem.getPatientName() + ", please be reminded to take medicine: " + medicineTrackerCloudItem.getName() + " - from:  " + MainActivity.medicineTrackerManager.getPhoneOwner();
                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
                                        intent.putExtra("sms_body", message);
                                        startActivity(intent);
                                    }
                                });*/

                        builder1.setPositiveButton(
                                "Remind",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        medicineTrackerManager.remindMedicineTrackerItemInCloud(medicineTrackerCloudItem);
                                        medicineTrackerManager.removeMedicineTrackerItemInCloud(medicineTrackerCloudItem);

                                    }
                                });
                        builder1.setNegativeButton(
                                "Later",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {


                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DEBUG","OnCancelled", databaseError.toException());
            }
        });

        MainActivity.mCloudDatabase.getReference("reminder").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(MainActivity.sharedPreferences.getBoolean("notification",false) == false) return;

                Map<String,Map<String,MedicineTrackerCloudItem>> data = (Map<String,Map<String,MedicineTrackerCloudItem>>)dataSnapshot.getValue();
                if(data == null || data.size()==0) return;

                for(Map.Entry<String,Map<String,MedicineTrackerCloudItem>> pair : data.entrySet()){
                    String user = pair.getKey();
                    Map<String,MedicineTrackerCloudItem> items = pair.getValue();

                    for(Map.Entry<String, MedicineTrackerCloudItem> item: items.entrySet()){
                        final MedicineTrackerCloudItem medicineTrackerCloudItem = MedicineTrackerCloudItem.fromMap((Map)item.getValue());
                        if(medicineTrackerCloudItem.getDateInStr().equals(new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date())) == false || medicineTrackerCloudItem.getFrom().equals(sharedPreferences.getString("patientName",""))){
                            continue;
                        }

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(mainActivity);
                        builder1.setMessage(medicineTrackerCloudItem.getFrom() + " remind you to take " + medicineTrackerCloudItem.getName() + "! Do you want to take it now?");
                        builder1.setTitle("Reminder from " + medicineTrackerCloudItem.getFrom());
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Got it",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //medicineTrackerManager.remindMedicineTrackerItemInCloud(medicineTrackerCloudItem);
                                        medicineTrackerManager.removeMedicineTrackerItemReminderInCloud(medicineTrackerCloudItem);
                                    }
                                });
                        builder1.setNegativeButton(
                                "Ignore",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {


                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DEBUG","OnCancelled", databaseError.toException());
            }
        });
    }

    public void initializeTimerTask() {

        timerTask = new TimerTask() {
            public void run() {

                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {
                        createCloudDbListener();
                    }
                });
            }
        };
    }

    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, 10000, 20000); //
    }
    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }


}
