package com.fathor.pondokkopi.Activity.Orders;

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
import android.widget.TextView;
import android.widget.Toast;

import com.fathor.pondokkopi.API.APIRequestOrders;
import com.fathor.pondokkopi.API.APIUtils;
import com.fathor.pondokkopi.Activity.Auth.Preferences;
import com.fathor.pondokkopi.Model.Orders.OrdersResponse;
import com.fathor.pondokkopi.R;

import java.io.File;
import java.text.NumberFormat;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {

    private APIRequestOrders apiRequestOrders;
    private Integer id_order, id_user, total;
    private TextView payTotal;
    private ImageView payImage, payBtnBack;
    private Button payBtnPilih, payBtnUpload;
    private Integer IMG_REQUEST = 21;
    private String token;
    private String part_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        String code = Preferences.getLoginToken(getBaseContext());
        token = "Bearer "+code;
        apiRequestOrders = APIUtils.getReqOrders();

        id_order = getIntent().getIntExtra("id_order", 0);
        id_user = getIntent().getIntExtra("id_user", 0);
        total = getIntent().getIntExtra("total", 0);

        payBtnBack = findViewById(R.id.pay_btnback);
        payTotal = findViewById(R.id.pay_tvtotal);
        payBtnPilih = findViewById(R.id.pay_btnpilihimg);
        payBtnUpload = findViewById(R.id.pay_btnuploadimg);
        payImage = findViewById(R.id.pay_image);

        payTotal.setText(formatRupiah(Double.parseDouble(total.toString())));

        payBtnPilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, IMG_REQUEST);
            }
        });

        payBtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        payBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void uploadImage(){
        File imagefile = new File(part_image);
        RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"),imagefile);
        MultipartBody.Part partImage = MultipartBody.Part.createFormData("image", imagefile.getName(),reqBody);

        Call<OrdersResponse> call = apiRequestOrders.postOrderPayment(id_order,token,partImage);
        call.enqueue(new Callback<OrdersResponse>() {
            @Override
            public void onResponse(Call<OrdersResponse> call, Response<OrdersResponse> response) {
                if (response.isSuccessful()){
                    String msg = response.body().getMessage();
                    Toast.makeText(getBaseContext(), "pesan: "+msg, Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getBaseContext(), "Gagal input payment", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<OrdersResponse> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Gagal konek server: "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null)
        {
            if(requestCode == IMG_REQUEST)
            {
                Uri dataimage = data.getData();
                String[] imageprojection = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(dataimage,imageprojection,null,null,null);

                if (cursor != null)
                {
                    cursor.moveToFirst();
                    int indexImage = cursor.getColumnIndex(imageprojection[0]);
                    part_image = cursor.getString(indexImage);

                    if(part_image != null)
                    {
                        File image = new File(part_image);
                        payImage.setImageBitmap(BitmapFactory.decodeFile(image.getAbsolutePath()));
                    }
                }
            }
        }

    }

    private String formatRupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }

}