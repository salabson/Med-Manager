package com.example.med_manager.med_manager.ui;

import android.content.Context;
import android.util.Log;

import com.example.med_manager.med_manager.utils.NotificationUtils;

/**
 * Created by salabs on 17/04/2018.
 */

public class ReminderTasks {

   // public static final String ACTION_INCREMENT_WATER_COUNT = "increment-water-count";
    public static final String ACTION_DISMISS_NOTIFICATION = "dismiss-notification";
    static final String ACTION_MEDICATION_REMINDER = "med-reminder";

    public static void executeTask(Context context, String action) {
        if (ACTION_MEDICATION_REMINDER.equals(action)) {
            issueMedicationReminder(context);
            Log.v("ReminderTasks", "notification sent" );
        } else if (ACTION_DISMISS_NOTIFICATION.equals(action)) {
            NotificationUtils.clearAllNotifications(context);
        }
    }

    private static void issueMedicationReminder(Context context) {
        NotificationUtils.remindUserForMedication(context);
    }
}
