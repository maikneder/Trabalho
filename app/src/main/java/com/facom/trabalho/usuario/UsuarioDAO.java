package com.facom.trabalho.usuario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.facom.trabalho.DBHelper;

public class UsuarioDAO {

    private DBHelper dbHelper;
    private SQLiteDatabase banco;

    public UsuarioDAO(Context context) {
        dbHelper = new DBHelper(context);
        banco = dbHelper.getWritableDatabase();
    }

    public long inserir(Usuario usuario) {
        ContentValues values = new ContentValues();
        values.put("nome", usuario.getNome());
        values.put("senha", usuario.getSenha());

        return banco.insert("usuario", null, values);
    }

    public String buscaSenha(String uNome) {
        banco = dbHelper.getReadableDatabase();
        String query = "select nome, senha from usuario";
        Cursor cursor = banco.rawQuery(query,null);
        String a, b;
        b = "Não encontrado";
        if(cursor.moveToFirst()) {
            do {
                    a = cursor.getString(0);
                    if(a.equals(uNome)) {
                        b = cursor.getString(1);
                        break;
                    }
            } while(cursor.moveToNext());
        }
        return b;
    }
}
