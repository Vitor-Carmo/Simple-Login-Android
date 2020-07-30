package com.example.simplelogin;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Database {
    private SQLiteDatabase database;
    private Activity activity;

    Database(Activity activity){
        this.activity = activity;
    }


    void database(){

        String sql_code = "CREATE TABLE IF NOT EXISTS " +
                "user(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "email VARCHAR," +
                    "password VARCHAR," +
                    "name VARCHAR," +
                    "sex VARCHAR" +
                ")";

        try {
            database.execSQL(sql_code);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    void addUser(String name, String sex, String email, String password){

        try{
            String sql_code = "INSERT INTO user(name, sex, email, password) " +
                    "VALUES ('"+name+"','"+sex+"','"+email+"', '"+password+"')";

            database.execSQL(sql_code);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    int loginUser(String email, String password){
        String sql_search_user = "SELECT * FROM user WHERE email = '"+email+"'";


        Cursor c = database.rawQuery(sql_search_user, null);
        System.out.println(c.getCount());
        while(c.moveToNext()){
            if(email.equals(c.getString(c.getColumnIndex("email")))){
                if (password.equals(c.getString(c.getColumnIndex("password")))){
                        return Integer.parseInt(c.getString(c.getColumnIndex("id")));
                    };

                }

            }

        return -1;
    }





    void deleteUser(String id){
        try{
            String sql_code = "DELETE FROM user WHERE id = "+id;
            database.execSQL(sql_code);
        }catch (Exception e){
            e.printStackTrace();
        }


    }


    public String[] getUser(int id){
        String sql_search_user = "SELECT * FROM user WHERE id = "+id;

        Cursor c = database.rawQuery(sql_search_user, null);
        System.out.println(c.getCount());
        while(c.moveToNext()){
            return new String[] {c.getString(c.getColumnIndex("id")),
                    c.getString(c.getColumnIndex("name")),
                    c.getString(c.getColumnIndex("sex")),
                    c.getString(c.getColumnIndex("email"))
            };
        }

        return null;
    }

    void setDatabase(SQLiteDatabase database) {
        this.database = database;
    }
}
