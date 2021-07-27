package com.fathor.pondokkopi.Activity.Orders;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fathor.pondokkopi.API.APIRequestOrders;
import com.fathor.pondokkopi.API.APIUtils;
import com.fathor.pondokkopi.Activity.Auth.Preferences;
import com.fathor.pondokkopi.Adapter.OrdersAdapter;
import com.fathor.pondokkopi.Model.Orders.OrdersAll;
import com.fathor.pondokkopi.Model.Orders.OrdersRincian;
import com.fathor.pondokkopi.Model.Orders.OrdersResponse;
import com.fathor.pondokkopi.Model.Orders.ProductAll;
import com.fathor.pondokkopi.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersFragment extends Fragment {
    public APIRequestOrders apiRequestOrders;
    public RecyclerView recyclerView;
    public List<OrdersAll> ordersAll = new ArrayList<>();
    public List<ProductAll> productAlls = new ArrayList<>();
    private String token;

    public OrdersFragment() {
        // Required empty public constructor
    }

    public static OrdersFragment newInstance() {
        OrdersFragment fragment = new OrdersFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        apiRequestOrders = APIUtils.getReqOrders();
        String code = Preferences.getLoginToken(getActivity());
        token = "Bearer "+code;

        buildListData();
        initRecyclerView(view);
        return view;
    }

    private void initRecyclerView(View view){
        recyclerView = view.findViewById(R.id.recycler_orders);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public  void buildListData(){
        Integer id = Integer.parseInt(Preferences.getLoginId(getActivity()));
        Call<OrdersResponse> call = apiRequestOrders.getOrdersAll(id,token);
        call.enqueue(new Callback<OrdersResponse>() {
            @Override
            public void onResponse(Call<OrdersResponse> call, Response<OrdersResponse> response) {
                if(response.body() != null){
                    String message = response.body().getMessage();
                    ordersAll = response.body().getAllOrders();
                    productAlls = response.body().getAllproduct();
                    
                    OrdersAdapter adapter = new OrdersAdapter(getActivity(), ordersAll, productAlls);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

//                    Toast.makeText(getActivity(), "Pesan : "+ ordersDetails, Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getActivity(),"Gagal ambil orders", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<OrdersResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Gagal Konek server: " + t, Toast.LENGTH_LONG).show();
            }
        });
    }
}