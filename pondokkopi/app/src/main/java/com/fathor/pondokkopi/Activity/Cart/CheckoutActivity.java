package com.fathor.pondokkopi.Activity.Cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fathor.pondokkopi.API.APIRequestCarts;
import com.fathor.pondokkopi.API.APIRequestUser;
import com.fathor.pondokkopi.API.APIUtils;
import com.fathor.pondokkopi.Activity.Auth.Preferences;
import com.fathor.pondokkopi.Activity.MainActivity;
import com.fathor.pondokkopi.Adapter.CheckoutAdapter;
import com.fathor.pondokkopi.Model.Auth.AccountData;
import com.fathor.pondokkopi.Model.Auth.AccountResponse;
import com.fathor.pondokkopi.Model.Cart.CartData;
import com.fathor.pondokkopi.Model.Cart.Checkout;
import com.fathor.pondokkopi.Model.Cart.CheckoutDetail;
import com.fathor.pondokkopi.Model.Cart.CheckoutResponse;
import com.fathor.pondokkopi.Model.Ongkir.DataOngkir;
import com.fathor.pondokkopi.Model.Ongkir.DataOngkirRequest;
import com.fathor.pondokkopi.Model.Ongkir.DataOngkirResponse;
import com.fathor.pondokkopi.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {

    private String id_user, token;
    private APIRequestUser apiRequestUser;
    private APIRequestCarts apiRequestCarts;

    private List<AccountData> dataUser = new ArrayList<>();
    private List<CartData> cartList;
    private List<DataOngkir> ongkirList = new ArrayList<>();

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private CheckoutAdapter adapter;

    private Integer   district, weight=0, subtotal=0, biayaOngkir = 0, grandTotal = 0;
    private String alamatKirim, keterangan;

    private TextView checkNamauser, checkAlamat, checkSubtotal, checkOngkir, checkGrandtotal, checkGrandtotal2;
    private RadioGroup checkRadiogrup;
    private RadioButton checkRadioButton;
    private Button checkBtnBuatpesanan;
    private ImageView checkBtnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        apiRequestUser = APIUtils.getReqUser();
        apiRequestCarts = APIUtils.getReqCarts();

        getfindView();
        prepareData();
        ambilCart();
        setDataCart();
        ambilAkun(Integer.parseInt(id_user),token);

        initRecyclerView(); // untuk cartList


    }

    private void prepareData(){
        id_user = Preferences.getLoginId(getBaseContext());
        String code = Preferences.getLoginToken(getBaseContext());
        token = "Bearer "+code;

        checkBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void setDataCart(){
        List<CartData> cart = cartList;

        for (int i=0; i<cart.size(); i++){
            subtotal += cart.get(i).getSubtotal();
            weight += cart.get(i).getBerat();
        }
        checkSubtotal.setText(formatRupiah(Double.parseDouble(subtotal.toString())));
    }

    public void setInfoUser(){
        List<AccountData> user = dataUser;
        checkNamauser.setText(user.get(0).getName());
        checkAlamat.setText(user.get(0).getAddress());
        district = user.get(0).getDistrict();
        alamatKirim = user.get(0).getAddress();
    }

    private void initRecyclerView(){
        recyclerView = findViewById(R.id.check_recyclercart);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);

        adapter = new CheckoutAdapter(getBaseContext(), cartList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void getfindView(){
        checkNamauser = findViewById(R.id.check_namauser);
        checkAlamat = findViewById(R.id.check_alamat);
        checkSubtotal = findViewById(R.id.check_subtotal);
        checkOngkir = findViewById(R.id.check_ongkir);
        checkGrandtotal = findViewById(R.id.check_grandtotal);
        checkGrandtotal2 = findViewById(R.id.check_grandtotal2);
        checkRadiogrup = findViewById(R.id.check_radiogrup);
        checkBtnBuatpesanan = findViewById(R.id.check_btnpesanan);
        checkBtnBack = findViewById(R.id.check_btnback);
    }

    private void ambilCart(){
        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("Cart", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("CartList", null);
        Type type = new TypeToken<List<CartData>>(){}.getType();
        cartList = gson.fromJson(json,type);

        if (cartList == null){
            cartList = new ArrayList<>();
        }
    }

    private void setDataOngkir(){
        List<DataOngkir> oList = ongkirList;
        checkRadiogrup = findViewById(R.id.check_radiogrup);

        for(int i = 0; i<=oList.size()-1; i++){
            checkRadioButton = new RadioButton(this);
            keterangan = oList.get(i).getKurir().toUpperCase() +" "+oList.get(i).getServis() +" "+formatRupiah(Double.parseDouble(oList.get(i).getBiaya().toString())) +" "+oList.get(i).getEstimasi() +"hari";
            checkRadioButton.setText(keterangan);
            checkRadioButton.setId(oList.get(i).getIdkurir());
            checkRadiogrup.addView(checkRadioButton);
        }
        
        checkRadiogrup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectId = checkRadiogrup.getCheckedRadioButtonId();
                for(int i = 0; i<=oList.size()-1; i++){
                    if (selectId == oList.get(i).getIdkurir()){
                        biayaOngkir = oList.get(i).getBiaya();
                        checkOngkir.setText(formatRupiah(Double.parseDouble(biayaOngkir.toString())));
                        grandTotal = subtotal+biayaOngkir;
                        checkGrandtotal.setText(formatRupiah(Double.parseDouble(grandTotal.toString())));
                        checkGrandtotal2.setText(formatRupiah(Double.parseDouble(grandTotal.toString())));
                    }
                }
            }
        });

        checkBtnBuatpesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (biayaOngkir == 0 && biayaOngkir == 0 && grandTotal == 0){
                    Toast.makeText(getBaseContext(),"Pilih ongkir terlebih dahulu",Toast.LENGTH_SHORT).show();
                }else {
                    buatPesanan();

                }
            }
        });
    }

    public String formatRupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }

    private void ambilAkun(int id,String token){
        Call<AccountResponse> call = apiRequestUser.getUser(id,token);
        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                if (response.isSuccessful()){
                    String message = response.body().getMessage();
                    dataUser = response.body().getUser();
                    setInfoUser(); // dipanggil di Call nya iniii ingettttt
                    ambilOngkir(token);

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

    private void ambilOngkir(String token){
        DataOngkirRequest doRequest = new DataOngkirRequest();
        doRequest.setDistrict(district);
        doRequest.setSumweight(weight);
        Call<DataOngkirResponse> call = apiRequestCarts.ambilOngkir(token, doRequest);
        call.enqueue(new Callback<DataOngkirResponse>() {
            @Override
            public void onResponse(Call<DataOngkirResponse> call, Response<DataOngkirResponse> response) {
                if (response.isSuccessful()){
                    String msg = response.body().getMessage();
                    ongkirList = response.body().getDataongkir();
                    setDataOngkir();
                }else{
                    Toast.makeText(getBaseContext(),"Gagal ambil ongkir", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DataOngkirResponse> call, Throwable t) {
                Toast.makeText(getBaseContext(),"Gagal konek server ongkir: "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void buatPesanan(){
        List<CheckoutDetail> dataVariasi = new ArrayList<>();
        List<CartData> cart = cartList;
        for (int i=0; i<cart.size(); i++){
            CheckoutDetail chkDetail = new CheckoutDetail();
            chkDetail.setId_variasi(cart.get(i).getId_variasi());
            chkDetail.setJumlah(cart.get(i).getJumlah());
            chkDetail.setSubtotal(cart.get(i).getSubtotal());

            dataVariasi.add(chkDetail);
        }

        Checkout checkout = new Checkout();
        checkout.setId_user(Integer.parseInt(id_user));
        checkout.setOngkir(biayaOngkir);
        checkout.setTotal(grandTotal);
        checkout.setKeterangan(keterangan);
        checkout.setAlamat_kirim(alamatKirim);
        checkout.setDataVariasi(dataVariasi);

        Call<CheckoutResponse> call = apiRequestCarts.kirimCheckout(token,checkout);
        call.enqueue(new Callback<CheckoutResponse>() {
            @Override
            public void onResponse(Call<CheckoutResponse> call, Response<CheckoutResponse> response) {
                if (response.isSuccessful()){
                    String msg = response.body().getMessage();
                    SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("Cart", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("CartList");
                    editor.apply();

                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                    finish();

                    Toast.makeText(getBaseContext(),msg,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getBaseContext(),"Gagal input Checkout",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<CheckoutResponse> call, Throwable t) {
                Toast.makeText(getBaseContext(),"Gagal konek server: "+ t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}