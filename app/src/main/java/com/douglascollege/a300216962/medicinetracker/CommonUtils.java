package com.douglascollege.a300216962.medicinetracker;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 300216962 on 7/22/2017.
 */

public class CommonUtils {
    public static String getDateInString(Date date){
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

}
