package com.monkeybusiness.jaaar.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.support.v7.app.AlertDialog;
import android.util.Patterns;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by neeraj on 15/12/15.
 */
public class Utils {

    public final static String REGEX_STR = "^[7-9]{1}[0-9]{9}$";
    public static int classFlag;


    public static String appVersion(Context context) {
        String version = "0";
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            version = String.valueOf(pInfo.versionCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;
    }

    public static ArrayList<String> getEmailIds(Context context)
    {
        ArrayList<String> stringArrayList = new ArrayList<>();
        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(context).getAccounts();
//        Log.e("email_res","==="+new Gson().toJson(accounts));
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches() && !stringArrayList.contains(account.name)) {
                android.util.Log.e("account.name", "=======" + account.name);
                stringArrayList.add(account.name);
            }
        }
        return stringArrayList;
    }

    public static void failureDialog(Context context,String title,String msg) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(msg);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.show();
    }

    public static String formatDateAndTime(String isoDate)
    {
        try {
            Calendar calendar = ISO8601.toCalendar(isoDate);
//            Log.d("calendar","calendar : "+new Gson().toJson(calendar));

            Date date = new Date();
            date.setYear(calendar.get(Calendar.YEAR)-1900);
            date.setMonth(calendar.get(Calendar.MONTH));
            date.setDate(calendar.get(Calendar.DAY_OF_MONTH));
            date.setHours(calendar.get(Calendar.HOUR_OF_DAY));
            date.setMinutes(calendar.get(Calendar.MINUTE));

            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm, EEE dd MMM yyyy");
//            Log.d("calendar","date : "+dateFormat.format(date));

            String dateAndTime = dateFormat.format(date);

            return dateAndTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }
}
