package com.example.architecture.bad.myfigurecollection.download;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class DownloadService extends IntentService {
    private static final String TAG = DownloadService.class.getName();
    private static final String ACTION_DOWNLOAD = "com.example.architecture.bad.myfigurecollection.download.action.DOWNLOAD";
    private static final String EXTRA_PARAM_IMAGE_ID = "com.example.architecture.bad.myfigurecollection.download.extra.IMAGE_ID";
    private static final String EXTRA_PARAM_IMAGE_URL = "com.example.architecture.bad.myfigurecollection.download.extra.IMAGE_URL";

    public DownloadService() {
        super("DownloadService");
    }

    /**
     * Starts this service to perform action DOWNLOAD with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionDownload(Context context, String id, String paramImageUrl) {
        Intent intent = new Intent(context, DownloadService.class);
        intent.setAction(ACTION_DOWNLOAD);
        intent.putExtra(EXTRA_PARAM_IMAGE_ID, id);
        intent.putExtra(EXTRA_PARAM_IMAGE_URL, paramImageUrl);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_DOWNLOAD.equals(action)) {
                final String paramImageUrl = intent.getStringExtra(EXTRA_PARAM_IMAGE_URL);
                final String paramId = intent.getStringExtra(EXTRA_PARAM_IMAGE_ID);
                handleActionDownload(paramId, paramImageUrl);
            }
        }
    }

    /**
     * Handle action Download in the provided background thread with the provided
     * parameters.
     */
    private void handleActionDownload(String id, String paramImageUrl) {
        try {
            Bitmap image = Picasso.with(this).load(paramImageUrl).get();
            File picturesDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File mfcDirectory = new File(picturesDirectory, "MyFigureCollection");
            //noinspection ResultOfMethodCallIgnored
            mfcDirectory.mkdirs();
            File imageFile = new File(mfcDirectory, id + ".jpg");
            if (imageFile.createNewFile()) {
                FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        } catch (IOException e) {
            Log.e(TAG, "Error saving image!", e);
        }
    }

}
