package com.douglascollege.a300216962.medicinetracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.douglascollege.a300216962.medicinetracker.database.MedicineTrackerCloudItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class SettingActivity extends Activity {
    private SettingActivity settingActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        settingActivity = this;


        final EditText editTextName = (EditText)findViewById(R.id.editTextName);
        editTextName.setText(MainActivity.sharedPreferences.getString("patientName",""));

        final EditText editTextPhone = (EditText)findViewById(R.id.editTextPhone);
        editTextPhone.setText(MainActivity.sharedPreferences.getString("phoneNumber",""));

        Button buttonSave = (Button)findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();
                editor.putString("patientName", editTextName.getText().toString());
                editor.putString("phoneNumber", editTextPhone.getText().toString());

                editor.commit();
            }
        });
    }


}
