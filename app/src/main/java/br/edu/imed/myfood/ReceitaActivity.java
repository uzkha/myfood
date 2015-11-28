package br.edu.imed.myfood;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

import br.edu.imed.myfood.db.ReceitaDao;
import br.edu.imed.myfood.model.Receita;

public class ReceitaActivity extends AbstractActivity {

    static final String PATH = "myfoodimg/";
    String nome = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receita);

        findViewById(R.id.btnFechar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.btnSalvar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    salvar();

                    showMessage("Receita salva com sucesso", Toast.LENGTH_SHORT);

                    finish();

                } catch (Exception e) {
                    showMessage(e.getMessage(), Toast.LENGTH_SHORT);
                }
            }
        });

        findViewById(R.id.btnPhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturarImagem();
            }
        });

    }

    private void capturarImagem() {

        nome = new java.util.Date().toString();

        Log.i("data", nome);

        File file = new File(Environment.getExternalStorageDirectory(),"myfoodimg" + "nome");
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file) );
        startActivityForResult(cameraIntent, 2);

    }


    private void salvar() throws Exception{

        EditText edNome = (EditText) findViewById(R.id.edNomeReceita);
        String nome = edNome.getText().toString();

        EditText edPreparo = (EditText) findViewById(R.id.edPreparo);
        String preparo = edPreparo.getText().toString();

        EditText edIngrediente = (EditText) findViewById(R.id.edIngrediente);
        String ingrediente = edIngrediente.getText().toString();

        Receita receita = criarObjetoReceita(null, nome, ingrediente, preparo);

        validarReceita(receita);

        salvarReceita(receita);

    }

    private void salvarReceita(Receita receita) throws Exception {

        ReceitaDao receitaDao = new ReceitaDao(this);
        receitaDao.salvar(receita);

    }

    private void validarReceita(Receita receita) throws Exception{

        if(receita.getNome().length() < 1){
            throw new Exception("Informe o nome da receita.");
        }

        if(receita.getIngrediente().length() < 1){
            throw new Exception("Informe o nome dos ingredientes.");
        }

        if(receita.getModoPreparo().length() < 1){
            throw new Exception("Informe o modo de preparo da receita.");
        }


    }

    private Receita criarObjetoReceita(Long id, String nome, String ingrediente, String preparo) {

        Receita receita = new Receita();

        receita.setId(id);
        receita.setNome(nome);
        receita.setIngrediente(ingrediente);
        receita.setModoPreparo(preparo);

        return receita;

    }


}
