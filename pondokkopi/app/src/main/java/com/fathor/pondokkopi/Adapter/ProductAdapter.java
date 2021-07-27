package com.fathor.pondokkopi.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fathor.pondokkopi.API.APIRequestProducts;
import com.fathor.pondokkopi.API.APIUtils;
import com.fathor.pondokkopi.Activity.ProductDetails;
import com.fathor.pondokkopi.R;
import com.fathor.pondokkopi.Model.Products.Products;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    Context context;
    APIRequestProducts apiRequestProducts;
    List<Products> productsList;

    public ProductAdapter(@NonNull Context context, @NonNull List<Products> productsList) {
        this.context = context;
        this.productsList = productsList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_item_products, parent, false);
        apiRequestProducts = APIUtils.getReqProducts();
        return new ProductViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        holder.prodName.setText(productsList.get(position).getNama_produk());
        String url_img = productsList.get(position).getGambar_mobile();
        Picasso.get().load("https://wsjti.id/jembercoffecentre/public/files/product/"+url_img).into(holder.prodImg);

        holder.btDetailProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, ProductDetails.class);

                i.putExtra("id_product", productsList.get(position).getId());
                i.putExtra("nama_product", productsList.get(position).getNama_produk());
                i.putExtra("gambar_product", productsList.get(position).getGambar_mobile());
                i.putExtra("deskripsi_product", productsList.get(position).getDeskripsi());

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(holder.prodImg, "image");
//                pairs[0] = new Pair<View, String>(holder.prodBg1, "imagee");
                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity) context, pairs);
                context.startActivity(i, activityOptions.toBundle());
                // Hati hati diatasnya

            }
        });

    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public static final class ProductViewHolder extends RecyclerView.ViewHolder{

        ImageView prodImg, prodBg1;
        TextView prodName, prodVar, btDetailProduk;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            prodName = itemView.findViewById(R.id.prod_name);
            prodVar = itemView.findViewById(R.id.prod_var);
            prodImg = itemView.findViewById(R.id.prod_img);
            prodBg1 = itemView.findViewById(R.id.prod_bg1);
            btDetailProduk = itemView.findViewById(R.id.bt_detail_produk);
        }
    }

}
