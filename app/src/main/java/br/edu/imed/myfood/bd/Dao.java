package br.edu.imed.myfood.bd;

/**
 * Created by diogo on 22/11/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import br.edu.imed.myfood.model.Usuario;


/**
 * Created by diogo on 22/11/2015.
 */
public class Dao {

    private Context context;


    public Dao(Context context){
        this.context = context;
    }

    private SQLiteDatabase criarConexao(){
        SQLiteDatabase db = new DataBaseHelper(this.context).getWritableDatabase();
        return db;
    }


    public void salvarUsuario(Usuario usuario) throws Exception{

        SQLiteDatabase db = criarConexao();

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
}
