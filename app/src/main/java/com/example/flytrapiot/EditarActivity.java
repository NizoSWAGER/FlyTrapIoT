
package com.example.flytrapiot;

import android.app.Activity;
import android.os.Bundle;
import android.database.Cursor;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

public class EditarActivity extends Activity {

    EditText etSector, etTipo, etNivel, etObservaciones;
    Button btnActualizar, btnEliminar;
    DBHelper db;
    int idTrampa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        db = new DBHelper(this);

        etSector = findViewById(R.id.etSectorEditar);
        etTipo = findViewById(R.id.etTipoEditar);
        etNivel = findViewById(R.id.etNivelEditar);
        etObservaciones =
                findViewById(R.id.etObservacionesEditar);

        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);

        // Recibir ID desde ListaActivity
        idTrampa = getIntent().getIntExtra("id", -1);

        if (idTrampa == -1) {
            Toast.makeText(this, "ID no válido",
                    Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        cargarTrampa();

        btnActualizar.setOnClickListener(v -> actualizar());
        btnEliminar.setOnClickListener(v -> eliminar());
    }

    private void cargarTrampa() {

        try (Cursor cursor = db.buscar(idTrampa)) {

            if (cursor.moveToFirst()) {

                etSector.setText(cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "codigo_sector")));

                etTipo.setText(cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "tipo_trampa")));

                etNivel.setText(String.valueOf(cursor.getInt(
                        cursor.getColumnIndexOrThrow(
                                "nivel_captura"))));

                etObservaciones.setText(cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "observaciones")));

            } else {
                Toast.makeText(this,
                        "Trampa no encontrada",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void actualizar() {

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
                    "Nivel inválido",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        boolean resultado = db.actualizar(
                idTrampa, sector, tipo, nivel, observaciones
        );

        if (resultado) {
            Toast.makeText(this,
                    "Trampa actualizada",
                    Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this,
                    "Error al actualizar",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void eliminar() {

        // La pauta solicita eliminar sin confirmación
        boolean resultado = db.eliminar(idTrampa);

        if (resultado) {
            Toast.makeText(this,
                    "Trampa eliminada",
                    Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this,
                    "Error al eliminar",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
