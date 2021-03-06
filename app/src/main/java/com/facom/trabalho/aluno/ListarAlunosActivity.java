package com.facom.trabalho.aluno;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.ListView;
import android.widget.SearchView;

import com.facom.trabalho.usuario.LoginActivity;
import com.facom.trabalho.R;

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
        // ArrayAdapter<Aluno> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alunosFiltrados);
        AlunoAdapter adaptador = new AlunoAdapter(this, alunosFiltrados);
        lista.setAdapter(adaptador);

        // Faz o link do menu de contexto de alterar e excluir com a lista de alunos
        registerForContextMenu(lista);
    }

    // Infla o menu superior para exibir o cadastrar e o pesquisar
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

    // Chama a tela para cadastrar
    public void cadastrar(MenuItem item) {
        Intent it = new Intent(this, CadAlunoActivity.class);
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

    public void atualizar(MenuItem item) {
        // Armazena qual a posição o usuário clicou
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Aluno alunoAtualizar = alunosFiltrados.get(menuInfo.position);

        Intent it = new Intent(this, CadAlunoActivity.class);
        it.putExtra("aluno", alunoAtualizar);
        startActivity(it);
    }

    public void sair(MenuItem item) {
        Intent it = new Intent(this, LoginActivity.class);
        startActivity(it);
    }

    // Sobrescrevendo método para atualizar a lista após inserir um novo aluno, tela voltou a estar ativa
    @Override
    public void onResume() {
        super.onResume();
        alunos = dao.obterTodos();
        // Limpa a lista de alunos
        alunosFiltrados.clear();
        // Adiciona a lista de alunos com o último registro que foi inserido
        alunosFiltrados.addAll(alunos);
        // Invalida dados antigos da lista para poder mostra atualizado.
        lista.invalidateViews();
    }
}