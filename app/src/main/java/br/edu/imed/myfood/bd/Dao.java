package br.edu.imed.myfood.bd;

/**
 * Created by diogo on 22/11/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.edu.imed.myfood.model.Usuario;


/**
 * Created by diogo on 22/11/2015.
 */
public class Dao {

    private Context context;


    public Dao(Context context){
        this.context = context;
    }

    private SQLiteDatabase criarConexaoWrite(){
        SQLiteDatabase db = new DataBaseHelper(this.context).getWritableDatabase();
        return db;
    }

    private SQLiteDatabase criarConexaoRead(){
        SQLiteDatabase db = new DataBaseHelper(this.context).getReadableDatabase();
        return db;
    }

    public void salvarUsuario(Usuario usuario) throws Exception{

        SQLiteDatabase db = criarConexaoWrite();

        ContentValues valores = new ContentValues();
        valores.put("nome", usuario.getNome());
        valores.put("email", usuario.getEmail());
        valores.put("senha", usuario.getSenha());
        long rs = db.insert("usuario", null, valores);

        db.close();

        if (rs == -1) {
            throw new Exception("Usuário não cadastrado. -1");
        }

    }

    public Long validarLogin(String email, String senha){

        Long usuarioId = Long.valueOf(0);

        SQLiteDatabase db = criarConexaoRead();

        Cursor cursor =  db.rawQuery("SELECT _id FROM usuario where email = '" + email + "' and senha = '" + senha + "'",  null);

        //move cursor para primeiro registro
        cursor.moveToFirst();


        for (int i = 0; i < cursor.getCount(); i++) {

            usuarioId  = Long.valueOf(String.valueOf(cursor.getInt(0)));

            //move cursor proximo registro
            cursor.moveToNext();
        }

        //fecha cursor
        cursor.close();

        return usuarioId;
    }



}
