package com.douglascollege.a300216962.medicinetracker.database;

/**
 * Created by 300216962 on 7/29/2017.
 */

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

public class MedicineTrackerDao
{
    private Context context;
    private Dao<MedicineTracker, Integer> medicineTrackerDaoOpe;
    private DatabaseHelper helper;

    public MedicineTrackerDao(Context context)
    {
        this.context = context;
        try
        {
            helper = DatabaseHelper.getHelper(context);
            medicineTrackerDaoOpe = helper.getDao(MedicineTracker.class);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * add new medicineTracker
     * @param medicineTracker
     */
    public void add(MedicineTracker medicineTracker)
    {
        try
        {
            medicineTrackerDaoOpe.create(medicineTracker);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

    }//...other operations

    public MedicineTracker get(int id){
        MedicineTracker medicineTracker = null;
        try
        {
            medicineTracker = medicineTrackerDaoOpe.queryForId(id);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return medicineTracker;
    }

    public List<MedicineTracker> getMedicineTrackersByName(String name){
        List<MedicineTracker> medicineTrackerList = null;
        try
        {
            medicineTrackerList = medicineTrackerDaoOpe.queryForEq("name",name);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return medicineTrackerList;
    }

    public List<MedicineTracker> getAll(){
        List<MedicineTracker> medicineTrackers = null;

        try
        {
            medicineTrackers = medicineTrackerDaoOpe.queryForAll();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return medicineTrackers;
    }

    public int  delete(int id){
        int result = -1;

        try
        {
            result =  medicineTrackerDaoOpe.deleteById(id);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return result;
    }




}