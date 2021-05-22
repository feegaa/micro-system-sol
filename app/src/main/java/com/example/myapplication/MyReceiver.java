package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SmsBroadcastReceiver";
    private static String msg, phoneNumber;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Intent Received "+intent.getAction());
        if (intent.getAction().equals(SMS_RECEIVED)) {
            Bundle dataBundle = intent.getExtras();
            if (dataBundle != null) {
                Object[] mypdu = (Object[])dataBundle.get("pdus");
                final SmsMessage[] smsMessages = new SmsMessage[mypdu.length];
                for (int i=0; i<mypdu.length; i++) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        String format = dataBundle.getString("format");
                        smsMessages[i] = SmsMessage.createFromPdu((byte[])mypdu[i], format);
                    }
                    else {
                        smsMessages[i] = SmsMessage.createFromPdu((byte[])mypdu[i]);
                    }
                    msg = smsMessages[i].getMessageBody();
                    phoneNumber = smsMessages[i].getOriginatingAddress();
                }
                abortBroadcast();

                SharedPreferences sharedPref = context.getSharedPreferences("SP", 0);
                String saved_phn_num = sharedPref.getString("phone_number", "");

                if (phoneNumber.equals(saved_phn_num)) {
                    abortBroadcast();

                    Toast.makeText(context,
                            phoneNumber + "\nmy phn: "+ saved_phn_num,
                            Toast.LENGTH_LONG).show();
                }


                MainActivity.getInstance().msgReceived(msg, phoneNumber);
            }
        }
    }
//
//    public boolean deleteSms(String smsId) {
//        boolean isSmsDeleted = false;
//        try {
//            mActivity.getContentResolver().delete(Uri.parse("content://sms/" + smsId), null, null);
//            isSmsDeleted = true;
//
//        } catch (Exception ex) {
//            isSmsDeleted = false;
//        }
//        return isSmsDeleted;
//    }

    public static String getMsg() {
        return msg;
    }

    public static String getPhoneNumber() {
        return phoneNumber;
    }
}