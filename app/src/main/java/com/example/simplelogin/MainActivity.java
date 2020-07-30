package com.example.simplelogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    EditText emailUser;
    EditText passwordUser;

    Database database = new Database(this);

    String ID_KEY = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database.setDatabase(openOrCreateDatabase("SimpleLogin", MODE_PRIVATE, null));
        database.database();

        emailUser = findViewById(R.id.emailUser);
        passwordUser = findViewById(R.id.passwordUser);

    }


    @Override
    protected void onStart() {
        super.onStart();
        SessionManagement sessionManagement = new SessionManagement(this);
        int userId = sessionManagement.getSession();

        if (userId != -1){
            moveToUserActivity(ID_KEY, userId);
        }


    }

    public void goToRegisterActivity(View v){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);


    }

    public void login(View v){
       if(!InputIsNull()){
           int userID =  database.loginUser(emailUser.getText().toString(), passwordUser.getText().toString());

           if(userID != -1){

               SessionManagement sessionManagement = new SessionManagement(this);
               sessionManagement.saveSession(userID);

               moveToUserActivity(ID_KEY, userID);

           }else{
               Toast.makeText(this, "\""+emailUser.getText().toString()+"\", não está associado a nenhuma conta.", Toast.LENGTH_LONG).show();
           }


       } else{
           Toast.makeText(this, "Por favor, preencher todos os Campos.", Toast.LENGTH_LONG).show();
       }

    }

    private boolean InputIsNull(){
        String empty = "";
        return emailUser.getText().toString().equals(empty) || passwordUser.getText().toString().equals(empty);
    }

    private void moveToUserActivity(String id, int userID){
        Intent intent = new Intent(this, UserActivity.class);

        intent.putExtra(id, userID);

        startActivity(intent);
        finish();
    }
}
