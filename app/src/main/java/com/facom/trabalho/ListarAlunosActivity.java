package com.facom.trabalho;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SearchView;

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

        // Faz o link do menu de contexto de alterar e excluir com a lista de alunos
        registerForContextMenu(lista);
    }

    // Infla o menu superior
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_principal, menu);

        // Grava o que o usuário digitou na pesquisa
        SearchView sv = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                procuraAluno(s);
                return false;
            }
        });
        return true;
    }

    // Cria menu de contexto para alterar ou excluir
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto, menu);
    }

    public void procuraAluno(String nome) {
        alunosFiltrados.clear();
        for(Aluno a : alunos) {
            if(a.getNome().toLowerCase().contains(nome.toLowerCase())){
                alunosFiltrados.add(a);
            }
        }
        lista.invalidateViews();
    }

    public void cadastrar(MenuItem item) {
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
    }

    public void excluir(MenuItem item) {
        // Armazena qual a posição o usuário clicou
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Aluno alunoExcluir = alunosFiltrados.get(menuInfo.position);

        // Cria caixa de alerta antes da exclusão
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Deseja realmente excluir o aluno?")
                .setNegativeButton("Não", null)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alunosFiltrados.remove(alunoExcluir);
                        alunos.remove(alunoExcluir);
                        dao.excluir(alunoExcluir);
                        lista.invalidateViews();
                    }
                }).create();
        dialog.show();
    }

    // Sobrescrevendo método para atualizar a lista após inserir um novo aluno
    @Override
    public void onResume() {
        super.onResume();
        alunos = dao.obterTodos();
        alunosFiltrados.clear();
        alunosFiltrados.addAll(alunos);
        lista.invalidateViews();
    }
}