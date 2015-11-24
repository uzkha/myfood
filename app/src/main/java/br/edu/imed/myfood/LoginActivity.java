package br.edu.imed.myfood;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.edu.imed.myfood.bd.Dao;
import br.edu.imed.myfood.model.Usuario;

public class LoginActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //txCadastrar
        findViewById(R.id.txCadastrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    cadastrar();
                } catch (Exception e) {
                    showMessage(e.getMessage(), Toast.LENGTH_SHORT);
                }
            }
        });

        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    logar();
                } catch (Exception e) {
                    showMessage(e.getMessage(), Toast.LENGTH_SHORT);
                }
            }
        });

    }

    private void logar() throws Exception{

            EditText edEmail = (EditText) findViewById(R.id.edEmailLogin);
            String email = edEmail.getText().toString();

            EditText edSenha = (EditText) findViewById(R.id.edSenhaLogin);
            String senha = edSenha.getText().toString();

            Long usuarioId = validarLogin(email, senha);

            if(usuarioId > 0){

                //seta shared preferencia
                setarUsuarioSessao(usuarioId);

                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);

            }else{
                showMessage("Usuário ou senha inválidos.", Toast.LENGTH_SHORT);
            }

    }

    private Long validarLogin(String email, String senha) {

        Dao usuarioDao = new Dao(this);
        return usuarioDao.validarLogin(email, senha);

    }


    private void cadastrar() throws Exception{

            LayoutInflater li = getLayoutInflater();

            View view = li.inflate(R.layout.activity_cadastro, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Criando novo arquivo de apresentação");
            builder.setView(view);

            builder.setPositiveButton("Salvar",
                    new DialogInterface.OnClickListener()  {

                        @Override
                        public void onClick(DialogInterface dialog, int which){

                            try {

                                EditText edNome = (EditText) ((Dialog) dialog).findViewById(R.id.edNome);
                                String nome = edNome.getText().toString();

                                EditText edEmail = (EditText) ((Dialog) dialog).findViewById(R.id.edEmail);
                                String email = edEmail.getText().toString();

                                EditText edSenha = (EditText) ((Dialog) dialog).findViewById(R.id.edSenha);
                                String senha = edSenha.getText().toString();

                                EditText edSenhaCfm = (EditText) ((Dialog) dialog).findViewById(R.id.edSenhaCfm);
                                String senhaCfm = edSenhaCfm.getText().toString();

                                Usuario usuario = criarObjetoUsuario(null, nome, email, senha, senhaCfm);

                                validarUsuario(usuario);

                                salvarUsuario(usuario);

                                setarEmail(email, senha);

                                showMessage("Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT);

                            }catch (Exception e){
                                showMessage(e.getMessage(), Toast.LENGTH_SHORT);
                            }

                        }

                    });

            builder.setNegativeButton("Canceler", null);
            builder.show();



    }

    private void setarEmail(String email, String senha) {

        EditText edEmail = (EditText) findViewById(R.id.edEmailLogin);
        EditText edSenha = (EditText) findViewById(R.id.edSenhaLogin);

        edEmail.setText(email);
        edSenha.setText(senha);
    }

    private void validarUsuario(Usuario usuario) throws Exception{

        if(usuario.getNome().length() < 1){
            throw new Exception("Informe o nome do usuário.");
        }

        if(usuario.getEmail().length() < 1){
            throw new Exception("Informe o email");
        }

        if(usuario.getSenha().length() < 1){
            throw new Exception("Informe a senha");
        }

        if(! usuario.getSenha().equals( usuario.getSenhaCfm())){
            throw new Exception("Senhas digitadas não conferem.");
        }

    }

    private void salvarUsuario(Usuario usuario) throws Exception{

        Dao usuarioDao = new Dao(this);
        usuarioDao.salvarUsuario(usuario);

    }

    private Usuario criarObjetoUsuario(Long id, String nome, String email, String senha, String senhaCfm){

        Usuario usuario = new Usuario();

        usuario.setId(id);
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setSenhaCfm(senhaCfm);

        return usuario;

    }



}


