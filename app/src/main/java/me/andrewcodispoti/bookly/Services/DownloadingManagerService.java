package me.andrewcodispoti.bookly.Services;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.IntentSender;
import android.util.Log;

/**
 * Created by andrewcodispoti on 2015-08-17.
 */
public class DownloadingManagerService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public DownloadingManagerService() {
        super("Downloading Service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("BOOKLY", "got intent");
    }
}
