package br.edu.imed.myfood.db;

/**
 * Created by diogo on 22/11/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import br.edu.imed.myfood.model.Receita;


/**
 * Created by diogo on 22/11/2015.
 */
public class ReceitaDao {

    private Context context;

    static final String TABLE = "receita";
    static final String ID = "_id";
    static final String NOME = "nome";
    static final String INGREDIENTE = "ingrediente";
    static final String PREPARO = "preparo";

    public ReceitaDao(Context context){
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

    public void salvar(Receita receita) throws Exception{

        SQLiteDatabase db = criarConexaoWrite();

        ContentValues valores = new ContentValues();
        valores.put(NOME, receita.getNome());
        valores.put(INGREDIENTE, receita.getIngrediente());
        valores.put(PREPARO, receita.getModoPreparo());
        long rs = db.insert(TABLE, null, valores);

        db.close();

        if (rs == -1) {
            throw new Exception("Receita não cadastrada. -1");
        }

    }

}
