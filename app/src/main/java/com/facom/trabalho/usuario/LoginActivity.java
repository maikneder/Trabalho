package com.facom.trabalho.usuario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.facom.trabalho.R;
import com.facom.trabalho.aluno.ListarAlunosActivity;

public class LoginActivity extends AppCompatActivity {

    private UsuarioDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        dao = new UsuarioDAO(this);
    }

    public void onButtonClick(View v) {
        if(v.getId() == R.id.Blogin) {
            EditText nome = findViewById(R.id.edtNome);
            String strNome = nome.getText().toString();
            EditText senha = findViewById(R.id.edtSenha);
            String strSenha = senha.getText().toString();

            if(strNome == null || strNome.equals("")){
               nome.setError("Campo Nome não pode ser vazio");
            } else if(strSenha == null || strSenha.equals("")) {
                senha.setError("Campo Senha não pode ser vazio");
            } else {
                String password = dao.buscaSenha(strNome);
                if(strSenha.equals(password)) {
                    Intent i = new Intent(LoginActivity.this, ListarAlunosActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(LoginActivity.this, "Nome e/ou senha estão incorretos", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if(v.getId() == R.id.Bsignup) {
            Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(i);
        }
    }
}
