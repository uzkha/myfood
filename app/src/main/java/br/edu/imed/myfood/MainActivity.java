package br.edu.imed.myfood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AbstractActivity {

    boolean logado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //verifica se o usuario já esta logado
        logado = verificarLogin();

        //usuario não logado, abre activity de login
        if(logado == false) {

            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);


        }

    }

    private boolean verificarLogin() {
        return false;
    }

}
