
package com.example.flytrapiot;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    EditText etSector, etTipo, etNivel, etObservaciones;
    Button btnGuardar, btnVer;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(this);

        etSector = findViewById(R.id.etSector);
        etTipo = findViewById(R.id.etTipo);
        etNivel = findViewById(R.id.etNivel);
        etObservaciones = findViewById(R.id.etObservaciones);

        btnGuardar = findViewById(R.id.btnGuardar);
        btnVer = findViewById(R.id.btnVer);

        btnGuardar.setOnClickListener(v -> guardar());

        btnVer.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    ListaActivity.class
            );
            startActivity(intent);
        });
    }

    private void guardar() {

        String sector = etSector.getText().toString().trim();
        String tipo = etTipo.getText().toString().trim();
        String nivelTexto = etNivel.getText().toString().trim();
        String observaciones =
                etObservaciones.getText().toString().trim();

        if (sector.isEmpty() || tipo.isEmpty()
                || nivelTexto.isEmpty()) {

            Toast.makeText(this,
                    "Complete los campos obligatorios",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        int nivel;

        try {
            nivel = Integer.parseInt(nivelTexto);
        } catch (NumberFormatException e) {
            Toast.makeText(this,
                    "Nivel de captura inválido",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        boolean resultado = db.insertar(
                sector, tipo, nivel, observaciones
        );

        if (resultado) {
            Toast.makeText(this,
                    "Trampa registrada correctamente",
                    Toast.LENGTH_SHORT).show();

            etSector.setText("");
            etTipo.setText("");
            etNivel.setText("");
            etObservaciones.setText("");

        } else {
            Toast.makeText(this,
                    "Error al guardar",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
