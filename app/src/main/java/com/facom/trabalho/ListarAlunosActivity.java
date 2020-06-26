package com.facom.trabalho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

public class ListarAlunosActivity extends AppCompatActivity {

    private ListView lista;
    private AlunoDAO dao;
    private List<Aluno> alunos;
    private List<Aluno> alunosFiltrados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_alunos);

        lista = findViewById(R.id.lista_alunos);
        dao = new AlunoDAO(this);
        alunos = dao.obterTodos();
        alunosFiltrados.addAll(alunos);
        ArrayAdapter<Aluno> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alunosFiltrados);
        lista.setAdapter(adaptador);
    }

    // Infla o menu superior
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_principal, menu);
        return true;
    }

    public void cadastrar(MenuItem item) {
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
    }

    @Override
    public void onResume() {
        super.onResume();
        alunos = dao.obterTodos();
        alunosFiltrados.clear();
        alunosFiltrados.addAll(alunos);
        lista.invalidateViews();
    }
}