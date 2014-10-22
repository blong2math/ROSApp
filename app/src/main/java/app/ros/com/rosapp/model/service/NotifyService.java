package app.ros.com.rosapp.model.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class NotifyService extends Service {
    public NotifyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
