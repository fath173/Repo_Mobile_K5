package com.fathor.pondokkopi.Activity.Orders;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.fathor.pondokkopi.API.APIRequestOrders;
import com.fathor.pondokkopi.API.APIUtils;
import com.fathor.pondokkopi.Activity.Auth.Preferences;
import com.fathor.pondokkopi.Model.Orders.OrdersResponse;
import com.fathor.pondokkopi.Model.Orders.OrdersUpdateTesti;
import com.fathor.pondokkopi.R;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestimoniOrdersActivity extends AppCompatActivity {
    private APIRequestOrders apiRequestOrders;
    private Integer id_order, id_user;
    private String kesan;
    private View fokus = null;
    private Boolean cancel = false;
    private Button btnKirim;
    private ImageView btnBack;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testimoniorders);
        apiRequestOrders = APIUtils.getReqOrders();
        String code = Preferences.getLoginToken(getBaseContext());
        token = "Bearer "+code;

        getIntentDetail();

        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                razia();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void razia(){
        TextInputLayout testi = findViewById(R.id.ts_inputkesan);
        kesan = testi.getEditText().getText().toString();

        testi.setError(null);

        if (TextUtils.isEmpty(kesan)){
            testi.setError("This field is required");
            fokus = testi;
            cancel = true;
        }

        if (cancel){
            fokus.requestFocus();
        }else{
            inputKesan();
            finish();
        }

    }

    public void inputKesan(){
        String id = Preferences.getLoginId(getBaseContext());
        OrdersUpdateTesti testi = new OrdersUpdateTesti();
        testi.setId_user(id);
        testi.setKesan(kesan);

        Call<OrdersResponse> call = apiRequestOrders.putOrderUptesti(id_order, testi, token);
        call.enqueue(new Callback<OrdersResponse>() {
            @Override
            public void onResponse(Call<OrdersResponse> call, Response<OrdersResponse> response) {
                if (response.isSuccessful()){
                    String msg = response.body().getMessage();
                    Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getBaseContext(), "Gagal Update Testi", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<OrdersResponse> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Gagal konek server", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void getIntentDetail(){
        id_order = getIntent().getIntExtra("id_order", 0);
        btnKirim = findViewById(R.id.ts_btnkirim);
        btnBack = findViewById(R.id.ts_btnback);
    }
}