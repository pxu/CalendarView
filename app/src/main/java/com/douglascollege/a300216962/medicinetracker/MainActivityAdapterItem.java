package com.douglascollege.a300216962.medicinetracker;

/**
 * Created by 300216962 on 7/22/2017.
 */

public class MainActivityAdapterItem {
    private String medicineName;
    private String medicineQuantity;
    private String startDate;

    public MainActivityAdapterItem(String medicineName, String medicineQuantity, String startDate){
        this.medicineName = medicineName;
        this.medicineQuantity = medicineQuantity;
        this.startDate = startDate;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getMedicineQuantity() {
        return medicineQuantity;
    }

    public void setMedicineQuantity(String medicineQuantity) {
        this.medicineQuantity = medicineQuantity;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
