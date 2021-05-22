package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

import static android.app.DownloadManager.COLUMN_ID;

public class  DBOpenHelper extends SQLiteOpenHelper {

    public static final String SYSTEM_STATE_TABLE    = "CUSTOMER_TABLE";
    public static final String COLUMN_CREATED_AT     = "CREATED_AT";
    public static final String COLUMN_ID             = "ID";
    public static final String COLUMN_TEMPERATURE    = "TEMPERATURE";
    public static final String COLUMN_ENV_MOIST      = "ENV_MOIST";
    public static final String COLUMN_WOOD_MOIST     = "WOOD_MOIST";
    public static final String COLUMN_PARAM1         = "PARAM1";
    public static final String COLUMN_PARAM2         = "PARAM2";
    public static final String COLUMN_AIR_SUCKER     = "AIR_SUCKER";
    public static final String COLUMN_AIR_BLOWER     = "AIR_BLOWER";
    public static final String COLUMN_AIR_HUMIDIFIER = "AIR_HUMIDIFIER";

    public DBOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    // First design of DB
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table_statements = String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT, %s INT, %s INT, %s INT, %s INT, %s INT, %s BOOL, %s BOOL, %s BOOL, %s DATETIME DEFAULT CURRENT_TIMESTAMP)",
                SYSTEM_STATE_TABLE,
                COLUMN_TEMPERATURE,
                COLUMN_ENV_MOIST,
                COLUMN_WOOD_MOIST,
                COLUMN_PARAM1,
                COLUMN_PARAM2,
                COLUMN_AIR_SUCKER,
                COLUMN_AIR_BLOWER,
                COLUMN_AIR_HUMIDIFIER,
                COLUMN_CREATED_AT);
        db.execSQL(create_table_statements);

    }

    // Upgrades to new version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public boolean add(SystemStateModel state) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cvs = new ContentValues();
        cvs.put(COLUMN_TEMPERATURE, state.getTemperature());
        cvs.put(COLUMN_ENV_MOIST, state.getEnvMoist());
        cvs.put(COLUMN_WOOD_MOIST, state.getWoodMoist());
        cvs.put(COLUMN_PARAM1, state.getParam1());
        cvs.put(COLUMN_PARAM2, state.getParam2());
        cvs.put(COLUMN_AIR_SUCKER, state.isAirSucker());
        cvs.put(COLUMN_AIR_BLOWER, state.isAirBlower());
        cvs.put(COLUMN_AIR_HUMIDIFIER, state.isAirHumidifier());

        long insert = db.insert(SYSTEM_STATE_TABLE, null, cvs);
        return insert != -1;
    }

    public List<HistoryClass> getHistory() {
        List<HistoryClass> history = new ArrayList<>();

        String get_query = String.format("SELECT %s, %s FROM %s", COLUMN_CREATED_AT, COLUMN_ID, SYSTEM_STATE_TABLE);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(get_query, null);
        if (cursor.moveToFirst()) {
            do {
                int row_id = cursor.getInt(0);
                String created_at = cursor.getString(1);

                HistoryClass row = new HistoryClass(created_at, row_id);
                history.add(row);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return history;
    }

}
