package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SetPhoneNumber extends AppCompatActivity {

    private EditText phone_number_et;
    private TextView phn_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_phone_number);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init() {
        Toast.makeText(SetPhoneNumber.this,"init", Toast.LENGTH_LONG).show();
        SharedPreferences sharedPref = getSharedPreferences("SP", 0);
        String saved_phn_num = sharedPref.getString("phone_number", "");
        Toast.makeText(this, "phone number: "+  saved_phn_num, Toast.LENGTH_LONG).show();
        String message = "شماره ثبت شده: " + saved_phn_num;
        phone_number_et = this.findViewById(R.id.phone_number_et);
        phn_msg         = this.findViewById(R.id.phone_number_tv);
        if(!saved_phn_num.isEmpty())
            phn_msg.setText(message);

        Button save_btn  = this.findViewById(R.id.phone_number_submit_btn);

        save_btn.setOnClickListener(v -> {
            String phone_number = phone_number_et.getText().toString();
            if (phone_number.length() == 10 &&
                    phone_number.startsWith("9") &&
                    TextUtils.isDigitsOnly(phone_number)) {
                Toast.makeText(SetPhoneNumber.this,"in if", Toast.LENGTH_LONG).show();
                String phn_num = "+98"+phone_number;
                MainActivity.setReceiverPhoneNumber(phn_num);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("phone_number", phn_num);
                boolean commit = editor.commit();
                String phone_number_text ="شماره تلفن ثبت شده: "+MainActivity.getReceiverPhoneNumber();
                phn_msg.setText(phone_number_text);
                Toast.makeText(SetPhoneNumber.this, "شماره تلفن ثبت شد", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(SetPhoneNumber.this,"شماره وارد شده معتبر نیست", Toast.LENGTH_LONG).show();
        });
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}