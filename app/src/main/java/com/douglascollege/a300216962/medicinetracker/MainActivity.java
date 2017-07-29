package com.douglascollege.a300216962.medicinetracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.henry.calendarview.SimpleMonthAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends Activity {


    public static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        Button buttonAdd = (Button)findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddMedicineTrackerActivity.class);

                startActivityForResult(intent,1);
            }
        });


        final List<MainActivityAdapterItem> data = new ArrayList<>();

        data.add(new MainActivityAdapterItem("Aspinlin","1", "2017-07-18"));
        data.add(new MainActivityAdapterItem("Aspinlin2","3", "2017-07-18"));
        data.add(new MainActivityAdapterItem("Aspinlin3","3", "2017-07-18"));
        data.add(new MainActivityAdapterItem("Aspinlin","1", "2017-07-18"));
        data.add(new MainActivityAdapterItem("Aspinlin2","3", "2017-07-18"));
        data.add(new MainActivityAdapterItem("Aspinlin3","3", "2017-07-18"));
        data.add(new MainActivityAdapterItem("Aspinlin","1", "2017-07-18"));
        data.add(new MainActivityAdapterItem("Aspinlin2","3", "2017-07-18"));
        data.add(new MainActivityAdapterItem("Aspinlin3","3", "2017-07-18"));

        data.add(new MainActivityAdapterItem("Aspinlin","1", "2017-07-18"));
        data.add(new MainActivityAdapterItem("Aspinlin2","3", "2017-07-18"));
        data.add(new MainActivityAdapterItem("Aspinlin3","3", "2017-07-18"));


        final ListView listview = (ListView) findViewById(R.id.listviewMedicineTakingItem);
        listview.setAdapter(new MainActivityAdapter(context, data));

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, MedicineTrackerActivity.class);

                startActivityForResult(intent,2);
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1) {
            Bundle b = data.getExtras();
            List<SimpleMonthAdapter.CalendarDay> selectedDays = (List<SimpleMonthAdapter.CalendarDay>) b.get("selected_dates");
            String medicineTakingDateRange = "";
            if(selectedDays!=null && selectedDays.size()>0){
                medicineTakingDateRange = CommonUtils.getDateInString(selectedDays.get(0).getDate());
                medicineTakingDateRange += " - " +  CommonUtils.getDateInString(selectedDays.get(selectedDays.size()-1).getDate());

            }

            for (SimpleMonthAdapter.CalendarDay day : selectedDays) {
                System.out.println(" pengfei: " + day.toString());

            }

            //editTextMedicineDates.setText(medicineTakingDateRange);

        }



    }
}
