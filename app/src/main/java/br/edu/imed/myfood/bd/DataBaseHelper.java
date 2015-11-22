package br.edu.imed.myfood.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by diogo on 14/11/2015.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    static final int VERSION = 1;

    public DataBaseHelper(Context context) {
        super(context, "myfood", null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL("CREATE TABLE USUARIO(" +
                  " _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                  " NOME VARCHAR(100)," +
                  " EMAIL VARCHAR(100)," +
                  " SENHA VARCHAR(64)" +
                  ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
