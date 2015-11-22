package br.edu.imed.myfood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AbstractActivity {

    Long usuarioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //verifica se o usuario já esta logado
        usuarioId = verificarLogin();

        //usuario não logado, abre activity de login
        if(usuarioId < 1) {

            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);

            finish();

        }

    }

    private Long verificarLogin() {
        return buscarUsuarioSessao();
    }


}
