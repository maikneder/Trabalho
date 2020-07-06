package com.facom.trabalho;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private UsuarioDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        dao = new UsuarioDAO(this);
    }

    public void salvarUsuario(View v) {
        if(v.getId() == R.id.btnSignup) {
            EditText nome = findViewById(R.id.edtNomeU);
            EditText senha1 = findViewById(R.id.edtSenha1);
            EditText senha2 = findViewById(R.id.edtSenha2);

            String strNome = nome.getText().toString();
            String strSenha1 = senha1.getText().toString();
            String strSenha2 = senha2.getText().toString();

            if(strNome == null || strNome.equals("")) {
                nome.setError("O campo Nome é obrigatório");
            } else if(strSenha1 == null || strSenha1.equals("")) {
                senha1.setError("O campo Senha é obrigatório");
            } else if(strSenha2 == null || strSenha2.equals("")) {
                senha2.setError("O campo Confirmar Senha é obrigatório");
            } else {
                if(!strSenha1.equals(strSenha2)) {
                    Toast.makeText(SignUpActivity.this, "Senhas não combinam!", Toast.LENGTH_SHORT).show();
                } else {
                    Usuario u = new Usuario();
                    u.setNome(strNome);
                    u.setSenha(strSenha1);

                    dao.inserir(u);
                    Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(this, LoginActivity.class);
                    startActivity(it);
                }
            }
        }
    }
}
