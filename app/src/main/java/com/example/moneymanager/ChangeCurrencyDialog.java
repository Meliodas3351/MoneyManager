package com.example.moneymanager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

public class ChangeCurrencyDialog extends DialogFragment {
    private Button toEur, toUsd, toByn, toRub;
    private Button saveChanges;

    private static String currentCurrency = Storage.currentUser.getCurrency();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.change_currency_dialog_window);
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        toByn = getDialog().findViewById(R.id.changeToBYN);
        toEur = getDialog().findViewById(R.id.changeToEUR);
        toRub = getDialog().findViewById(R.id.changeToRUB);
        toUsd = getDialog().findViewById(R.id.changeToUSD);

        saveChanges = getDialog().findViewById(R.id.saveCurrencyChanges);

        updateCurrentView();
        toUsd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCurrency = toUsd.getText().toString().toUpperCase();
                updateCurrentView();
            }
        });
        toRub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCurrency = toRub.getText().toString().toUpperCase();
                updateCurrentView();
            }
        });
        toByn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCurrency = toByn.getText().toString().toUpperCase();
                updateCurrentView();
            }
        });
        toEur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCurrency = toEur.getText().toString().toUpperCase();
                updateCurrentView();
            }
        });

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Currency currency = null;
                for(Currency c : Storage.currencies){
                    if(c.getFROM().toUpperCase().equals(Storage.currentUser.getCurrency())&&c.getTO().toUpperCase().equals(currentCurrency)){
                        currency = c;
                        break;
                    }
                }
                if(currency!=null){
                    Storage.currentUser.setCurrency(currentCurrency);
                    for(int k = 0; k < Storage.currentUserNotes.size();k++){
                        Storage.currentUserNotes.get(k).setPrice(Math.round(Storage.currentUserNotes.get(k).getPrice()*currency.getVALUE()*100)/100.0);
                        Storage.dbManager.updateNote(Storage.currentUserNotes.get(k));
                    }
                    Storage.dbManager.updateUserCurrency(Storage.currentUser);
                    ManagerActivity managerActivity = (ManagerActivity) getActivity();
                    managerActivity.updateAll();
                }
                dismiss();
            }
        });

    }

    @SuppressLint("ResourceAsColor")
    private void updateCurrentView(){
        if(currentCurrency.equals("BYN")){
            toByn.setBackgroundResource(R.drawable.blue_rectangle_corners);
            toByn.setTextColor(R.color.white);

            toUsd.setBackgroundResource(R.drawable.light_rectangle_corners);
            toUsd.setTextColor(R.color.black);

            toRub.setBackgroundResource(R.drawable.light_rectangle_corners);
            toRub.setTextColor(R.color.black);

            toEur.setBackgroundResource(R.drawable.light_rectangle_corners);
            toEur.setTextColor(R.color.black);
        }else if(currentCurrency.equals("RUB")){
            toRub.setBackgroundResource(R.drawable.blue_rectangle_corners);
            toRub.setTextColor(R.color.white);

            toUsd.setBackgroundResource(R.drawable.light_rectangle_corners);
            toUsd.setTextColor(R.color.black);

            toByn.setBackgroundResource(R.drawable.light_rectangle_corners);
            toByn.setTextColor(R.color.black);

            toEur.setBackgroundResource(R.drawable.light_rectangle_corners);
            toEur.setTextColor(R.color.black);
        }else if(currentCurrency.equals("USD")){
            toUsd.setBackgroundResource(R.drawable.blue_rectangle_corners);
            toUsd.setTextColor(R.color.white);

            toRub.setBackgroundResource(R.drawable.light_rectangle_corners);
            toRub.setTextColor(R.color.black);

            toByn.setBackgroundResource(R.drawable.light_rectangle_corners);
            toByn.setTextColor(R.color.black);

            toEur.setBackgroundResource(R.drawable.light_rectangle_corners);
            toEur.setTextColor(R.color.black);
        }else if(currentCurrency.equals("EUR")){
            toEur.setBackgroundResource(R.drawable.blue_rectangle_corners);
            toEur.setTextColor(R.color.white);

            toRub.setBackgroundResource(R.drawable.light_rectangle_corners);
            toRub.setTextColor(R.color.black);

            toByn.setBackgroundResource(R.drawable.light_rectangle_corners);
            toByn.setTextColor(R.color.black);

            toUsd.setBackgroundResource(R.drawable.light_rectangle_corners);
            toUsd.setTextColor(R.color.black);
        }
    }
}
