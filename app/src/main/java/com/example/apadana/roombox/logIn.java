package com.example.apadana.roombox;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class logIn extends AppCompatActivity {


    //Button loginBtn;
    //EditText username;
    @BindView(R.id.usernameEditText) EditText username;
    @BindView (R.id.loginBtn) Button loginBtn ;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        ButterKnife.bind(this);

        View.OnClickListener on = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHomeActivity();

            }
        };

        loginBtn.setOnClickListener(on);
    }

    private void startHomeActivity() {
        Intent homeIntent = new Intent(logIn.this, HomeActivity.class );
        startActivity(homeIntent );
    }


}
