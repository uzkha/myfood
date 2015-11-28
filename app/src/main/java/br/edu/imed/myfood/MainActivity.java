package br.edu.imed.myfood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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


        findViewById(R.id.btnAddReceita).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    adicionarReceita();
                } catch (Exception e) {
                    showMessage(e.getMessage(), Toast.LENGTH_SHORT);
                }
            }
        });


    }

    private void adicionarReceita() {
        Intent i = new Intent(MainActivity.this, ReceitaActivity.class);
        startActivity(i);
    }

    private void logout() {
        setarUsuarioSessao(Long.valueOf(0));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Long verificarLogin() {
        return buscarUsuarioSessao();
    }


}
