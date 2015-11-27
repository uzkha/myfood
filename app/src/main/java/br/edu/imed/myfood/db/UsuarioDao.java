package br.edu.imed.myfood.db;

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
public class UsuarioDao {

    private Context context;

    static final String tableUsuario = "usuario";
    static final String ID = "_id";
    static final String NOME = "nome";
    static final String EMAIL = "email";
    static final String SENHA = "senha";

    public UsuarioDao(Context context){
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

    public void salvar(Usuario usuario) throws Exception{

        SQLiteDatabase db = criarConexaoWrite();

        ContentValues valores = new ContentValues();
        valores.put(NOME, usuario.getNome());
        valores.put(EMAIL, usuario.getEmail());
        valores.put(SENHA, usuario.getSenha());
        long rs = db.insert(tableUsuario, null, valores);

        db.close();

        if (rs == -1) {
            throw new Exception("Usuário não cadastrado. -1");
        }

    }

    public Long validarLogin(String email, String senha){

        Long usuarioId = Long.valueOf(0);

        SQLiteDatabase db = criarConexaoRead();

        Cursor cursor =  db.rawQuery("SELECT "  + ID + " FROM " +  tableUsuario + " where "+ EMAIL + " = '" + email + "' and " + SENHA +" = '" + senha + "'",  null);

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
