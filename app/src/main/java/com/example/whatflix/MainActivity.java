package com.example.whatflix;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnInicioSesion; // Declaración del botón

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInicioSesion = findViewById(R.id.btnIncioSesion); // Inicialización del botón

        // Crear un drawable con forma redondeada y color de fondo
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE); // Forma rectangular
        drawable.setCornerRadius(20); // Radio de las esquinas
        drawable.setColor(0xFFFF0000); // Color de fondo (rojo)

        // Aplicar el drawable como fondo del botón
        btnInicioSesion.setBackground(drawable);
    }
}
