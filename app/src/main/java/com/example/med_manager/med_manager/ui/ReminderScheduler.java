package com.example.med_manager.med_manager.ui;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.med_manager.med_manager.provider.MedContract;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

import static com.example.med_manager.med_manager.provider.MedContract.BASE_CONTENT_URI;
import static com.example.med_manager.med_manager.provider.MedContract.MEDS_PATH;

/**
 * Created by salabs on 17/04/2018.
 */

public class ReminderScheduler {
   private static final String TAG = ReminderScheduler.class.getSimpleName();

    // COMPLETED (15) Create three constants and one variable:
    //  - REMINDER_INTERVAL_SECONDS should be an integer constant storing the number of seconds in 15 minutes
    //  - SYNC_FLEXTIME_SECONDS should also be an integer constant storing the number of seconds in 15 minutes
    //  - REMINDER_JOB_TAG should be a String constant, storing something like "hydration_reminder_tag"
    //  - sInitialized should be a private static boolean variable which will store whether the job
    //    has been activated or not
    /*
     * Interval at which to remind the user for his medication
     */
    private static  int REMINDER_INTERVAL_MINUTES;
    private static  int REMINDER_INTERVAL_SECONDS;
    private static int  SYNC_FLEXTIME_SECONDS ;

    private static   String REMINDER_JOB_TAG = "medication_reminder_tag";

    private static boolean sInitialized;

    // Create a synchronized, public static method called scheduleChargingeminder that takes
    // in a context. This method will use FirebaseJobDispatcher to schedule a job that repeats roughly
    // every REMINDER_INTERVAL_SECONDS for each medication . It will trigger MedReminderFirebaseJobService

    synchronized public static void scheduleMedicationReminder(@NonNull final Context context) {

        // retrieve medication info from the DB and iterate to schedule the reminder for each
        Uri MED_URI = BASE_CONTENT_URI.buildUpon().appendPath(MEDS_PATH).build();
        Cursor cursor = context.getContentResolver().query(MED_URI,null,null,null,null,null);
        cursor.moveToFirst();
        for (int i = 0; i<cursor.getCount(); i++) {
            int freqIndex = cursor.getColumnIndex(MedContract.MedEntry.COLUMN_FREQUENCY);
            int freq = cursor.getInt(freqIndex);
            REMINDER_INTERVAL_MINUTES = freq;
            REMINDER_INTERVAL_SECONDS = (int) (TimeUnit.MINUTES.toSeconds(REMINDER_INTERVAL_MINUTES));
            SYNC_FLEXTIME_SECONDS = REMINDER_INTERVAL_SECONDS;

            //REMINDER_JOB_TAG= REMINDER_JOB_TAG + i;
            // Create a new GooglePlayDriver
            Driver driver = new GooglePlayDriver(context);
            // Create a new FirebaseJobDispatcher with the driver
            FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

            //  Use FirebaseJobDispatcher's newJobBuilder method to build a job which:
            // - has MedReminderFirebaseJobService as it's service
            // - has the tag REMINDER_JOB_TAG
            // - only triggers if the device is charging
            // - has the lifetime of the job as forever
            // - has the job recur
            // - occurs every 15 minutes with a window of 15 minutes. You can do this using a
            //   setTrigger, passing in a Trigger.executionWindow
            // - replaces the current job if it's already running
            // Finally, you should build the job.
        /* Create the Job to periodically create reminders to user to takr his/her medication */
            Job constraintReminderJob = dispatcher.newJobBuilder()
                /* The Service that will be used to write to preferences */
                    .setService(MedReminderFirebaseJobservice.class)
                /*
                 * Set the UNIQUE tag used to identify this Job.
                 */

                    .setTag(REMINDER_JOB_TAG)
                /*
                 * Network constraints on which this Job should run. In this app, we're using the
                 * device charging constraint so that the job only executes if the device is
                 * charging.
                 *
                 * In a normal app, it might be a good idea to include a preference for this,
                 * as different users may have different preferences on when you should be
                 * syncing your application's data.
                 */
                    .setConstraints(Constraint.DEVICE_CHARGING)
                /*
                 * setLifetime sets how long this job should persist. The options are to keep the
                 * Job "forever" or to have it die the next time the device boots up.
                 */
                    .setLifetime(Lifetime.FOREVER)
                /*
                 * We want these reminders to continuously happen, so we tell this Job to recur.
                 */
                    .setRecurring(true)
                /*
                 * We want the reminders to happen every  number of minutes interval set by the user in the DB. The first argument for
                 * Trigger class's static executionWindow method is the start of the time frame
                 * when the
                 * job should be performed. The second argument is the latest point in time at
                 * which the data should be synced. Please note that this end time is not
                 * guaranteed, but is more of a guideline for FirebaseJobDispatcher to go off of.
                 */
                    .setTrigger(Trigger.executionWindow(
                            REMINDER_INTERVAL_SECONDS,
                            REMINDER_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                /*
                 * If a Job with the tag with provided already exists, this new job will replace
                 * the old one.
                 */
                    .setReplaceCurrent(true)
                /* Once the Job is ready, call the builder's build method to return the Job */
                    .build();

            // Use dispatcher's schedule method to schedule the job
        /* Schedule the Job with the dispatcher */
            dispatcher.schedule(constraintReminderJob);
            REMINDER_INTERVAL_MINUTES = 0;
            REMINDER_INTERVAL_SECONDS = 0;
            SYNC_FLEXTIME_SECONDS = 0;
            Log.v(TAG, "Scheduled job " + i);

        }
        // COMPLETED (17) If the job has already been initialized, return
        if (sInitialized) return;



        // COMPLETED (22) Set sInitialized to true to mark that we're done setting up the job
        /* The job has been initialized */
        sInitialized = true;
    }
}
