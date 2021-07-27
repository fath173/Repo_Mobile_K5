package com.fathor.pondokkopi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fathor.pondokkopi.API.APIRequestProducts;
import com.fathor.pondokkopi.API.APIRequestTestimonials;
import com.fathor.pondokkopi.API.APIUtils;
import com.fathor.pondokkopi.Model.Products.Products;
import com.fathor.pondokkopi.Model.Testimonials.TestimonialsData;
import com.fathor.pondokkopi.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestimonialsAdapter extends RecyclerView.Adapter<TestimonialsAdapter.TestiViewHolder> {

    Context context;
    APIRequestTestimonials apiRequestTestimonials;
    List<TestimonialsData> testiList;

    public TestimonialsAdapter(Context context, List<TestimonialsData> testiList){
        this.context = context;
        this.testiList = testiList;
    }

    @NonNull
    @Override
    public TestiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_item_testimonials, parent, false);
        apiRequestTestimonials = APIUtils.getReqTestimonials();
        return new TestimonialsAdapter.TestiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestiViewHolder holder, int position) {
        holder.testiNama.setText(testiList.get(position).getName()+" ");
        holder.testiKesan.setText(testiList.get(position).getKesan());

        String tglLama=testiList.get(position).getTgl_testi();
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        try {
            String tglBaru=dateFormat.format(df.parse(tglLama));
            holder.testiTanggal.setText(tglBaru+" ");
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        holder.testiTanggal.setText(testiList.get(position).getTgl_testi()+" ");
        String url_img = testiList.get(position).getImage();
        Picasso.get().load("https://wsjti.id/jembercoffecentre/public/files/img-users/"+url_img).into(holder.testiImg);
        
    }

    @Override
    public int getItemCount() {
        return testiList.size();
    }

    public class TestiViewHolder extends RecyclerView.ViewHolder {
        ImageView testiImg;
        TextView testiKesan, testiNama, testiTanggal;
        public TestiViewHolder(@NonNull View itemView) {
            super(itemView);
            testiImg = itemView.findViewById(R.id.testi_img);
            testiKesan = itemView.findViewById(R.id.testi_kesan);
            testiNama = itemView.findViewById(R.id.testi_namauser);
            testiTanggal = itemView.findViewById(R.id.testi_tanggal);
        }
    }
}
