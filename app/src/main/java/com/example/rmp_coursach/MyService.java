package com.example.rmp_coursach;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class MyService extends Service {

    private static final String CHANNEL_ID = "MyServiceChannel";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // Создаём уведомление
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Опросник работает")
                .setContentText("Фоновая задача запущена")
                .setSmallIcon(R.drawable.ic_launcher_foreground) // или свою иконку
                .build();

        // Запускаем сервис как Foreground
        startForeground(1, notification);

        // Пример фоновой задачи
        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                Log.d("MyService", "Foreground task running: " + i);
                SystemClock.sleep(1000);
            }
            stopForeground(true);
            stopSelf();
        }).start();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d("MyService", "Foreground Service Destroyed");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Фоновый сервис";
            String description = "Канал уведомлений сервиса";
            int importance = NotificationManager.IMPORTANCE_LOW;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }
}
