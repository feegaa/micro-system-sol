package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class Operations extends AppCompatActivity {

    private final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operations);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    public boolean checkPermission(String permission) {
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }

    public void request(View view) {
        if(checkPermission(Manifest.permission.SEND_SMS)){
            if(!MainActivity.getReceiverPhoneNumber().isEmpty()) {
                SmsManager sms_manager = SmsManager.getDefault();
                if(view.getId() == R.id.request_blower) {
                    sms_manager.sendTextMessage(MainActivity.getReceiverPhoneNumber(), null, "2", null, null);
                    Toast.makeText(this, "درخواست فعال کردن دمنده ارسال شد", Toast.LENGTH_LONG).show();
                } else if(view.getId() == R.id.request_sucker) {
                    sms_manager.sendTextMessage(MainActivity.getReceiverPhoneNumber(), null, "3", null, null);
                    Toast.makeText(this, "درخواست فعال کردن مکنده ارسال شد", Toast.LENGTH_LONG).show();
                } else if(view.getId() == R.id.request_humidifier) {
                    sms_manager.sendTextMessage(MainActivity.getReceiverPhoneNumber(), null, "4", null, null);
                    Toast.makeText(this, "درخواست فعال کردن رطوبت ساز ارسال شد", Toast.LENGTH_LONG).show();
                }
            } else
                Toast.makeText(this, "شماره تلفن سامانه ثبت نشده است", Toast.LENGTH_LONG).show();
        } else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
            Toast.makeText(this, "دسترسی لازم داده نشده است", Toast.LENGTH_LONG).show();
        }
    }
}