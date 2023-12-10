package com.example.moneymanager;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.ParameterMetaData;
import java.sql.SQLTransactionRollbackException;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CurrenciesAPIService {
    private static final OkHttpClient okHttpClient = new OkHttpClient();

    public static void sendRequest(Context context){
        String url = "https://currate.ru/api/?get=rates&pairs=EURUSD,USDRUB,EURRUB,EURBYN,USDBYN,BYNRUB&key=4454c5d37bd6f5d0c0e76c648e7fcade";
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    Storage.requestJSONResult = response.body().string();
                    parseJSON(context);
                }
            }
        });
    }

    public static void parseJSON(Context context) {
        String averageResult = Storage.requestJSONResult;

        averageResult = averageResult.replace("{", "");
        averageResult = averageResult.replace("}", "");
        averageResult = averageResult.replace("\"", "");
        averageResult = averageResult.replace("data:", "");

        String parseRes[] = averageResult.split(",");


        try {
            for (String currentCourse : Constants.COURSES) {
                String subString = "";

                for (String s : parseRes) {
                    if (s.contains(currentCourse)) {
                        subString = s;
                        break;
                    }
                }
                if (subString.equals("")) {
                    return;
                }
                String[] subArryay = subString.split(":");
                String firstCurrencyName = "" + currentCourse.charAt(0) + currentCourse.charAt(1) + currentCourse.charAt(2);
                String secondCurrencyName = "" + currentCourse.charAt(3) + currentCourse.charAt(4) + currentCourse.charAt(5);

                double value = Double.parseDouble(subArryay[1]);

                Storage.currencies.add(new Currency(firstCurrencyName, secondCurrencyName, value));
                Storage.currencies.add(new Currency(secondCurrencyName, firstCurrencyName, 1.0 / value));

            }

            Storage.parseResult = true;
        } catch (NumberFormatException e) {

        }
    }
}
