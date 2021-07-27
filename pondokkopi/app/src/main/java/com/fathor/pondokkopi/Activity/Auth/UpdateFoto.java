package com.fathor.pondokkopi.Activity.Auth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.fathor.pondokkopi.API.APIRequestUser;
import com.fathor.pondokkopi.API.APIUtils;
import com.fathor.pondokkopi.Model.Auth.AccountResponse;
import com.fathor.pondokkopi.R;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateFoto extends AppCompatActivity {

    private APIRequestUser apiRequestUser;
    private Button btnPilih, btnUpload;
    private ImageView imgFoto, imgPreview;

    private Integer IMG_REQUEST = 21;
    private String token;
    String part_image;
    
    private Integer id_user;
    private String photo;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatefoto);
        

        String code = Preferences.getLoginToken(getBaseContext());
        token = "Bearer "+code;
        
        apiRequestUser = APIUtils.getReqUser();

        id_user = getIntent().getIntExtra("id_user", 0);
        photo = getIntent().getStringExtra("photo");
        
        btnPilih = findViewById(R.id.ft_btnpilih);
        btnUpload = findViewById(R.id.ft_btnupload);
        imgFoto = findViewById(R.id.ft_fotoimg);
        imgPreview = findViewById(R.id.ft_previewimg);

        Picasso.get().load("https://wsjti.id/jembercoffecentre/public/files/img-users/"+photo).into(imgFoto);

        btnPilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, IMG_REQUEST);
            }
        });
        
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFoto();
                finish();
            }
        });
        
    }

    public void uploadFoto(){
        File f = new File(part_image);
        RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"),f);
        MultipartBody.Part partImage = MultipartBody.Part.createFormData("image", f.getName(),reqBody);

        Call<AccountResponse> call = apiRequestUser.updatePhoto(id_user,token,partImage);
        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                if (response.isSuccessful()){
                    String msg = response.body().getMessage();
                    Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getBaseContext(), "Gagal Update Photo", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Gagal konek server: "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            if (requestCode == IMG_REQUEST) {
                Uri dataimage = data.getData();
                String[] imageprojection = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(dataimage, imageprojection, null, null, null);

                if (cursor != null) {
                    cursor.moveToFirst();
                    int indexImage = cursor.getColumnIndex(imageprojection[0]);
                    part_image = cursor.getString(indexImage);

                    if (part_image != null) {
                        File image = new File(part_image);
                        imgPreview.setImageBitmap(BitmapFactory.decodeFile(image.getAbsolutePath()));
                    }
                }
            }
        }
        
    }
}