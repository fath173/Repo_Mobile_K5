package com.fathor.pondokkopi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fathor.pondokkopi.API.APIRequestOrders;
import com.fathor.pondokkopi.API.APIUtils;
import com.fathor.pondokkopi.Activity.Orders.DetailOrdersActivity;
import com.fathor.pondokkopi.Activity.Orders.PaymentActivity;
import com.fathor.pondokkopi.Model.Orders.OrdersAll;
import com.fathor.pondokkopi.Model.Orders.OrdersDetail;
import com.fathor.pondokkopi.Model.Orders.ProductAll;
import com.fathor.pondokkopi.R;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder> {

    Context context;
//    APIRequestOrders apiRequestOrders;
    List<OrdersAll> ordersAll;
    List<OrdersDetail> ordersDetails = new ArrayList<>();
    List<ProductAll> productAllList = new ArrayList<>();

    private RecyclerView recyclerView;
    public View view;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public OrdersAdapter(@NonNull Context context, @NonNull List<OrdersAll> ordersAlls, List<ProductAll> product) {
        this.context = context;
        this.ordersAll = ordersAlls;
        this.productAllList = product;
    }

    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_item_orders, parent, false);

        return new OrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {
        ordersDetails = ordersAll.get(position).getOrder_detail();
        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.odRvDetail.getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setInitialPrefetchItemCount(ordersAll.get(position).getOrder_detail().size());

        OrdersDetailAdapter odAdapter = new OrdersDetailAdapter(holder.odRvDetail.getContext(), ordersDetails, productAllList);
        holder.odRvDetail.setLayoutManager(layoutManager);
        holder.odRvDetail.setAdapter(odAdapter);
        holder.odRvDetail.setRecycledViewPool(viewPool);


        holder.odStatus.setText(ordersAll.get(position).getStatus());
        holder.odTotal.setText(formatRupiah(Double.parseDouble(ordersAll.get(position).getTotal().toString())) );
        String tglLama=ordersAll.get(position).getTgl_pesan();
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        try {
            String tglBaru=dateFormat.format(df.parse(tglLama));
            holder.odTanggal.setText(tglBaru+" ");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String status = ordersAll.get(position).getStatus();
        String belum = "belum bayar";
        if (status.equals(belum)){
            holder.odBtnBayar.setVisibility(View.VISIBLE);
            holder.odBtnBayar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, PaymentActivity.class);
                    i.putExtra("id_order", ordersAll.get(position).getId());
                    i.putExtra("id_user", ordersAll.get(position).getId_user());
                    i.putExtra("status", ordersAll.get(position).getStatus());
                    i.putExtra("total", ordersAll.get(position).getTotal());
                    context.startActivity(i);
                }
            });
        }

        holder.odBtnRincian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "ARRAY: "+ ordersDetails.get(0).getId_variasi(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, DetailOrdersActivity.class);
                i.putExtra("id_order", ordersAll.get(position).getId());
                i.putExtra("id_user", ordersAll.get(position).getId_user());
                i.putExtra("status", ordersAll.get(position).getStatus());
                i.putExtra("alamat_kirim", ordersAll.get(position).getAlamat_kirim());
                i.putExtra("ongkir", ordersAll.get(position).getOngkir().toString());
                i.putExtra("total", ordersAll.get(position).getTotal().toString());
                i.putExtra("bukti_bayar", ordersAll.get(position).getBukti_bayar());
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return ordersAll.size();
    }


    public static final class OrdersViewHolder extends RecyclerView.ViewHolder {
        TextView odTanggal, odStatus, odTotal, odIdVariasi;
        Button odBtnRincian, odBtnBayar;
        RecyclerView odRvDetail;
        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            odTanggal = itemView.findViewById(R.id.od_tanggal);
            odStatus = itemView.findViewById(R.id.od_status);
            odTotal = itemView.findViewById(R.id.od_total);
            odBtnRincian = itemView.findViewById(R.id.od_btnrincian);
            odBtnBayar = itemView.findViewById(R.id.od_btnbayar);
            odRvDetail = itemView.findViewById(R.id.od_detailrecycler);
        }
    }

    private String formatRupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }
}
