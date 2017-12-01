package com.example.android.bakingapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class StackWidgetServce extends Service {
    public StackWidgetServce() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
