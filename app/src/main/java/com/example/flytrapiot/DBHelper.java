package com.example.flytrapiot;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "flytrap_db";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE trampas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "codigo_sector TEXT, " +
                "tipo_trampa TEXT, " +
                "nivel_captura INTEGER, " +
                "observaciones TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS trampas");
        onCreate(db);
    }

    public boolean insertar(String sector, String tipo,
                            int nivel, String observaciones) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues valores = new ContentValues();

        valores.put("codigo_sector", sector);
        valores.put("tipo_trampa", tipo);
        valores.put("nivel_captura", nivel);
        valores.put("observaciones", observaciones);

        return db.insert("trampas", null, valores) != -1;
    }

    public Cursor listar() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM trampas", null);
    }

    public Cursor buscar(int id) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM trampas WHERE id=?",
                new String[]{String.valueOf(id)});
    }

    public boolean actualizar(int id, String sector,
                              String tipo, int nivel,
                              String observaciones) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues valores = new ContentValues();

        valores.put("codigo_sector", sector);
        valores.put("tipo_trampa", tipo);
        valores.put("nivel_captura", nivel);
        valores.put("observaciones", observaciones);

        return db.update("trampas", valores, "id=?",
                new String[]{String.valueOf(id)}) > 0;
    }

    public boolean eliminar(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("trampas", "id=?",
                new String[]{String.valueOf(id)}) > 0;
    }
}
