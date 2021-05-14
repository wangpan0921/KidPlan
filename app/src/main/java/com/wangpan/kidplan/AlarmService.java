package com.wangpan.kidplan;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class AlarmService extends Service {
    private final String TAG = CommonUtils.COMMON_TAG + this.getClass().getSimpleName();
    public static final String ACTION_ALARM = "ACTION_ALARM_SCHEDULE_PLAY";
    public AlarmService(){
//        super("AlarmService construction.");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate.");
    }

//    @Override
//    protected void onHandleIntent(@Nullable Intent intent) {
//        Log.d(TAG, "onHandleIntent");
//        ArrayList<String> audioList = (ArrayList)intent.getSerializableExtra("audioList");
//        for (String path :
//                audioList) {
//            Log.d(TAG, "path:" + path);
//            Uri uri = Uri.parse(path);
//            Log.d(TAG, "uri:" + uri);
//            MediaPlayerUtil.getInstance().startPlay(getApplicationContext(), uri);
//        }
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy.");
        MediaPlayerUtil.getInstance().stopPlay();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        ArrayList<Uri> audioList = (ArrayList)intent.getSerializableExtra("audioList");
        for (Uri uri :
                audioList) {
            Log.d(TAG, "uri:" + uri);
            MediaPlayerUtil.getInstance().startPlay(getApplicationContext(), uri);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");

        return null;
    }

}
