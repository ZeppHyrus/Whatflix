package com.example.whatflix;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText userEditText, passEditText, repeatPassEditText;
    private Button btnRegistro;
    private SQLiteHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new SQLiteHelper(this);

        userEditText = findViewById(R.id.editNombre);
        passEditText = findViewById(R.id.editPass);
        repeatPassEditText = findViewById(R.id.editPassRepeat);
        btnRegistro = findViewById(R.id.Registro_btn);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = userEditText.getText().toString();
                String pass = passEditText.getText().toString();
                String repeatPass = repeatPassEditText.getText().toString();

                if (!pass.equals(repeatPass)){
                    Toast.makeText(RegisterActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                } else if (!isValidPassword(pass)){
                    Toast.makeText(RegisterActivity.this, "La contraseña debe tener al menos 8 caracteres, una letra mayúscula, una letra minúscula, un dígito y un símbolo", Toast.LENGTH_LONG).show();
                } else {
                    if (dbHelper.insertUser(user, pass)){
                        Toast.makeText(RegisterActivity.this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(RegisterActivity.this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=-])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(passwordPattern);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}