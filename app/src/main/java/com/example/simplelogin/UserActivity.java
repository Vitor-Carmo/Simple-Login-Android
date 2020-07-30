package com.example.simplelogin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class UserActivity extends AppCompatActivity {
    TextView name;
    TextView loginEmail;
    TextView loginSex;
    Database database = new Database(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        database.setDatabase(openOrCreateDatabase("SimpleLogin", MODE_PRIVATE, null));
        database.database();


        SessionManagement sessionManagement = new SessionManagement(this);
        int id = sessionManagement.getSession();

        String[] userdata = database.getUser(id);


        name = findViewById(R.id.name);
        name.setText(userdata[1]);

        loginEmail = findViewById(R.id.loginEmail);
        loginEmail.setText(userdata[3]);

        loginSex = findViewById(R.id.loginSex);
        loginSex.setText(userdata[2]);

    }



    public void logout(View v){
        SessionManagement sessionManagement = new SessionManagement(this);
        sessionManagement.removeSession();

        gotoLoginActivity();
    }


    public void deleteUser(View v){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Você tem certeza que deseja excluir sua conta?");
                alertDialogBuilder.setPositiveButton("deletar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                // code to delete user
                                try {
                                    SessionManagement sessionManagement = new SessionManagement(UserActivity.this);
                                    database.deleteUser(String.valueOf(sessionManagement.getSession()));

                                    sessionManagement.removeSession();

                                    Toast("Usuário deletado com sucesso!");

                                    gotoLoginActivity();

                                }catch (Exception e){

                                    Toast("Erro ao deletar usuário");

                                    e.printStackTrace();
                                }
                            }
                        });

        alertDialogBuilder.setNegativeButton("cancelar",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast("Cancelado");
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }


    private void Toast(String text){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    private void gotoLoginActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
