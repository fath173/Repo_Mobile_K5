package com.fathor.pondokkopi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fathor.pondokkopi.Model.Cart.CartData;
import com.fathor.pondokkopi.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.CheckoutViewHolder> {
    private Context context;
    private List<CartData> cartList;

    public CheckoutAdapter(Context context, List<CartData> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public CheckoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_item_checkout, parent, false);
        return new CheckoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutViewHolder holder, int position) {
        CartData item = cartList.get(position);
        String url_img = item.getGambar();
        Picasso.get().load("https://wsjti.id/jembercoffecentre/public/files/product/"+url_img).into(holder.imgProduk);
        holder.namaProduk.setText(item.getNamaProduk());
        holder.namaVariasi.setText(item.getNamaVariasi()+" "+item.getBerat()+"g");
        holder.jumlah.setText("x"+item.getJumlah().toString());
        holder.hargaProduk.setText(formatRupiah(Double.parseDouble(item.getHarga().toString())));
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class CheckoutViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduk;
        TextView namaProduk, namaVariasi, jumlah, hargaProduk;
        public CheckoutViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduk = itemView.findViewById(R.id.check_imgproduk);
            namaProduk = itemView.findViewById(R.id.check_namaproduk);
            namaVariasi = itemView.findViewById(R.id.check__variasi);
            jumlah = itemView.findViewById(R.id.check_qty);
            hargaProduk = itemView.findViewById(R.id.check_hargaproduk);
        }
    }
    public String formatRupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }
}
