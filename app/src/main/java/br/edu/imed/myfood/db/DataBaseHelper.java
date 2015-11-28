package br.edu.imed.myfood.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by diogo on 14/11/2015.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    static final int VERSION = 1;
    static final String DB_NAME = "myfood";

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL("CREATE TABLE " + UsuarioDao.TABLE +"("
                + UsuarioDao.ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UsuarioDao.NOME + " VARCHAR(100),"
                + UsuarioDao.EMAIL + "  VARCHAR(100),"
                + UsuarioDao.SENHA + " VARCHAR(64)"
                + ")");

        db.execSQL("CREATE TABLE " + ReceitaDao.TABLE +"("
                + ReceitaDao.ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ReceitaDao.NOME + " VARCHAR(100),"
                + ReceitaDao.INGREDIENTE + "  TEXT,"
                + ReceitaDao.PREPARO + " TEXT,"
                + ReceitaDao.PATHIMAGEM + " TEXT,"
                + ReceitaDao.USUARIOID + " INTEGER "
                + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
