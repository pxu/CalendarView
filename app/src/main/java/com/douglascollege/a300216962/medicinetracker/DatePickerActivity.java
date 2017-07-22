package com.douglascollege.a300216962.medicinetracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import com.henry.calendarview.DatePickerController;
import com.henry.calendarview.DayPickerView;
import com.henry.calendarview.SimpleMonthAdapter;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class DatePickerActivity extends Activity {

    DayPickerView dayPickerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);

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
                Toast.makeText(MainActivity.context, "onDayOfMonthSelected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDateRangeSelected(List<SimpleMonthAdapter.CalendarDay> selectedDays) {

                Intent intent = new Intent();
                Bundle b = new Bundle();
                b.putSerializable("selected_dates", (Serializable) selectedDays);

                intent.putExtras(b);
                setResult(RESULT_OK, intent);
                finish();

            }

            @Override
            public void alertSelectedFail(FailEven even) {
                Toast.makeText(MainActivity.context, "alertSelectedFail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
