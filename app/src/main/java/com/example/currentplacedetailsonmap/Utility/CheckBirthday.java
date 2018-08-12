package com.example.currentplacedetailsonmap.Utility;

import android.util.Log;
import java.util.Calendar;

public class CheckBirthday {
    public static boolean isOver18(Calendar dob) {
        try {
            Calendar today = Calendar.getInstance();

            int todayYear = today.get(Calendar.YEAR);
            int birthDateYear = dob.get(Calendar.YEAR);
            int todayDayOfYear = today.get(Calendar.DAY_OF_YEAR);
            int birthDateDayOfYear = dob.get(Calendar.DAY_OF_YEAR);
            int todayMonth = today.get(Calendar.MONTH);
            int birthDateMonth = dob.get(Calendar.MONTH);
            int todayDayOfMonth = today.get(Calendar.DAY_OF_MONTH);
            int birthDateDayOfMonth = dob.get(Calendar.DAY_OF_MONTH);
            int age = todayYear - birthDateYear;

            // If birth date is greater than todays date (after 2 days adjustment of leap year) then decrement age one year
            if ((birthDateDayOfYear - todayDayOfYear > 3) || (birthDateMonth > todayMonth)) {
                age--;

                // If birth date and todays date are of same month and birth day of month is greater than todays day of month then decrement age
            } else if ((birthDateMonth == todayMonth) && (birthDateDayOfMonth > todayDayOfMonth)) {
                age--;
            }

            Log.i("chad", String.valueOf(age));

            if (age >= 18) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
