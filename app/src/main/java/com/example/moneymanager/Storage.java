package com.example.moneymanager;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Storage {
    static User currentUser;
    static ArrayList<User> users;
    static ArrayList<Note> notes;

    static ArrayList<Note> currentUserNotes = new ArrayList<>();
    static ArrayList<Note> resulOfFindNotes = new ArrayList<>();

    static DBManager dbManager;
    static  boolean isNewUser = false;

    static Categories categories;
    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    static String requestJSONResult;
    static boolean parseResult = false;

    static ArrayList<Currency> currencies = new ArrayList<>();
}
