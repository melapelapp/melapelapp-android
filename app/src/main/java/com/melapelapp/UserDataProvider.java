package com.melapelapp;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Patterns;

import java.util.regex.Pattern;

/**
 * Created by gesban on 3/23/2016.
 */
public class UserDataProvider {

   Context context;

   public UserDataProvider(Context context)
   {
       this.context = context;
   }


    public String getEmail()
    {
        String email="";

        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(context).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                email = account.name;
                break;
            }
        }
        return email;
    }

    public String getPhoneNumber()
    {
        String phoneNumber="555555555";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            phoneNumber = telephonyManager.getLine1Number();
        }
        catch(Exception e)
        {
            phoneNumber="6666666";
        }
            return phoneNumber;
    }

}
