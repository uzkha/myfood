package br.edu.imed.myfood.db;

/**
 * Created by diogo on 22/11/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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
    static final String PATHIMAGEM = "path_imagem";
    static final String USUARIOID = "usuario_id";

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
        long rs;

        ContentValues valores = new ContentValues();
        valores.put(NOME, receita.getNome());
        valores.put(INGREDIENTE, receita.getIngrediente());
        valores.put(PREPARO, receita.getModoPreparo());
        valores.put(PATHIMAGEM, receita.getPathImagem());
        valores.put(USUARIOID, receita.getUsuarioId());

        if(receita.getId() == null) { // insert

            rs = db.insert(TABLE, null, valores);

        }else{  // update

            String where = "USUARIO_ID = ? AND _ID = ?";
            String argumentos[] = { receita.getUsuarioId().toString(), receita.getId().toString() };

            valores.put(ID, receita.getId());
            rs = db.update(TABLE, valores, where, argumentos);

        }

        db.close();

        if (rs == -1) {
            throw new Exception("Receita não cadastrada. -1");
        }

    }



    public List<Receita> listarReceitas(Long usuarioId){

        SQLiteDatabase db = criarConexaoRead();

        List<Receita> listaReceitas = new ArrayList<Receita>();



        Cursor cursor =  db.rawQuery("SELECT "+ ID + ", " + NOME + ", "
                                              + INGREDIENTE + ", "
                                              + PREPARO + ", "
                                              + PATHIMAGEM +
                                              " FROM " +  TABLE + " where " + USUARIOID  + " = " + usuarioId,  null);


        //move cursor para primeiro registro
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {

            Receita receita = new Receita();

            receita.setId(Long.valueOf(String.valueOf(cursor.getInt(0))));
            receita.setNome(cursor.getString(1));
            receita.setIngrediente(cursor.getString(2));
            receita.setModoPreparo(cursor.getString(3));
            receita.setPathImagem(cursor.getString(4));
            receita.setUsuarioId(usuarioId);

            listaReceitas.add(receita);

            //move cursor proximo registro
            cursor.moveToNext();
        }

        //fecha cursor
        cursor.close();

        return listaReceitas;
    }




    public Receita listarReceita(Long usuarioId, Long id){

        SQLiteDatabase db = criarConexaoRead();

        Receita receita = new Receita();

        Cursor cursor =  db.rawQuery("SELECT "+ ID + ", " + NOME + ", "
                + INGREDIENTE + ", "
                + PREPARO + ", "
                + PATHIMAGEM +
                " FROM " +  TABLE + " where " + USUARIOID  + " = " + usuarioId +
                " AND " + ID + " = " + id,  null);


        //move cursor para primeiro registro
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {


            receita.setId(Long.valueOf(String.valueOf(cursor.getInt(0))));
            receita.setNome(cursor.getString(1));
            receita.setIngrediente(cursor.getString(2));
            receita.setModoPreparo(cursor.getString(3));
            receita.setPathImagem(cursor.getString(4));
            receita.setUsuarioId(usuarioId);

            //move cursor proximo registro
            cursor.moveToNext();
        }

        //fecha cursor
        cursor.close();

        return receita;
    }

    public void deletar(Long usuarioId, Long idReceita) throws Exception{

        SQLiteDatabase db = criarConexaoWrite();
        long rs;

        String where = "USUARIO_ID = ? AND _ID = ?";
        String argumentos[] = { usuarioId.toString(), idReceita.toString() };

        rs = db.delete(TABLE, where, argumentos);

        db.close();

        if (rs == -1) {
            throw new Exception("Receita não excluída. -1");
        }

    }
}
