package br.edu.imed.myfood;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by diogo on 07/11/2015.
 */
public class AbstractActivity extends AppCompatActivity {

    final String TAG = this.getClass().getSimpleName();

    public static final String PREFS_NAME = "MyFoodPrefsFile";

    void showMessage(String s, int tempo){
        Toast.makeText(this, s, tempo).show();
    }


    /**
     * @param id
     * Recebe o ID do usuário para setar na preferencia compartilhada o ID do usuário logado na aplicação
     */
    void setarUsuarioSessao(Long id) {

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong("usuarioId", id);

        //Confirma a gravação dos dados
        editor.commit();

    }


    /**
     * @return
     * Busca o usuário logado na aplicação. Retorna 0 caso não existe usuário logado.
     */
    Long buscarUsuarioSessao(){

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        Long usuarioid =  settings.getLong("usuarioId", 0);

        return usuarioid;


    }

}
