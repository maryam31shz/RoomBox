package com.example.apadana.roombox;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

public class logIn2 extends BaseActivity {
    private FrameLayout dynamicContent;
    private View wizard;

    private EditText email;
    private EditText password ;
    private Button loginBtn;
    private Button signupBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setView();
        setDatabase();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkUser(email.getText().toString() , password.getText().toString())){

                    startHomeActivity();
                }
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDb.execSQL("insert into user values(1,'maryam','mshz','123',222,203520,1)");            }
        });
    }


    private void setView() {
        dynamicContent = (FrameLayout)  findViewById(R.id.dynamicContent);
        wizard = getLayoutInflater().inflate(R.layout.login2, null);
        dynamicContent.addView(wizard);
        email = (EditText) findViewById(R.id.usernameEditText) ;
        password = (EditText) findViewById(R.id.passEditText) ;
        loginBtn = (Button) findViewById(R.id.loginBtn)  ;
        signupBtn = (Button) findViewById(R.id.signUpBtn)  ;


    }

    private void startHomeActivity() {

        Intent homeIntent = new Intent(getBaseContext(), HomeActivity.class );
        startActivity(homeIntent );
    }
    public boolean checkUser(String email , String password) {

        Cursor cursor = mDb.rawQuery("select * from user where Email = ? and Password = ? " ,
                new String[]{email,password});


        if (cursor != null && cursor.moveToFirst()){

            userid = cursor.getInt(cursor.getColumnIndex("ID"));
            user = new user(
                    cursor.getInt(cursor.getColumnIndex(user.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(user.COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(user.COLUMN_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(user.COLUMN_PASSWORD)),
                    cursor.getInt(cursor.getColumnIndex(user.COLUMN_BANKNUMBER)),
                    cursor.getInt(cursor.getColumnIndex(user.COLUMN_PHONENUMBER)),
                    cursor.getInt(cursor.getColumnIndex(user.COLUMN_CAR)));
            cursor.close();
            return true;

        }
        else {
            Toast.makeText(getBaseContext(),"Please Enter correct email and password!"
                    , Toast.LENGTH_LONG).show();
            cursor.close();
            return false;
        }


    }

}
