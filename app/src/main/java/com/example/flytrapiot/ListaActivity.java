package com.example.flytrapiot;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.database.Cursor;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class ListaActivity extends Activity {

    ListView listaTrampas;
    DBHelper db;
    ArrayList<String> datos;
    ArrayList<Integer> ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        listaTrampas = findViewById(R.id.listaTrampas);
        db = new DBHelper(this);

        listaTrampas.setOnItemClickListener(
                (parent, view, position, id) -> {

                    Intent intent = new Intent(
                            ListaActivity.this,
                            EditarActivity.class
                    );

                    intent.putExtra("id", ids.get(position));
                    startActivity(intent);
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarDatos();
    }

    private void cargarDatos() {

        datos = new ArrayList<>();
        ids = new ArrayList<>();

        try (Cursor cursor = db.listar()) {
            while (cursor.moveToNext()) {

                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow("id"));

                String sector = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "codigo_sector"));

                String tipo = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "tipo_trampa"));

                int nivel = cursor.getInt(
                        cursor.getColumnIndexOrThrow(
                                "nivel_captura"));

                ids.add(id);

                datos.add(
                        "ID: " + id +
                                "\nSector: " + sector +
                                "\nTipo: " + tipo +
                                "\nNivel: " + nivel
                );
            }
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        datos
                );

        listaTrampas.setAdapter(adapter);

        if (datos.isEmpty()) {
            Toast.makeText(this,
                    "No hay trampas registradas",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
