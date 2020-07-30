package com.example.simplelogin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    EditText name;
    EditText email;
    Spinner sex;
    EditText password;
    EditText password_confirm;
    Button save;
    Database database = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        database.setDatabase(openOrCreateDatabase("SimpleLogin", MODE_PRIVATE, null));
        database.database();

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        password_confirm = findViewById(R.id.password_confirm);

        sex = findViewById(R.id.sex_spinner);
        save = findViewById(R.id.save);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.sex_array, android.R.layout.simple_spinner_dropdown_item);
        sex.setAdapter(adapter);

    }

    public void registerUser(View v){
        if (InputIsNull()){
            Toast("Por favor, preencha os campos corretamente!");
            return;
        }

        if (!password.getText().toString().equals(password_confirm.getText().toString())){
            Toast( "Suas senhas não coincidem. Tente novamente.");
            return;
        }

        String sex = this.sex.getSelectedItem().toString();
        String name = this.name.getText().toString();
        String password = this.password.getText().toString();
        String email = this.email.getText().toString();

        database.addUser(name, sex, email, password);
        Toast("Usuário cadastrado!");
        save.setEnabled(false);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });

        thread.start();

    }




    public void finish(View v){
        finish();
    }





    private boolean InputIsNull(){
       String empty = "";

        return name.getText().toString().equals(empty)
                || email.getText().toString().equals(empty)
                || password.getText().toString().equals(empty)
                || password_confirm.getText().toString().equals(empty);

    }

    private void Toast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }



}
