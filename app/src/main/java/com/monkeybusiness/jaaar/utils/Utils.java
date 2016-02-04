package com.monkeybusiness.jaaar.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.util.Patterns;

import java.util.ArrayList;
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
}
