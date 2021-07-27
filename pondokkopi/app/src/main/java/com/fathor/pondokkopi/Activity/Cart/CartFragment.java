package com.fathor.pondokkopi.Activity.Cart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.fathor.pondokkopi.Adapter.CartAdapter;
import com.fathor.pondokkopi.Model.Cart.CartData;
import com.fathor.pondokkopi.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class CartFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<CartData> cartList;
    private LinearLayoutManager linearLayoutManager;
    private CartAdapter adapter;
    private Button btnCheckout;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        btnCheckout = view.findViewById(R.id.cart_btncheckout);

        loadData();
        initRecyclerView(view);

        btnClick();
        return view;
    }
    public void btnClick(){
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
                if (!cartList.isEmpty()){
                    startActivity(new Intent(getActivity(), CheckoutActivity.class));
                }else{
                    Toast.makeText(getActivity(), "Tambahkan Produk Kedalam Keranjang!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void initRecyclerView(View view){
        recyclerView = view.findViewById(R.id.cart_recycler);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        adapter = new CartAdapter(getActivity(), cartList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Cart", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("CartList", null);
        Type type = new TypeToken<List<CartData>>(){}.getType();
        cartList = gson.fromJson(json,type);

        if (cartList == null){
            cartList = new ArrayList<>();
        }
    }

}