package com.henry.calendarview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

import java.io.Serializable;
import java.util.List;

public class DayPickerView extends RecyclerView {
    protected Context mContext;
    protected SimpleMonthAdapter mAdapter;
    private DatePickerController mController;
    protected int mCurrentScrollState = 0;
    protected long mPreviousScrollPosition;
    protected int mPreviousScrollState = 0;
    private TypedArray typedArray;
    private OnScrollListener onScrollListener;

    private DataModel dataModel;

    public DayPickerView(Context context) {
        this(context, null);
    }

    public DayPickerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DayPickerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.DayPickerView);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        init(context);
    }

    public void init(Context paramContext) {
        setLayoutManager(new LinearLayoutManager(paramContext));
        mContext = paramContext;
        setUpListView();

        onScrollListener = new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                final SimpleMonthView child = (SimpleMonthView) recyclerView.getChildAt(0);
                if (child == null) {
                    return;
                }

                mPreviousScrollPosition = dy;
                mPreviousScrollState = mCurrentScrollState;
            }
        };
    }

    protected void setUpAdapter() {
        if (mAdapter == null) {
            mAdapter = new SimpleMonthAdapter(getContext(), typedArray, mController, dataModel);
            setAdapter(mAdapter);
        }
        mAdapter.notifyDataSetChanged();
    }

    protected void setUpListView() {
        setVerticalScrollBarEnabled(false);
        setOnScrollListener(onScrollListener);
        setFadingEdgeLength(0);
    }

    /**
     * 设置参数
     *
     * @param dataModel   数据
     * @param mController 回调监听
     */
    public void setParameter(DataModel dataModel, DatePickerController mController) {
        if(dataModel == null) {
            Log.e("crash", "please set parameter in the dataModel");
            return;
        }
        this.dataModel = dataModel;
        this.mController = mController;
        setUpAdapter();
        // scroll to the month selected
        scrollToSelectedPosition(dataModel.selectedDays, dataModel.monthStart);
    }

    private void scrollToSelectedPosition(SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays, int monthStart) {
        if(selectedDays != null && selectedDays.getFirst() != null && selectedDays.getFirst().month > monthStart) {
            int position = selectedDays.getFirst().month - monthStart;
            scrollToPosition(position);
        }
    }

    public static class DataModel implements Serializable {
        // TYPE_MULTI：multiple selection ，TYPE_RANGE：range selection ，TYPE_ONLY_READ：read only
//        public enum TYPE {TYPE_MULTI, TYPE_RANGE, TYPE_ONLY_READ}

//        TYPE type;
        public int yearStart;                                      // start year of the calendar
        public int monthStart;                                     // start month of the calendar
        public int monthCount;                                     // the count of month to display
        public List<SimpleMonthAdapter.CalendarDay> invalidDays;
        public List<SimpleMonthAdapter.CalendarDay> busyDays;
        public SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays;  // default selected days
        public int leastDaysNum;                                   // minimum days to select
        public int mostDaysNum;                                    // maximum days to select
        public List<SimpleMonthAdapter.CalendarDay> tags;          // tags in the days
        public String defTag;                                      // default tag
//        public boolean displayTag;                               // will display tag of not
    }
}