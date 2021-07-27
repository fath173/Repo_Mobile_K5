package com.fathor.pondokkopi.Activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fathor.pondokkopi.API.APIRequestProducts;
import com.fathor.pondokkopi.API.APIUtils;
//import com.fathor.pondokkopi.API.RetroServer;
import com.fathor.pondokkopi.Adapter.ProductAdapter;
import com.fathor.pondokkopi.Model.Products.Products;
//import com.fathor.pondokkopi.Model.ResponseProducts;
import com.fathor.pondokkopi.Model.Products.ProductResponse;
import com.fathor.pondokkopi.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    public  APIRequestProducts apiRequestProducts;
    private List<Products> productsList = new ArrayList<>();
    public  RecyclerView recyclerView;
    private View view;
    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view = inflater.inflate(R.layout.fragment_home, container, false);

       apiRequestProducts = APIUtils.getReqProducts();
       initRecyclerView(view);
       buildListData();
       return view;
    }
    private void initRecyclerView(View view){
        recyclerView = view.findViewById(R.id.product_recycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    public void buildListData(){
        Call<ProductResponse> call = apiRequestProducts.getProducts();
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if(response.body() != null){
                    String message = response.body().getMessage();
                    productsList = response.body().getData();

                    ProductAdapter adapter = new ProductAdapter(getActivity(), productsList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }else{
                    Toast.makeText(getActivity(), "Gagal ambil produk", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Gagal Konek server: " + t, Toast.LENGTH_LONG).show();
            }
        });
    }

}