package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
public class MainActivity extends AppCompatActivity implements AMapLocationListener, SensorEventListener, View.OnClickListener {
    private SensorManager mSensorMgr;
    private  TextView tvx;
    private  TextView tvy;
    private  TextView tvz;
    private  TextView judge; String longitude;
    String latitude;
    String country;
    String province;
    String city;
    String county;
    String street;
    String hnumber;
    String bump;
//    private AMapLocation aMapLocation;


    float token;
    Button bt,bt_stop,bt_show,bt_clear;
//    Button back;
    UserDao userDao;
    private static final int MY_PERMISSIONS_REQUEST_CALL_LOCATION = 1;
    public AMapLocationClient mlocationClient;
    public AMapLocationClientOption mLocationOption = null;
    public TextView location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        userDao = new UserDao(this);

        location = (TextView) findViewById(R.id.TV_LOACATION);
        AMapLocationClient.updatePrivacyShow(this,true,true);
        AMapLocationClient.updatePrivacyAgree(this,true);

        //检查版本是否大于M
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_CALL_LOCATION);
            } else {
                //"权限已申请";
                showLocation();
            }
        }

        bt=findViewById(R.id.btn_start);
        bt.setOnClickListener(this);
        bt_stop=findViewById(R.id.btn_end);
        bt_stop.setOnClickListener(this);
        bt_show=findViewById(R.id.btn_show);
        bt_show.setOnClickListener(this);
        bt_clear=findViewById(R.id.btn_clear);
        bt_clear.setOnClickListener(this);
//        back=findViewById(R.id.btn_back);
//        back.setOnClickListener(this);

        tvx=findViewById(R.id.tvx);
        tvy=findViewById(R.id.tvy);
        tvz=findViewById(R.id.tvz);

        judge=findViewById(R.id.judge);

        mSensorMgr=(SensorManager)getSystemService(Context.SENSOR_SERVICE);



    }

    protected void onPause()
    {
        super.onPause();
        mSensorMgr.unregisterListener(this);
    }

    protected void onResume()
    {
        super.onResume();
    }

//    public void onSensorChanged(SensorEvent event) {
//        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
//            float[] values = event.values;
//
//            tvx.setText("ACC_X: " + String.format("%.8f", values[0]));
//            tvy.setText("ACC_Y: " + String.format("%.8f", values[1]));
//            tvz.setText("ACC_Z: " + String.format("%.8f", values[2]));
//
//            token = values[0] * values[0] + values[1] * values[1] + values[2] * values[2];
//            if ()
//
//        }
//    }
public void onSensorChanged(SensorEvent event) {
    if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
        float[] values = event.values;
            tvx.setHint("ACC_X: " + String.format("%.5f", values[0]));
            tvy.setHint("ACC_Y: " + String.format("%.5f", values[1]));
            tvz.setHint("ACC_Z: " + String.format("%.5f", values[2]));

        token = values[0] * values[0] + values[1] * values[1] + values[2] * values[2];

//            if ((token > 120 && token <= 150) || (token > 50 && token <= 80)) {
//
////                String username = (String)token;
////
////                    longitude=String.valueOf(aMapLocation.getLatitude());
////                    latitude=String.valueOf(aMapLocation.getLongitude());
////                    country=String.valueOf(aMapLocation.getCountry());
////                    province = String.valueOf(aMapLocation.getProvince());
////                    city=String.valueOf(aMapLocation.getCity());
////                    county=String.valueOf(aMapLocation.getDistrict());
////                    street=String.valueOf(aMapLocation.getStreet());
////                    hnumber=String.valueOf(aMapLocation.getStreetNum());
//                   longitude="  a";
//                    latitude="  a";
//                    country="  a";
//                    province = "  a";
//                    city="  a";
//                    county="  a";
//                    street="  a";
//                    hnumber="  a";
//                    bump = String.valueOf(token);
////                String password = etUpwd.getText().toString();
////        String password="  a";
//                    boolean flag = userDao.insertUser(longitude ,latitude ,country ,province ,city ,county ,street ,hnumber ,bump );
//                    if (flag){
//                        judge.setText("1");
//                    }
//            }
    }
//    return token;
}
    public void onAccuracyChanged(Sensor sensor,int accuracy)
    {//不用处理，空着就行
        return;
    }



    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btn_start)
        {


            mlocationClient.setLocationListener(this);
            //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationOption.setInterval(1000);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            //启动定位
            mlocationClient.startLocation();
            mSensorMgr.unregisterListener(this,mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
            mSensorMgr.registerListener(this,
                    mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                    SensorManager.SENSOR_DELAY_NORMAL);


            return;
        }
        if(view.getId()==R.id.btn_end)
        {
            mSensorMgr.unregisterListener(this);
            mlocationClient.stopLocation();
            return;
        }
        if(view.getId()==R.id.btn_show)
        {
            Intent intent = new Intent();
            intent.setClass(this, Show.class);
            startActivity(intent);
//            String password="  a";-0
//            String username = String.valueOf(token);
//            startActivity(new Intent(MainActivity.this,show.class));
//            UserBean userBean = userDao.querryUser(username,password);
//            String test= userBean.getUsername();
//            judge.setText(test);
//            Toast.makeText(getApplicationContext(),"登录失败",Toast.LENGTH_SHORT).show();
//            setContentView(R.layout.show);


        }
        if(view.getId()==R.id.btn_clear){
            userDao.deleteUser();
        }
//        if(view.getId()==R.id.btn_back){
//            setContentView(R.layout.activity_main);
//        }

    }





    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //"权限已申请"
                showLocation();
            } else {
                Toast.makeText(getBaseContext(),"权限已拒绝,不能定位",Toast.LENGTH_SHORT).show();

            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    // TODO:
    private void showLocation() {
        try {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            mlocationClient.setLocationListener(this);
            //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationOption.setInterval(1000);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            //启动定位
            mlocationClient.startLocation();
        } catch (Exception e) {

        }
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        try {
            if (amapLocation != null) {
                StringBuffer dz = new StringBuffer();
                if (amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
//                    Toast.makeText(getBaseContext(),"定位成功",Toast.LENGTH_SHORT).show();
                    //获取当前定位结果来源，如网络定位结果，详见定位类型表
                    dz.append("定位类型:  "+ amapLocation.getLocationType() + "\n");

                    dz.append("获取纬度:  "+ amapLocation.getLatitude() + "\n");
                    dz.append("获取经度:  "+ amapLocation.getLongitude() + "\n");
                    dz.append("获取精度信息:  "+ amapLocation.getAccuracy() + "\n");

                    //如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    dz.append("地址:  "+ amapLocation.getAddress() + "\n");
                    dz.append("国家信息:  "+ amapLocation.getCountry() + "\n");
                    dz.append("省信息:  "+ amapLocation.getProvince() + "\n");
                    dz.append("城市信息:  "+ amapLocation.getCity() + "\n");
                    dz.append("城区信息:  "+ amapLocation.getDistrict() + "\n");
                    dz.append("街道信息:  "+ amapLocation.getStreet() + "\n");
                    dz.append("街道门牌号信息:  "+ amapLocation.getStreetNum() + "\n");
                    dz.append("城市编码:  "+ amapLocation.getCityCode() + "\n");
                    dz.append("地区编码:  "+ amapLocation.getAdCode() + "\n");
                    dz.append("获取当前定位点的AOI信息:  "+ amapLocation.getAoiName() + "\n");
                    dz.append("获取GPS的当前状态:  "+ amapLocation.getGpsAccuracyStatus() + "\n");
                    //获取定位时间
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(amapLocation.getTime());
                    dz.append("获取定位时间:  "+  df.format(date)+ "\n");
                    location.setText(dz);
                    // 停止定位
                    if ((token > 120 ) || (token >0 &&token <= 60)) {

//                String username = (String)token;

                    longitude=String.valueOf(amapLocation.getLatitude());
                    latitude=String.valueOf(amapLocation.getLongitude());
                    country=String.valueOf(amapLocation.getCountry());
                    province = String.valueOf(amapLocation.getProvince());
                    city=String.valueOf(amapLocation.getCity());
                    county=String.valueOf(amapLocation.getDistrict());
                    street=String.valueOf(amapLocation.getStreet());
                    hnumber=String.valueOf(amapLocation.getStreetNum());
//颠簸算法
                        if(token>=200) {
                            bump ="非常颠簸";
                        }
                        else if (token<20) {
                            bump="非常颠簸";
                        }
                        else {
                            bump="颠簸";
                        }


                        boolean flag = userDao.insertUser(longitude ,latitude ,country ,province ,city ,county ,street ,hnumber ,bump );
                        if (flag){
                            Toast.makeText(getApplicationContext(),"存入数据成功",Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
            else{
                Toast.makeText(getBaseContext(),"定位失败",Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        // 停止定位
        if (null != mlocationClient) {
            mlocationClient.stopLocation();
        }
        mSensorMgr.unregisterListener(this);
    }
    /**
     * 销毁定位
     */
    private void destroyLocation() {
        if (null != mlocationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            mlocationClient.onDestroy();
            mlocationClient = null;
        }
    }

    @Override
    protected void onDestroy() {
        destroyLocation();
        super.onDestroy();
    }

}