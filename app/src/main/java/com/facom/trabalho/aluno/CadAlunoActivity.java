package com.facom.trabalho.aluno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facom.trabalho.R;

public class CadAlunoActivity extends AppCompatActivity {

    private EditText nome;
    private EditText cpf;
    private EditText telefone;
    private AlunoDAO dao;
    private Aluno aluno = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_aluno);

        nome = findViewById(R.id.edtNomeU);
        cpf = findViewById(R.id.edtCPF);
        telefone = findViewById(R.id.edtTelefone);
        dao = new AlunoDAO(this);

        // Passa os dados do aluno se clicou em Atualizar
        Intent it = getIntent();
        if(it.hasExtra("aluno")) {
            aluno = (Aluno) it.getSerializableExtra("aluno");
            nome.setText(aluno.getNome());
            cpf.setText(aluno.getCpf());
            telefone.setText(aluno.getTelefone());
        }
    }

    public void salvar(View view) {
        if(nome.getText().toString() == null || nome.getText().toString().equals("")) {
            nome.setError("O campo Nome é obrigatório");
        } else if(cpf.getText().toString() == null || cpf.getText().toString().equals("")) {
            cpf.setError("O campo CPF é obrigatório");
        } else if(telefone.getText().toString() == null || telefone.getText().toString().equals("")) {
            telefone.setError("O campo Telefone é obrigatório");
        } else {
            if(aluno == null) {
                aluno = new Aluno();
                aluno.setNome(nome.getText().toString());
                aluno.setCpf(cpf.getText().toString());
                aluno.setTelefone(telefone.getText().toString());
                long id = dao.inserir(aluno);
                Toast.makeText(this, "Aluno inserido com id: " + id, Toast.LENGTH_SHORT).show();
            } else {
                aluno.setNome(nome.getText().toString());
                aluno.setCpf(cpf.getText().toString());
                aluno.setTelefone(telefone.getText().toString());
                dao.atualizar(aluno);
                Toast.makeText(this, "Aluno foi atualizado ", Toast.LENGTH_SHORT).show();
            }
        }
    }
}