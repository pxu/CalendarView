package com.douglascollege.a300216962.medicinetracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.henry.calendarview.SimpleMonthAdapter;

import java.util.List;

import static com.douglascollege.a300216962.medicinetracker.MainActivity.context;

public class AddMedicineTrackerActivity extends AppCompatActivity {
    EditText editTextMedicineDates;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine_tracker);

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
        final EditText editTextMedicineName = (EditText)findViewById(R.id.editTextMedicineName);
        editTextMedicineName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    hideSoftKeyBoard();
                    return true;
                }
                return false;
            }
        });

        final EditText editTextMedicineQuantity = (EditText)findViewById(R.id.editTextMedicineQuantity);
        editTextMedicineQuantity.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    hideSoftKeyBoard();
                    return true;
                }
                return false;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*data.add(new MainActivityAdapterItem(
                        editTextMedicineName.getText().toString(),
                        editTextMedicineQuantity.getText().toString(),
                        editTextMedicineDates.getText().toString())
                );
                listview.invalidateViews();*/
                hideSoftKeyBoard();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            Bundle b = data.getExtras();
            List<SimpleMonthAdapter.CalendarDay> selectedDays = (List<SimpleMonthAdapter.CalendarDay>) b.get("selected_dates");
            String medicineTakingDateRange = "";
            if (selectedDays != null && selectedDays.size() > 0) {
                medicineTakingDateRange = CommonUtils.getDateInString(selectedDays.get(0).getDate());
                medicineTakingDateRange += " - " + CommonUtils.getDateInString(selectedDays.get(selectedDays.size() - 1).getDate());

            }

            for (SimpleMonthAdapter.CalendarDay day : selectedDays) {
                System.out.println(" pengfei: " + day.toString());

            }

            editTextMedicineDates.setText(medicineTakingDateRange);

        }
    }

    private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        if(imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
