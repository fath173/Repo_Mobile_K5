package com.fathor.pondokkopi.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fathor.pondokkopi.API.APIRequestProducts;
import com.fathor.pondokkopi.API.APIUtils;
import com.fathor.pondokkopi.Model.Cart.CartData;
import com.fathor.pondokkopi.Model.Products.ProductDetailResponse;
import com.fathor.pondokkopi.Model.Products.ProductVariasi;
import com.fathor.pondokkopi.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductDetails extends AppCompatActivity {
    public APIRequestProducts apiRequestProducts;
    private TextView namaProduk, deskProduk, stokProduk, hargaProduk;
    private ImageView gambarProduk;
    private ImageView btnBackHome;
    private Button btnKeranjang;

    private List<ProductVariasi> variasi = new ArrayList<>();
    private Integer productId;
    private String nama, gambar, deskripsi, totalStok;
    private RadioGroup radioGroup;
    private  RadioButton radioButton;
    List<CartData> cartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        apiRequestProducts = APIUtils.getReqProducts();
        productId = getIntent().getIntExtra("id_product", 0);

        getDataProduct();
        getProductDetail(productId);
        findView();
        setViewProduct();

        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnKeranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();

                int selectIdProduct = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(selectIdProduct);
                List<ProductVariasi> pList = variasi;
                for(int i = 0; i<=pList.size()-1; i++){
                    if (selectIdProduct == pList.get(i).getId_variasi()){
                        int pId = pList.get(i).getId_variasi();
                        String pImg = gambar;
                        String pNamaProd = nama;
                        String pNamaVar = pList.get(i).getNama_variasi();
                        int pBerat = pList.get(i).getBerat();
                        int pHarga = pList.get(i).getHarga();


                        List<CartData> cList = cartList;
                        for(int j = 0; j<=cList.size()-1; j++){
                            if (pId == cList.get(j).getId_variasi()){
                                int cJumlah = cList.get(j).getJumlah();
                                int cHasilJum = cJumlah + 1;
                                cList.get(j).setJumlah(cHasilJum);
                                int cSubtotal = cHasilJum * pHarga;
                                cList.get(j).setSubtotal(cSubtotal);
                                saveData();
                                return;
                            }
                        }

                        cartList.add(new CartData(pId,pImg,pNamaProd,pNamaVar,pBerat,1,pHarga, pHarga));
                        saveData();
                        Toast.makeText(getBaseContext(), "Success Add to Cart", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

            }
        });
    }

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("Cart", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cartList);
        editor.putString("CartList", json);
        editor.apply();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("Cart", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("CartList", null);
        Type type = new TypeToken<List<CartData>>(){}.getType();
        cartList = gson.fromJson(json,type);

        if (cartList == null){
            cartList = new ArrayList<>();
        }
    }

    public void setViewProduct(){
        namaProduk.setText(nama);
        deskProduk.setText(deskripsi);
        String url_img = gambar;
        Picasso.get().load("https://wsjti.id/jembercoffecentre/public/files/product/"+url_img).into(gambarProduk);
    }


    private void getDataProduct(){
        nama = getIntent().getStringExtra("nama_product");
        gambar = getIntent().getStringExtra("gambar_product");
        deskripsi = getIntent().getStringExtra("deskripsi_product");
    }

    private void findView(){
        gambarProduk = findViewById(R.id.img_produk);
        namaProduk = findViewById(R.id.pd_namaproduk);
        deskProduk = findViewById(R.id.pd_deskproduk);
        stokProduk = findViewById(R.id.pd_stok);
        hargaProduk = findViewById(R.id.pd_harga);
        btnBackHome = findViewById(R.id.btn_backHome);
        btnKeranjang = findViewById(R.id.btn_keranjang);
    }

    private void buatRadioButtonDua(){
        List<ProductVariasi> vList = variasi;
        radioGroup = findViewById(R.id.pd_radiobutton);
        for(int i = 0; i<=vList.size()-1; i++){
            radioButton = new RadioButton(this);
            radioButton.setText(vList.get(i).getBerat()+"g");
            radioButton.setId(vList.get(i).getId_variasi());
            radioGroup.addView(radioButton);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selecteId = radioGroup.getCheckedRadioButtonId();
                for(int i = 0; i<=vList.size()-1; i++){
                    if (selecteId == vList.get(i).getId_variasi()){
                        Integer stok = vList.get(i).getStok();
                        Integer harga = vList.get(i).getHarga();
                        stokProduk.setText(stok.toString()+" buah");
                        hargaProduk.setText(formatRupiah(Double.parseDouble(harga.toString())));
                    }
                }
            }
        });
        
    }

    public  void getProductDetail(int id){

        Call<ProductDetailResponse> call = apiRequestProducts.getProductsDetails(id);
        call.enqueue(new Callback<ProductDetailResponse>() {
            @Override
            public void onResponse(Call<ProductDetailResponse> call, Response<ProductDetailResponse> response) {
                if (response.body() != null){
                    variasi = response.body().getVariasi();
                    totalStok = response.body().getTotalStok().toString();
                    stokProduk.setText(totalStok+" buah");
                    buatRadioButtonDua();
                }
            }

            @Override
            public void onFailure(Call<ProductDetailResponse> call, Throwable t) {
                Toast.makeText(ProductDetails.this, "Gagal Konek server: " + t, Toast.LENGTH_LONG).show();
            }
        });
    }

    private String formatRupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }

}