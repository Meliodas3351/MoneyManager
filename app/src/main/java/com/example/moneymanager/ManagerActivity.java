package com.example.moneymanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ManagerActivity extends AppCompatActivity {
    //экран добавления затрат

    private LinearLayout products, cafe, dosug, transport, health, family, gifts, buys;
    private TextView productsPrice, cafePrice, dosugPrice, transportPrice, healthPrice, familyPrice, giftsPrice, buysPrice;

    private TextView totalSpending;
    private Button changeCurrency;

    private RecyclerView recyclerView;
    private SpendingAdapter spendingAdapter;
    private TopMenuPresenter topMenuPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        if(Storage.isNewUser){
            DialogFragment dialogFragment = new NewUserDialog();
            dialogFragment.show(getSupportFragmentManager(),"");
            Storage.isNewUser = false;
        }
        initElements();
        Storage.currentUserNotes = new ArrayList<>();
        updateAll();
    }

    public void updateAll(){
        updateCurrentUserSpending();
        topMenuPresenter.updateResultOfFindNotes();
        updateSpending();
        setAdapter();
    }

    public void initElements() {
        topMenuPresenter = new TopMenuPresenter(getWindow().getDecorView()){
            @Override
            public void logOut() {
                ManagerActivity.super.finish();
            }

            @Override
            public void find() {
                updateAll();
            }
        };
        recyclerView = findViewById(R.id.resultNotes);

        products = findViewById(R.id.eda);
        products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Storage.categories = Categories.PRODUCTS;
                showAddNoteDialog();
            }
        });
        productsPrice = findViewById(R.id.edaPrice);
        cafe = findViewById(R.id.cafe);
        cafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Storage.categories = Categories.CAFE;
                showAddNoteDialog();
            }
        });
        cafePrice = findViewById(R.id.cafePrice);
        dosug = findViewById(R.id.dosug);
        dosug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Storage.categories = Categories.LEISURE;
                showAddNoteDialog();
            }
        });
        dosugPrice = findViewById(R.id.dosugPrice);
        transport = findViewById(R.id.transport);
        transport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Storage.categories = Categories.TRANSPORT;
                showAddNoteDialog();
            }
        });
        transportPrice = findViewById(R.id.transportPrice);
        health = findViewById(R.id.zdorovie);
        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Storage.categories = Categories.HEALTH;
                showAddNoteDialog();
            }
        });
        healthPrice = findViewById(R.id.zdoroviePrice);
        family = findViewById(R.id.semya);
        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Storage.categories = Categories.FAMILY;
                showAddNoteDialog();
            }
        });
        familyPrice = findViewById(R.id.semyaPrice);
        gifts = findViewById(R.id.podarki);
        gifts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Storage.categories = Categories.GIFTS;
                showAddNoteDialog();
            }
        });
        giftsPrice = findViewById(R.id.podarkiPrice);
        buys = findViewById(R.id.pokupki);
        buys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Storage.categories = Categories.PURCHASES;
                showAddNoteDialog();
            }
        });
        buysPrice = findViewById(R.id.pokupkiPrice);
        totalSpending = findViewById(R.id.totalSpending);
        changeCurrency = findViewById(R.id.changeCurrency);
        changeCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Storage.parseResult){
                    DialogFragment dialogFragment = new ChangeCurrencyDialog();
                    dialogFragment.show(getSupportFragmentManager(), null);
                }else{
                    DialogFragment dialogFragment = new FatalCurrencyDownload();
                    dialogFragment.show(getSupportFragmentManager(), null);
                }
            }
        });
    }

    public void showAddNoteDialog() {
        DialogFragment dialogFragment = new AddNoteDialog();
        dialogFragment.show(getSupportFragmentManager(), null);
    }

    public void updateCurrentUserSpending() {
        Storage.currentUserNotes.clear();
        String id = Integer.toString(Storage.currentUser.getId());;
        for (Note note : Storage.notes) {
            if (note.getUserId().equals(id)) {
                Storage.currentUserNotes.add(note);
            }
        }
    }



    public void updateSpending() {
        updateSpendingByCategories(productsPrice, Categories.PRODUCTS);
        updateSpendingByCategories(cafePrice, Categories.CAFE);
        updateSpendingByCategories(transportPrice, Categories.TRANSPORT);
        updateSpendingByCategories(buysPrice, Categories.PURCHASES);
        updateSpendingByCategories(familyPrice, Categories.FAMILY);
        updateSpendingByCategories(giftsPrice, Categories.GIFTS);
        updateSpendingByCategories(dosugPrice, Categories.LEISURE);
        updateSpendingByCategories(healthPrice, Categories.HEALTH);
        double total = 0.0;
        for (Note note : Storage.resulOfFindNotes) {
            total += note.getPrice();
        }
        totalSpending.setText((Math.round(total * 100) / 100.0) + " "+Storage.currentUser.getCurrency());
    }

    public void updateSpendingByCategories(TextView textView, Categories categories) {
        double totalSpending = 0.0;
        for (Note note : Storage.resulOfFindNotes) {
            if (categories.getCategoryName().equals(note.getCategory())) {
                totalSpending += note.getPrice();
            }
        }
        textView.setText(Double.toString(Math.round(totalSpending * 100) / 100.0) + " "+Storage.currentUser.getCurrency());
    }

    public void updateSomeCategory() {
        switch (Storage.categories) {
            case CAFE: {
                updateSpendingByCategories(cafePrice, Categories.CAFE);
                break;
            }
            case GIFTS: {
                updateSpendingByCategories(giftsPrice, Categories.GIFTS);
                break;
            }
            case FAMILY: {
                updateSpendingByCategories(familyPrice, Categories.FAMILY);
                break;
            }
            case HEALTH: {
                updateSpendingByCategories(healthPrice, Categories.HEALTH);
                break;
            }
            case LEISURE: {
                updateSpendingByCategories(dosugPrice, Categories.LEISURE);
                break;
            }
            case PRODUCTS: {
                updateSpendingByCategories(productsPrice, Categories.PRODUCTS);
                break;
            }
            case PURCHASES: {
                updateSpendingByCategories(buysPrice, Categories.PURCHASES);
                break;
            }
            case TRANSPORT: {
                updateSpendingByCategories(transportPrice, Categories.TRANSPORT);
                break;
            }

        }
    }
    public void setAdapter() {
        spendingAdapter = new SpendingAdapter(this, Storage.resulOfFindNotes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(spendingAdapter);
    }
}