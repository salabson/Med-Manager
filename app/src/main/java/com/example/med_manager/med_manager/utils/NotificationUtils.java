package com.example.med_manager.med_manager.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import com.example.android.med_manager.R;
import com.example.med_manager.med_manager.ui.MedListActivity;
import com.example.med_manager.med_manager.ui.MedReminderIntentService;
import com.example.med_manager.med_manager.ui.ReminderTasks;

/**
 * Created by salabs on 17/04/2018.
 */

public class NotificationUtils {
    private static final int MED_REMINDER_NOTIFICATION_ID = 1000;
    /**
     * This pending intent id is used to uniquely reference the pending intent
     */
    private static final int MED_REMINDER_PENDING_INTENT_ID = 3000;
    //private static final int ACTION_DRINK_PENDING_INTENT_ID = 1;
    private static final int ACTION_IGNORE_PENDING_INTENT_ID = 14;

    public static void clearAllNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static void remindUserForMedication(Context context) {

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.medical)
                .setContentTitle(context.getString(R.string.med_reminder_notification_title))
                .setContentText(context.getString(R.string.med_reminder_notification_body))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string.med_reminder_notification_body)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                //.addAction(drinkWaterAction(context))
                .addAction(ignoreReminderAction(context))
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
        }


        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        /* MED_REMINDER_NOTIFICATION_ID allows you to update or cancel the notification later on */
        notificationManager.notify(MED_REMINDER_NOTIFICATION_ID, notificationBuilder.build());
    }
    private static NotificationCompat.Action ignoreReminderAction(Context context) {
        Intent ignoreReminderIntent = new Intent(context, MedReminderIntentService.class);
        ignoreReminderIntent.setAction(ReminderTasks.ACTION_DISMISS_NOTIFICATION);
        PendingIntent ignoreReminderPendingIntent = PendingIntent.getService(
                context,
                ACTION_IGNORE_PENDING_INTENT_ID,
                ignoreReminderIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action ignoreReminderAction = new NotificationCompat.Action(R.drawable.ic_cancel_black_24dp,
                "No, thanks.",
                ignoreReminderPendingIntent);
        return ignoreReminderAction;
    }

    /*private static NotificationCompat.Action drinkWaterAction(Context context) {
        Intent incrementWaterCountIntent = new Intent(context, MedReminderIntentService.class);
        incrementWaterCountIntent.setAction(ReminderTasks.ACTION_INCREMENT_WATER_COUNT);
        PendingIntent incrementWaterPendingIntent = PendingIntent.getService(
                context,
                ACTION_DRINK_PENDING_INTENT_ID,
                incrementWaterCountIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Action drinkWaterAction = new NotificationCompat.Action(R.drawable.medical,
                "I did it!",
                incrementWaterPendingIntent);
        return drinkWaterAction;
    }*/

    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, MedListActivity.class);
        return PendingIntent.getActivity(
                context,
                MED_REMINDER_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
