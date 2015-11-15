package br.edu.imed.myfood;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //txCadastrar
        findViewById(R.id.txCadastrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrar();
            }
        });

    }

    private void cadastrar() {

        final LayoutInflater inflater = getLayoutInflater();

        //chama a intent de cadastro
        new AlertDialog.Builder(this)
                .setTitle("Cadastro")
                .setView(inflater.inflate(R.layout.activity_cadastro, null))
                .setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //String nome = edNome.getText().toString();
                        //showMessage(nome, Toast.LENGTH_SHORT);

                    }
                }).setNegativeButton("Cancelar", null)
                .create()
                .show();

    }

}
