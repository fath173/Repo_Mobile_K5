package com.fathor.pondokkopi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fathor.pondokkopi.API.APIRequestOrders;
import com.fathor.pondokkopi.API.APIUtils;
import com.fathor.pondokkopi.Model.Orders.OrdersRincian;
import com.fathor.pondokkopi.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class DetailordersAdapter extends RecyclerView.Adapter<DetailordersAdapter.DetailViewHolder> {

    APIRequestOrders apiRequestOrders;
    Context context;
    List<OrdersRincian> detailList;

    public DetailordersAdapter(@NonNull Context context, @NonNull List<OrdersRincian> detailsList){
        this.context = context;
        this.detailList = detailsList;
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_item_detailorder, parent, false);
        apiRequestOrders = APIUtils.getReqOrders();
        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, int position) {
        holder.doNamaProduk.setText(detailList.get(position).getNama_produk());
        holder.doVariasi.setText(detailList.get(position).getNama_variasi() + " " + detailList.get(position).getBerat().toString() + "g");
        holder.doQty.setText("x"+detailList.get(position).getQty().toString());
        holder.doHargaProduk.setText(formatRupiah(Double.parseDouble(detailList.get(position).getHarga().toString())) );
        String url_img = detailList.get(position).getGambar_mobile();
        Picasso.get().load("https://wsjti.id/jembercoffecentre/public/files/product/"+url_img).into(holder.doGambarProduk);
    }

    @Override
    public int getItemCount() {
        return detailList.size();
    }

    public class DetailViewHolder extends RecyclerView.ViewHolder {
        private ImageView doGambarProduk;
        private TextView doNamaProduk, doVariasi, doQty, doHargaProduk;

        public DetailViewHolder(@NonNull View itemView) {
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
