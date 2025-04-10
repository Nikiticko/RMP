package com.example.rmp_coursach;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Random;

public class BoundService extends Service {

    // Binder для доступа из активности
    private final IBinder binder = new LocalBinder();
    private final Random random = new Random();

    // Класс, через который активность будет взаимодействовать с сервисом
    public class LocalBinder extends Binder {
        public BoundService getService() {
            return BoundService.this;
        }
    }

    // Возвращает Binder клиенту
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    // Метод, который будет вызываться из активности
    public int getRandomNumber() {
        return random.nextInt(100); // случайное число от 0 до 99
    }
}
