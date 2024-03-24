package com.example.myapplication;

public class UserBean {
//    longitude text,latitude text,country text,province text,city text,county text,street text,hnumber text,bump text
    private int id;
    private String longitude;
    private String latitude;
    private String country;
    private String province;
    private String city;
    private String county;
    private String street;
    private String hnumber;
    private String bump;

    public UserBean(String longitude,String latitude,String country,String province,String city,String county,String street,String hnumber,String bump){
        super();
        this.longitude=longitude;
        this.latitude=latitude;
        this.country=country;
        this.province=province;
        this.city=city;
        this.county=county;
        this.street=street;
        this.hnumber=hnumber;
        this.bump=bump;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLongitude() {return longitude;}

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
    public String getHnumber() {
        return hnumber;
    }

    public void setHnumber(String hnumber) {
        this.hnumber = hnumber;
    }
    public String getBump() {
        return bump;
    }

    public void setBump(String bump) {
        this.bump = bump;
    }
    @Override
    public String toString() {
        return "经度:"+longitude + "\n" +"纬度:"+ latitude+ "\n" +"国家:"+ country+ "  "+"省份:" + province+"  "+"城市:"+city+"\n"+"城区:"+county+"  "+"街道:"+street+"  "+"门牌号:"+hnumber+"\n"+"颠簸程度:"+bump+"\n" ;
    }

}