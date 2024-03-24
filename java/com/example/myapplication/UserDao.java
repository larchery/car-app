package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * 数据库访问对象
 * DAO:DATEBASE ACCESS OBJECT
 * 是控制层于数据库交互的中间层，用于做数据库的增删改查具体实现
 * */
public class UserDao {
    String longitude;
    String latitude;
    String country;
    String province;
    String city;
    String county;
    String street;
    String hnumber;
    String bump;
    //SQLiteDatabase对象封装了所有SQLite的增删改查语句的操作方法，让开发者直接调用就行
    private SQLiteDatabase sqLiteDatabase;

    public UserDao(Context context){
        //初始化刚刚写的MySQLiteHelper对象
        MySQLiteHelper mySQLiteHelper = new MySQLiteHelper(context);
        //获取sqLiteDatabase对象
        sqLiteDatabase = mySQLiteHelper.getWritableDatabase();
    }

    /**
     * 插入一条记录进入user表
     * insert into user("username","password") values("zhangsan","123456");
     * */
    public boolean insertUser(String longitude,String latitude,String country,String province,String city,String county,String street,String hnumber,String bump){
        ContentValues values = new ContentValues();
        values.put("longitude",longitude);
        values.put("latitude",latitude);
        values.put("country",country);
        values.put("province",province);
        values.put("city",city);
        values.put("county",county);
        values.put("street",street);
        values.put("hnumber",hnumber);
        values.put("bump",bump);

        //第一个是表名，第二个null，第三个是相当于sql插入语句的values
        //id用于判断是否插入成功： 如果大于0则表示插入了至少一条数据，否则插入失败
        long id = sqLiteDatabase.insert("user",null,values);
        return id>0?true:false;
    }

    /**
     * 查询记录
     * select * from user where username = "zhangsan";
     * */
    @SuppressLint("Range")
    public UserBean querryUser(String longitude, String latitude, String country, String province, String city, String county, String street, String hnumber, String bump){
//        longitude,latitude,country,province,city,county,street,hnumber,bump
        Cursor cursor = sqLiteDatabase.query("user",new String[]{"longitude","latitude","country","province","city","county","street","hnumber","bump"},"longitude=? and latitude=? and country=?and province=?and city=?and county=?and street=?and hnumber=?and bump=?",new String[]{longitude,latitude,country,province,city,county,street,hnumber,bump},null,null,null);

        UserBean userBean = new UserBean(longitude, latitude, country, province, city, county,street, hnumber, bump);
        while (cursor.moveToNext()){
            userBean.setLongitude(cursor.getString(cursor.getColumnIndex("longitude")));
            userBean.setLatitude(cursor.getString(cursor.getColumnIndex("latitude")));
            userBean.setCountry(cursor.getString(cursor.getColumnIndex("country")));
            userBean.setProvince(cursor.getString(cursor.getColumnIndex("province")));
            userBean.setCity(cursor.getString(cursor.getColumnIndex("city")));
            userBean.setCounty(cursor.getString(cursor.getColumnIndex("county")));
            userBean.setStreet(cursor.getString(cursor.getColumnIndex("street")));
            userBean.setHnumber(cursor.getString(cursor.getColumnIndex("hnumber")));
            userBean.setBump(cursor.getString(cursor.getColumnIndex("bump")));
            Log.e("tag", userBean.getLongitude() + "|" + userBean.getLatitude() + "|" + userBean.getCountry() + "|" + userBean.getProvince() + "|" + userBean.getCity() + "|" + userBean.getCounty() + "|" + userBean.getStreet() + "|" + userBean.getHnumber() + "|" + userBean.getBump());
            //userBean.setId(cursor.getInt(cursor.getColumnIndex("id")));
//            userBean.setUsername(cursor.getString(cursor.getColumnIndex("username")));
//            userBean.setPassword(cursor.getString(cursor.getColumnIndex("password")));
//            Log.e("tag",userBean.getId()+"|"+userBean.getUsername()+"|"+userBean.getPassword());
//            System.out.println(1);
        }
        cursor.close();
        return userBean;
    }

    /**
     * 修改数据库表记录
     * update user set password = 123123 where username = zhangsan
     * */
    public boolean updateUser(String longitude, String latitude, String country, String province, String city, String county, String street, String hnumber, String bump){

//        ContentValues values = new ContentValues();
//        values.put("password",newpassword);
//        long id = sqLiteDatabase.update("user",values,"username = ? ",new String[]{username});
//        flag = id > 0?true : false;
        return true;
    }



    /**
     * 删除
     * delete from user where username = "xxx"
     * */
    public boolean deleteUser(){
        sqLiteDatabase.delete("user", null, null);
        //        long id = sqLiteDatabase.delete("user","username = ?",new String[]{username});
        return true;
    }
}