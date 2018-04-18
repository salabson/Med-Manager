package com.example.med_manager.med_manager.ui;

import android.app.IntentService;
import android.content.Intent;
import android.renderscript.Int2;

/**
 * Created by salabs on 17/04/2018.
 */

public class MedReminderIntentService extends IntentService {
    public MedReminderIntentService() {
        super("MedReminderIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        ReminderTasks.executeTask(this, action);
    }
}
