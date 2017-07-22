package com.douglascollege.a300216962.medicinetracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.henry.calendarview.SimpleMonthAdapter;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends Activity {


    public static Context context;
    EditText editTextMedicineDates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        editTextMedicineDates = (EditText)findViewById(R.id.editTextMedicineDates);
        Button button = (Button) findViewById(R.id.buttonAddMedicine);

        editTextMedicineDates.setFocusable(false);
        editTextMedicineDates.setClickable(true);
        editTextMedicineDates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DatePickerActivity.class);

                startActivityForResult(intent,1);


            }
        });


        ListView listview = (ListView) findViewById(R.id.listviewMedicineTakingItem);
        listview.setAdapter(new MainActivityAdapter(context, new String[] { "Medicine 1",
                "Medicine 2" , "Medicine 2" }));


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

            editTextMedicineDates.setText(medicineTakingDateRange);

        }



    }
}
