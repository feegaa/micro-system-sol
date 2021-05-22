package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private static MainActivity instance;
    private static final int PERMISSION_REQUEST_SMS = 0;
    private TextView temperature_value, env_moist_value, wood_moist_value, param1_value, param2_value;
    private ImageView air_blower_iv, air_sucker_iv, air_humidifier_iv;
    private static String RECEIVER_PHONE_NUMBER = "";
    private final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;

        // check if permission is not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) !=
            PackageManager.PERMISSION_GRANTED) {
            // check if permission is not been granted then check if user denied the permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS)) {
                // Do nothing
            } else {
                // Show a popup for grant permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, PERMISSION_REQUEST_SMS);
            }
        }
        init();
    }

    // After getting result of permission
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResult) {
        switch (requestCode) {
            case PERMISSION_REQUEST_SMS:
            {
                if (grantResult.length > 0 && grantResult[0]==PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this,"دسترسی پذیرفته شد", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(this,"بدون دسترسی های لازم برنامه کار نمی کند", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.phone_input_item) {
            Intent goto_set_phone_number = new Intent(MainActivity.this, SetPhoneNumber.class);
            goto_set_phone_number.putExtra("receiver_phn_num", RECEIVER_PHONE_NUMBER);
            startActivity(goto_set_phone_number);
        } else if (item.getItemId() == R.id.operations_item) {
            Intent goto_operations = new Intent(MainActivity.this, Operations.class);
            startActivity(goto_operations);
        } else if (item.getItemId() == R.id.alarm_item) {
            Intent goto_operations = new Intent(MainActivity.this, AlarmActivity.class);
            startActivity(goto_operations);
        }
        return super.onOptionsItemSelected(item);
    }

    public static MainActivity getInstance() {
        return instance;
    }

    private void init() {
        SharedPreferences sharedPref = getSharedPreferences("SP", 0);
        RECEIVER_PHONE_NUMBER = sharedPref.getString("phone_number", "");
        temperature_value     = this.findViewById(R.id.temperature_value);
        env_moist_value       = this.findViewById(R.id.env_moist_value);
        wood_moist_value      = this.findViewById(R.id.wood_moist_value);
        param1_value          = this.findViewById(R.id.param1_value);
        param2_value          = this.findViewById(R.id.param2_value);
        air_blower_iv         = this.findViewById(R.id.air_blower_iv);
        air_sucker_iv         = this.findViewById(R.id.air_sucker_iv);
        air_humidifier_iv     = this.findViewById(R.id.air_humidifier_iv);
        Button check_request  = this.findViewById(R.id.check_request);
        Button instant_temp   = this.findViewById(R.id.instant_temp);
        Button periodic_graph = this.findViewById(R.id.periodic_graph);
        check_request.setEnabled(false);
        if(checkPermission(Manifest.permission.SEND_SMS)) {
            check_request.setEnabled(true);
            instant_temp.setEnabled(true);
            periodic_graph.setEnabled(true);
            check_request.setOnClickListener(v -> {
                onSend("1");
            });
            instant_temp.setOnClickListener(v -> {
                instantGraph(new String[]{"1", "2"});
//                onSend("2");
            });
            periodic_graph.setOnClickListener(v -> {
                periodicGraph(new String[]{"1", "2"});
//                onSend("3");
            });
        } else
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
    }

    public void instantGraph(String[] msg) {
        Intent goto_instant_graph_activity = new Intent(MainActivity.this, InstantGraphActivity.class);
        goto_instant_graph_activity.putExtra("msg", msg);
        startActivity(goto_instant_graph_activity);
    }

    public void periodicGraph(String[] msg) {
        Intent goto_periodic_graph_activity = new Intent(MainActivity.this, PeriodicGraphActivity.class);
        goto_periodic_graph_activity.putExtra("msg", msg);
        startActivity(goto_periodic_graph_activity);
    }

    public void systemCheck(String[] msg_splitted) {
        if (msg_splitted.length == 9) {
            temperature_value.setText(msg_splitted[1].concat(" \u2103"));
            env_moist_value.setText(msg_splitted[2].concat(" %"));
            wood_moist_value.setText(msg_splitted[3].concat(" %"));
            param1_value.setText(msg_splitted[4].concat(" %"));
            param2_value.setText(msg_splitted[5].concat(" %"));

            if (msg_splitted[6].equals("0"))
                air_sucker_iv.setImageResource(R.drawable.off);
            else
                air_sucker_iv.setImageResource(R.drawable.on);

            if (msg_splitted[7].equals("0"))
                air_blower_iv.setImageResource(R.drawable.off);
            else
                air_blower_iv.setImageResource(R.drawable.on);

            if (msg_splitted[8].equals("0"))
                air_humidifier_iv.setImageResource(R.drawable.off);
            else
                air_humidifier_iv.setImageResource(R.drawable.on);

        } else
            Toast.makeText(this, "فورمت اطلاعات درست نیست!", Toast.LENGTH_LONG).show();
    }

    public void msgReceived(String msg, String phoneNumber) {
        if(phoneNumber.equals(RECEIVER_PHONE_NUMBER)) {
            String[] msg_splitted = msg.split("\n");
            switch (msg_splitted[0]) {
                case "$1":
                    systemCheck(msg_splitted);
                    break;
                case "$2":
                    instantGraph(msg_splitted);
                    break;
                case "$3":
                    periodicGraph(msg_splitted);
                    break;
                default:
                    Toast.makeText(this, "فورمت اطلاعات درست نیست!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public static void setReceiverPhoneNumber(String receiverPhoneNumber) {
        RECEIVER_PHONE_NUMBER = receiverPhoneNumber;
    }

    public static String getReceiverPhoneNumber() {
        return RECEIVER_PHONE_NUMBER;
    }

    public void onSend(String sms_message) {
        if(checkPermission(Manifest.permission.SEND_SMS)) {
            SmsManager sms_manager = SmsManager.getDefault();
            try {
                sms_manager.sendTextMessage(RECEIVER_PHONE_NUMBER, null, sms_message, null, null);
                Toast.makeText(this, "درخواست ارسال شد!", Toast.LENGTH_LONG).show();
            } catch (Exception e){
                Toast.makeText(this, "ارسال موفق نبود!", Toast.LENGTH_LONG).show();
            }
        } else
            Toast.makeText(this, "دسترسی لازم را نداده‌اید!", Toast.LENGTH_LONG).show();
    }


    public boolean checkPermission(String permission) {
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }
}