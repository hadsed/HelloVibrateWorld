package com.example.hseddiqi.helloworld;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.util.Log;

import java.util.List;


public class ShowSensorsActivity extends ActionBarActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mMagnetometer;
    private float[] mLastAccelerometer = new float[3];
    private float[] mLastMagnetometer = new float[3];
    private boolean mLastAccelerometerSet = false;
    private boolean mLastMagnetometerSet = false;
    private float[] mR = new float[9];
    private float[] mOrientation = new float[3];
//    private TextView textView;
    private TextView accelXVal;
    private TextView accelYVal;
    private TextView accelZVal;
    private TextView orientXVal;
    private TextView orientYVal;
    private TextView orientZVal;
    private TextView magnetoXVal;
    private TextView magnetoYVal;
    private TextView magnetoZVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_sensors);
        // sensor manager
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        // list of all sensors on device
        List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        // print it out since it's useful
        Log.w("Device Sensors", deviceSensors.toString());
        // get these text views
        accelXVal = (TextView) findViewById(R.id.accel_x_value);
        accelYVal = (TextView) findViewById(R.id.accel_y_value);
        accelZVal = (TextView) findViewById(R.id.accel_z_value);
        orientXVal = (TextView) findViewById(R.id.orient_x_value);
        orientYVal = (TextView) findViewById(R.id.orient_y_value);
        orientZVal = (TextView) findViewById(R.id.orient_z_value);
        magnetoXVal = (TextView) findViewById(R.id.magneto_x_value);
        magnetoYVal = (TextView) findViewById(R.id.magneto_y_value);
        magnetoZVal = (TextView) findViewById(R.id.magneto_z_value);
        // initialize them
        accelXVal.setText("0.00");
        accelYVal.setText("0.00");
        accelZVal.setText("0.00");
        orientXVal.setText("0.00");
        orientYVal.setText("0.00");
        orientZVal.setText("0.00");
        magnetoXVal.setText("0.00");
        magnetoYVal.setText("0.00");
        magnetoZVal.setText("0.00");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == mAccelerometer) {
            System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.length);
            mLastAccelerometerSet = true;
        } else if (event.sensor == mMagnetometer) {
            System.arraycopy(event.values, 0, mLastMagnetometer, 0, event.values.length);
            mLastMagnetometerSet = true;
        }
        if (mLastAccelerometerSet && mLastMagnetometerSet) {
            SensorManager.getRotationMatrix(mR, null, mLastAccelerometer, mLastMagnetometer);
            SensorManager.getOrientation(mR, mOrientation);
            float azimuthRads = mOrientation[0];
            float pitchRads = mOrientation[1];
            float rollRads = mOrientation[2];
            float azimuthDegs = (float)(Math.toDegrees(azimuthRads)+360) % 360;
            float pitchDegs = (float)(Math.toDegrees(pitchRads)+360) % 360;
            float rollDegs = (float)(Math.toDegrees(rollRads)+360) % 360;
            accelXVal.setText(String.format("%16f", mLastAccelerometer[0]));
            accelYVal.setText(String.format("%16f", mLastAccelerometer[1]));
            accelZVal.setText(String.format("%16f", mLastAccelerometer[2]));
            orientXVal.setText(String.format("%16f", azimuthDegs));
            orientYVal.setText(String.format("%16f", pitchDegs));
            orientZVal.setText(String.format("%16f", rollDegs));
            magnetoXVal.setText(String.format("%16f", mLastMagnetometer[0]));
            magnetoYVal.setText(String.format("%16f", mLastMagnetometer[1]));
            magnetoZVal.setText(String.format("%16f", mLastMagnetometer[2]));
        }
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mMagnetometer,
                SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_sensors, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
