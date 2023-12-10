package com.example.moneymanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.logging.Logger;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {
    //класс отвечающий за экран регистрации
    private EditText inputUsername, inputPassword;
    private Button logIn, logInDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);
        Storage.dbManager = new DBManager(this);
        Storage.users = Storage.dbManager.getUsers();
        Storage.notes = Storage.dbManager.getNotes();
//        if(Storage.users.size()==0){
//            Storage.dbManager.insertUserIntoDB("demo user", "12345678");
//            Storage.users = Storage.dbManager.getUsers();
//        }
        initElements();
        CurrenciesAPIService.sendRequest(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Storage.currentUser = null;
    }

    public void initElements() {
        inputUsername = findViewById(R.id.inputUsername);
        inputPassword = findViewById(R.id.inputPassword);
        logIn = findViewById(R.id.logIn);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogInViewModel.logIn(MainActivity.this, inputUsername.getText().toString(), inputPassword.getText().toString());
            }
        });
        logInDemo = findViewById(R.id.logInDemo);
        logInDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Storage.currentUser = LogInViewModel.returnCurrentUser("demo user");
                Intent intent = new Intent(v.getContext(), ManagerActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    public void showIncorrectInput() {
        DialogFragment dialogFragment = new IncorrectInputDialog();
        dialogFragment.show(getSupportFragmentManager(), "");
    }
}