package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Person;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Show extends MainActivity implements View.OnClickListener {
//    String longitude;
//    String latitude;
//    String country;
//    String province;
//    String city;
//    String county;
//    String street;
//    String hnumber;
//    String bump;
//    private Button back;
//    UserDao userDao;
    List<UserBean> List;
    @Override
    @SuppressLint("Range")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show);
//        back=findViewById(R.id.btn_back);
//        back.setOnClickListener(this);
        List=new ArrayList<UserBean>();
        MySQLiteHelper oh = new MySQLiteHelper(this);
        SQLiteDatabase db = oh.getWritableDatabase();
        Cursor cursor = db.query("user", null, null, null, null, null, null);
//        userDao.querryUser(longitude, latitude, country, province, city, county,street, hnumber, bump);

        while (cursor.moveToNext()){
            String longitude=cursor.getString(cursor.getColumnIndex("longitude"));
            String latitude=cursor.getString(cursor.getColumnIndex("latitude"));
            String country=cursor.getString(cursor.getColumnIndex("country"));
            String province=cursor.getString(cursor.getColumnIndex("province"));
            String city=cursor.getString(cursor.getColumnIndex("city"));
            String county=cursor.getString(cursor.getColumnIndex("county"));
            String street=cursor.getString(cursor.getColumnIndex("street"));
            String hnumber=cursor.getString(cursor.getColumnIndex("hnumber"));
            String bump=cursor.getString(cursor.getColumnIndex("bump"));
        UserBean p=new UserBean(longitude, latitude, country, province, city, county,street, hnumber, bump);
        List.add(p);
        }
        LinearLayout ll=(LinearLayout) findViewById(R.id.ll);
        for(UserBean p:List){
            //1.集合中每有一条数据，就new一个TextView
            TextView tv=new TextView(this);
            //2.把人物的信息设置为文本的内容
            tv.setText(p.toString());
            tv.setTextSize(18);
            //3.把TextView设置成线性布局的子节点
            ll.addView(tv);

        }
    }


//    public void onClick(View view){
//        if(view.getId()==R.id.btn_back){
//            finish();
//        }
//    }
//    public void append(){
//
//    }
}
