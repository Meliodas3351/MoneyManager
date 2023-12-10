package com.example.moneymanager;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TopMenuPresenter {
    private TextView dateFrom, dateTo;
    private ImageView logOut, magnyfingGlass;

    public TopMenuPresenter(View view) {
        dateFrom = view.findViewById(R.id.dateFrom);
        dateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String date = year + ".";
                                if (monthOfYear < 9) {
                                    date += "0" + Integer.toString(++monthOfYear) + ".";
                                } else {
                                    //use ++month of year because january is 0, and we need 1
                                    date += ++monthOfYear + ".";
                                }
                                if (dayOfMonth < 10) {
                                    date += "0" + Integer.toString(dayOfMonth);
                                } else {
                                    date += Integer.toString(dayOfMonth);
                                }
                                dateFrom.setText(date);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Удалить выбор", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_NEGATIVE) {
                            dateFrom.setText("");
                        }
                    }
                });
                datePickerDialog.show();
            }
        });
        dateTo = view.findViewById(R.id.dateTo);
        dateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String date = year + ".";
                                if (monthOfYear < 9) {
                                    date += "0" + Integer.toString(++monthOfYear) + ".";
                                } else {
                                    date += ++monthOfYear + ".";
                                }
                                if (dayOfMonth < 10) {
                                    date += "0" + Integer.toString(dayOfMonth);
                                } else {
                                    date += Integer.toString(dayOfMonth);
                                }
                                dateTo.setText(date);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Удалить выбор", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_NEGATIVE) {
                            dateTo.setText("");
                        }
                    }
                });
                datePickerDialog.show();
            }
        });
        logOut = view.findViewById(R.id.logOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });
        magnyfingGlass = view.findViewById(R.id.magnyfingGlass);
        magnyfingGlass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                find();
            }
        });

    }

    @SuppressLint("SimpleDateFormat")
    public void updateResultOfFindNotes() {
        Storage.resulOfFindNotes.clear();
//        if(dateTo.getText()==""){
//            dateTo.setText("Yes");
//        }else{
//            dateTo.setText("No");
//
//        }
//        if(dateTo.getText().equals("")){
//            dateTo.setText("Yes");
//        }else{
//            dateTo.setText("No");
//
//        }
        if (dateTo.getText().toString().equals("") && dateFrom.getText().toString().equals("")) {
            Storage.resulOfFindNotes.addAll(Storage.currentUserNotes);
        } else if (dateTo.getText().toString().equals("")  && !dateFrom.getText().toString().equals("") ) {
            Date dateStart;
            try {
                dateStart = new SimpleDateFormat("yyyy.MM.dd").parse(dateFrom.getText().toString());
                for (Note note : Storage.currentUserNotes) {
                    Date dateNote = new SimpleDateFormat("yyyy.MM.dd").parse(note.getDate());
                    if(dateNote.after(dateStart)||dateNote.equals(dateStart)){
                        Storage.resulOfFindNotes.add(note);
                    }
                }
            } catch (ParseException ignored) {

            }

        }else if (!dateTo.getText().toString().equals("")  && dateFrom.getText().toString().equals("") ) {
            Date dateEnd;
            try {
                dateEnd = new SimpleDateFormat("yyyy.MM.dd").parse(dateTo.getText().toString());
                for (Note note : Storage.currentUserNotes) {
                    Date dateNote = new SimpleDateFormat("yyyy.MM.dd").parse(note.getDate());
                    assert dateNote != null;
                    if(dateNote.before(dateEnd)||dateNote.equals(dateEnd)){
                        Storage.resulOfFindNotes.add(note);
                    }
                }
            } catch (ParseException ignored) {

            }

        }else if (!dateTo.getText().toString().equals("")  && !dateFrom.getText().toString().equals("") ) {
            Date dateStart, dateEnd;
            try {
                dateStart = new SimpleDateFormat("yyyy.MM.dd").parse(dateFrom.getText().toString());
                dateEnd = new SimpleDateFormat("yyyy.MM.dd").parse(dateTo.getText().toString());

                for (Note note : Storage.currentUserNotes) {
                    Date dateNote = new SimpleDateFormat("yyyy.MM.dd").parse(note.getDate());
                    assert dateNote != null;
                    if((dateNote.after(dateStart)||dateNote.equals(dateStart))&&(dateNote.before(dateEnd)||dateNote.equals(dateEnd))){
                        Storage.resulOfFindNotes.add(note);
                    }
                }
            } catch (ParseException ignored) {

            }
        }
    }

    public void logOut(){}

    public void find(){}
}
