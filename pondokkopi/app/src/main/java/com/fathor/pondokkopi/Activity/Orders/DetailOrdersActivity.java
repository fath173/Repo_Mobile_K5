package com.fathor.pondokkopi.Activity.Orders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fathor.pondokkopi.API.APIRequestOrders;
import com.fathor.pondokkopi.API.APIRequestUser;
import com.fathor.pondokkopi.API.APIUtils;
import com.fathor.pondokkopi.Activity.Auth.Preferences;
import com.fathor.pondokkopi.Adapter.DetailordersAdapter;
import com.fathor.pondokkopi.Model.Auth.AccountData;
import com.fathor.pondokkopi.Model.Auth.AccountResponse;
import com.fathor.pondokkopi.Model.Orders.OrdersRincian;
import com.fathor.pondokkopi.Model.Orders.OrdersResponse;
import com.fathor.pondokkopi.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailOrdersActivity extends AppCompatActivity {

    private Integer id_order, id_user, testi;
    private String status, alamatKirim, ongkosKirim, subtotal, totalPesanan, buktiBayar;

    private APIRequestOrders apiRequestOrders;
    private APIRequestUser apiRequestUser;

    private List<OrdersRincian> detailList = new ArrayList<>();
    private List<AccountData> dataUser = new ArrayList<>();
    private RecyclerView recyclerView;

    private TextView doNamaUser, doAlamat, doSubtotal, doOngkir, doTotal;
    private ImageView doBukti, doBack;
    private Button doBtnBatal, doBtnDiterima, doBtnTesti;
//    private String namaUser;

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailorders);

        getIntentOrder();
        getFindView();

        apiRequestOrders = APIUtils.getReqOrders();
        apiRequestUser = APIUtils.getReqUser();
        String code = Preferences.getLoginToken(getBaseContext());
        token = "Bearer "+code;

        buildListData(id_order);


    }


    public void setfindView(){
        String belum = "belum bayar";
        String verifi = "verifikasi";
        String sudah = "sudah bayar";
        String dikirim = "sedang dikirim";
        String selesai = "selesai";
        String batal = "dibatalkan";
        if (status.equals(belum)){
            doBtnBatal.setVisibility(View.VISIBLE);
        }else if(status.equals(dikirim)){
            doBtnDiterima.setVisibility(View.VISIBLE);
        }else if (status.equals(selesai) && testi == 0){
            doBtnTesti.setVisibility(View.VISIBLE);
            doBtnTesti.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(DetailOrdersActivity.this, TestimoniOrdersActivity.class);
                    i.putExtra("id_order", id_order);
                    DetailOrdersActivity.this.startActivity(i);
                }
            });
        }

        doSubtotal.setText(formatRupiah(Double.parseDouble(subtotal)));
        doAlamat.setText(alamatKirim);
        doOngkir.setText(formatRupiah(Double.parseDouble(ongkosKirim)));
        doTotal.setText(formatRupiah(Double.parseDouble(totalPesanan)));
        List<AccountData> user = dataUser;
        doNamaUser.setText(user.get(0).getName());
        if (!TextUtils.isEmpty(buktiBayar)){
            doBukti.setVisibility(View.VISIBLE);
            String url_img = buktiBayar;
            Picasso.get().load("https://wsjti.id/jembercoffecentre/public/files/payment/"+url_img).into(doBukti);
        }
        doBtnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelOrder(id_order);
            }
        });
        doBtnDiterima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmOrder(id_order);
            }
        });


        doBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void initRecyclerView(){
        recyclerView = findViewById(R.id.do_recycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DetailOrdersActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    public void buildListData(int id){
        Call<OrdersResponse> call = apiRequestOrders.getOrderDetail(id,token);
        call.enqueue(new Callback<OrdersResponse>() {
            @Override
            public void onResponse(Call<OrdersResponse> call, Response<OrdersResponse> response) {
                if (response.isSuccessful()){
//                    String msg = response.body().getMessage();
                    detailList = response.body().getRincianOrder();
                    subtotal = response.body().getSubTotal().toString();
                    testi = response.body().getTesti();
                    status = response.body().getStatus();
                    initRecyclerView();

                    DetailordersAdapter adapter = new DetailordersAdapter(DetailOrdersActivity.this, detailList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    loadAccount(id_user);
                }else{
                    Toast.makeText(getBaseContext(), "Gagal ambil detail order", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<OrdersResponse> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Gagal konek server: "+ t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void loadAccount(int id_user){

        Call<AccountResponse> call = apiRequestUser.getUser(id_user,token);
        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                if (response.isSuccessful()){
                    String message = response.body().getMessage();
                    dataUser = response.body().getUser();
                    setfindView(); // dipanggil di Call nya iniii ingettttt

//                    Toast.makeText(getBaseContext(),"Sukses: "+ message, Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getBaseContext(),"Gagal ambil user", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Gagal Konek server: " + t, Toast.LENGTH_LONG).show();
            }
        });

    }

    public void cancelOrder(int id){
        Call<OrdersResponse> call = apiRequestOrders.putOrderCancel(id,token);
        call.enqueue(new Callback<OrdersResponse>() {
            @Override
            public void onResponse(Call<OrdersResponse> call, Response<OrdersResponse> response) {
                if (response.isSuccessful()){
                    String msg = response.body().getMessage();
                    Toast.makeText(getBaseContext(), "Pesan: "+ msg, Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getBaseContext(), "Gagal cancel order", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<OrdersResponse> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Gagal konek server: "+ t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void confirmOrder(int id){
        Call<OrdersResponse> call = apiRequestOrders.putOrderConfirm(id,token);
        call.enqueue(new Callback<OrdersResponse>() {
            @Override
            public void onResponse(Call<OrdersResponse> call, Response<OrdersResponse> response) {
                if (response.isSuccessful()){
                    String msg = response.body().getMessage();
                    finish();
                    Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getBaseContext(), "Gagal cancel order", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<OrdersResponse> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Gagal konek server: "+ t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getIntentOrder(){
        id_order = getIntent().getIntExtra("id_order", 0);
        id_user = getIntent().getIntExtra("id_user", 0);
//        status = getIntent().getStringExtra("status");
        alamatKirim = getIntent().getStringExtra("alamat_kirim");
        ongkosKirim = getIntent().getStringExtra("ongkir");
        totalPesanan = getIntent().getStringExtra("total");
        buktiBayar = getIntent().getStringExtra("bukti_bayar");
    }

    public void getFindView(){
        doNamaUser = findViewById(R.id.check_namauser);
        doAlamat = findViewById(R.id.check_alamat);
        doSubtotal = findViewById(R.id.do_subtotal);
        doOngkir = findViewById(R.id.do_ongkir);
        doTotal = findViewById(R.id.do_total);
        doBukti = findViewById(R.id.do_bukti);
        doBtnBatal = findViewById(R.id.do_btnbatal);
        doBtnDiterima = findViewById(R.id.do_btnditerima);
        doBtnTesti = findViewById(R.id.do_btntesti);
        doBack = findViewById(R.id.do_back);

    }

    private String formatRupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }

}