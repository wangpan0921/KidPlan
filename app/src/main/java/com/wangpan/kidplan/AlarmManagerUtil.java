package com.wangpan.kidplan;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

public class AlarmManagerUtil {
    public static final String TAG = CommonUtils.COMMON_TAG + AlarmManagerUtil.class.getSimpleName();
    private static AlarmManager mAlarmManager;
    private static AlarmManagerUtil mAlarmManagerUtil = new AlarmManagerUtil();
    private AlarmManagerUtil(){}

    public static AlarmManagerUtil getInstance(Context context) {
        mAlarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        return mAlarmManagerUtil;
    }

    public void schedulePlay(Context context, ArrayList audioList, TimeInfo timeInfo) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2021);
        calendar.set(Calendar.MONTH, Calendar.MAY);
        calendar.set(Calendar.DAY_OF_MONTH, 11);
        calendar.set(Calendar.HOUR_OF_DAY, timeInfo.hour);
        calendar.set(Calendar.MINUTE, timeInfo.minute);
        calendar.set(Calendar.SECOND,0);
        Log.d(TAG, "schedulePlay:" + timeInfo.hour + ":" + timeInfo.minute);

        Intent intent = new Intent(context, AlarmService.class);
        intent.setAction("com.wangpan.kidplan.AlarmService");
        intent.putExtra("audioList", audioList);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if(Build.VERSION.SDK_INT < 19){
            mAlarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }else{
            mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }


    }

    public static class TimeInfo{
        private int hour;
        private int minute;
        public TimeInfo(int h, int m) {
            hour = h;
            minute = m;
        }

        public int getHour() {
            return hour;
        }

        public int getMinute() {
            return minute;
        }
    }
}
