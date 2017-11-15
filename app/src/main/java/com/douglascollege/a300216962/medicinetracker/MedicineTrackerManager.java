package com.douglascollege.a300216962.medicinetracker;

import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.provider.ContactsContract;

import com.douglascollege.a300216962.medicinetracker.database.MedicineTracker;
import com.douglascollege.a300216962.medicinetracker.database.MedicineTrackerCloudItem;
import com.douglascollege.a300216962.medicinetracker.database.MedicineTrackerDao;
import com.douglascollege.a300216962.medicinetracker.database.MedicineTrackerItem;
import com.douglascollege.a300216962.medicinetracker.database.MedicineTrackerItemDao;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.douglascollege.a300216962.medicinetracker.MainActivity.context;
import static com.douglascollege.a300216962.medicinetracker.MainActivity.sharedPreferences;

/**
 * Created by pengfei.xu on 7/31/2017.
 */

public class MedicineTrackerManager {

    MedicineTrackerDao medicineTrackerDao;
    MedicineTrackerItemDao medicineTrackerItemDao;

    public MedicineTrackerManager(){
        medicineTrackerDao = new MedicineTrackerDao(context);
        medicineTrackerItemDao = new MedicineTrackerItemDao(context);
    }

    public void createMedicineTracker(MedicineTracker medicineTracker){
        medicineTrackerDao.add(medicineTracker);
    }

    public void createMedicineTrackerItem(MedicineTrackerItem medicineTrackerItem){
        medicineTrackerItemDao.add(medicineTrackerItem);
    }

    public void createMedicineTrackerItemInCloud(MedicineTrackerItem medicineTrackerItem, MedicineTracker medicineTracker){
        MedicineTrackerCloudItem item = MedicineTrackerCloudItem.fromPersistentObjects(medicineTrackerItem,medicineTracker);

        MainActivity.mCloudDatabase.getReference("records").child(item.getPatientName()).child(item.getDateInStr()).setValue(item);
    }

    public void remindMedicineTrackerItemInCloud(MedicineTrackerCloudItem item){
        item.setFrom(sharedPreferences.getString("patientName",""));
        MainActivity.mCloudDatabase.getReference("reminder").child(item.getPatientName()).child(item.getDateInStr()).setValue(item);
    }

    public void removeMedicineTrackerItemReminderInCloud(MedicineTrackerCloudItem item){
        MainActivity.mCloudDatabase.getReference("reminder").child(item.getPatientName()).child(item.getDateInStr()).removeValue();
    }

    public void removeMedicineTrackerItemInCloud(MedicineTrackerCloudItem item){
        MainActivity.mCloudDatabase.getReference("records").child(item.getPatientName()).child(item.getDateInStr()).removeValue();
    }


    public MedicineTracker getMedicineTrackerById(int id){
        return medicineTrackerDao.get(id);
    }

    public List<MedicineTracker> getAllMedicineTrackers(){
        return medicineTrackerDao.getAll();
    }

    public boolean hasMedicineAlreadyTracked(MedicineTracker newMedicineTracker){
        List<MedicineTracker> trackers = medicineTrackerDao.getMedicineTrackersByName(newMedicineTracker.getName());

        if(trackers != null && trackers.size()>0 ){
            for(MedicineTracker track: trackers){
                if(CommonUtils.isDateInBetween(track.getStartDate(), newMedicineTracker.getStartDate(),newMedicineTracker.getEndDate() ) ||
                        CommonUtils.isDateInBetween(track.getEndDate(), newMedicineTracker.getStartDate(),newMedicineTracker.getEndDate())){
                    return true;
                }
            }
        }

        return false;

    }

    public boolean isMedicineTrackerCompletedSuccessfully(int id){
        MedicineTracker mt = getMedicineTrackerById(id);
        Collection<MedicineTrackerItem> items = mt.getMedicineTrackerItems();

        for(MedicineTrackerItem item: items){
            if(!item.isTaken()){
                return false;
            }
        }

        return true;
    }

    public String getMedicineTrackerProgress(MedicineTracker tracker){
        MedicineTracker mt = getMedicineTrackerById(tracker.getId());
        Collection<MedicineTrackerItem> items = mt.getMedicineTrackerItems();

        int total = items.size();
        int completedDays = 0;
        for(MedicineTrackerItem item: items){
            if(item.isTaken()){
                completedDays++;
            }
        }

        return "" + completedDays + " out of " + total + " days completed";
    }

    public boolean deleteMedicineTracker(int id){
        MedicineTracker mt = getMedicineTrackerById(id);
        Collection<MedicineTrackerItem> items = mt.getMedicineTrackerItems();

        int deletedItemCount = medicineTrackerItemDao.deleteItems(items);
        int result =  medicineTrackerDao.delete(id);
        return result>0?true:false;
    }

    public boolean updateMedicineTrackerItem(MedicineTrackerItem medicineTrackerItem){
        int result = medicineTrackerItemDao.update(medicineTrackerItem);
        return result>0?true:false;
    }

    public static String getPhoneOwner(){
        return sharedPreferences.getString("patientName","");
    }

}
