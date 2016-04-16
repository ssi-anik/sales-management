package com.example.anik.agent.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.anik.agent.helpers.AppConstant;

import java.util.Map;

public class Database extends SQLiteOpenHelper {
    private final String TAG = "DATABASE";
    Migration migration;
    Seed seed;
    Map<String, String> schema;
    Map<String, String> seeds;

    public Database(Context context) {
        super(context, AppConstant.DATABASE_NAME, null, AppConstant.DATABASE_VERSION);
        migration = new Migration();
        schema = migration.getSchema();
        seed = new Seed();
        seeds = seed.getSeed();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(AppConstant.TAG, "On create method");
        for (Map.Entry<String, String> map : this.schema.entrySet()) {
            String key = map.getKey();
            String query = map.getValue();
            Log.i(AppConstant.TAG, "Creating table " + key + "\n" + query);
            db.execSQL(query);
        }
        for (Map.Entry<String, String> map : this.seeds.entrySet()) {
            String key = map.getKey();
            String query = map.getValue();
            Log.i(AppConstant.TAG, "Seeding Fake data: " + key + "\n" + query);
            //db.execSQL(query);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            this.onCreate(db);
        }
    }
}
