package com.douglascollege.a300216962.medicinetracker;

/**
 * Created by 300216962 on 7/22/2017.
 */

public class MedicineTrackerActivityAdapterItem {

    private boolean isTaken = false;
    private String takingDate;

    public boolean isTaken() {
        return isTaken;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }

    public MedicineTrackerActivityAdapterItem(boolean isTaken , String takingDate){
        this.isTaken = isTaken;
        this.takingDate = takingDate;
    }

    public String getTakingDate() {
        return takingDate;
    }

    public void setTakingDate(String takingDate) {
        this.takingDate = takingDate;
    }
}
