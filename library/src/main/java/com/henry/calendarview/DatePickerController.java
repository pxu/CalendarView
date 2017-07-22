package com.henry.calendarview;

import java.util.List;

public interface DatePickerController {

    enum FailEven {
        CONTAIN_NO_SELECTED, CONTAIN_INVALID, NO_REACH_LEAST_DAYS, NO_REACH_MOST_DAYS, END_MT_START;

    }
//	public abstract int getMaxYear();

    void onDayOfMonthSelected(SimpleMonthAdapter.CalendarDay calendarDay);          // callback method for day select, plus 1

    void onDateRangeSelected(List<SimpleMonthAdapter.CalendarDay> selectedDays);    // callback method for date range selection, remember plus 1

//    void onDaysSelected(List<SimpleMonthAdapter.CalendarDay> seleDaysList);       // callback method for multiple days select, plus 1 for the selection

    void alertSelectedFail(FailEven even);
}