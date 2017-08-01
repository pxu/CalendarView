package com.douglascollege.a300216962.medicinetracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.douglascollege.a300216962.medicinetracker.database.MedicineTracker;
import com.douglascollege.a300216962.medicinetracker.database.MedicineTrackerDao;
import com.douglascollege.a300216962.medicinetracker.database.MedicineTrackerItem;
import com.douglascollege.a300216962.medicinetracker.database.MedicineTrackerItemDao;
import com.henry.calendarview.SimpleMonthAdapter;

import java.util.List;

import static com.douglascollege.a300216962.medicinetracker.MainActivity.medicineTrackerManager;

public class AddMedicineTrackerActivity extends Activity {
    EditText editTextMedicineDates;

    List<SimpleMonthAdapter.CalendarDay> selectedDays;

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine_tracker);
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

                if(editTextMedicineName.getText().toString().isEmpty()){
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);
                    dlgAlert.setMessage("Please type the medicine name");
                    dlgAlert.setTitle("warning");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                    return;
                } else if(editTextMedicineQuantity.getText().toString().isEmpty()){
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);
                    dlgAlert.setMessage("Please type the daily dosage");
                    dlgAlert.setTitle("warning");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                    return;
                }else if(selectedDays == null || selectedDays.size()<=0){
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);
                    dlgAlert.setMessage("Please select dates");
                    dlgAlert.setTitle("warning");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                    return;
                }

                MedicineTracker mt = new MedicineTracker();
                mt.setDailyDosage(Double.parseDouble(editTextMedicineQuantity.getText().toString()));
                mt.setName(editTextMedicineName.getText().toString());

                mt.setStartDate(selectedDays.get(0).getDate());
                mt.setEndDate(selectedDays.get(selectedDays.size()-1).getDate());

                if(medicineTrackerManager.hasMedicineAlreadyTracked(mt)){
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);
                    dlgAlert.setMessage("You've already have overlapped track on this medicine in the app");
                    dlgAlert.setTitle("Warning");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                }else {
                    medicineTrackerManager.createMedicineTracker(mt);

                    for (SimpleMonthAdapter.CalendarDay day : selectedDays) {
                        MedicineTrackerItem mti = new MedicineTrackerItem();
                        mti.setDate(day.getDate());
                        mti.setMedicineTracker(mt);
                        medicineTrackerManager.createMedicineTrackerItem(mti);
                    }

                    finish();
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            Bundle b = data.getExtras();
            selectedDays = (List<SimpleMonthAdapter.CalendarDay>) b.get("selected_dates");
            String medicineTakingDateRange = "";
            if (selectedDays != null && selectedDays.size() > 0) {
                medicineTakingDateRange = CommonUtils.getDateInString(selectedDays.get(0).getDate());
                medicineTakingDateRange += " - " + CommonUtils.getDateInString(selectedDays.get(selectedDays.size() - 1).getDate());

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
