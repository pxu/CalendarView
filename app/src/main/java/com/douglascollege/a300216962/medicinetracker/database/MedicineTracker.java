package com.douglascollege.a300216962.medicinetracker.database;

import com.douglascollege.a300216962.medicinetracker.CommonUtils;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Date;
import java.util.Collection;
import java.util.List;

@DatabaseTable(tableName = "tb_medicine_tracker")
public class MedicineTracker
{
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "daily_dosage")
    private double dailyDosage;

    @DatabaseField(columnName = "start_date")
    private Date startDate;

    @DatabaseField(columnName = "end_date")
    private Date endDate;

    @ForeignCollectionField
    private Collection<MedicineTrackerItem> medicineTrackerItems;

    public MedicineTracker()
    {
    }

    public MedicineTracker(int id, String name, double dailyDosage, Date startDate, Date endDate, List<MedicineTrackerItem> medicineTrackerItems) {
        this.id = id;
        this.name = name;
        this.dailyDosage = dailyDosage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.medicineTrackerItems = medicineTrackerItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDailyDosage() {
        return dailyDosage;
    }

    public void setDailyDosage(double dailyDosage) {
        this.dailyDosage = dailyDosage;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<MedicineTrackerItem> getMedicineTrackerItems() {

        List<MedicineTrackerItem> data = new ArrayList<>();
        for(MedicineTrackerItem item: medicineTrackerItems){
            data.add(item);
        }

        return data;
    }

    public void setMedicineTrackerItems(Collection<MedicineTrackerItem> medicineTrackerItems) {
        this.medicineTrackerItems = medicineTrackerItems;
    }

    public String getPeriod(){
        return CommonUtils.getDateInString(getStartDate()) + " - " + CommonUtils.getDateInString(getEndDate());
    }
}