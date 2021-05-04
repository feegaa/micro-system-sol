//package com.example.myapplication;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import androidx.annotation.Nullable;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class DBOpenHelper extends SQLiteOpenHelper {
//
//    public static final String CUSTOMER_TABLE = "CUSTOMER_TABLE";
//    public static final String COLUMN_CUSTOMER_USERNAME = "CUSTOMER_NAME";
//    public static final String COLUMN_CUSTOMER_AGE = "CUSTOMER_AGE";
//    public static final String COLUMN_IS_ACTIVE = "IS_ACTIVE";
//
//    public DBOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }
//
//
//    // First design of DB
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        String create_table_statements = "CREATE TABLE " +
//                CUSTOMER_TABLE +
//                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                COLUMN_CUSTOMER_USERNAME + " TEXT, " +
//                COLUMN_CUSTOMER_AGE + " INT, " +
//                COLUMN_IS_ACTIVE + " BOOL)";
//
//        db.execSQL(create_table_statements);
//
//    }
//
//    // Upgrades to new version
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
//    }
//
//    public boolean add(CustomerModel customer) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cvs = new ContentValues();
//        cvs.put(COLUMN_CUSTOMER_USERNAME, customer.getUsername());
//        cvs.put(COLUMN_CUSTOMER_AGE, customer.getAge());
//        cvs.put(COLUMN_IS_ACTIVE, customer.isActive());
//
//        long insert = db.insert(CUSTOMER_TABLE, null, cvs);
//        return insert != -1;
//    }
//
//    public List<CustomerModel> getCustomers() {
//        List<CustomerModel> customers = new ArrayList<>();
//
//        String get_query = "SELECT * FROM " + CUSTOMER_TABLE;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(get_query, null);
//        if (cursor.moveToFirst()) {
//            do {
//                int customer_id = cursor.getInt(0);
//                String customer_username = cursor.getString(1);
//                int customer_age = cursor.getInt(2);
//                boolean customer_is_active = cursor.getInt(3) == 1;
//
//                CustomerModel customer = new CustomerModel(customer_id, customer_username, customer_age, customer_is_active);
//                customers.add(customer);
//
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        db.close();
//
//        return customers;
//    }
//
//}



//////////////////////////////////////////////////////////
package com.example.myapplication;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.SwitchCompat;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.Switch;
//import android.widget.Toast;
//
//import java.util.List;
//
//public class MainActivity extends AppCompatActivity {
//
//    private Button add_btn, list_btn;
//    private EditText username_et, age_et;
//    private Switch active_switch;
//    private ListView list_lv;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        init();
//
//        add_btn.setOnClickListener(v -> {
//            CustomerModel customer;
//            try {
//                customer = new CustomerModel(-1,
//                                            username_et.getText().toString(),
//                                            Integer.parseInt(age_et.getText().toString()),
//                                            active_switch.isChecked());
//                Toast.makeText(this, customer.toString(), Toast.LENGTH_SHORT).show();
//            } catch (Exception e) {
//                customer = new CustomerModel(-1,
//                        "error",
//                        0,
//                        false);
//                Toast.makeText(this, "Error creating customer", Toast.LENGTH_SHORT).show();
//            }
//            DBOpenHelper db_helper = new DBOpenHelper(MainActivity.this, "customer.db", null, 1);
//            boolean add = db_helper.add(customer);
//            Toast.makeText(this, "Success: " + add, Toast.LENGTH_SHORT).show();
//
//        });
//
//        list_btn.setOnClickListener(v -> {
//            Toast.makeText(this, "List btn", Toast.LENGTH_SHORT).show();
//            DBOpenHelper db_helper = new DBOpenHelper(MainActivity.this, "customer.db", null, 1);
//            List<CustomerModel> customers_list = db_helper.getCustomers();
//            ArrayAdapter cusomer_aa = new ArrayAdapter<CustomerModel>(MainActivity.this, android.R.layout.simple_list_item_1, customers_list);
//            list_lv.setAdapter(cusomer_aa);
//        });
//    }
//
//    public void init() {
//        this.add_btn  = findViewById(R.id.add_btn);
//        this.list_btn = findViewById(R.id.list_btn);
//        this.username_et = findViewById(R.id.username_et);
//        this.age_et = findViewById(R.id.age_et);
//        this.active_switch = findViewById(R.id.active_switch);
//        this.list_lv = findViewById(R.id.list_lv);
//    }
//
//}