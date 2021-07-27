package com.fathor.pondokkopi.Activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fathor.pondokkopi.API.APIRequestTestimonials;
import com.fathor.pondokkopi.API.APIUtils;
import com.fathor.pondokkopi.Adapter.TestimonialsAdapter;
import com.fathor.pondokkopi.Model.Testimonials.TestimonialsData;
import com.fathor.pondokkopi.Model.Testimonials.TestimonialsResponse;
import com.fathor.pondokkopi.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TestiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestiFragment extends Fragment {
    private APIRequestTestimonials apiRequestTestimonials;
    private RecyclerView recyclerView;
    private List<TestimonialsData> testiList = new ArrayList<>();

    public TestiFragment() {
        // Required empty public constructor
    }

    public static TestiFragment newInstance(String param1, String param2) {
        TestiFragment fragment = new TestiFragment();
        return fragment;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_testi, container, false);

        apiRequestTestimonials = APIUtils.getReqTestimonials();
        initRecyclerView(view);
        buildListData();

        return view;
    }
    
    private void initRecyclerView(View view){
        recyclerView = view.findViewById(R.id.recycler_testi);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(linearLayoutManager);


    }
    
    public void buildListData(){
        Call<TestimonialsResponse> call = apiRequestTestimonials.getTestimonials();
        call.enqueue(new Callback<TestimonialsResponse>() {
            @Override
            public void onResponse(Call<TestimonialsResponse> call, Response<TestimonialsResponse> response) {
                if(response.body() != null){
                    String message = response.body().getMessage();
                    testiList = response.body().getDataTesti();

                    TestimonialsAdapter adapter = new TestimonialsAdapter(getActivity(), testiList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

//                    Toast.makeText(getActivity(), "Pesan" + message, Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getActivity(), "Gagal ambil testi", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TestimonialsResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Gagal Konek server: " + t, Toast.LENGTH_LONG).show();
            }
        });
    }
}