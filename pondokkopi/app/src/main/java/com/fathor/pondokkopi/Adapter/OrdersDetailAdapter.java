package com.fathor.pondokkopi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fathor.pondokkopi.API.APIUtils;
import com.fathor.pondokkopi.Model.Orders.OrdersAll;
import com.fathor.pondokkopi.Model.Orders.OrdersDetail;
import com.fathor.pondokkopi.Model.Orders.ProductAll;
import com.fathor.pondokkopi.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class OrdersDetailAdapter extends RecyclerView.Adapter<OrdersDetailAdapter.detailViewHolder> {

    Context context;
    List<OrdersDetail> detailList;
    List<ProductAll> productList;

    public OrdersDetailAdapter(@NonNull Context context, @NonNull List<OrdersDetail> details, @NonNull List<ProductAll> product) {
        this.context = context;
        this.detailList = details;
        this.productList = product;
    }
    @NonNull
    @Override
    public detailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_item_detailorder, parent, false);
        return new OrdersDetailAdapter.detailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull detailViewHolder holder, int position) {
//        Toast.makeText(context, "ARRAY: "+ productList.get(0).getNama_produk(), Toast.LENGTH_SHORT).show();

        for (int i=0; i<=productList.size()-1; i++){
            if (detailList.get(position).getId_variasi() == Integer.valueOf(productList.get(i).getId())){
                String url_img = productList.get(i).getGambar_mobile();
                Picasso.get().load("https://wsjti.id/jembercoffecentre/public/files/product/"+url_img).into(holder.doGambarProduk);
                holder.doNamaProduk.setText(productList.get(i).getNama_produk());
                holder.doVariasi.setText(productList.get(i).getNama_variasi() +" "+productList.get(i).getBerat()+"g");
                holder.doQty.setText("x"+detailList.get(position).getQty().toString());
                holder.doHargaProduk.setText(formatRupiah(Double.parseDouble(productList.get(i).getHarga().toString())));
            }
        };

    }

    @Override
    public int getItemCount() {
        return detailList.size();
    }

    public class detailViewHolder extends RecyclerView.ViewHolder {
        private ImageView doGambarProduk;
        private TextView doNamaProduk, doVariasi, doQty, doHargaProduk;
        public detailViewHolder(@NonNull View itemView) {
            super(itemView);
            doGambarProduk = itemView.findViewById(R.id.do_imgproduk);
            doNamaProduk = itemView.findViewById(R.id.do_namaproduk);
            doVariasi = itemView.findViewById(R.id.do_variasi);
            doQty = itemView.findViewById(R.id.do_qty);
            doHargaProduk = itemView.findViewById(R.id.do_hargaproduk);
        }
    }

    private String formatRupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }
}
