package com.example.whatflix;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "usuarios.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "usuarios";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_USERNAME + " TEXT PRIMARY KEY, "
                + COLUMN_PASSWORD + " TEXT)"; // Añadir el nuevo campo de rol
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean isValidUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public boolean insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Encriptar la contraseña utilizando SHA-256
        String encryptedPassword = encryptPassword(password);

        // Primero, verificar si el nombre de usuario ya existe en la base de datos
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_USERNAME}, COLUMN_USERNAME + " = ?", new String[]{username}, null, null, null);
        boolean userExists = cursor.getCount() > 0;
        cursor.close();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, encryptedPassword); // Guardar la contraseña encriptada



        if (userExists) {
            // Si el usuario ya existe, actualizar la contraseña y el rol
            int rowsAffected = db.update(TABLE_NAME, values, COLUMN_USERNAME + " = ?", new String[]{username});
            return rowsAffected > 0;
        } else {
            // Si el usuario no existe, insertar una nueva fila con nombre de usuario, contraseña y rol
            long result = db.insert(TABLE_NAME, null, values);
            return result != -1;
        }
    }


    private String encryptPassword(String password) {
        try {
            // Crear un objeto MessageDigest para SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Aplicar el algoritmo de encriptación a la contraseña
            byte[] hashedBytes = digest.digest(password.getBytes());

            // Convertir los bytes en formato hexadecimal
            StringBuilder builder = new StringBuilder();
            for (byte b : hashedBytes) {
                builder.append(String.format("%02x", b));
            }

            // Devolver la contraseña encriptada en formato hexadecimal
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }



    public boolean isUserExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

}

