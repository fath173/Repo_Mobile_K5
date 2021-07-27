package com.fathor.pondokkopi.Activity.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fathor.pondokkopi.API.APIRequestUser;
import com.fathor.pondokkopi.API.APIUtils;
import com.fathor.pondokkopi.Model.Auth.RegisterRequest;
import com.fathor.pondokkopi.Model.Auth.RegisterResponse;
import com.fathor.pondokkopi.R;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    public APIRequestUser apiRequestUser;

    private Button btnToLogin, btnDaftar;
    private String rgNama, rgEmail, rgPas, rgPass;
    private View fokus = null;
    private Boolean cancel = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        apiRequestUser = APIUtils.getReqUser();

        findView();
        btnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                razia();
            }
        });


    }

    public void razia(){
        TextInputLayout nama = findViewById(R.id.rg_nama);
        rgNama = nama.getEditText().getText().toString();
        TextInputLayout email = findViewById(R.id.rg_email);
        rgEmail = email.getEditText().getText().toString();
        TextInputLayout pas = findViewById(R.id.rg_pas);
        rgPas = pas.getEditText().getText().toString();
        TextInputLayout pass = findViewById(R.id.rg_pass);
        rgPass = pass.getEditText().getText().toString();

        nama.setError(null);
        pas.setError(null);
        pass.setError(null);

        if (TextUtils.isEmpty(rgNama)){
            nama.setError("This field is required");
            fokus = nama;
            cancel = true;
        }

        if (TextUtils.isEmpty(rgPas)){
            pas.setError("This field is required");
            fokus = pas;
            cancel = true;
        }else if (!cekPassword(rgPas,rgPass)){
            pass.setError("This password is incorrect");
            fokus = pass;
            cancel = true;
        }

        if (TextUtils.isEmpty(rgEmail)){
            email.setError("This field is required");
            fokus = email;
            cancel = true;
        }

        if (cancel){
            fokus.requestFocus();
        }else{
            cekUser();
        }

    }

    public void findView(){
        btnToLogin = findViewById(R.id.btn_toLogin);
        btnDaftar = findViewById(R.id.btn_daftar);
    }
    private boolean cekPassword(String password, String repassword){
        return password.equals(repassword);
    }

    private void cekUser(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName(rgNama);
        registerRequest.setEmail(rgEmail);
        registerRequest.setPassword(rgPas);
        registerRequest.setPassword_confirmation(rgPass);

        Call<RegisterResponse> call = apiRequestUser.userRegister(registerRequest);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()){
                    String message = response.body().getMessage();
                    finish();
                    Toast.makeText(getBaseContext(),"Verifikasi Pada Email Anda", Toast.LENGTH_LONG).show();
                }else {
                    TextInputLayout email = findViewById(R.id.rg_email);
                    rgEmail = email.getEditText().getText().toString();
                    email.setError("This Email is already exist");
                    fokus = email;
                    cancel = true;
                    Toast.makeText(getBaseContext(),"Gagal Registrasi: Email anda sudah terpakai", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
//                Toast.makeText(getBaseContext(),"Gagal konek server: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Toast.makeText(getBaseContext(),"Verifikasi Pada Email Anda", Toast.LENGTH_LONG).show();
                finish();
            }
        });


    }

}