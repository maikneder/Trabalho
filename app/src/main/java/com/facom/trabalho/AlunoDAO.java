package com.facom.trabalho;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class AlunoDAO {

    private DBHelper dbHelper;
    private SQLiteDatabase banco;

    public AlunoDAO(Context context) {
        dbHelper = new DBHelper(context);
        banco = dbHelper.getWritableDatabase();
    }

    public long inserir(Aluno aluno) {
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("cpf", aluno.getCpf());
        values.put("telefone", aluno.getTelefone());
        return banco.insert("aluno", null, values);
    }
}
