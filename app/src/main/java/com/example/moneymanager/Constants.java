package com.example.moneymanager;

public class Constants {
    public static final String USD_RUB ="USDRUB";
    public static final String USD_BYN ="USDBYN";
    public static final String EUR_RUB ="EURRUB";
    public static final String EUR_USD ="EURUSD";
    public static final String EUR_BYN ="EURBYN";
    public static final String BYN_RUB ="BYNRUB";


    public static String[] COURSES = {USD_BYN, USD_RUB, EUR_BYN,EUR_BYN,EUR_RUB,EUR_USD,BYN_RUB};

    //константные строки (названия таблиц, БД, запросы создания и удаления таблиц
    public static final String DB_NAME = "money.db";
    public static final int DB_VERSION = 1;

    public static String USER_TABLE_NAME = "users";
    public static String USER_ID = "_id";
    public static String USER_USERNAME = "username";
    public static String USER_PASSWORD = "password";
    public static String USER_CURRENCY = "currency";

    public static final String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + USER_TABLE_NAME + " ("
            + USER_ID + " INTEGER PRIMARY KEY, " + USER_USERNAME + " TEXT, " + USER_PASSWORD + " TEXT, " + USER_CURRENCY + " TEXT);";

    public static final String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + USER_TABLE_NAME + ";";

    public static String NOTE_TABLE_NAME = "notes";
    public static String NOTE_ID = "_id";
    public static String NOTE_SUM = "price";
    public static String NOTE_USER = "userId";
    public static String NOTE_CATEGORY = "category";
    public static String NOTE_DATE = "date";

    public static final String CREATE_NOTES_TABLE = "CREATE TABLE IF NOT EXISTS " + NOTE_TABLE_NAME + " (" + NOTE_ID
            + " INTEGER PRIMARY KEY, " + NOTE_SUM + " TEXT, " + NOTE_USER + " TEXT, " + NOTE_CATEGORY +" TEXT, " + NOTE_DATE + " TEXT);";

    public static final String DROP_NOTES_TABLE = "DROP TABLE IF EXISTS " + NOTE_TABLE_NAME + ";";
}
