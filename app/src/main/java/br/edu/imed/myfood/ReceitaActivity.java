package br.edu.imed.myfood;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import br.edu.imed.myfood.db.ReceitaDao;
import br.edu.imed.myfood.model.Receita;

public class ReceitaActivity extends AbstractActivity {

    static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myfood/receitas/imagens/";
    String nome;
    File file;
    String pathImagem;
    Long idReceita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receita);


        //verifica se tem ID na passagem de parametro
        if(getIntent().hasExtra("ID")){

            idReceita = Long.valueOf(getIntent().getStringExtra("ID"));

            montarReceitaView();

        }

        if(idReceita == null){
            ImageView imvReceita = (ImageView) findViewById(R.id.btnDelete);
            imvReceita.setVisibility(View.GONE);
        }

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

        findViewById(R.id.btnGallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturarImagemGallery();
            }
        });

        findViewById(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletar();
            }
        });

    }

    private void deletar(){

        new AlertDialog.Builder(this)
                .setTitle("Deletar Receita")
                .setMessage("Atenção! Confirma a exclusão da Receita?")
                .setPositiveButton("Deletar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            deletarReceita();
                        } catch (Exception e) {
                            showMessage(e.getMessage(), Toast.LENGTH_LONG);
                        }
                    }
                }).setNegativeButton("Cancelar", null)
                .create()
                .show();



    }

    private void deletarReceita() throws Exception{

        ReceitaDao receitaDao = new ReceitaDao(this);

        //busca a receita
        Receita receita = receitaDao.listarReceita(buscarUsuarioSessao(), idReceita);

        String path = receita.getPathImagem();

        //deleta a receita no banco de dados
        receitaDao.deletar(buscarUsuarioSessao(), idReceita);

        //deleta a imagem
        File f = new File(path);
        f.delete();

        showMessage("Receita excluida com sucesso!", Toast.LENGTH_LONG);
        finish();
    }

    private void montarReceitaView() {

        try {

            Receita receita = listarReceita(idReceita);

            EditText edNomeReceita = (EditText) findViewById(R.id.edNomeReceita);
            EditText edIngrediente = (EditText) findViewById(R.id.edIngrediente);
            EditText edPreparo = (EditText) findViewById(R.id.edPreparo);

            edNomeReceita.setText(receita.getNome());
            edIngrediente.setText(receita.getIngrediente());
            edPreparo.setText(receita.getModoPreparo());

            String path = receita.getPathImagem();

            if (path != null) {

                ImageView imageView = (ImageView) findViewById(R.id.imvReceita);

                InputStream inputStreamBmp = new FileInputStream(path);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStreamBmp);

                bitmap = Bitmap.createScaledBitmap(bitmap, 300, 400, false);

                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setImageBitmap(bitmap);
            }


        }catch (Exception e){
            showMessage(e.getMessage(), Toast.LENGTH_LONG);
        }

    }

    private void capturarImagemGallery() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 1);

    }

    private void capturarImagem() {

        nome = new java.util.Date().toString() + ".jpg";

        pathImagem = PATH + nome;

        file = new File(pathImagem);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(cameraIntent, 2);

    }


    public void copy(InputStream in) throws IOException {

        File direct = new File(PATH);

        if (!direct.exists()) {
            direct.mkdirs();
        }

        File file = new File(pathImagem);

        OutputStream out = new FileOutputStream(file);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {

            try {

                nome = new java.util.Date().toString() + ".jpg";

                pathImagem = PATH + nome;

                ImageView imageView = (ImageView) findViewById(R.id.imvReceita);

                InputStream inputStream    = getContentResolver().openInputStream(data.getData());
                InputStream inputStreamBmp = getContentResolver().openInputStream(data.getData());

                copy(inputStream);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStreamBmp);

                bitmap = Bitmap.createScaledBitmap(bitmap, 300, 400, false);

                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setImageBitmap(bitmap);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
                showMessage(e.getMessage(), Toast.LENGTH_SHORT);
            } catch (IOException e) {
                e.printStackTrace();
                showMessage(e.getMessage(), Toast.LENGTH_SHORT);
            }catch (Exception e){
                e.printStackTrace();
                showMessage(e.getMessage(), Toast.LENGTH_SHORT);
            }
        }

        if (requestCode == 2 && resultCode == RESULT_OK) {

            Bitmap myBitmap = null;
            try {
                myBitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                showMessage(e.getMessage(), Toast.LENGTH_SHORT);
            }

            myBitmap = Bitmap.createScaledBitmap(myBitmap, 300, 400, false);

            ImageView imageView = (ImageView) findViewById(R.id.imvReceita);
            imageView.setImageBitmap(myBitmap);

        }

    }

    private void salvar() throws Exception{

        EditText edNome = (EditText) findViewById(R.id.edNomeReceita);
        String nome = edNome.getText().toString();

        EditText edPreparo = (EditText) findViewById(R.id.edPreparo);
        String preparo = edPreparo.getText().toString();

        EditText edIngrediente = (EditText) findViewById(R.id.edIngrediente);
        String ingrediente = edIngrediente.getText().toString();

        Receita receita = criarObjetoReceita(idReceita, nome, ingrediente, preparo, pathImagem);

        validarReceita(receita);

        salvarReceita(receita);

    }

    private void salvarReceita(Receita receita) throws Exception {

        ReceitaDao receitaDao = new ReceitaDao(this);
        receitaDao.salvar(receita);

    }

    private Receita listarReceita(Long id){
        ReceitaDao receitaDao = new ReceitaDao(this);
        return receitaDao.listarReceita(buscarUsuarioSessao(), id);
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

    private Receita criarObjetoReceita(Long id, String nome, String ingrediente, String preparo, String pathImagem) {

        Receita receita = new Receita();

        receita.setId(id);
        receita.setNome(nome);
        receita.setIngrediente(ingrediente);
        receita.setModoPreparo(preparo);
        receita.setPathImagem(pathImagem);
        receita.setUsuarioId(buscarUsuarioSessao());

        return receita;

    }


}
