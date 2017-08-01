package com.douglascollege.a300216962.medicinetracker.database;

/**
 * Created by 300216962 on 7/29/2017.
 */

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

public class MedicineTrackerItemDao
{
    private Dao<MedicineTrackerItem, Integer> medicineTrackerItemDaoOpe;
    private DatabaseHelper helper;

    @SuppressWarnings("unchecked")
    public MedicineTrackerItemDao(Context context)
    {
        try
        {
            helper = DatabaseHelper.getHelper(context);
            medicineTrackerItemDaoOpe = helper.getDao(MedicineTrackerItem.class);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * add new medicineTrackerItem
     * @param medicineTrackerItem
     */
    public void add(MedicineTrackerItem medicineTrackerItem)
    {
        try
        {
            medicineTrackerItemDaoOpe.create(medicineTrackerItem);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    /**
     * get Medicine Tracker Item by its id
     * @param id
     * @return
     */
    public MedicineTrackerItem get(int id)
    {
        MedicineTrackerItem medicineTrackerItem = null;
        try
        {
            medicineTrackerItem = medicineTrackerItemDaoOpe.queryForId(id);

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return medicineTrackerItem;
    }

    public int update(MedicineTrackerItem medicineTrackerItem)
    {
        int result = -1;
        try
        {
            result = medicineTrackerItemDaoOpe.update(medicineTrackerItem);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return result;
    }

    public int deleteItems(Collection<MedicineTrackerItem> items){
        int result = -1;

        try
        {
            result =  medicineTrackerItemDaoOpe.delete(items);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * get all medicineTrackerItem under one medicine tracer record
     * @param id
     * @return
     */
    public List<MedicineTrackerItem> listByMedicineTrackerId(int id)
    {
        try
        {
            return medicineTrackerItemDaoOpe.queryBuilder().where().eq("medicine_tracker_id", id)
                    .query();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

}