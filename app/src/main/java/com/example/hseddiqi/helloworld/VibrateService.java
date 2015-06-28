package com.example.hseddiqi.helloworld;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.content.Context;
import android.os.Vibrator;
import android.widget.Toast;


public class VibrateService extends Service {
    public VibrateService() {
    }

    public void onStart(Intent intent, int startid) {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // pass the number of millseconds fro which you want to vibrate the phone here we
        // have passed 2000 so phone will vibrate for 2 seconds.
        v.vibrate(2000);

        // If you want to vibrate  in a pattern
        //long pattern[]={0,800,200,1200,300,2000,400,4000};
        // 2nd argument is for repetition pass -1 if you do not want to repeat the Vibrate
        //v.vibrate(pattern, -1);

        Toast.makeText(getApplicationContext(), "Phone is Vibrating", Toast.LENGTH_LONG).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
