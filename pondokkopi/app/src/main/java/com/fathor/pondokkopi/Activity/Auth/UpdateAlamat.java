package com.fathor.pondokkopi.Activity.Auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fathor.pondokkopi.API.APIRequestUser;
import com.fathor.pondokkopi.API.APIUtils;
import com.fathor.pondokkopi.Model.Auth.AccountResponse;
import com.fathor.pondokkopi.Model.Auth.AccountUpdateAddress;
import com.fathor.pondokkopi.Model.Auth.DataDistrik;
import com.fathor.pondokkopi.Model.Auth.DataProvinsi;
import com.fathor.pondokkopi.Model.Products.ProductVariasi;
import com.fathor.pondokkopi.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateAlamat extends AppCompatActivity {

    private APIRequestUser apiRequestUser;
    private ImageView btnBack, btnSave;
    private TextView tvProv, tvDist;
    private Integer kode_prov, kode_dist, id_user;
    private String token;
    private RadioGroup rGProvinsi, rGDistrik;
    private RadioButton rbProvinsi, rbDistrik;

    private List<DataProvinsi> provinsiList = new ArrayList<>();
    private List<DataDistrik> distrikList = new ArrayList<>();

    private Integer updt_kodeprov, updt_kodedist;
    private String detailAlamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatealamat);
        prepareData();

        getFindView();
        getProvinces();

        btnClick();

    }

    public void btnClick(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayout alamat = findViewById(R.id.al_detailalamat);
                detailAlamat = alamat.getEditText().getText().toString();
                if (updt_kodedist == null || updt_kodedist == null || detailAlamat == null){
                    Toast.makeText(getBaseContext(),"Lengkapi Data Alamat", Toast.LENGTH_SHORT).show();
                }else {
                    saveAddress();
                }

            }
        });

    }

    public void setRadioDistrik(){
        List<DataDistrik> dList = distrikList;
        rGDistrik = findViewById(R.id.al_radiodistrik);
        rGDistrik.removeAllViews();
        for(int i = 0; i<=dList.size()-1; i++){
            rbDistrik = new RadioButton(this);
            rbDistrik.setText(dList.get(i).getType()+" "+dList.get(i).getCity_name());
            rbDistrik.setId(Integer.parseInt(dList.get(i).getCity_id()));
            rGDistrik.addView(rbDistrik);
        }

        rGDistrik.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectId = rGDistrik.getCheckedRadioButtonId();
                for(int i = 0; i<=dList.size()-1; i++){
                    if (selectId == Integer.parseInt(dList.get(i).getCity_id())){
                        updt_kodedist = Integer.parseInt(dList.get(i).getCity_id());
                        String namaDist = dList.get(i).getType()+" "+dList.get(i).getCity_name();
                        tvDist.setText(namaDist);
                    }
                }
            }
        });
    }
    public void setRadioProvinsi(){
        List<DataProvinsi> pList = provinsiList;
        rGProvinsi = findViewById(R.id.al_radioprovinsi);
        for(int i = 0; i<=pList.size()-1; i++){
            rbProvinsi = new RadioButton(this);
            rbProvinsi.setText(pList.get(i).getProvince());
            rbProvinsi.setId(Integer.parseInt(pList.get(i).getProvince_id()));
            rGProvinsi.addView(rbProvinsi);
        }

        rGProvinsi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectId = rGProvinsi.getCheckedRadioButtonId();
                for(int i = 0; i<=pList.size()-1; i++){
                    if (selectId == Integer.parseInt(pList.get(i).getProvince_id())){
                        updt_kodeprov = Integer.parseInt(pList.get(i).getProvince_id());
                        String namaProv = pList.get(i).getProvince();
                        tvProv.setText(namaProv);

                        getDistrict(updt_kodeprov);
                    }
                }
            }
        });
    }

    public void getFindView(){
        btnBack = findViewById(R.id.al_alback);
        btnSave = findViewById(R.id.al_btnsave);
        tvProv = findViewById(R.id.al_tvprovinsi);
        tvDist = findViewById(R.id.al_tvdistrik);
    }

    public void prepareData(){
        apiRequestUser = APIUtils.getReqUser();
        kode_prov = getIntent().getIntExtra("id_provinsi",0);
        kode_dist = getIntent().getIntExtra("id_distrik", 0);
        id_user = getIntent().getIntExtra("id_user", 0);
        String code = Preferences.getLoginToken(getBaseContext());
        token = "Bearer "+code;
    }

    public void getProvinces(){
        Call<AccountResponse> call = apiRequestUser.getProvinsi(token);
        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                if (response.isSuccessful()){
                    String msg = response.body().getMessage();
                    provinsiList = response.body().getProvinsi();
                    setRadioProvinsi();
//                    Toast.makeText(getBaseContext(),msg, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getBaseContext(),"Gagal ambil provinsi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                Toast.makeText(getBaseContext(),"Gagal Konek Server: "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getDistrict(int id){
        Call<AccountResponse> call = apiRequestUser.getDistrik(id,token);
        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                if (response.isSuccessful()){
                    String msg = response.body().getMessage();
                    distrikList = response.body().getDistrik();
                    setRadioDistrik();
//                    Toast.makeText(getBaseContext(),msg, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getBaseContext(),"Gagal ambil Distrik", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                Toast.makeText(getBaseContext(),"Gagal Konek Server: "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveAddress(){
        Integer id = Integer.parseInt(Preferences.getLoginId(getBaseContext()));
        AccountUpdateAddress alamat = new AccountUpdateAddress();
        alamat.setAddress(detailAlamat);
        alamat.setProvince(updt_kodeprov);
        alamat.setDistrict(updt_kodedist);

        Call<AccountResponse> call = apiRequestUser.updateAddress(id,token,alamat);
        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                String msg = response.body().getMessage();
                Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {

            }
        });
    }

}