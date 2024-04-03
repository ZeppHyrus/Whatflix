package com.example.whatflix;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView btnInicioSesion, btnInvitado, btnRegistro;
    private EditText editUser, editPass;
    private SQLiteHelper dbHelper;
    CheckBox checkBoxMostrar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialización del botón
        btnInicioSesion = findViewById(R.id.btn_InicioSesion);
        btnInvitado = findViewById(R.id.btn_Invitado);
        btnRegistro = findViewById(R.id.btn_Registro);

        // Inicialización del EditTextText
        editUser = findViewById(R.id.editTextUsuario);
        editPass = findViewById(R.id.editTextContraseña);

        // Inicialización del CheckBox
        checkBoxMostrar = findViewById(R.id.checkBoxMostrarContraseña);

        // Inicialización de la clase SQLiteHelper apodada dbHelper
        dbHelper = new SQLiteHelper(this);

        //Metodo para hacer que los botones tengan un radio de 20dp para que no tenga picos
        btnRojos();
        btnGrs();

        checkBoxMostrar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Si el CheckBox está marcado, mostrar la contraseña
                    editPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    // Si el CheckBox no está marcado, ocultar la contraseña
                    editPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        btnInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        btnInvitado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userRole = "invitado";
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.putExtra("USER_ROLE", userRole); // Pasar el rol del usuario a HomeActivity
                startActivity(intent);
            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public void login(){
        String username = editUser.getText().toString();
        String password = editPass.getText().toString();

        if (dbHelper.isValidUser(username, password)) {
            // Login successful
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish(); // Finalizar MainActivity para evitar que el usuario vuelva atrás con el botón de retroceso
        } else {
            // Login failed, show error message
            Toast.makeText(this, "Nombre de usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }

    }

    public void btnRojos(){
        // Crear un drawable con forma redondeada y color de fondo
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE); // Forma rectangular
        drawable.setCornerRadius(20); // Radio de las esquinas
        drawable.setColor(0xFFFF0000); // Color de fondo (rojo puro)

        // Aplicar el drawable como fondo del botón
        btnInicioSesion.setBackground(drawable);

        GradientDrawable drawable2 = new GradientDrawable();
        drawable2.setShape(GradientDrawable.RECTANGLE); // Forma rectangular
        drawable2.setCornerRadius(20); // Radio de las esquinas
        drawable2.setColor(0xFFFF0000); // Color de fondo (rojo puro)

        btnInvitado.setBackground(drawable2);
    }

    public void btnGrs(){
        // Crear un drawable con forma redondeada y color de fondo
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE); // Forma rectangular
        drawable.setCornerRadius(20); // Radio de las esquinas
        drawable.setColor(0xFF808080); // Color de fondo (gris)

        // Aplicar el drawable como fondo del botón
        btnRegistro.setBackground(drawable);

    }
}
