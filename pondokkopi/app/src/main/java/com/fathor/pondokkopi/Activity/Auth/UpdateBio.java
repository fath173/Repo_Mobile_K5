package com.fathor.pondokkopi.Activity.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.fathor.pondokkopi.API.APIRequestUser;
import com.fathor.pondokkopi.API.APIUtils;
import com.fathor.pondokkopi.Model.Auth.AccountResponse;
import com.fathor.pondokkopi.Model.Auth.AccountUpdateProfile;
import com.fathor.pondokkopi.R;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateBio extends AppCompatActivity {

    private APIRequestUser apiRequestUser;
    private String name, gender, birthday, email, notelpon;
    private String bname, bgender, bbirthday, bemail, bnotelpon;
    private ImageView btnBack;
    private Button btnUpdateProfile;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_bio);
        apiRequestUser = APIUtils.getReqUser();
        String code = Preferences.getLoginToken(getBaseContext());
        token = "Bearer "+code;

        getDataProfile();
        findView();

        setTextProfile();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTextUpdate();
                updateBio();
                finish();
            }
        });
    }

    public void updateBio(){
        Integer id = Integer.parseInt(Preferences.getLoginId(getBaseContext()));
        AccountUpdateProfile profil = new AccountUpdateProfile();
        profil.setName(bname);
        profil.setGender(bgender);
        profil.setBirthday(bbirthday);
        profil.setEmail(bemail);
        profil.setPhone_number(bnotelpon);

        Call<AccountResponse> call = apiRequestUser.updateBio(id,token,profil);
        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                if (response.isSuccessful()){
                    String msg = response.body().getMessage();
                    Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getBaseContext(), "Gagal Update", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Gagal konek server", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getTextUpdate(){
        TextInputLayout nama = findViewById(R.id.al_detailalamat);
        bname = nama.getEditText().getText().toString();
        TextInputLayout gendr = findViewById(R.id.txt_gender);
        bgender = gendr.getEditText().getText().toString();
        TextInputLayout day = findViewById(R.id.txt_tgl);
        bbirthday = day.getEditText().getText().toString();
        TextInputLayout temail = findViewById(R.id.txt_email);
        bemail = temail.getEditText().getText().toString();
        TextInputLayout telpon = findViewById(R.id.txt_notelpon);
        bnotelpon = telpon.getEditText().getText().toString();
    }

    public void setTextProfile(){
        TextInputLayout nama = findViewById(R.id.al_detailalamat);
        nama.getEditText().setText(name);
        TextInputLayout gendr = findViewById(R.id.txt_gender);
        gendr.getEditText().setText(gender);
        TextInputLayout day = findViewById(R.id.txt_tgl);
        day.getEditText().setText(birthday);
        TextInputLayout temail = findViewById(R.id.txt_email);
        temail.getEditText().setText(email);
        TextInputLayout telpon = findViewById(R.id.txt_notelpon);
        telpon.getEditText().setText(notelpon);
    }

    public void getDataProfile(){
        name = getIntent().getStringExtra("name");
        gender = getIntent().getStringExtra("gender");
        birthday = getIntent().getStringExtra("birthday");
        email = getIntent().getStringExtra("email");
        notelpon = getIntent().getStringExtra("notelpon");
    }

    public void findView(){
        btnBack = findViewById(R.id.btn_back);
        btnUpdateProfile = findViewById(R.id.btn_updateProfile);
    }

}