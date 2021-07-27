package com.fathor.pondokkopi.API;

import com.fathor.pondokkopi.Model.Auth.AccountResponse;
import com.fathor.pondokkopi.Model.Auth.AccountUpdateAddress;
import com.fathor.pondokkopi.Model.Auth.AccountUpdateProfile;
import com.fathor.pondokkopi.Model.Auth.LoginRequest;
import com.fathor.pondokkopi.Model.Auth.LoginResponse;
import com.fathor.pondokkopi.Model.Auth.RegisterRequest;
import com.fathor.pondokkopi.Model.Auth.RegisterResponse;
import com.fathor.pondokkopi.Model.Orders.OrdersResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface APIRequestUser {

    @POST("api/login")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

    @POST("api/register")
    Call<RegisterResponse> userRegister(@Body RegisterRequest registerRequest);

    @POST("api/logout")
    Call<LoginResponse> userLogout(@Header("Authorization") String auth);

    @GET("api/account/{id}")
    Call<AccountResponse> getUser(@Path("id") int id, @Header("Authorization") String auth);

    @PUT("api/account/{id}/bio")
    Call<AccountResponse> updateBio(@Path("id") int id,
                                        @Header("Authorization") String auth,
                                        @Body AccountUpdateProfile bio);

    @Multipart
    @POST("api/account/{id}/photo")
    Call<AccountResponse> updatePhoto(@Path("id") int id,
                                          @Header("Authorization") String auth,
                                          @Part MultipartBody.Part image);

    @GET("api/provinsi")
    Call<AccountResponse> getProvinsi(@Header("Authorization") String auth);

    @GET("api/distrik/{id}")
    Call<AccountResponse> getDistrik(@Path("id") int id, @Header("Authorization") String auth);

    @PUT("api/account/{id}/address")
    Call<AccountResponse> updateAddress(@Path("id") int id,
                                    @Header("Authorization") String auth,
                                    @Body AccountUpdateAddress address);

}
