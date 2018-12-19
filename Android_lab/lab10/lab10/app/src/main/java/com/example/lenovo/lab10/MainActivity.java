package com.example.lenovo.lab10;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity {

    SensorManager mSensorManager = null;
    Sensor mMagneticSensor = null;
    Sensor mAccelerometerSensor = null;

    private SensorManager sensorManager;
    private Vibrator vibrator;

    private static final String TAG = "TestSensorActivity";
    private static final int SENSOR_SHAKE = 10;


    float[] accValues = null;
    float[] magValues = null;

    private LocationManager locationManager;
    private String locationProvider;

    TextView JD = null;
    TextView WD = null;
    ArrowView arrowView = null;
    TextView degree = null;
    TextView count = null;

    private SensorEventListener mSensorEventListener = new SensorEventListener() {

        long lastShakeTime = 0;
        @Override
        public void onSensorChanged(SensorEvent event) {

            switch (event.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    // do something about values of accelerometer
                    accValues = event.values;
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    // do something about values of magnetic field
                    magValues = event.values;
                    break;
                default:
                    break;
            }
            if (accValues != null && magValues != null) {
                float[] values = new float[3];
                float[] R = new float[9];
                SensorManager.getRotationMatrix(R, null, accValues, magValues);
                SensorManager.getOrientation(R, values);
                float newRotationDegree = (float) -Math.toDegrees(values[0]);
                arrowView.onUpdateRotation(newRotationDegree);
                degree.setText(String.valueOf(arrowView.getCurRotation()));
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
    };


    LocationListener locationListener =  new LocationListener() {

        /**
         * GPS状态变化时触发
         */
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                //GPS状态为可见时
                case LocationProvider.AVAILABLE:
                    Log.i(TAG, "当前GPS状态为可见状态");
                    break;
                //GPS状态为服务区外时
                case LocationProvider.OUT_OF_SERVICE:
                    Log.i(TAG, "当前GPS状态为服务区外状态");
                    break;
                //GPS状态为暂停服务时
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.i(TAG, "当前GPS状态为暂停服务状态");
                    break;
            }
        }

        /**
         * GPS开启时触发
         */
        public void onProviderEnabled(String provider) {
            Location location=locationManager.getLastKnownLocation(provider);
        }

        /**
         * GPS禁用时触发
         */
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onLocationChanged(Location location) {
            Log.i("test", "location changed");
            showLocation(location);
        }
    };

    /**
     * 重力感应监听
     */
    private SensorEventListener sensorEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            // 传感器信息改变时执行该方法
            float[] values = event.values;
            float x = values[0]; // x轴方向的重力加速度，向右为正
            float y = values[1]; // y轴方向的重力加速度，向前为正
            float z = values[2]; // z轴方向的重力加速度，向上为正
            //Log.i(TAG, "x轴方向的重力加速度" + x +  "；y轴方向的重力加速度" + y +  "；z轴方向的重力加速度" + z);
            // 一般在这三个方向的重力加速度达到40就达到了摇晃手机的状态。
            int medumValue = 19;
            if (Math.abs(x) > medumValue || Math.abs(y) > medumValue || Math.abs(z) > medumValue) {
                vibrator.vibrate(200);
                int cnt = Integer.valueOf(count.getText().toString()).intValue() + 1;
                count.setText(String.valueOf(cnt));
                Log.i(TAG, String.valueOf(cnt));
                //Message msg = new Message();
                //msg.what = SENSOR_SHAKE;
               // handler.sendMessage(msg);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    /**
     * 动作执行

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SENSOR_SHAKE:
                    Toast.makeText(MainActivity.this, "检测到摇晃，执行操作！", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "检测到摇晃，执行操作！");
                    break;
            }
        }

    };
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mMagneticSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        JD = (TextView)findViewById(R.id.JD);
        WD = (TextView)findViewById(R.id.WD);
        arrowView = (ArrowView)findViewById(R.id.arrow);
        degree = (TextView)findViewById(R.id.degree);
        count = (TextView)findViewById(R.id.count);


        Log.v("test", "test0");
        mSensorManager.registerListener(mSensorEventListener, mAccelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorEventListener, mMagneticSensor,SensorManager.SENSOR_DELAY_NORMAL);

        Log.v("test", "test1");
        //获取地理位置管理器
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //获取所有可用的位置提供器
        List<String> providers = locationManager.getProviders(true);
        Log.v("test", String.valueOf(providers.size()));
        if(providers.contains(LocationManager.GPS_PROVIDER)){
            //如果是GPS
            locationProvider = LocationManager.GPS_PROVIDER;
        }else if(providers.contains(LocationManager.NETWORK_PROVIDER)){
            //如果是Network
            locationProvider = LocationManager.NETWORK_PROVIDER;
        }else{
            Toast.makeText(this, "没有可用的位置提供器", Toast.LENGTH_SHORT).show();
            return ;
        }

        Log.v("test", "test2:"+locationProvider);

        if (locationProvider != null) {
            locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
        }
        //获取Location
        Location location = locationManager.getLastKnownLocation(locationProvider);
        if(location!=null){
            //不为空,显示地理位置经纬度
            showLocation(location);
        }

    }

    private void showLocation(Location location){
        JD.setText(String.valueOf(location.getLongitude()) );
        WD.setText(String.valueOf(location.getLatitude()));
    }


    @Override
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(mSensorEventListener, mMagneticSensor,
                SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(mSensorEventListener, mAccelerometerSensor,
                SensorManager.SENSOR_DELAY_GAME);
        if (locationProvider != null) {
            locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
        }
        if (sensorManager != null) {// 注册监听器
            sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
                         // 第一个参数是Listener，第二个参数是所得传感器类型，第三个参数值获取传感器信息的频率
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(mSensorEventListener);
        locationManager.removeUpdates(locationListener);
        if (sensorManager != null) {// 取消监听器
            sensorManager.unregisterListener(sensorEventListener);
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mSensorManager.unregisterListener(mSensorEventListener);
        locationManager.removeUpdates(locationListener);
    }

}
