package com.example.moneymanager;

import android.content.Context;
import android.content.Intent;

public class LogInViewModel {
    //логика регистрации
    public static void logIn(Context context, String username, String password) {
        User user1 = returnCurrentUser(username);
        if (username.equals("")) {
            MainActivity mainActivity = (MainActivity) context;
            mainActivity.showIncorrectInput();
        } else if (user1 == null) {
            Storage.dbManager.insertUserIntoDB(username, password);
            Storage.users = Storage.dbManager.getUsers();
            Storage.isNewUser = true;
            for (User user : Storage.users) {
                if (user.getUsername().equals(username)) {
                    Storage.currentUser = user;
                    Intent intent = new Intent(context, ManagerActivity.class);
                    context.startActivity(intent);
                    break;
                }
            }
        } else {
            if (user1.getPassword().equals(password)) {
                Storage.currentUser = user1;
                Intent intent = new Intent(context, ManagerActivity.class);
                context.startActivity(intent);
            } else {
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.showIncorrectInput();
            }
        }
    }

    public static User returnCurrentUser(String username) {
        for (User user : Storage.users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
