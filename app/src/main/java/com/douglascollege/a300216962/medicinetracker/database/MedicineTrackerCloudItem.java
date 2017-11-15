package com.douglascollege.a300216962.medicinetracker.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static com.douglascollege.a300216962.medicinetracker.MainActivity.sharedPreferences;

public class MedicineTrackerCloudItem
{

    private boolean taken;

    private String name;

    private String patientName;

    private String from;

    private String dateInStr;

    public MedicineTrackerCloudItem()
    {
    }

    public String getDateInStr(){
        return dateInStr;
    }

    public void setDateInStr(String dateInStr){
        this.dateInStr = dateInStr;
    }


    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }


    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public static MedicineTrackerCloudItem fromMap(Map<String, Object> map){
        MedicineTrackerCloudItem item = new MedicineTrackerCloudItem();
        item.name = ((String)map.get("name"));
        item.dateInStr = (String)map.get("dateInStr");
        item.patientName = (String)map.get("patientName");
        if(map.containsKey("from")){
            item.from = (String)map.get("from");
        }

        return item;

    }

    public static MedicineTrackerCloudItem fromPersistentObjects(MedicineTrackerItem medicineTrackerItem, MedicineTracker medicineTracker){
        MedicineTrackerCloudItem item = new MedicineTrackerCloudItem();
        item.dateInStr = new SimpleDateFormat("yyyy-MM-dd").format(medicineTrackerItem.getDate());
        item.setTaken(medicineTrackerItem.isTaken());
        item.setName(medicineTracker.getName());
        String patientName = sharedPreferences.getString("patientName","");
        item.setPatientName(patientName);
        return item;

    }


}

