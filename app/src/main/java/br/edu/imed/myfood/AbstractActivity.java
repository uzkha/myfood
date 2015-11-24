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


    void setarUsuarioSessao(Long id) {

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong("usuarioId", id);

        //Confirma a gravação dos dados
        editor.commit();

    }

    Long buscarUsuarioSessao(){

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        Long usuarioid =  settings.getLong("usuarioId", 0);

        return usuarioid;


    }

}
