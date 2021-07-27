package com.fathor.pondokkopi.Activity.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fathor.pondokkopi.R;

public class LupaPasswordActivity extends AppCompatActivity {

    private TextView btnToLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupapassword);
        btnToLogin = findViewById(R.id.lupa_btntologin);
        btnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}