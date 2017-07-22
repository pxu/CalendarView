package com.douglascollege.a300216962.medicinetracker;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.henry.calendarview.DatePickerController;
import com.henry.calendarview.DayPickerView;
import com.henry.calendarview.SimpleMonthAdapter;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends Activity {

    DayPickerView dayPickerView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        dayPickerView = (DayPickerView) findViewById(R.id.dpv_calendar);

        DayPickerView.DataModel dataModel = new DayPickerView.DataModel();
        Calendar today = Calendar.getInstance();
        dataModel.yearStart = today.get(Calendar.YEAR);
        dataModel.monthStart = today.get(Calendar.MONTH);
        dataModel.monthCount = 12;
        dataModel.defTag = "";
        dataModel.leastDaysNum = 1;
        dataModel.mostDaysNum = 100;


        dayPickerView.setParameter(dataModel, new DatePickerController() {
            @Override
            public void onDayOfMonthSelected(SimpleMonthAdapter.CalendarDay calendarDay) {
                Toast.makeText(context, "onDayOfMonthSelected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDateRangeSelected(List<SimpleMonthAdapter.CalendarDay> selectedDays) {
                for(SimpleMonthAdapter.CalendarDay day: selectedDays){
                    System.out.println(" peng: " + day.toString());

                }

            }

            @Override
            public void alertSelectedFail(FailEven even) {
                Toast.makeText(context, "alertSelectedFail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
