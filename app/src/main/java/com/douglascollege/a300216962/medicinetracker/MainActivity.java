package com.douglascollege.a300216962.medicinetracker;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.henry.calendarview.DatePickerController;
import com.henry.calendarview.DayPickerView;
import com.henry.calendarview.SimpleMonthAdapter;
import com.douglascollege.a300216962.medicinetracker.R;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DayPickerView dayPickerView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        dayPickerView = (DayPickerView) findViewById(R.id.dpv_calendar);

        DayPickerView.DataModel dataModel = new DayPickerView.DataModel();
        dataModel.yearStart = Calendar.YEAR-1;
        dataModel.monthStart = Calendar.MONTH-1;
        dataModel.monthCount = 16;
        dataModel.defTag = "";
        dataModel.leastDaysNum = 2;
        dataModel.mostDaysNum = 100;


        dayPickerView.setParameter(dataModel, new DatePickerController() {
            @Override
            public void onDayOfMonthSelected(SimpleMonthAdapter.CalendarDay calendarDay) {
                Toast.makeText(context, "onDayOfMonthSelected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDateRangeSelected(List<SimpleMonthAdapter.CalendarDay> selectedDays) {
                Toast.makeText(context, "onDateRangeSelected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void alertSelectedFail(FailEven even) {
                Toast.makeText(context, "alertSelectedFail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
