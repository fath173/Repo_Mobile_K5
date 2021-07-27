package com.fathor.pondokkopi.Activity.Auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fathor.pondokkopi.API.APIRequestUser;
import com.fathor.pondokkopi.API.APIUtils;
import com.fathor.pondokkopi.Model.Auth.AccountData;
import com.fathor.pondokkopi.Model.Auth.AccountResponse;
import com.fathor.pondokkopi.Model.Auth.LoginResponse;
import com.fathor.pondokkopi.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment {


    public APIRequestUser apiRequestUser;
    private String id_user;
    private View fragmentView;
    private Button btnLogout;
    private TextView btnProfile, btnAlamat;
    private TextView tvNama, tvGender, tvAlamat, tvEmail, tvTelpon, editPhoto;
    private ImageView imgAkun;

    private List<AccountData> dataUser = new ArrayList<>(); // dari Response

    private String token;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       fragmentView = inflater.inflate(R.layout.fragment_account, container, false);
       apiRequestUser = APIUtils.getReqUser();

        String code = Preferences.getLoginToken(getActivity());
        token = "Bearer "+code;

       loadFind();
       loadPage(Integer.parseInt(id_user));
       btnClick();
       return fragmentView;
    }

    public void setView(){
        View fokus = null;
        List<AccountData> user = dataUser;
        String url_img = user.get(0).getImage();
        Picasso.get().load("https://wsjti.id/jembercoffecentre/public/files/img-users/"+url_img).into(imgAkun);
        tvNama.setText(user.get(0).getName());
        tvGender.setText(user.get(0).getGender());
        tvAlamat.setText(user.get(0).getAddress());
        tvEmail.setText(user.get(0).getEmail());
        tvTelpon.setText(user.get(0).getPhone_number());

    }

    public void loadPage(int id){

        Call<AccountResponse> call = apiRequestUser.getUser(id,token);
        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                if (response.isSuccessful()){
                    String message = response.body().getMessage();
                    dataUser = response.body().getUser();
                    setView(); // dipanggil di Call nya iniii ingettttt

//                    Toast.makeText(getActivity(),"Sukses: "+ message, Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getActivity(),"Gagal ambil user", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Gagal Konek server: " + t, Toast.LENGTH_LONG).show();
            }
        });

    }

    public void logout(String token){

        Call<LoginResponse> logoutCall = apiRequestUser.userLogout(token);
        logoutCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()){
                    String message = response.body().getMessage();
                    Preferences.clearLogoutUser(getActivity());
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                    Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getActivity(), "Logout Failed",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Gagal Konek server: "+t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public void btnClick(){

        editPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AccountData> user = dataUser;
                Intent i = new Intent(getActivity(), UpdateFoto.class);
                i.putExtra("photo", user.get(0).getImage());
                i.putExtra("id_user", user.get(0).getId());
                getActivity().startActivity(i);
            }
        });
        btnAlamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AccountData> user = dataUser;
                Intent i = new Intent(getActivity(), UpdateAlamat.class);
                i.putExtra("id_provinsi", user.get(0).getProvince());
                i.putExtra("id_distrik", user.get(0).getDistrict());
                i.putExtra("id_user", user.get(0).getId());
                getActivity().startActivity(i);
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AccountData> user = dataUser;
                Intent i = new Intent(getActivity(), UpdateBio.class);
                i.putExtra("name", user.get(0).getName());
                i.putExtra("gender", user.get(0).getGender());
                i.putExtra("birthday", user.get(0).getBirthday());
                i.putExtra("email", user.get(0).getEmail());
                i.putExtra("notelpon", user.get(0).getPhone_number());
                getActivity().startActivity(i);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String code = Preferences.getLoginToken(getActivity());
//                String token = "Bearer "+code;
                logout(token);
//                Toast.makeText(getActivity(),"Tokennya: "+hasil, Toast.LENGTH_LONG).show();

            }
        });
    }


    public void loadFind(){
        id_user = Preferences.getLoginId(getActivity());

        btnLogout = fragmentView.findViewById(R.id.btn_logout);
        editPhoto = fragmentView.findViewById(R.id.akun_editphoto);
        btnProfile = fragmentView.findViewById(R.id.akun_btnbio);
        btnAlamat = fragmentView.findViewById(R.id.akun_btnalamat);

        tvNama = fragmentView.findViewById(R.id.tv_nama);
        tvGender = fragmentView.findViewById(R.id.tv_gender);
        tvAlamat = fragmentView.findViewById(R.id.tv_alamat);
        tvEmail = fragmentView.findViewById(R.id.tv_email);
        tvTelpon = fragmentView.findViewById(R.id.tv_telpon);

        imgAkun = fragmentView.findViewById(R.id.img_akun);

    }


}