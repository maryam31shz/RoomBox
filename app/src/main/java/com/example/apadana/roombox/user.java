package com.example.apadana.roombox;
public class user {
    public static final String TABLE_NAME = "user";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_EMAIL = "Email";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String COLUMN_BANKNUMBER = "BankNumber";
    public static final String COLUMN_PHONENUMBER = "PhoneNumber";
    public static final String COLUMN_CAR = "car";




    private int id;
    private String name;
    private String email;
    private String password;
    private int banknumber;
    private int phonenumber;
    private int car;


    public user() {
    }

    public user(int id, String name, String email , String password ,
                int banknumber , int phonenumber , int car) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.banknumber = banknumber;
        this.phonenumber = phonenumber;
        this.car = car;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getBanknumber() {
        return banknumber;
    }
    public int getCar() {
        return car;
    }
    public int getPhonenumber() {
        return phonenumber;
    }

    public void setBanknumber(int banknumber) {
        this.banknumber = banknumber;
    }

    public void setPhonenumber(int phonenumber) {
        this.phonenumber = phonenumber;
    }
    public void setCar(int car) {
        this.car = car;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}