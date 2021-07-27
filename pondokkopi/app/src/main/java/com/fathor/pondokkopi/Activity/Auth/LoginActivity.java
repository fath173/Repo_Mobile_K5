package com.fathor.pondokkopi.Activity.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fathor.pondokkopi.API.APIRequestUser;
import com.fathor.pondokkopi.API.APIUtils;
import com.fathor.pondokkopi.Activity.MainActivity;
import com.fathor.pondokkopi.Model.Auth.LoginRequest;
import com.fathor.pondokkopi.Model.Auth.LoginResponse;
import com.fathor.pondokkopi.R;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public APIRequestUser apiRequestUser;
    private Button btnToRegister;
    private Button btnLogin;
    private String lgEmail;
    private String lgPassword;
    private TextView btnToLupapass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        apiRequestUser = APIUtils.getReqUser();

        findView();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayout email = findViewById(R.id.lg_email);
                lgEmail = email.getEditText().getText().toString();
                TextInputLayout pass = findViewById(R.id.lg_password);
                lgPassword = pass.getEditText().getText().toString();

                if (TextUtils.isEmpty(lgEmail) || TextUtils.isEmpty(lgPassword)){
                    Toast.makeText(LoginActivity.this,"Email dan Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }else{
//                    Toast.makeText(LoginActivity.this,"Email"+lgEmail +lgPassword, Toast.LENGTH_SHORT).show();
                    cekData();
                }
            }
        });
        btnToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
            }
        });

        btnToLupapass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, LupaPasswordActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
            }
        });

    }

    public void cekData(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(lgEmail);
        loginRequest.setPassword(lgPassword);

        Call<LoginResponse> loginResponseCall = apiRequestUser.userLogin(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.isSuccessful()){
                    String id = response.body().getId().toString();
                    String email = response.body().getEmail();
                    String token = response.body().getToken();

                    Preferences.setLoginId(getBaseContext(), id);
                    Preferences.setLoginEmail(getBaseContext(), email);
                    Preferences.setLogInToken(getBaseContext(), token);
                    Preferences.setLoginStatus(getBaseContext(), true);

//                    Toast.makeText(LoginActivity.this,"Login Succesfuly; ID: "+id+"; Email: "+email+"Token: "+ token, Toast.LENGTH_LONG).show();

                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this,"Login Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"Gagal konek server: "+ t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    public void findView(){
        btnLogin = findViewById(R.id.btn_login);
        btnToRegister = findViewById(R.id.btn_toRegister);
        btnToLupapass = findViewById(R.id.btn_lupapass);
    }
}