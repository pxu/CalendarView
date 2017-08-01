package com.douglascollege.a300216962.medicinetracker.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;
import java.text.SimpleDateFormat;

@DatabaseTable(tableName = "tb_medicine_tracker_item")
public class MedicineTrackerItem
{
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private Date date;

    @DatabaseField(columnName = "taken")
    private boolean taken;

    @DatabaseField(canBeNull = true, foreign = true, columnName = "medicine_tracker_id")
    private MedicineTracker medicineTracker;

    public MedicineTrackerItem()
    {
    }

    public MedicineTrackerItem(int id, Date date, boolean taken, MedicineTracker medicineTracker) {
        this.id = id;
        this.date = date;
        this.taken = taken;
        this.medicineTracker = medicineTracker;
    }

    public String getDay(){
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("E"); // the day of the week abbreviated
        return simpleDateformat.format(date);

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public MedicineTracker getMedicineTracker() {
        return medicineTracker;
    }

    public void setMedicineTracker(MedicineTracker medicineTracker) {
        this.medicineTracker = medicineTracker;
    }
}

