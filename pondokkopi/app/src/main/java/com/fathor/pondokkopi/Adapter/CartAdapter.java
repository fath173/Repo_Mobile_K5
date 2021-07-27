package com.fathor.pondokkopi.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fathor.pondokkopi.Model.Cart.CartData;
import com.fathor.pondokkopi.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartsViewHolder> {

    Context context;
    List<CartData> cartList;

    public CartAdapter(Context context, List<CartData> cartList){
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public CartsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_item_cart, parent, false);
        loadData();
        return new CartAdapter.CartsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartsViewHolder holder, int position) {
        holder.namaProduk.setText(cartList.get(position).getNamaProduk());
        holder.namaVariasi.setText(cartList.get(position).getNamaVariasi() + " "+ cartList.get(position).getBerat().toString()+"g" );
        holder.jumlah.setText(cartList.get(position).getJumlah().toString());
        holder.harga.setText(formatRupiah(Double.parseDouble(cartList.get(position).getHarga().toString())));
        holder.subtotal.setText(formatRupiah(Double.parseDouble(cartList.get(position).getSubtotal().toString())));
        String url_img = cartList.get(position).getGambar();
        Picasso.get().load("https://wsjti.id/jembercoffecentre/public/files/product/"+url_img).into(holder.imgProduk);
        holder.btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData(position);
            }
        });
        holder.btnIncres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incresData(cartList.get(position).getId_variasi());
            }
        });
        holder.btnDecres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decresData(cartList.get(position).getId_variasi(), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class CartsViewHolder extends RecyclerView.ViewHolder {
        TextView namaProduk, namaVariasi, jumlah, harga, subtotal;
        ImageView btnHapus, btnIncres, btnDecres;
        ImageView imgProduk;
        public CartsViewHolder(@NonNull View itemView) {
            super(itemView);
            namaProduk = itemView.findViewById(R.id.cart_namaproduk);
            namaVariasi = itemView.findViewById(R.id.cart_namavariasi);
            jumlah = itemView.findViewById(R.id.cart_jumlah);
            harga = itemView.findViewById(R.id.cart_hargasatuan);
            subtotal = itemView.findViewById(R.id.cart_subtotal);
            btnHapus = itemView.findViewById(R.id.cart_btnhapus);
            btnIncres = itemView.findViewById(R.id.cart_btnincres);
            btnDecres = itemView.findViewById(R.id.cart_btndecres);
            imgProduk = itemView.findViewById(R.id.cart_imgproduk);
        }
    }

    public void incresData(int pId){
        List<CartData> cList = cartList;
        for(int j = 0; j<=cList.size()-1; j++){
            if (pId == cList.get(j).getId_variasi()){
                int cJumlah = cList.get(j).getJumlah();
                int cHasilJum = cJumlah + 1;
                cList.get(j).setJumlah(cHasilJum);
                int cSubtotal = cHasilJum * cList.get(j).getHarga();
                cList.get(j).setSubtotal(cSubtotal);
                saveData();
                return;
            }
        }
    }
    public void decresData(int pId, int idPosisi){
        List<CartData> cList = cartList;
        for(int j = 0; j<=cList.size()-1; j++){
            if (pId == cList.get(j).getId_variasi()){
                int cJumlah = cList.get(j).getJumlah();
                int cHasilJum = cJumlah - 1;
                int cSubtotal = cHasilJum * cList.get(j).getHarga();
                if (cJumlah <= 1){
                    deleteData(idPosisi);
                    return;
                }else {
                    cList.get(j).setSubtotal(cSubtotal);
                    cList.get(j).setJumlah(cHasilJum);
                    saveData();
                }
                return;
            }
        }
    }

    public void deleteData(int id){
        List<CartData> cList = cartList;
        cList.remove(id);
        saveData();
    }

    private void saveData(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Cart", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cartList);
        editor.putString("CartList", json);
        editor.apply();
        notifyDataSetChanged();
    }
    
    public void loadData(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Cart", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("CartList", null);
        Type type = new TypeToken<List<CartData>>(){}.getType();
        cartList = gson.fromJson(json,type);

        if (cartList == null){
            cartList = new ArrayList<>();

        }
    }

    private String formatRupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }

}
