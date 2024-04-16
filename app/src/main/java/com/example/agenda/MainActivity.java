package com.example.agenda;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    EditText txtNombre, txtTelefono, txtCorreo;
    Set<String> contactosSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtNombre = findViewById(R.id.txtNombre);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtCorreo = findViewById(R.id.txtCorreo);

        cargarContactos();
    }

    private void cargarContactos() {
        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        contactosSet = preferences.getStringSet("contactos", new HashSet<>());
    }

    public void cmdGuardar_onClick(View v) {
        String nombre = txtNombre.getText().toString();
        String telefono = txtTelefono.getText().toString();
        String correo = txtCorreo.getText().toString();

        String nuevoContacto = nombre + "," + telefono + "," + correo;
        contactosSet.add(nuevoContacto);

        guardarContactos();

        Toast.makeText(this, "Datos guardados correctamente !!", Toast.LENGTH_SHORT).show();
    }

    private void guardarContactos() {
        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet("contactos", contactosSet);
        editor.apply();
    }

    public void cmdBuscar_onClick(View v) {
        String nombreABuscar = txtNombre.getText().toString();
        String contactoEncontradoStr = null;
        for (String contactoStr : contactosSet) {
            String[] partes = contactoStr.split(",");
            if (partes.length == 3 && partes[0].equalsIgnoreCase(nombreABuscar)) {
                contactoEncontradoStr = contactoStr;
                break;
            }
        }

        if (contactoEncontradoStr != null) {
            String[] partes = contactoEncontradoStr.split(",");
            txtTelefono.setText(partes[1]);
            txtCorreo.setText(partes[2]);
            Toast.makeText(this, "Contacto encontrado !!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Contacto no encontrado !!", Toast.LENGTH_SHORT).show();
        }
    }

    public void cmdBorrar_onClick(View v) {
        txtNombre.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
    }


}