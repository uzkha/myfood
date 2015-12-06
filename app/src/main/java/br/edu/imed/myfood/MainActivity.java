package br.edu.imed.myfood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.edu.imed.myfood.db.ReceitaDao;
import br.edu.imed.myfood.model.Receita;

public class MainActivity extends AbstractActivity {

    Long usuarioId;

    List<Receita> lista = new ArrayList<Receita>();
    ArrayAdapter<Receita> adapter;


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


        montarAdapter();


        ListView lv = (ListView) findViewById(R.id.listViewReceita);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3) {
                // Buscar alguma view dentro deste item
                TextView txId = (TextView) v.findViewById(R.id.txId);
                String id = txId.getText().toString().trim();

                Intent intent = new Intent(MainActivity.this, ReceitaActivity.class);
                intent.putExtra("ID", id);
                startActivityForResult(intent, 1);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        montarAdapter();
    }

 /*   @Override
    public void onResume(){

        montarAdapter();
        super.onResume();

    }*/


    /**
     * Monta a View Adapter para listagem das receitas
     */
    private void montarAdapter() {

        lista = listarReceita();

        adapter = new ReceitaAdapter(this, lista);

        ListView listView = (ListView) findViewById(R.id.listViewReceita);
        listView.setAdapter(adapter);
    }


    /**
     * @return
     * Cria o objeto DAO para pesquisa de todas as receitas do Usuário
     * Retornando uma lista de receitas
     */
    private List<Receita> listarReceita() {

        ReceitaDao receitaDao = new ReceitaDao(this);
        return receitaDao.listarReceitas(buscarUsuarioSessao());

    }

    /**
     * Chama a Intent para criar uma nova receita
     */
    private void adicionarReceita() {
        Intent i = new Intent(MainActivity.this, ReceitaActivity.class);
        startActivityForResult(i, 1);
    }


    /**
     * Executa o logout do usuário logado,
     * limpando as preferencias do arquivo SharedPreference
     */
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
